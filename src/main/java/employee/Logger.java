package employee;

public class Logger {
    public void info(String msg, Exception e){

        System.out.println(msg);
        System.out.println(e.getMessage());
    }
}
