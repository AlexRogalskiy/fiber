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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
            REFERENCE_RESOLVER.clearSerialize();
        }
    }

    public InputStream serializeToStream(Object o) {
        ByteBuffer buffer = serialize(o);
        return new ByteBufferInputStream(buffer);
    }

    public byte[] serializeToBytes(Object o) {
        ByteBuffer byteBuffer = serialize(o);
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes, 0, bytes.length);
        return bytes;
    }

    public <T> T deserialize(ByteBuffer byteBuffer) {
        try {
            return new Input(config, byteBuffer).read();
        } finally {
            byteBuffer.reset();
            REFERENCE_RESOLVER.clearDeserialize();
        }
    }

    public <T> T deserializeFromStream(InputStream stream) {
        ByteBuffer byteBuffer = createByteBufferFromInputStream(stream);
        return deserialize(byteBuffer);
    }

    public <T> T deserializeFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return deserialize(byteBuffer);
    }

    private ByteBuffer createByteBufferFromInputStream(InputStream stream) {
        int available;
        try {
            available = stream.available();
        } catch (IOException e) {
            throw new IllegalStateException("Could not create byte buffer for input stream", e);
        }
        ReadableByteChannel channel = Channels.newChannel(stream);
        ByteBuffer byteBuffer = ByteBuffer.allocate(available);
        try {
            channel.read(byteBuffer);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read input stream into byte buffer", e);
        }
        return (ByteBuffer) byteBuffer.flip().mark();
    }

    public void register(Class clazz) {
        this.config.register(clazz);
    }

    public void register(Serializer serializer) {
        this.config.register(serializer);
    }
}
