Fiber
=====
A java serialization engine. Fast, compact &amp; thread safe serialization.

Started this project out of frustration Kryo is not thread safe. For a comparison on speed and size, see «[performance](#performance)».
Version 0.3 is already pretty fast and compact.

If you want to use this library, keep the following in mind:
* Java 1.6 or newer
* all classes should be registered upfront.
* if a class doesn't have a default constructor (or for effeciency) you might want to write (and register!) your own serializer. Take a look at the provided ones.
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
<img src='https://chart.googleapis.com/chart?chtt=total+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:1256,1401,1593,1628,1669,1678,1741,2144,2422,2738,2906,3278,3420,3426,3673,4148,4236,5727,6125,6444,6711,8028,8321,9180,10764,11076,15525,15762,15828,16220,16732,17214,18934,19010,19339,40179,40905,48427,52325,55064,88111,122219&chds=0,134441.45&chxt=y&chxl=0:|json%2Fjson-lib-databind|json%2Fargo-manual%2Ftree|jboss-marshalling-serial|json%2Fflexjson%2Fdatabind|json%2Fjsonij-jpath|jboss-marshalling-river|java-built-in|xml%2Fxstream%2Bc|xml%2Fxstream%2Bc-woodstox|xml%2Fxstream%2Bc-fastinfo|json%2Fsvenson-databind|xml%2Fxstream%2Bc-aalto|json%2Forg.json%2Fmanual%2Ftree|xml%2Fjavolution|json%2Fjson.simple%2Fmanual|jboss-serialization|bson%2Fmongodb|hessian|json%2Fjson-smart%2Fmanual%2Ftree|protostuff-runtime|jboss-marshalling-river-manual|protobuf%2Fprotostuff|fiber|jboss-marshalling-river-ct|protostuff-manual|json%2Fprotostuff-runtime|fiber-manual|json%2Fprotostuff-manual|avro-generic|avro|jboss-marshalling-river-ct-manual|thrift|thrift-compact|msgpack|protobuf|kryo-opt|wobly-compact|kryo|java-manual|wobly|protostuff|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=ser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:561,587,769,830,935,958,979,1034,1389,1567,1676,1829,1887,1960,1968,2137,2459,2495,2959,3007,3587,3606,3667,4221,4619,4842,5068,5273,5491,5774,6013,6081,6154,7034,7229,8140,9546,13407,24196,27759,36053,71985&chds=0,79184.05&chxt=y&chxl=0:|json%2Fargo-manual%2Ftree|json%2Fjsonij-jpath|json%2Fjson-lib-databind|json%2Fflexjson%2Fdatabind|jboss-marshalling-serial|xml%2Fxstream%2Bc-fastinfo|jboss-serialization|json%2Forg.json%2Fmanual%2Ftree|json%2Fjson.simple%2Fmanual|xml%2Fxstream%2Bc-woodstox|xml%2Fjavolution|xml%2Fxstream%2Bc|java-built-in|json%2Fjson-smart%2Fmanual%2Ftree|jboss-marshalling-river|xml%2Fxstream%2Bc-aalto|json%2Fsvenson-databind|fiber|hessian|bson%2Fmongodb|protostuff-runtime|jboss-marshalling-river-ct|fiber-manual|protobuf%2Fprotostuff|jboss-marshalling-river-manual|protostuff-manual|avro-generic|thrift|jboss-marshalling-river-ct-manual|json%2Fprotostuff-runtime|avro|thrift-compact|json%2Fprotostuff-manual|protobuf|wobly-compact|wobly|msgpack|java-manual|kryo-opt|kryo|protostuff|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=deser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:614,644,693,695,755,814,900,911,938,1062,1141,1288,1317,1464,1591,1825,2105,2349,2538,3268,3689,3751,4715,5533,6543,7385,7409,8728,8991,9387,9747,11664,12372,12374,12856,13326,16125,28129,34405,35632,41657,94460&chds=0,103906.00000000001&chxt=y&chxl=0:|json%2Fjson-lib-databind|jboss-marshalling-serial|jboss-marshalling-river|java-built-in|json%2Fflexjson%2Fdatabind|json%2Fargo-manual%2Ftree|xml%2Fxstream%2Bc|xml%2Fxstream%2Bc-woodstox|json%2Fjsonij-jpath|json%2Fsvenson-databind|xml%2Fxstream%2Bc-aalto|xml%2Fjavolution|xml%2Fxstream%2Bc-fastinfo|json%2Forg.json%2Fmanual%2Ftree|json%2Fjson.simple%2Fmanual|bson%2Fmongodb|jboss-serialization|hessian|jboss-marshalling-river-manual|protostuff-runtime|protobuf%2Fprotostuff|json%2Fjson-smart%2Fmanual%2Ftree|protostuff-manual|jboss-marshalling-river-ct|json%2Fprotostuff-runtime|json%2Fprotostuff-manual|fiber|avro|msgpack|jboss-marshalling-river-ct-manual|avro-generic|fiber-manual|thrift-compact|thrift|kryo-opt|kryo|protostuff|protobuf|kryo-manual|java-manual|wobly-compact|wobly&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=size+%28bytes%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:211,211,214,221,221,225,233,236,239,239,239,239,240,241,246,251,255,289,298,345,349,449,469,478,483,485,485,485,487,495,495,495,495,501,503,504,525,525,694,856,889,932&chds=0,1025.2&chxt=y&chxl=0:|jboss-serialization|java-built-in|jboss-marshalling-serial|jboss-marshalling-river|xml%2Fxstream%2Bc-woodstox|xml%2Fxstream%2Bc-aalto|xml%2Fjavolution|json%2Fflexjson%2Fdatabind|hessian|json%2Fjson.simple%2Fmanual|json%2Fjson-smart%2Fmanual%2Ftree|bson%2Fmongodb|json%2Fsvenson-databind|xml%2Fxstream%2Bc|json%2Fargo-manual%2Ftree|json%2Fjson-lib-databind|json%2Forg.json%2Fmanual%2Ftree|jboss-marshalling-river-manual|json%2Fjsonij-jpath|json%2Fprotostuff-runtime|json%2Fprotostuff-manual|thrift|xml%2Fxstream%2Bc-fastinfo|jboss-marshalling-river-ct|jboss-marshalling-river-ct-manual|java-manual|wobly|fiber|protostuff-runtime|thrift-compact|protostuff-manual|protostuff|protobuf|protobuf%2Fprotostuff|fiber-manual|msgpack|wobly-compact|avro-generic|avro|kryo|kryo-manual|kryo-opt&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=size%2Bdfl+%28bytes%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:131,131,133,133,134,139,146,147,148,149,149,150,150,151,151,164,167,170,197,199,233,240,243,244,255,259,263,263,263,264,265,269,269,273,273,273,278,313,400,498,514,582&chds=0,640.2&chxt=y&chxl=0:|jboss-serialization|java-built-in|jboss-marshalling-serial|jboss-marshalling-river|hessian|bson%2Fmongodb|json%2Fflexjson%2Fdatabind|xml%2Fxstream%2Bc-woodstox|xml%2Fxstream%2Bc-aalto|json%2Fjson.simple%2Fmanual|json%2Fjson-smart%2Fmanual%2Ftree|json%2Fsvenson-databind|xml%2Fxstream%2Bc-fastinfo|json%2Fargo-manual%2Ftree|json%2Fjson-lib-databind|xml%2Fjavolution|json%2Forg.json%2Fmanual%2Ftree|json%2Fjsonij-jpath|xml%2Fxstream%2Bc|json%2Fprotostuff-runtime|jboss-marshalling-river-manual|json%2Fprotostuff-manual|jboss-marshalling-river-ct|thrift|fiber|jboss-marshalling-river-ct-manual|fiber-manual|wobly|protostuff-runtime|protostuff-manual|protostuff|protobuf|protobuf%2Fprotostuff|thrift-compact|java-manual|msgpack|wobly-compact|kryo|avro-generic|avro|kryo-manual|kryo-opt&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=create+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x576&chd=t:43,44,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,64,65,65,65,65,65,65,65,65,65,66,91,99,110,116,286,286,370,397,433&chds=0,477.05350000000004&chxt=y&chxl=0:|avro-generic|protobuf%2Fprotostuff|thrift|protostuff-manual|protostuff-runtime|thrift-compact|protobuf|avro|protostuff|json%2Fjson-smart%2Fmanual%2Ftree|json%2Fsvenson-databind|json%2Fflexjson%2Fdatabind|jboss-marshalling-serial|bson%2Fmongodb|json%2Forg.json%2Fmanual%2Ftree|xml%2Fjavolution|xml%2Fxstream%2Bc-aalto|fiber-manual|jboss-marshalling-river-ct|json%2Fjson.simple%2Fmanual|fiber|json%2Fprotostuff-manual|json%2Fjsonij-jpath|xml%2Fxstream%2Bc-woodstox|json%2Fargo-manual%2Ftree|xml%2Fxstream%2Bc-fastinfo|jboss-serialization|xml%2Fxstream%2Bc|jboss-marshalling-river-manual|jboss-marshalling-river-ct-manual|kryo|json%2Fjson-lib-databind|java-manual|msgpack|kryo-opt|kryo-manual|jboss-marshalling-river|java-built-in|hessian|json%2Fprotostuff-runtime|wobly|wobly-compact&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=7,0,6&nonsense=aaa.png'/>

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
