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

import java.util.Collection;

public class CollectionSerializer<C extends Collection> extends Serializer<C> implements GenericObjectSerializer {

    private ObjectCreator objectCreator;

    public CollectionSerializer(Class<C> collectionClass) {
        super(collectionClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public C read(Input input) {
        short length = input.readShort();
        C collection = createNewInstance(length);
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                collection.add(input.read());
            }
        }
        return collection;
    }

    protected C createNewInstance(short length) {
        return objectCreator.createNewCollectionInstance(getId(), length);
    }

    @Override
    public void write(C collection, Output output) {
        if (collection.size() > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Max allowed size for a collection is " + Short.MAX_VALUE + " elements.");
        }
        addReferenceForSerialization(collection);
        output.writeShort((short) collection.size());
        for (Object o : collection) {
            output.write(o);
        }
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public void registerObjectCreator(ObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }
}
