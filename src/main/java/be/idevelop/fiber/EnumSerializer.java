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

public final class EnumSerializer<E extends Enum> extends Serializer<E> {

    private final E[] constants;

    private final IntType enumIntType;

    public EnumSerializer(Class<E> serializedClass) {
        super(serializedClass);
        this.constants = serializedClass.getEnumConstants();
        if (this.constants.length < Byte.MAX_VALUE) {
            this.enumIntType = IntType.BYTE;
        } else if (this.constants.length < Short.MAX_VALUE) {
            this.enumIntType = IntType.SHORT;
        } else {
            throw new IllegalArgumentException("Max allowed enum constants is " + Short.MAX_VALUE + ".");
        }
    }

    @Override
    public E read(Input input) {
        switch (enumIntType) {
            case BYTE:
                return constants[input.readByte()];
            default:
                return constants[input.readShort()];
        }
    }

    @Override
    public void write(E object, Output output) {
        switch (enumIntType) {
            case BYTE:
                output.writeByte((byte) object.ordinal());
                break;
            default:
                output.writeShort((short) object.ordinal());
        }
    }

    @Override
    public boolean isImmutable() {
        return true;
    }
}
