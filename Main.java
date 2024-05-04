import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main <workflow_file_path> <output_file_path>");
            return;
        }

        String workflowFilePath = args[0];
        String outputFilePath = args[1];

        File workflowFile = new File(workflowFilePath);
        if (!workflowFile.exists() || !workflowFile.canRead()) {
            System.out.println("Error: Workflow file does not exist or is not accessible.");
            return;
        }
        
        File jobFile = new File(outputFilePath);
        if (!jobFile.exists() || !jobFile.canRead()) {
            System.out.println("Error: Job file does not exist or is not accessible.");
            return;
        }

        WorkflowParser.parseWorkflow(workflowFile, jobFile);
    }
}
