package be.idevelop.fiber;

public class ExtendedObject extends SimpleObject {

    private final String s;

    public ExtendedObject() {
        super();
        this.s = null;
    }

    public ExtendedObject(int value, String s) {
        super(value);
        this.s = s;
    }

    public String getS() {
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedObject)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ExtendedObject that = (ExtendedObject) o;

        return !(s != null ? !s.equals(that.s) : that.s != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (s != null ? s.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExtendedObject{" +
                "s='" + s + '\'' +
                "} " + super.toString();
    }
}
