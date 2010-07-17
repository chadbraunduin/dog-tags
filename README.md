# dog-tags

## What?
A project for creating html tag lists and tag clouds for use in your blogging application

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

You may also pass in options to sort by value (higher values at the top) and to limit the
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
This functionality has not been created, yet.

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

    [dog-tags "1.0.0-SNAPSHOT"]

to your project.clj and run

    $lein deps

to get dog-task into your application

## TODO

* Create the tag cloud functionality.
* Add tests.
