package employee;

public final class Employee extends BaseEntity {
    static {
        String s = null;
        s.endsWith("hah");
    }

    private int id;

    public static int getCount(){
        throw new UnsupportedOperationException();
    }

    public static void increment() {
        throw new UnsupportedOperationException();
    }

    public boolean isNew() {
        throw new UnsupportedOperationException();
    }

    public void create() {
        throw new UnsupportedOperationException();
    }

    public void update() {
        throw new UnsupportedOperationException();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
