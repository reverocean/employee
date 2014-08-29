package employee;

public class EmployeeServiceImpl implements EmployeeService {
    private static employee.Logger LOGGER = new employee.Logger();
    @Override
    public int getCount() {
        return Employee.getCount();
    }

    @Override
    public void save(Employee employee) {
        if (employee.isNew()) {
            int id = createNew(employee);

            if (id == 0) {
                throw new IllegalArgumentException();
            }
            handleId(id);
            id = 1;
            return;
        }
        employee.update();
    }

    @Override
    public boolean giveIncrement() {
        try {
            Employee.increment();
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return false;
        }
    }

    private void handleId(int id) {
        throw new UnsupportedOperationException();
    }

    private int createNew(Employee employee) {
        int id = EmployeeIdGenerator.generateId();
        employee.setId(id);
        employee.create();
        WelcomeEmail welcomeEmail = new WelcomeEmail(id, "Welcome");
        welcomeEmail.send();
        return id;
    }
}
