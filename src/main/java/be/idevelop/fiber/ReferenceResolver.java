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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

enum ReferenceResolver {

    REFERENCE_RESOLVER;

    private static final ThreadLocal<SerializeReferenceContainer> SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL = new InheritableThreadLocal<SerializeReferenceContainer>();

    private static final ThreadLocal<DeserializeReferenceContainer> DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL = new InheritableThreadLocal<DeserializeReferenceContainer>();

    private static final int MAX_OBJECT_INSTANCES = 8 * 1024;

    static {
        SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.set(new SerializeReferenceContainer());
        DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.set(new DeserializeReferenceContainer());
    }

    void addForSerialize(Object o, short classId, boolean immutable) {
        getSerializeReferenceContainer().add(o, classId, immutable);
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

    short getReferenceId(Object o, short classId, boolean immutable) {
        return getSerializeReferenceContainer().getId(o, classId, immutable);
    }

    private SerializeReferenceContainer getSerializeReferenceContainer() {
        return SERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.get();
    }

    private DeserializeReferenceContainer getDeserializeReferenceContainer() {
        return DESERIALIZE_REFERENCE_CONTAINER_THREAD_LOCAL.get();
    }

    private static class SerializeReferenceContainer {

        private short referenceId = 0;

        private Set<Short> classIds = new HashSet<Short>();

        @SuppressWarnings("unchecked")
        private List<Object>[] objects = new List[MAX_OBJECT_INSTANCES];

        private Map<Integer, Short> objectIdToReferenceId = new TreeMap<Integer, Short>();

        @SuppressWarnings("unchecked")
        private Map<Object, Short>[] immutableObjects = new Map[MAX_OBJECT_INSTANCES];

        void clear() {
            referenceId = 0;
            for (Short classId : classIds) {
                objects[classId] = null;
                immutableObjects[classId] = null;
            }
            classIds.clear();
            objectIdToReferenceId.clear();
        }

        void add(Object o, short classId, boolean immutable) {
            if (o != null) {
                if (objects[classId] == null) {
                    objects[classId] = new ArrayList<Object>();
                    classIds.add(classId);
                }
                objects[classId].add(o);
                objectIdToReferenceId.put(System.identityHashCode(o), referenceId);
                if (immutable) {
                    if (immutableObjects[classId] == null) {
                        immutableObjects[classId] = new HashMap<Object, Short>();
                    }
                    immutableObjects[classId].put(o, referenceId);
                }
                referenceId++;
            }
        }

        short getId(Object o, short classId, boolean immutable) {
            Short id = null;
            if (!immutable) {
                id = objectIdToReferenceId.get(System.identityHashCode(o));
            } else {
                if (immutableObjects[classId] == null) {
                    immutableObjects[classId] = new HashMap<Object, Short>();
                } else {
                    id = immutableObjects[classId].get(o);
                }
            }
            return id != null ? id : -1;
        }

    }

    private static class DeserializeReferenceContainer {

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
