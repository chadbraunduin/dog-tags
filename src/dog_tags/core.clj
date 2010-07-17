(ns
    #^{:author "Chad Braun-Duin"
       :doc "creates an HTML tag list or tag cloud from a sequence of hash-maps"}
  dog-tags.core
  (:use
   [hiccup.core :only (html)]))

;; assumes RESTful architecture
(def *tag-root* "/tags/")

(defn- rank-tags
  "Creates a hash-map with the tag name as the key
and the count of occurrences as the value.
The input should be a collection of hash-maps with :tags as the key
and a vector of tags (as strings) as the value.
Tags are case-insensitive.
"
  ([tag-list] (rank-tags tag-list {}))
  ([tag-list rank]
     (if (empty? tag-list)
       rank
       (let [first-item (first tag-list)
             tags (map #(.toLowerCase %) (:tags first-item))
             new-rank (apply hash-map
                             (interleave
                              tags
                              (map (fn [tag] (count (filter #{tag} tags))) tags)))]
         (recur (rest tag-list) (merge-with + rank new-rank))))))

(defn create-tag-list
  "Creates an html unordered list of tag links with their occurrences.
In addition to the list of tag hash-maps, you can pass in some options.
These are:
  :sort :values | nil
  :limit N 
"
  ([tag-list] (create-tag-list tag-list {}))
  ([tag-list options]
     (let [rank (rank-tags tag-list)
           sort-fn (if (= (:sort options) :values)
                     ;;descending sort by values; important values on top
                     (fn [k1 k2] (>= (rank k1) (rank k2)))
                     (fn [k1 k2] (compare k1 k2)))
           limit (:limit options)
           mapped-items (map #(vector
                               :li
                               [:a {:href (str *tag-root* (key %))} (key %)]
                               (str "&nbsp;(" (val %) ")")
                               )
                             (into (sorted-map-by sort-fn) rank))
           list-items (if (nil? limit)
                        mapped-items
                        (take limit mapped-items))]
       (html
        [:ul
         list-items
         ]))))

