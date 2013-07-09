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
                int[] array = new int[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readInteger();
                }
                return array;
            } else if (long.class.equals(arrayElementType)) {
                long[] array = new long[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readLong();
                }
                return array;
            } else if (boolean.class.equals(arrayElementType)) {
                boolean[] array = new boolean[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readBoolean();
                }
                return array;
            } else if (double.class.equals(arrayElementType)) {
                double[] array = new double[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readDouble();
                }
                return array;
            } else if (float.class.equals(arrayElementType)) {
                float[] array = new float[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readFloat();
                }
                return array;
            } else if (byte.class.equals(arrayElementType)) {
                byte[] array = new byte[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readByte();
                }
                return array;
            } else if (short.class.equals(arrayElementType)) {
                short[] array = new short[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readShort();
                }
                return array;
            } else if (char.class.equals(arrayElementType)) {
                char[] array = new char[length];
                REFERENCE_RESOLVER.addForDeserialize(array);
                for (int i = 0; i < length; i++) {
                    array[i] = input.readChar();
                }
                return array;
            }
        }
        Object array = Array.newInstance(arrayElementType, length);
        REFERENCE_RESOLVER.addForDeserialize(array);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, input.read());
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
                for (int i = 0; i < length; i++) {
                    output.writeInt(Array.getInt(array, i));
                }
            } else if (long.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeLong(Array.getLong(array, i));
                }
            } else if (boolean.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeBoolean(Array.getBoolean(array, i));
                }
            } else if (double.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeDouble(Array.getDouble(array, i));
                }
            } else if (float.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeFloat(Array.getFloat(array, i));
                }
            } else if (byte.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeByte(Array.getByte(array, i));
                }
            } else if (short.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeShort(Array.getShort(array, i));
                }
            } else if (char.class.equals(arrayElementType)) {
                for (int i = 0; i < length; i++) {
                    output.writeChar(Array.getChar(array, i));
                }
            }
        } else {
            writeArrayFields((Object[]) array, output);
        }
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    private <T> void writeArrayFields(T[] array, Output output) {
        for (Object obj : array) {
            output.write(obj);
        }
    }
}
