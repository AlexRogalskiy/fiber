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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ComplexClassWithDefaultConstructorAndReferences {

    public static final String CONSTANT = "constant";

    private final String alwaysNull = null;

    private int i;

    private String s = "test";

    private Map<String, List<BigInteger>> map = new HashMap<String, List<BigInteger>>();

    private ComplexClassWithDefaultConstructorAndReferences[] references = new ComplexClassWithDefaultConstructorAndReferences[2];

    private ComplexClassWithDefaultConstructorAndReferences() {
    }

    static ComplexClassWithDefaultConstructorAndReferences createNewInstance() {
        ComplexClassWithDefaultConstructorAndReferences complexClass = new ComplexClassWithDefaultConstructorAndReferences();
        complexClass.i = 10;
        complexClass.s = CONSTANT;
        List<BigInteger> fibonacci = Arrays.asList(BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.valueOf(8), BigInteger.valueOf(13), BigInteger.valueOf(21), BigInteger.valueOf(34), BigInteger.valueOf(55), BigInteger.valueOf(89));
        complexClass.map.put("fibonacci", fibonacci);
        List<BigInteger> binary = Arrays.asList(BigInteger.ONE, BigInteger.ZERO);
        complexClass.map.put("binary", binary);

        complexClass.references[0] = null;
        complexClass.references[1] = complexClass;

        return complexClass;
    }

    int getI() {
        return i;
    }

    String getS() {
        return s;
    }

    Map<String, List<BigInteger>> getMap() {
        return map;
    }

    String getAlwaysNull() {
        return alwaysNull;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplexClassWithDefaultConstructorAndReferences)) {
            return false;
        }

        ComplexClassWithDefaultConstructorAndReferences that = (ComplexClassWithDefaultConstructorAndReferences) o;

        boolean result = i == that.i && !(alwaysNull != null ? !alwaysNull.equals(that.alwaysNull) : that.alwaysNull != null) && !(map != null ? !map.equals(that.map) : that.map != null) && !(s != null ? !s.equals(that.s) : that.s != null);
        result = result && references.length == that.references.length;
        for (int j = 0; j < references.length && result; j++) {
            if (references[j] == null) {
                result = that.references[j] == null;
            } else if (references[j] == this) {
                result = that.references[j] == that;
            } else {
                result = references[j] == that.references[j];
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String string = "ComplexClassWithDefaultConstructorAndReferences{" +
                "alwaysNull='" + alwaysNull + '\'' +
                ", i=" + i +
                ", s='" + s + '\'' +
                ", map=" + map;
        for (ComplexClassWithDefaultConstructorAndReferences reference : references) {
            string += ", reference: @" + (reference != null ? Integer.toHexString(reference.hashCode()) : "null");
        }

        return string + "} " + super.toString();
    }
}
