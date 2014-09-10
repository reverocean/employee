package employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("employee.Employee")
public class EmployeeControllerTest {

    private EmployeeService employeeService;
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        employeeService = mock(EmployeeService.class);
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    public void test_get_employee_count_should_return_10() {
        when(employeeService.getCount()).thenReturn(10);

        assertThat(employeeController.getEmployeeCount(), is(10));
    }


    @Test
    public void test_save_should_return_true_without_exception() {
        Employee employee = mock(Employee.class);
        doNothing().when(employeeService).save(employee);

        boolean result = employeeController.save(employee) ;

        assertThat(result, is(true));
        Mockito.verify(employeeService).save(employee);

    }

    @Test
    public void test_save_should_return_false_with_exception() {
        Employee employee =  mock(Employee.class);
        doThrow(new RuntimeException()).when(employeeService).save(employee);

        boolean result = employeeController.save(employee) ;

        assertThat(result, is(false));
        Mockito.verify(employeeService).save(employee);
    }
}
