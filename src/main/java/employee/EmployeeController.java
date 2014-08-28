package employee;

public class EmployeeController {
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
            return false;
        }
    }
}
