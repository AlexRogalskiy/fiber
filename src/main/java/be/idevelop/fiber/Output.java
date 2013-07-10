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

public final class Output {

    private static final int DEFAULT_SIZE = 1024 * 8;

    private static final byte ONE_BYTE = (byte) 0x1;

    private final SerializerConfig config;

    private final ByteBuffer byteBuffer;

    private final short stringId;

    private final IntType classIntType;

    Output(SerializerConfig config) {
        this(config, DEFAULT_SIZE);
    }

    public Output(SerializerConfig config, int bufferSize) {
        this.config = config;
        this.byteBuffer = config.getByteBufferPool().allocate(bufferSize);
        this.stringId = config.getClassId(String.class);
        this.classIntType = config.getIntTypeForClassId();
    }

    public ByteBuffer getByteBuffer() {
        return (ByteBuffer) byteBuffer.limit(byteBuffer.position()).reset();
    }

    @SuppressWarnings("unchecked")
    public void write(Object o) {
        int position = this.byteBuffer.position();
        config.getReferenceSerializer().write(o, this);
        if (position == this.byteBuffer.position()) {
            Serializer<? super Object> serializer = this.config.getSerializer(o);
            this.writeClassId(serializer.getId());
            serializer.write(o, this);
        }
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

    public void writeBoolean(Boolean b) {
        writeByte((byte) (b ? 1 : 0));
    }

    public void writeClass(Class clazz) {
        writeClassId(this.config.getClassId(clazz));
    }

    void writeClassId(short classId) {
        switch (classIntType) {
            case BYTE:
                this.writeByte((byte) classId);
                break;
            default:
                this.writeShort(classId);
                break;
        }
    }

    public void writeString(String s) {
        REFERENCE_RESOLVER.addForSerialize(s, stringId, true);
        if (s.length() > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Max allowed size for a string is " + Short.MAX_VALUE + " characters.");
        }
        this.writeShort((short) s.length());
        short c;
        for (int i = 0; i < s.length(); i++) {
            c = (short) s.charAt(i);
            if (c >> 8 == 0) {
                this.byteBuffer.put((byte) c);
            } else {
                this.byteBuffer.put(ONE_BYTE);
                this.byteBuffer.putShort(c);
            }
        }
    }

}
