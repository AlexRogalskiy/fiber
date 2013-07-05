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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

enum ReferenceResolver {

    REFERENCE_RESOLVER;

    private static final ThreadLocal<SerializeReferenceContainer> SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL = new InheritableThreadLocal<SerializeReferenceContainer>();

    private static final ThreadLocal<DeserializeReferenceContainer> DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL = new InheritableThreadLocal<DeserializeReferenceContainer>();

    static {
        SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.set(new SerializeReferenceContainer());
        DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.set(new DeserializeReferenceContainer());
    }

    void addForSerialize(Object o) {
        getSerializeReferenceContainer().add(o);
    }

    <T> T addForDeserialize(T o) {
        return getDeserializeReferenceContainer().add(o);
    }

    Object getReference(int referenceId) {
        return getDeserializeReferenceContainer().getReference(referenceId);
    }

    void clearSerialize() {
        getSerializeReferenceContainer().clear();
    }

    void clearDeserialize() {
        getDeserializeReferenceContainer().clear();
    }

    boolean contains(Object o) {
        return getSerializeReferenceContainer().contains(o);
    }

    int getReferenceId(Object o) {
        return getSerializeReferenceContainer().getId(o);
    }

    private SerializeReferenceContainer getSerializeReferenceContainer() {
        return SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.get();
    }

    private DeserializeReferenceContainer getDeserializeReferenceContainer() {
        return DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.get();
    }

    private static class SerializeReferenceContainer {

        private int referenceId = 0;

        private Set<Integer> objectIds = new HashSet<Integer>();

        private Map<Integer, Integer> objectIdToReferenceId = new HashMap<Integer, Integer>();

        void clear() {
            referenceId = 0;
            objectIds.clear();
            objectIdToReferenceId.clear();
        }

        void add(Object o) {
            if (o != null) {
                int objectId = System.identityHashCode(o);
                if (!objectIds.contains(objectId)) {
                    objectIds.add(objectId);
                    objectIdToReferenceId.put(objectId, referenceId++);
                }
            }
        }

        boolean contains(Object o) {
            return o != null && objectIds.contains(System.identityHashCode(o));
        }

        int getId(Object o) {
            return objectIdToReferenceId.get(System.identityHashCode(o));
        }
    }

    private static class DeserializeReferenceContainer {

        private static final int MAX_OBJECT_INSTANCES = 8 * 1024;

        private int referenceId = 0;

        private Object[] objects = new Object[MAX_OBJECT_INSTANCES];

        <T> T add(T o) {
            if (o != null) {
                objects[referenceId++] = o;
            }
            return o;
        }

        public Object getReference(int referenceId) {
            return objects[referenceId];
        }

        void clear() {
            referenceId = 0;
        }
    }

}
