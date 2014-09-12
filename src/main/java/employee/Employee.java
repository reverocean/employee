package employee;

public final class Employee extends BaseEntity {
    static {
        String s = null;
    }




    private int id;

    public static int getCount(){
        return 1;
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
