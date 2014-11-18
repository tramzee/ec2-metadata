(ns io.hivewing.ec2-metadata
  (require [clojure.data.json :as json]
           [clojure.java.shell :refer [sh]]
           [environ.core :refer [env]]
           [clojure.java.io :as io]))

(def ^:private remote-host (env :remote-host))
(def ^:private stubbing (atom (env :stub-ec2-metadata)))

(defn stub
  ([] @stubbing)
  ([val] (reset! stubbing val)))

(defn- category-data-stream [cat]
  (let [target (str "http://169.254.169.254/latest/meta-data/" cat)
        target (if (not (nil? remote-host))
                 (let [cmd (format "ssh ubuntu@%s curl -s %s" remote-host target)
                       args (clojure.string/split cmd #"\s+")

                       out (:out (apply sh args))]
                   (char-array out))
                 target)]
    (io/reader target)))

(defn- key-fn [k]
  (->> (clojure.string/split k #"(?<=[a-z])(?=[A-Z])")
       (clojure.string/join \-)
       clojure.string/lower-case
       keyword))

(defn metadata
  "Return a map (or string) of category data from the instance"
  [category]
  (if (stub)
    "stub"
    (try
      (with-open [j (category-data-stream category)]
        (let [r (slurp j)]
          (cond (= (first r) \{)
                (json/read-str r :key-fn key-fn)
                (= (first r) \<) nil
                :else r)))
      (catch Exception e))))
