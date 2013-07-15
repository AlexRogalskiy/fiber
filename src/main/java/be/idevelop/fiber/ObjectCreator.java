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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;

import static be.idevelop.fiber.ReferenceResolver.REFERENCE_RESOLVER;

class ObjectCreator {

    private Constructor[] constructors;

    ObjectCreator() {
        this.constructors = new Constructor[Short.MAX_VALUE];
    }

    public void registerClass(Class clazz, short classId) {
        if (!Modifier.isAbstract(clazz.getModifiers())) {
            if (Collection.class.isAssignableFrom(clazz)) {
                constructors[classId] = getCollectionConstructor(clazz);
            } else {
                constructors[classId] = getDefaultConstructor(clazz);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T createNewInstance(short classId) {
        return REFERENCE_RESOLVER.addForDeserialize(createNewInstance((Constructor<T>) constructors[classId]));
    }

    @SuppressWarnings("unchecked")
    public <C extends Collection> C createNewCollectionInstance(short classId, short length) {
        return REFERENCE_RESOLVER.addForDeserialize(createNewInstance((Constructor<C>) constructors[classId], length));
    }

    private <C extends Collection> Constructor<C> getCollectionConstructor(Class<C> clazz) {
        Constructor<C> constructor;
        try {
            constructor = clazz.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No collection constructor found for class " + clazz.getName() + ". Implement a custom serializer.", e);
        }
        return constructor;
    }

    private <T> Constructor<T> getDefaultConstructor(Class<T> clazz) {
        Constructor<T> constructor;
        try {
            constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No default constructor found for class " + clazz.getName() + ". Implement a custom serializer.", e);
        }
        return constructor;
    }

    private <T> T createNewInstance(Constructor<T> constructor, Object... param) {
        T t;
        try {
            t = constructor.newInstance(param);
        } catch (InstantiationException e) {
            throw new IllegalStateException("Could not invoke default constructor for class " + constructor.getDeclaringClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not invoke default constructor for class " + constructor.getDeclaringClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Could not invoke default constructor for class " + constructor.getDeclaringClass().getName(), e);
        }
        return t;
    }
}
