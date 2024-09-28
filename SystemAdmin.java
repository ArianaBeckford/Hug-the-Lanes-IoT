import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
public class SystemAdmin {
    String username;
    String password;
    String entryUser;
    String entryPassword;
    boolean verified;
    boolean isEngineOn; //might change vechicleOn to include the "in"
    String LogFile; //contains name of log file
    boolean tasksCompleted; //how to test if tasks are completed?
    boolean vehicleOn; //technically a requirement
    private Scanner logFileScanner;
    boolean isComputerConnected;

    public SystemAdmin(String filename) {
        this.LogFile = filename; // Initialize LogFile properly
        File dataFile = new File(filename);
        tasksCompleted = true; // Assume all tasks are completed initially
        try {
            Scanner myReader = new Scanner(dataFile);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.contains("Incomplete")) {
                    tasksCompleted = false; // If any task is incomplete, set flag to false
                    break; // Exit loop, no need to check further
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
    }

    public void displayLogin(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Username: ");
        entryUser = input.next();
        System.out.println("Enter Password: ");
        entryPassword = input.next();
        verified = entryUser.equals(username) && entryPassword.equals(password);
        while(!(verified)){
            System.out.println("Incorrect Username or Password");
            System.out.println("Enter Username: ");
            entryUser = input.next();
            System.out.println("Enter Password: ");
            entryPassword = input.next();
        }
        if (verified == true){
            displayLogFile();
        }
    }
    public void displayLogFile() {
        try {
            logFileScanner = new Scanner(new File(LogFile));
            while (logFileScanner.hasNextLine()) {
                System.out.println(logFileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Log file not found.");
            e.printStackTrace();
        }
    }

    public void requestSignOff(){ 
        if(tasksCompleted){
            System.out.println("Signing out");
            if (logFileScanner != null){
                logFileScanner.close();
            }
        }
        else{
            System.out.println("Must complete tasks");
        }
    }
    public void updateLogFile(boolean verified) throws Exception {
        FileWriter myWriter = new FileWriter(LogFile);
        if (verified) {
            myWriter.write("Recent successful login.");
            myWriter.close();
        } else {
            myWriter.write("Recent failed login.");
            myWriter.close();
        }
    }

    if (isEngineOn == false && vehicleOn == true && isComputerConnected == true){ //allow Technician to log in once vehicle is on and engine is off
        displayLogin();
    }
    public static void main(String[] args){
        SystemAdmin admin = new SystemAdmin("logFile.txt");
        // Set username and password
        admin.isEngineOn = false;
        admin.vehicleOn = true;
        admin.username = "JohnDoe";
        admin.password = "Sandwich1234";
        admin.displayLogin();
        admin.requestSignOff();
    }
}

