import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        
        //This part makes sure user has entered workflow and job file names so it can read and write in them.
        //Code will print error message if either file does not exist or it is inaccasible.

        if (args.length != 2) {
            System.out.println("Usage: java Main <workflow_file> <job_file>");
            return;
        }
        
        String workflowFilePath = args[0];
        String jobFilePath = args[1];
        
        File workflowFile = new File(workflowFilePath);
        if (!workflowFile.exists() || !workflowFile.canRead()) {
            System.out.println("Error: Workflow file does not exist or is not accessible.");
            return;
        }
        
        File jobFile = new File(jobFilePath);
        if (!jobFile.exists() || !jobFile.canRead()) {
            System.out.println("Error: Job file does not exist or is not accessible.");
            return;
        }
        
    }
}
    
