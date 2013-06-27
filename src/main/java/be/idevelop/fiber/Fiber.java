/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Steven Willems
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package be.idevelop.fiber;

import java.nio.ByteBuffer;

import static be.idevelop.fiber.ReferenceResolver.REFERENCE_RESOLVER;

public final class Fiber {

    private final SerializerConfig config;

    public Fiber() {
        this.config = new SerializerConfig();
    }

    public ByteBuffer serialize(Object o) {
        try {
            Output output = new Output(config);
            output.write(o);
            return output.getByteBuffer();
        } finally {
            REFERENCE_RESOLVER.clear();
        }
    }

    public <T> T deserialize(ByteBuffer byteBuffer) {
        try {
            Input input = new Input(config, byteBuffer);
            return input.read();
        } finally {
            REFERENCE_RESOLVER.clear();
        }
    }

    public void register(Class clazz) {
        this.config.register(clazz);
    }

    public void register(Serializer serializer) {
        this.config.register(serializer);
    }
}
