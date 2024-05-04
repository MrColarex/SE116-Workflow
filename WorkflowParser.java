import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class WorkflowParser {
    public static void parseWorkflow(File workflowFile, File outputFile) {
        try {
            Scanner scanner = new Scanner(workflowFile);
            PrintWriter writer = new PrintWriter(outputFile);
            int lineNumber = 0;

            // Variables to store parsed information
            List<TaskType> taskTypes = new ArrayList<>();
            List<JobType> jobTypes = new ArrayList<>();
            List<Station> stations = new ArrayList<>();
            Set<String> declaredJobTypes = new HashSet<>();
            Set<String> declaredTaskTypes = new HashSet<>();

            // Flags to track parsing sections
            boolean taskTypesSection = false;
            boolean jobTypesSection = false;
            boolean stationsSection = false;

            // Read each line of the workflow file
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Parse each section
                if (line.startsWith("(TASKTYPES")) {
                    taskTypesSection = true;
                    jobTypesSection = false;
                    stationsSection = false;
                } else if (line.startsWith("(JOBTYPES")) {
                    taskTypesSection = false;
                    jobTypesSection = true;
                    stationsSection = false;
                } else if (line.startsWith("(STATIONS")) {
                    taskTypesSection = false;
                    jobTypesSection = false;
                    stationsSection = true;
                } else {
                    // Parse individual lines based on section flags
                    if (taskTypesSection) {
                        // Parse task type line
                        String[] parts = line.split("\\s+");
                        for (int i = 1; i < parts.length; i += 2) {
                            String taskTypeID = parts[i];
                            try {
                                double defaultTaskSize = Double.parseDouble(parts[i + 1]);
                                if (defaultTaskSize < 0) {
                                    writer.println("Line " + lineNumber + ": ○ " + taskTypeID + " has a negative task size.");
                                }
                                declaredTaskTypes.add(taskTypeID); // Record declared task types
                                taskTypes.add(new TaskType(taskTypeID, defaultTaskSize));
                            } catch (NumberFormatException e) {
                                writer.println("Line " + lineNumber + ": ● Invalid task size for " + taskTypeID);
                            }
                        }
                    } else if (jobTypesSection) {
                        // Parse job type line
                        String[] parts = line.split("\\s+");
                        String jobTypeID = parts[1];
                        if (declaredJobTypes.contains(jobTypeID)) {
                            writer.println("Line " + lineNumber + ": ○ " + jobTypeID + " already declared.");
                        } else {
                            declaredJobTypes.add(jobTypeID); // Record declared job types
                            List<TaskType> taskSequence = new ArrayList<>();
                            for (int i = 2; i < parts.length; i += 2) {
                                String taskTypeID = parts[i];
                                TaskType taskType = taskTypes.stream().filter(t -> t.getTaskTypeID().equals(taskTypeID)).findFirst().orElse(null);
                                if (taskType == null) {
                                    writer.println("Line " + lineNumber + ": ○ " + taskTypeID + " is not one of the declared task types.");
                                } else {
                                    taskSequence.add(taskType);
                                }
                            }
                            jobTypes.add(new JobType(jobTypeID, taskSequence));
                        }
                    } else if (stationsSection) {
                        // Parse station line
                        String[] parts = line.split("\\s+");
                        String stationID = parts[1];
                        if (stations.stream().anyMatch(s -> s.getStationID().equals(stationID))) {
                            writer.println("Line " + lineNumber + ": ○ " + stationID + " already declared.");
                        } else {
                            try {
                                int capacity = Integer.parseInt(parts[2]);
                                boolean canHandleMultipleTasks = parts[3].equals("Y");
                                double speed = Double.parseDouble(parts[parts.length - 1]);
                                List<TaskType> taskTypesHandled = new ArrayList<>();
                                for (int i = 4; i < parts.length - 1; i += 2) {
                                    String taskTypeID = parts[i];
                                    TaskType taskType = taskTypes.stream().filter(t -> t.getTaskTypeID().equals(taskTypeID)).findFirst().orElse(null);
                                    if (taskType == null) {
                                        writer.println("Line " + lineNumber + ": ○ " + taskTypeID + " is not one of the declared task types.");
                                    } else {
                                        taskTypesHandled.add(taskType);
                                    }
                                }
                                stations.add(new Station(stationID, capacity, canHandleMultipleTasks, speed, taskTypesHandled));
                            } catch (NumberFormatException e) {
                                writer.println("Line " + lineNumber + ": ● Invalid numeric value in station information.");
                            }
                        }
                    }
                }
            }

            // Check for missing task types in stations
            for (TaskType taskType : taskTypes) {
                boolean found = false;
                for (Station station : stations) {
                    if (station.getTaskTypesHandled().contains(taskType)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    writer.println("Warning: Task type " + taskType.getTaskTypeID() + " is not executed in any STATIONS even though it is listed as a possible task type.");
                }
            }

            // Print the correct format to the output file
            writer.println("(TASKTYPES " + taskTypes.stream().map(t -> t.getTaskTypeID() + " " + t.getDefaultTaskSize()).reduce((t1, t2) -> t1 + " " + t2).orElse("") + ")");
            writer.println("(JOBTYPES");
            for (JobType jobType : jobTypes) {
                List<TaskType> taskSequence = jobType.getTasksSequence();
                writer.println("(" + jobType.getJobTypeID() + " " + taskSequence.stream().map(TaskType::getTaskTypeID).reduce((t1, t2) -> t1 + " " + t2).orElse("") + ")");
            }
            writer.println("STATIONS");
            for (Station station : stations) {
                List<TaskType> taskTypesHandled = station.getTaskTypesHandled();
                writer.println("(" + station.getStationID() + " " + station.getCapacity() + " " + (station.canHandleMultipleTasks() ? "Y" : "N") +
                        taskTypesHandled.stream().map(TaskType::getTaskTypeID).reduce((t1, t2) -> t1 + " " + t2).orElse("") + " " + station.getSpeed() + ")");
            }
            writer.println(")");

            // Close the scanner and writer
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }
}
