
/**
 * 
 * Interface is used just to build the program loosely coupled,
 * in future you can easily implement another backup creation method
 * without breaking the code
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */
public interface CanUpload
{
    public void createBackup(Manager manager);
    public Manager loadBackup(Manager manager);
}