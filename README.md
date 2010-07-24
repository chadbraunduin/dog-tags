# dog-tags

## What?
A project for creating html tag lists and tag clouds for use in your
blogging application written in Clojure.

## Summary
dog-tags is essentially two functions.

* create-tag-list
* create-tag-cloud

Each takes the same data as input but outputs different html.

## Usage
### Creating a tag list
basic usage is as follows. Pass a sequence of maps to create-tag-list. This will return
an html unordered list of tag links with their occurrences.

    (create-tag-list [
                  {:tags ["programming" "clojure"]}
                  {:tags ["Programming" "c" "rant" "programming"]}
                  {:tags ["politics" "rant"]}
                  {:tags ["programming" "python"]}
                  ])

    => 
    <ul>
     <li><a href="/tags/c">c</a>&nbsp;(1)</li>
     <li><a href="/tags/clojure">clojure</a>&nbsp;(1)</li>
     <li><a href="/tags/politics">politics</a>&nbsp;(1)</li>
     <li><a href="/tags/programming">programming</a>&nbsp;(4)</li>
     <li><a href="/tags/python">python</a>&nbsp;(1)</li>
     <li><a href="/tags/rant">rant</a>&nbsp;(2)</li>
    </ul>

You may also pass in options to sort by values (higher values at the top) and to limit the
results.

    (create-tag-list [
                  {:tags ["programming" "clojure"]}
                  {:tags ["Programming" "c" "rant" "programming"]}
                  {:tags ["politics" "rant"]}
                  {:tags ["programming" "python"]}
                  ]
                 {:sort :values :limit 3})

    => 
    <ul>
     <li><a href="/tags/programming">programming</a>&nbsp;(4)</li>
     <li><a href="/tags/rant">rant</a>&nbsp;(2)</li>
     <li><a href="/tags/clojure">clojure</a>&nbsp;(1)</li>
    </ul>

### Creating a tag cloud
basic usage is as follows. Pass a sequence of maps to create-tag-cloud. This will return
an html sequence of tag links with their size adjusted according to
their frequency of occurrence.

    (create-tag-cloud [
                    {:tags ["programming" "clojure"]}
                    {:tags ["Programming" "c" "rant" "programming"]}
                    {:tags ["politics" "rant"]}
                    {:tags ["programming" "python"]}
                    ])

    =>
    <a href="/tags/c" style="font-size: 100%;">c</a> 
    <a href="/tags/clojure" style="font-size: 100%;">clojure</a> 
    <a href="/tags/politics" style="font-size: 100%;">politics</a> 
    <a href="/tags/programming" style="font-size: 150%;">programming</a> 
    <a href="/tags/python" style="font-size: 100%;">python</a> 
    <a href="/tags/rant" style="font-size: 117%;">rant</a> 

the default sizes for cloud links range from 100% to 150%. This can be
changed by binding

    *min-percent* or *max-percent*

For example, this will change the percent range to 100% to 200%:

    (binding [*max-percent* 200]
     (create-tag-cloud [
                    {:tags ["programming" "clojure"]}
                    {:tags ["Programming" "c" "rant" "programming"]}
                    {:tags ["politics" "rant"]}
                    {:tags ["programming" "python"]}
                    ]))

### Returning the results in a different format
By default the results are returned in html. However, I am using the
hiccup project to turn the results into html. If you are also using
hiccup in your project and would like to receive the results in the
hiccup format, use the :hiccup option in either function. For example,

    (create-tag-cloud [
                    {:tags ["programming" "clojure"]}
                    {:tags ["Programming" "c" "rant" "programming"]}
                    {:tags ["politics" "rant"]}
                    {:tags ["programming" "python"]}
                    ]
    {:format :hiccup}))

    =>
    (
     ([:a {:href /tags/c, :style font-size: 100%;} c]  ) 
     ([:a {:href /tags/clojure, :style font-size: 100%;} clojure]  ) 
     ([:a {:href /tags/politics, :style font-size: 100%;} politics]  ) 
     ([:a {:href /tags/programming, :style font-size: 150%;} programming]  ) 
     ([:a {:href /tags/python, :style font-size: 100%;} python]  ) 
     ([:a {:href /tags/rant, :style font-size: 117%;} rant]  )
    )

### Changing the tag-root

You can change the tag-root (e.g. the value "/tags/") to something
else by binding *tag-root* to another value

    (binding [*tag-root* "/labels/" ]
     (create-tag-list [{:tags [ "chad" "bob" ] } {:tags [ "chad" "sammy" ] }]))

    =>
    <ul>
     <li><a href="/labels/bob">bob</a>&nbsp;(1)</li>
     <li><a href="/labels/chad">chad</a>&nbsp;(2)</li>
     <li><a href="/labels/sammy">sammy</a>&nbsp;(1)</li>
    </ul>

## Installation

Leiningen is the recommended way to use dog-tags. Just add

    [dog-tags "1.1.0"]

to your project.clj and run

    $lein deps

to get dog-task into your application
