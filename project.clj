(defproject io.hivewing/ec2-metadata "0.2.0-SNAPSHOT"
  :description "A Simple lib for accessing metadata on AWS EC2"
  :url "http://github.com/tramzee/ec2-metadata.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot :all
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "1.0.0"]
                 [org.clojure/data.json "0.2.5"]]

  :plugins [[lein-environ "1.0.0"]]

  )
