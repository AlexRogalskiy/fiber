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

import java.lang.reflect.Array;

import static be.idevelop.fiber.ReferenceResolver.REFERENCE_RESOLVER;

final class ArraySerializer extends Serializer<Object> {

    ArraySerializer() {
        super(Object.class);
    }

    @Override
    public Object read(Input input) {
        int length = input.readShort();
        Class arrayElementType = input.read();
        if (arrayElementType.isPrimitive()) {
            if (int.class.equals(arrayElementType)) {
                return readIntArray(input, length);
            } else if (long.class.equals(arrayElementType)) {
                return readLongArray(input, length);
            } else if (boolean.class.equals(arrayElementType)) {
                return readBooleanArray(input, length);
            } else if (double.class.equals(arrayElementType)) {
                return readDoubleArray(input, length);
            } else if (float.class.equals(arrayElementType)) {
                return readFloatArray(input, length);
            } else if (byte.class.equals(arrayElementType)) {
                return readByteArray(input, length);
            } else if (short.class.equals(arrayElementType)) {
                return readShortArray(input, length);
            } else if (char.class.equals(arrayElementType)) {
                return readCharArray(input, length);
            }
        }
        return readObjectArray(input, length, arrayElementType);
    }

    private Object readIntArray(Input input, int length) {
        int[] array = new int[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readInteger();
        }
        return array;
    }

    private Object readLongArray(Input input, int length) {
        long[] array = new long[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readLong();
        }
        return array;
    }

    private Object readBooleanArray(Input input, int length) {
        boolean[] array = new boolean[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readBoolean();
        }
        return array;
    }

    private Object readDoubleArray(Input input, int length) {
        double[] array = new double[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readDouble();
        }
        return array;
    }

    private Object readFloatArray(Input input, int length) {
        float[] array = new float[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readFloat();
        }
        return array;
    }

    private Object readByteArray(Input input, int length) {
        byte[] array = new byte[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readByte();
        }
        return array;
    }

    private Object readShortArray(Input input, int length) {
        short[] array = new short[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readShort();
        }
        return array;
    }

    private Object readCharArray(Input input, int length) {
        char[] array = new char[length];
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.readChar();
        }
        return array;
    }

    private <T> T[] createArray(int length, Class<T> arrayElementType) {
        return (T[]) Array.newInstance(arrayElementType, length);
    }

    private Object readObjectArray(Input input, int length, Class arrayElementType) {
        Object[] array = createArray(length, arrayElementType);
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            array[i] = input.read();
        }
        return array;
    }

    @Override
    public void write(Object array, Output output) {
        REFERENCE_RESOLVER.addForSerialize(array, getId(), isImmutable());

        short length = (short) Array.getLength(array);
        output.writeShort(length);
        Class<?> arrayElementType = array.getClass().getComponentType();
        output.write(arrayElementType);
        if (arrayElementType.isPrimitive()) {
            if (int.class.equals(arrayElementType)) {
                writeIntArray(array, output, length);
            } else if (long.class.equals(arrayElementType)) {
                writeLongArray(array, output, length);
            } else if (boolean.class.equals(arrayElementType)) {
                writeBooleanArray(array, output, length);
            } else if (double.class.equals(arrayElementType)) {
                writeDoubleArray(array, output, length);
            } else if (float.class.equals(arrayElementType)) {
                writeFloatArray(array, output, length);
            } else if (byte.class.equals(arrayElementType)) {
                writeByteArray(array, output, length);
            } else if (short.class.equals(arrayElementType)) {
                writeShortArray(array, output, length);
            } else if (char.class.equals(arrayElementType)) {
                writeCharArray(array, output, length);
            }
        } else {
            writeObjectArray((Object[]) array, output);
        }
    }

    private void writeIntArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeInt(Array.getInt(array, i));
        }
    }

    private void writeLongArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeLong(Array.getLong(array, i));
        }
    }

    private void writeBooleanArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeBoolean(Array.getBoolean(array, i));
        }
    }

    private void writeDoubleArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeDouble(Array.getDouble(array, i));
        }
    }

    private void writeFloatArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeFloat(Array.getFloat(array, i));
        }
    }

    private void writeByteArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeByte(Array.getByte(array, i));
        }
    }

    private void writeShortArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeShort(Array.getShort(array, i));
        }
    }

    private void writeCharArray(Object array, Output output, short length) {
        for (int i = 0; i < length; i++) {
            output.writeChar(Array.getChar(array, i));
        }
    }

    private <T> void writeObjectArray(T[] array, Output output) {
        for (Object obj : array) {
            output.write(obj);
        }
    }

    @Override
    public boolean isImmutable() {
        return false;
    }
}
