
/**
 *
 * Creating a manager object and handling all exception as general
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */
public class Main
{
    public static void main(String[] args) {
        try {
            Manager manager = new Manager("John",new Backup());
        }
        catch (Exception e) {
            System.out.println("\nI am not supportive If"+
                " you go beyond Limits!\n");
        }
    }
}