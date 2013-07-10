package be.idevelop.fiber;

import java.util.Comparator;
import java.util.TreeSet;

final class TreeSetSerializer extends Serializer<TreeSet> {

    protected TreeSetSerializer() {
        super(TreeSet.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TreeSet read(Input input) {
        short length = input.readShort();
        Comparator comparator = input.read();
        TreeSet set;
        if (comparator != null) {
            set = new TreeSet(comparator);
        } else {
            set = new TreeSet();
        }
        for (short i = 0; i < length; i++) {
            set.add(input.read());
        }
        return set;
    }

    @Override
    public void write(TreeSet set, Output output) {
        if (set.size() > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Max allowed size for a TreeSet is " + Short.MAX_VALUE + " elements.");
        }
        output.writeShort((short) set.size());
        output.write(set.comparator());
        for (Object o : set) {
            output.write(o);
        }
    }

    @Override
    public boolean isImmutable() {
        return false;
    }
}
