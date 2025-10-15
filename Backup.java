
/**
 * 
 * It manages how the data is saved and retrieved internally
 * currently it is stored locally within a file named 'backupFile'
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Backup implements CanUpload
{
    @Override
    public void createBackup(Manager manager) {
        try {
            FileOutputStream fOS = new FileOutputStream("backupFile");
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(manager);
        }
        catch (Exception e) {
            System.out.println("Error");
        }
        System.out.println("Backup Successful\n");
    }
    
    @Override
    public Manager loadBackup(Manager manager) {
        try {
            File file = new File("backupFile");
            FileInputStream fIS = new FileInputStream(file);
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            return (Manager) oIS.readObject();
        }
        catch (Exception e) {
            System.out.println("No Previous Backup Found.\n");
        }
        return null;
    }
}