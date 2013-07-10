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

import java.util.Currency;

import static be.idevelop.fiber.ReferenceResolver.REFERENCE_RESOLVER;

final class CurrencySerializer extends Serializer<Currency> {

    CurrencySerializer() {
        super(Currency.class);
    }

    @Override
    public Currency read(Input input) {
        Currency currency = Currency.getInstance(input.readString());
        REFERENCE_RESOLVER.addForDeserialize(currency);
        return currency;
    }

    @Override
    public void write(Currency currency, Output output) {
        REFERENCE_RESOLVER.addForSerialize(currency, getId(), isImmutable());
        output.writeString(currency.getCurrencyCode());
    }

    @Override
    public boolean isImmutable() {
        return true;
    }
}
