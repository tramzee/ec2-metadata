(ns io.hivewing.ec2-metadata
  (require [clojure.data.json :as json]
           [clojure.java.io :as io ]))

(defn- category-data-stream [cat]
  (let [target (str "http://169.254.169.254/latest/meta-data/" cat)]
    (io/reader target)))

(defn- key-fn [k]
  (->> (clojure.string/split k #"(?<=[a-z])(?=[A-Z])")
       (clojure.string/join \-)
       clojure.string/lower-case
       keyword))

(defn metadata
  "Return a map of category data from the instance"
  [category]
  (try
    (with-open [j (category-data-stream category)]
      (let [r (slurp j)]
        (if (first r \{)
          (json/read-str r :key-fn key-fn)
          r)))
    (catch Exception e)))
