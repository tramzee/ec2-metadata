# ec2-metadata

ec2-metadata is a simple library to allow reading of EC2 [metadata] (http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-instance-metadata.html) when running on an EC2 instance.

## Installation

###Leiningen Coordinates
```clj
[io.hivewing/ec2-metadata "0.1.0"]
```

## Usage

###Snippet
```clj
(ns com.example
  (require [io.hivewing.ec2-metadata :refer [metadata]]))

(defn meta [category]
  (prn (metadata category)))
```

(See the AWS [docs] (http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-instance-metadata.html#instancedata-data-categories) for more information about what categtories are available.)

## License

Copyright (C) 2014 Tim Ramsey

Distributed under the Eclipse Public License, the same as Clojure.
