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
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import static be.idevelop.fiber.ReferenceResolver.REFERENCE_RESOLVER;

final class Output {

    private static final int DEFAULT_SIZE = 1024 * 1024;

    private final SerializerConfig config;

    private final ThreadLocal<CharsetEncoder> charsetEncoder = new ThreadLocal<CharsetEncoder>();

    private ByteBuffer byteBuffer;

    Output(SerializerConfig config) {
        this(config, DEFAULT_SIZE);
    }

    public Output(SerializerConfig config, int bufferSize) {
        this.config = config;
        this.byteBuffer = ByteBuffer.allocateDirect(bufferSize);
    }

    public ByteBuffer getByteBuffer() {
        return (ByteBuffer) byteBuffer.flip();
    }

    public void write(Object o) {
        Serializer<? super Object> serializer = this.config.getSerializer(o);
        this.writeShort(serializer.getId());
        REFERENCE_RESOLVER.add(this.createReferenceId(), o);
        serializer.write(o, this);
    }

    public void writeShort(Short s) {
        this.byteBuffer.putShort(s);
    }

    public void writeInt(Integer i) {
        this.byteBuffer.putInt(i);
    }

    public void writeLong(Long l) {
        this.byteBuffer.putLong(l);
    }

    public void writeByte(Byte b) {
        this.byteBuffer.put(b);
    }

    public void writeBytes(byte[] bytes) {
        this.byteBuffer.put(bytes);
    }

    public void writeFloat(Float f) {
        this.byteBuffer.putFloat(f);
    }

    public void writeDouble(Double d) {
        this.byteBuffer.putDouble(d);
    }

    public void writeChar(Character c) {
        this.byteBuffer.putChar(c);
    }

    public void writeClass(Class clazz) {
        this.writeShort(this.config.getClassId(clazz));
    }

    public void writeString(String s) {
        CharsetEncoder encoder = getEncoder();
        CharBuffer charBuffer = CharBuffer.wrap(s);
        try {
            this.byteBuffer.put(encoder.encode(charBuffer));
        } catch (CharacterCodingException e) {
            throw new IllegalStateException("Could not serialize String '" + s + "' with UTF-8 encoding.");
        }
    }

    private CharsetEncoder getEncoder() {
        if (charsetEncoder.get() == null) {
            charsetEncoder.set(Charset.forName("UTF-8").newEncoder());
        }
        return charsetEncoder.get().reset();
    }

    private int createReferenceId() {
        return this.byteBuffer.position();
    }
}
