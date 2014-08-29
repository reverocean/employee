package employee;

public class EmployeeController {
    private static employee.Logger LOGGER = new employee.Logger();

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    public int getEmployeeCount() {
        return employeeService.getCount();
    }

    public boolean save(Employee employee) {
        try {
            employeeService.save(employee);
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return false;
        }
    }
}
