/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
public class Task {

    public String taskName, taskDescription, taskId;
    public String taskOwner, assignedTo;
    public int taskCompletedLevel;
    public Date taskDueDate;
    public Date taskStartDate, taskCompletionDate;
    public Category taskCategory;
    public Priority taskPriority;
    public State taskState;
    private final static Map<String, Task> taskList = new HashMap<>();
    public static int taskCount = 1;

    public enum Category {
        PLANNING(1), DEFINING_REQUIREMENT(2), DESIGNING(3), DEVELOPING(4), TESTING(5), DEPLOYING(6), MAINTAINING(7);

        private final int order;

        private Category(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

    };

    public enum Priority {
        VERY_HIGH(4), HIGH(3), MEDIUM(2), LOW(1);

        private final int value;

        private Priority(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    };

    public enum State {
        INACTIVE(1), READY(2), ASSIGNED(3), PAUSED(4), FINISHED(5), FAILED(6), FORWARDED(7), TERMINATED(8), EXPIRED(9), COMPLETED(10);

        private final int order;

        private State(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

    };

    public Task(String taskName, String taskDescription, String taskId, String taskOwner, String assignedTo, int taskCompletedLevel, Date taskStartDate, Date taskDueDate, Date taskCompletionDate, Category taskCategory, Priority taskPriority, State taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskOwner = taskOwner;
        this.assignedTo = assignedTo;
        this.taskCompletedLevel = taskCompletedLevel;
        this.taskStartDate = taskStartDate;
        this.taskDueDate = taskDueDate;
        this.taskCompletionDate = taskCompletionDate;
        this.taskCategory = taskCategory;
        this.taskPriority = taskPriority;
        this.taskState = taskStatus;
    }

    public static State getState(int stateNumber) {
        switch (stateNumber) {
            case 1 -> {
                return State.READY;
            }
            case 2 -> {
                return State.PAUSED;
            }
            case 3 -> {
                return State.FINISHED;
            }
            case 4 -> {
                return State.FAILED;
            }
            case 5 -> {
                return State.TERMINATED;
            }
            case 6 -> {
                return State.EXPIRED;
            }
            case 7 -> {
                return State.COMPLETED;
            }
            default -> {
                return null;
            }
        }
    }

    public static void addTask(String taskId, Task task) {
        taskList.put(taskId, task);
    }

    public static Task getTask(Scanner ip) {
        String taskId;
        Task task;
        do {
            System.out.println("Enter task id");
            taskId = ip.next();
            task = Task.getUserTask(taskId);
        } while (task == null);

        return task;
    }

    public static String assignTask(Scanner ip) {
        User.printUsers();
        String userAssignedEmail;
        System.out.println("Task assigned to ");
        do {
            System.out.print("Enter user email ");
            ip.nextLine();
            userAssignedEmail = ip.nextLine();
        } while (!User.emailExists(userAssignedEmail));
        return userAssignedEmail;
    }

    public static String getTaskType(Scanner ip) {
        final String OWN_TASK = "ownTask";
        final String ASSIGNED_TASK = "assignedTask";
        char displayTaskType;
        System.out.println("1. Owned Task\n2. Assigned Task");
        displayTaskType = ip.next().charAt(0);
        return (displayTaskType == '1') ? OWN_TASK : ASSIGNED_TASK;

    }

    public static List<Task> getUserTaskMap(User currentUser, String taskType) {
        final String OWN_TASK = "ownTask";

        if (taskType.endsWith(OWN_TASK)) {
            return taskList.values().stream()
                    .filter(task -> task.taskOwner.equals(currentUser.userEmail))
                    .collect(Collectors.toList());
        } else {
            return taskList.values().stream()
                    .filter(task -> task.assignedTo != null && task.assignedTo.equals(currentUser.userEmail))
                    .collect(Collectors.toList());
        }

    }

    public static Task getUserTask(String taskId) {
        return taskList.getOrDefault(taskId, null);
    }

    public static int gettaskCount() {
        return taskCount++;
    }

    public static Task displayGetTask(User currentUser, Scanner ip, String taskType) {
        DisplayTask displayUserTask = new DisplayTask();
        final String OWN_TASK = "ownTask";
        final String ASSIGNED_TASK = "assignedTask";

        boolean taskExist;
        if (taskType.equals(OWN_TASK)) {
            taskExist = displayUserTask.displayAllTask(currentUser, OWN_TASK);
        } else {
            taskExist = displayUserTask.displayAllTask(currentUser, ASSIGNED_TASK);
        }

        if (taskExist) {
            return Task.getTask(ip);
        } else {
            return null;
        }
    }

}
