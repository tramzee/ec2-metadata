(ns io.hivewing.ec2-metadata-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io ]
            [clojure.pprint :refer [pprint]]
            [clojure.java.shell :refer [sh]]
            [io.hivewing.ec2-metadata :refer :all]))

(deftest stub-test
  (testing "metadata"
    (let [ostub (stub)]
      (try
        (stub true)
        (is (instance? String (metadata "ami-id")))
        (finally (stub ostub))))))

(deftest parse-test
  (testing "metadata"
    (with-redefs [io.hivewing.ec2-metadata/category-data-stream (fn [_]
                                                                  (-> "t.dat" io/resource io/reader))]
      (let [v (metadata "iam/info")]
        (and (is (instance? clojure.lang.PersistentArrayMap v))
             (is (some #{:code} (keys v))))))))

(deftest remote-test
  (testing "metadata"
    (pprint (metadata "iam/info"))))

;; (deftest ssh-test
;;   (testing "metadata"
;;     (with-redefs [io.hivewing.ec2-metadata/category-data-stream (fn [arg]
;;                                                                   (let [target (str "http://169.254.169.254/latest/meta-data/"
;;                                                                                     cat)]
;;                                                                     (io/reader target))
;;                                                                     (-> "t.dat" io/resource io/reader))]
;;       (let [v (metadata "iam/info")]
;;         (and (is (instance? clojure.lang.PersistentArrayMap v))
;;              (is (some #{:code} (keys v))))))))
