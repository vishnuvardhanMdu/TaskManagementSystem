/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
public class Task {

    private String taskName, taskDescription, assignedTo;
    final private String taskOwner, taskId;
    private int taskCompletedLevel;
    private Date taskDueDate, taskStartDate, taskCompletionDate;
    private Category taskCategory;
    private Priority taskPriority;
    private State taskState;
    private final static Map<String, Task> taskList = new HashMap<>();
    private static int taskCount = 1;

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

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public int getTaskCompletedLevel() {
        return taskCompletedLevel;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public Date getTaskCompletionDate() {
        return taskCompletionDate;
    }

    public Category getTaskCategory() {
        return taskCategory;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }

    public State getTaskState() {
        return taskState;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setTaskCompletedLevel(int taskCompletedLevel) {
        this.taskCompletedLevel = taskCompletedLevel;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public void setTaskCompletionDate(Date taskCompletionDate) {
        this.taskCompletionDate = taskCompletionDate;
    }

    public void setTaskCategory(Category taskCategory) {
        this.taskCategory = taskCategory;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setTaskState(State taskState) {
        this.taskState = taskState;
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

    public static Task getTask(User currentUser, String taskType) {
        String taskId;
        Task task;
        boolean invalidTaskId;
        do {
            invalidTaskId = false;
            System.out.println("Enter task id");
            taskId = TaskManagementSystem.ip.next();
            task = Task.getUserTask(taskId);
            if (task == null || (taskType.equals("ownTask") && !task.taskOwner.equals(currentUser.getUserEmail()))
                    || (taskType.equals("assignedTask") && !task.assignedTo.equals(currentUser.getUserEmail()))) {
                invalidTaskId = true;
                System.err.println("Invalid task Id");
            }
        } while (invalidTaskId);
        return task;
    }

    public static String assignTask() {
        User.printUsers();
        String assignedUserId;
        boolean incorrectUser;
        System.out.println("Task assigned to ");
        do {
            incorrectUser = false;
            System.out.print("Enter user id ");
            TaskManagementSystem.ip.nextLine();
            assignedUserId = TaskManagementSystem.ip.nextLine();
            if (!User.userIdExists(assignedUserId)) {
                incorrectUser = true;
                System.err.println("Incorrect user email");
            }
        } while (incorrectUser);
        return User.getUser(assignedUserId);
    }

    public static String getTaskType() {
        char displayTaskType;
        System.out.println("1. Owned Task\n2. Assigned Task");
        displayTaskType = TaskManagementSystem.ip.next().charAt(0);
        return (displayTaskType == '1') ? "ownTask" : "assignedTask";

    }

    public static ArrayList<Task> getUserTaskMap(User currentUser, String taskType) {

//        if (taskType.equals("ownTask")) {
//            
//            return taskList.values().stream()
//                    .filter(task -> task.taskOwner.equals(currentUser.userEmail))
//                    .collect(Collectors.toList());
//        } else {
//            return taskList.values().stream()
//                    .filter(task -> task.assignedTo != null && task.assignedTo.equals(currentUser.userEmail))
//                    .collect(Collectors.toList());
//        }
//        
        ArrayList<Task> list = new ArrayList<>();

        if (taskType.equals("ownTask")) {
            for (Task task : taskList.values()) {
                if (task.taskOwner.equals(currentUser.getUserEmail())) {
                    list.add(task);
                }
            }
        } else {
            for (Task task : taskList.values()) {
                if (task.assignedTo != null && task.assignedTo.equals(currentUser.getUserEmail())) {
                    list.add(task);
                }
            }
        }

        return list;

    }

    public static Task getUserTask(String taskId) {
        return taskList.getOrDefault(taskId, null);
    }

    public static int gettaskCount() {
        return taskCount++;
    }

    public static Task displayAndGetTask(User currentUser, String taskType) {

        boolean taskExist;
        if (taskType.equals("ownTask")) {
            taskExist = TaskVisualization.displayAllTask(currentUser, "ownTask");
        } else {
            taskExist = TaskVisualization.displayAllTask(currentUser, "assignedTask");
        }

        if (taskExist) {
            return Task.getTask(currentUser, taskType);
        } else {
            return null;
        }
    }

    public static void deleteTask(String taskId) {
        taskList.remove(taskId);
    }
}
