Fiber
=====
A java serialization engine. Fast, compact &amp; thread safe serialization.

Started this project out of frustration Kryo is not thread safe. Is not as fast or compact as Kryo yet, but it's already doing a pretty good job for being only a few days old.

If you want to use this library, keep the following in mind:
* Java 1.6 or newer
* all classes should be registered upfront.
* if a class doesn't have a default constructor (or for effeciency) you might want to write (and register!) your own serializer. Take a look at the provided ones.
* Fiber should be configured in exactly the same way at the writing side as at the reading side. I.e. Classes and their serializers should have been registered in exactly the same order.

If someone would like to test it using https://github.com/eishay/jvm-serializers/wiki, be my guest :)

More info to come.. but in the mean time, start forking!

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
