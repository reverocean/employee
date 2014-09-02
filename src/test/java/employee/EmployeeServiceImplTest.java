package employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EmployeeIdGenerator.class, EmployeeServiceImpl.class})
@SuppressStaticInitializationFor("employee.Employee")
public class EmployeeServiceImplTest {

    private EmployeeService employeeService;
    public static final String CREATE_NEW = "createNew";

    @Before
    public void setUp() {
        mockStatic(Employee.class);
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    public void test_get_count_should_return_10() {
        when(Employee.getCount()).thenReturn(10);

        assertThat(employeeService.getCount(), is(11));
    }


    @Test
    public void test_give_increment_should_return_true_without_exception() {
        doNothing().when(Employee.class);
        Employee.increment();

        boolean result = employeeService.giveIncrement();

        assertThat(result, is(true));
        verifyStatic();
        Employee.increment();
    }


    @Test
    public void test_give_increment_should_return_false_with_exception() {
        doThrow(new RuntimeException()).when(Employee.class);
        Employee.increment();

        boolean result = employeeService.giveIncrement();

        assertThat(result, is(false));
        verifyStatic();
        Employee.increment();
    }


    @Test
    public void test_save_should_create_when_employee_is_new() throws Exception {
        int id = 10;

        Employee employee = mockEmployee(id);

        EmployeeServiceImpl employeeServiceSpy = PowerMockito.spy(new EmployeeServiceImpl());

        Method createNewMethod = method(EmployeeServiceImpl.class, CREATE_NEW, Employee.class);
        doReturn(id).when(employeeServiceSpy, createNewMethod).withArguments(employee);

        Method handleIdMethod = method(EmployeeServiceImpl.class, "handleId", Integer.TYPE);
        doNothing().when(employeeServiceSpy, handleIdMethod).withArguments(id);


        employeeServiceSpy.save(employee);

        InOrder inOrder = Mockito.inOrder(employee);
        inOrder.verify(employee).isNew();
        inOrder.verify(employee, never()).update();

        verifyPrivate(employeeServiceSpy).invoke(CREATE_NEW, employee);
        verifyPrivate(employeeServiceSpy).invoke("handleId", id);
    }

    @Test
    public void test_save_should_update_when_employee_is_not_new() {
        Employee employee = mock(Employee.class);

        when(employee.isNew()).thenReturn(false);
        doNothing().when(employee).update();

        employeeService.save(employee);
        InOrder inOrder = Mockito.inOrder(employee);
        inOrder.verify(employee).isNew();
        inOrder.verify(employee, never()).create();
        inOrder.verify(employee).update();
    }

    @Test
    public void test_create_new_should_return_1() throws Exception {
        int id = 1;
        Employee employee = mockEmployee(id);
        mockGenerateId(1);
        mockWelcomeEmail(1);

        Integer result = Whitebox.invokeMethod(employeeService, CREATE_NEW, employee);

        assertThat(result, is(1));
    }

    private WelcomeEmail mockWelcomeEmail(int id) throws Exception {
        WelcomeEmail welcomeEmail = mock(WelcomeEmail.class);
        whenNew(WelcomeEmail.class).withArguments(id, "Welcome").thenReturn(welcomeEmail);
        doNothing().when(welcomeEmail).send();
        return welcomeEmail;
    }

    private void mockGenerateId(int id) {
        mockStatic(EmployeeIdGenerator.class);
        when(EmployeeIdGenerator.generateId()).thenReturn(id);
    }

    private Employee mockEmployee(int id) {
        Employee employee = mock(Employee.class);
        when(employee.isNew()).thenReturn(true);
        doNothing().when(employee).create();
        doNothing().when(employee).setId(id);
        return employee;
    }


}
