Fiber
=====
A java serialization engine. Fast, compact &amp; thread safe serialization.

Started this project out of frustration Kryo is not thread safe. For a comparison on speed and size, see «[performance](#performance)».
Version 0.3 is already pretty fast and compact.

If you want to use this library, keep the following in mind:
* Java 1.6 or newer
* all classes should be registered upfront.
* if a class doesn't have a default constructor you want to write (and register!) your own serializer. Take a look at the provided ones.
* Fiber should be configured in exactly the same way at the writing side as at the reading side. I.e. Classes and their serializers should have been registered in exactly the same order.
* Fiber allows no collections, strings, maps, etc. bigger than ```Short.MAX_VALLUE```

Performance
===========

Perfomance was measured with a modified version of [jvm-serializers](https://github.com/eishay/jvm-serializers/wiki).

```console
./run -chart -trials=500 -include=`cat serializers.txt | tr "\\n" ","`  data/media.1.cks
Checking correctness...
[done]
Pre-warmup... java-built-in java-manual hessian kryo kryo-opt kryo-manual fiber fiber-manual wobly wobly-compact jboss-serialization jboss-marshalling-river jboss-marshalling-river-manual jboss-marshalling-river-ct jboss-marshalling-river-ct-manual jboss-marshalling-serial protobuf protostuff protostuff-manual protostuff-runtime protobuf/protostuff thrift thrift-compact avro avro-generic msgpack json/protostuff-manual json/protostuff-runtime json/svenson-databind json/flexjson/databind json/json-lib-databind json/json.simple/manual json/json-smart/manual/tree json/org.json/manual/tree json/jsonij-jpath json/argo-manual/tree bson/mongodb xml/xstream+c xml/xstream+c-woodstox xml/xstream+c-aalto xml/xstream+c-fastinfo xml/javolution
[done]

pre.                              create     ser   deser   total   size  +dfl
java-built-in                          65    5775   34405   40180    889   514
java-manual                            65     936     693    1629    255   147
hessian                                65    4221    6544   10765    501   313
kryo                                   65     769     901    1670    214   134
kryo-opt                               65     830     911    1741    211   131
kryo-manual                            65     561     696    1257    211   131
fiber                                  65    4619    1825    6444    246   170
fiber-manual                           65    3007    1141    4148    236   164
wobly                                  44     979     615    1594    251   151
wobly-compact                          43    1034     644    1678    225   139
jboss-serialization                    65    8140    7386   15526    932   582
jboss-marshalling-river                65    5273   35632   40905    694   400
jboss-marshalling-river-manual         65    2495    5534    8029    483   240
jboss-marshalling-river-ct             65    3588    2538    6126    298   199
jboss-marshalling-river-ct-manual      65    1961    1318    3278    289   167
jboss-marshalling-serial               65   13408   41657   55065    856   498
protobuf                              111    1390     755    2145    239   149
protostuff                             91     588     814    1402    239   150
protostuff-manual                     286    2459    3268    5727    239   150
protostuff-runtime                    286    3606    4716    8322    241   151
protobuf/protostuff                   397    2960    3752    6711    239   149
thrift                                370    1968     939    2907    349   197
thrift-compact                        116    1676    1062    2738    240   148
avro                                  100    1830    1591    3421    221   133
avro-generic                          434    2138    1289    3426    221   133
msgpack                                65     958    1465    2423    233   146
json/protostuff-manual                 65    1568    2106    3673    449   233
json/protostuff-runtime                64    1887    2350    4237    469   243
json/svenson-databind                  66    4843   12372   17215    495   265
json/flexjson/databind                 65   24196   28129   52325    503   273
json/json-lib-databind                 65   27760   94460  122220    485   263
json/json.simple/manual                65    7034    8728   15762    495   269
json/json-smart/manual/tree            67    5491    3689    9180    495   269
json/org.json/manual/tree              65    7230    8991   16221    485   259
json/jsonij-jpath                      65   36054   12374   48428    478   255
json/argo-manual/tree                  65   71986   16126   88111    485   263
bson/mongodb                           65    3667    7409   11076    495   278
xml/xstream+c                          65    6014   13326   19340    487   244
xml/xstream+c-woodstox                 65    6154   12857   19011    525   273
xml/xstream+c-aalto                    65    5068   11665   16733    525   273
xml/xstream+c-fastinfo                 65    9547    9388   18934    345   264
xml/javolution                         65    6081    9747   15828    504   263


```
<img src="http://bit.ly/1t1xJYr"/>
<img src="http://bit.ly/1kTrkxh"/>
<img src="http://bit.ly/1n71aXx"/>
<img src="http://bit.ly/1kpRDrY"/>
<img src="http://bit.ly/1eBzQ1f"/>
<img src="http://bit.ly/1qX27PT"/>

License
=======
The MIT License (MIT)

Copyright (c) 2013 Steven Willems

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
