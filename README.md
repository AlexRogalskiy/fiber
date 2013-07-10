Fiber
=====
A java serialization engine. Fast, compact &amp; thread safe serialization.

Started this project out of frustration Kryo is not thread safe. For a comparison on speed and size, see "performance".
Version 3.0 is already pretty fast and compact.

If you want to use this library, keep the following in mind:
* Java 1.6 or newer
* all classes should be registered upfront.
* if a class doesn't have a default constructor (or for effeciency) you might want to write (and register!) your own serializer. Take a look at the provided ones.
* Fiber should be configured in exactly the same way at the writing side as at the reading side. I.e. Classes and their serializers should have been registered in exactly the same order.
* Fiber allows no collections, strings, maps, etc. bigger than ```Short.MAX_VALLUE```

Performance
===========

Perfomance was measured with modified version of [jvm-serializers](https://github.com/eishay/jvm-serializers/wiki).

```console
./run -chart -trials=500 -include=`cat fiber.txt | tr "\\n" ","`  data/media.1.cks
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/Users/steven/projects/others/jvm-serializers/tpc/lib/avro-tools-1.7.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/steven/projects/others/jvm-serializers/tpc/lib/slf4j-nop-1.5.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
Checking correctness...
[done]
Pre-warmup... java-built-in kryo kryo-manual fiber fiber-manual

pre.                              create     ser   deser   total   size  +dfl
java-built-in                          64    5829   33015   38844    889   514
kryo                                   64     780     881    1661    214   134
kryo-manual                            64     555     695    1250    211   131
fiber                                  64    4405    1746    6151    246   170
fiber-manual                           65    2840    1058    3898    236   164
```
<img src='https://chart.googleapis.com/chart?chtt=total+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:1250,1660,3898,6150,38843&chds=0,42727.850000000006&chxt=y&chxl=0:|java-built-in|fiber|fiber-manual|kryo|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=ser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:555,779,2840,4405,5828&chds=0,6411.35&chxt=y&chxl=0:|java-built-in|fiber|fiber-manual|kryo|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=deser+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:695,881,1058,1745,33015&chds=0,36316.5&chxt=y&chxl=0:|java-built-in|fiber|fiber-manual|kryo|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=size+%28bytes%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:211,214,236,246,889&chds=0,977.9000000000001&chxt=y&chxl=0:|java-built-in|fiber|fiber-manual|kryo|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=size%2Bdfl+%28bytes%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:131,134,164,170,514&chds=0,565.4000000000001&chxt=y&chxl=0:|java-built-in|fiber|fiber-manual|kryo|kryo-manual&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>
<img src='https://chart.googleapis.com/chart?chtt=create+%28nanos%29&chf=c||lg||0||FFFFFF||1||76A4FB||0|bg||s||EFEFEF&chs=500x130&chd=t:64,64,64,64,64&chds=0,70.983&chxt=y&chxl=0:|fiber-manual|kryo|java-built-in|kryo-manual|fiber&chm=N *f*,000000,0,-1,10&lklk&chdlp=t&chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|663366|663399|6633CC|6633FF|666600|666633|666666&cht=bhg&chbh=10,0,10&nonsense=aaa.png'/>

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
