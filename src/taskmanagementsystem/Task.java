/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class Task {

    User currentUser;
    Scanner ip;
    public String taskName, taskDescription;
    public String taskOwner, assignedTo;
    public int taskCompletedLevel;
    public Date taskDueDate;
    public Date taskStartDate, taskCompletionDate;
    public Category taskCategory;
    public Priority taskPriority;
    public State taskState;

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
//        INACTIVE(1), READY(2), ASSIGNED(3), PAUSED(4), VERIFIED(5), EXPIRED(6), TERMINATED(7), FAILED(8), FORWARDED(9), FINISHED(10), COMPLETED(11);
        INACTIVE(1), READY(2), ASSIGNED(3), PAUSED(4), FINISHED(5), FAILED(6), FORWARDED(7), TERMINATED(8), EXPIRED(9), COMPLETED(10);

        private final int order;

        private State(int order) {
            this.order = order;
        }

        public int getOrder() {
            return this.order;
        }

    };
    private final static ArrayList<Task> taskList = new ArrayList<>();

    public Task(User currentUser, Scanner ip) {
        this.currentUser = currentUser;
        this.ip = ip;
    }

    public Task(String taskName, String taskDescription, String taskOwner, String assignedTo, int taskCompletedLevel, Date taskStartDate, Date taskDueDate, Date taskCompletionDate, Category taskCategory, Priority taskPriority, State taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
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

    public void createNewTask() {
        System.out.println("---------------------------------------------------\n");
        System.out.println("Enter task details ");
        taskName = getTaskInput("Task Name");
        taskDescription = getTaskInput("Task Description");
        taskCategory = getTaskCategory();
        taskPriority = getTaskPriority();

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = ip.next().charAt(0);
        if (assignTaskOwnerInterest == '1') {
            assignedTo = assignTask();
            taskState = State.ASSIGNED;
        } else {
            assignedTo = null;
            taskState = State.INACTIVE;
        }
        taskOwner = currentUser.userEmail;
        taskCompletedLevel = 0;
        taskStartDate = getDateInput("Start Date");
        taskDueDate = getDateInput("Due Date");

        Task newTask = new Task(taskName, taskDescription, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        taskList.add(newTask);
        currentUser.addTask(newTask);

        if (assignedTo != null) {
            User assignedUser = currentUser.getUser(assignedTo);
            if (assignedUser != null) {
                assignedUser.addAssignedTask(newTask);
            }
        }

        System.out.println(taskName + " created successfully :) ");
        System.out.println("---------------------------------------------------\n");

    }

    public void copyTask() {
        if (currentUser.userTaskList.isEmpty()) {
            System.err.println("No existing task");
            return;
        }

        Task oldTask = getOwnTask();

        taskName = getTaskInput("Task Name");

        taskDescription = getTaskInput("Task Description");;
        taskCategory = oldTask.taskCategory;
        taskPriority = oldTask.taskPriority;

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = ip.next().charAt(0);

        if (assignTaskOwnerInterest == '1') {
            assignedTo = assignTask();
            taskState = State.ASSIGNED;
        } else {
            assignedTo = null;
            taskState = State.INACTIVE;
        }
        taskCompletedLevel = 0;
        taskOwner = currentUser.userEmail;
        taskStartDate = getDateInput("Start Date");
        taskDueDate = getDateInput("Due Date");

        Task newTask = new Task(taskName, taskDescription, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        taskList.add(newTask);
        currentUser.addTask(newTask);

        if (assignedTo != null) {
            User assignedUser = currentUser.getUser(assignedTo);
            if (assignedUser != null) {
                assignedUser.addAssignedTask(newTask);
            }
        }

        System.out.println(taskName + " created successfully :) ");
    }

    private Category getCategory(int categoryNumber) {

        switch (categoryNumber) {

            case 1 -> {
                return Category.PLANNING;
            }
            case 2 -> {
                return Category.DEFINING_REQUIREMENT;
            }
            case 3 -> {
                return Category.DESIGNING;
            }
            case 4 -> {
                return Category.DEVELOPING;
            }
            case 5 -> {
                return Category.TESTING;
            }
            case 6 -> {
                return Category.DEPLOYING;
            }
            case 7 -> {
                return Category.MAINTAINING;
            }

            default -> {
                return null;
            }
        }
    }

    private Priority getPriority(int priorityNumber) {
        switch (priorityNumber) {
            case 1 -> {
                return Priority.VERY_HIGH;
            }
            case 2 -> {
                return Priority.HIGH;
            }
            case 3 -> {
                return Priority.MEDIUM;
            }
            case 4 -> {
                return Priority.LOW;
            }
            default -> {
                return null;
            }
        }
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

    public static void printAllUserTask() {
        taskList.forEach(task -> {
            System.out.println(task.taskName);
        });
    }

    private String getTaskInput(String inputString) {
        System.out.println("Enter " + inputString + " :");
        ip.nextLine();
        return ip.nextLine();
    }

    private Category getTaskCategory() {
        int listNumber = 1;
        System.out.println("Category list");

        for (Category categoryName : Category.values()) {
            System.out.println((listNumber++) + ". " + categoryName);
        }
        int categoryNumber;
        do {
            System.out.println("Enter category number:");
            categoryNumber = ip.nextInt();
        } while (getCategory(categoryNumber) == null);
        return getCategory(categoryNumber);
    }

    private Priority getTaskPriority() {
        int listNumber = 1;
        System.out.println("Priority list");
        for (Priority priorityName : Priority.values()) {
            System.out.println((listNumber++) + ". " + priorityName);
        }
        int priorityNumber;
        do {
            System.out.println("Enter priority number:");
            priorityNumber = ip.nextInt();
        } while (getPriority(priorityNumber) == null);
        return getPriority(priorityNumber);
    }

    public String assignTask() {
        User.printUsers();
        String userAssignedEmail;
        System.out.println("Task assigned to ");
        do {
            System.out.print("Enter user email ");
            ip.nextLine();
            userAssignedEmail = ip.nextLine();
        } while (!currentUser.emailExists(userAssignedEmail));
        return userAssignedEmail;
    }

    private Date getDateInput(String dateType) {
        boolean vaidDate;
        Date date = null;
        do {
            try {
                System.out.println("Enter task " + dateType + "(dd/MM/yyyy) ");
                String startDate = ip.next();
                date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                vaidDate = true;
            } catch (ParseException e) {
                System.out.println("Invalid data input ");
                vaidDate = false;
            }
        } while (!vaidDate);

        return date;
    }

    public Task getOwnTask() {

        DisplayUserTask displayUserTask = new DisplayUserTask(ip, currentUser);
        final String OWN_TASK = "ownTask";
        displayUserTask.displayAllTask(OWN_TASK);
        return getTask(OWN_TASK);
    }

    public Task getTask(String taskType) {
        int taskNumber;
        do {
            System.out.println("Enter task number");
            taskNumber = ip.nextInt();
        } while (currentUser.getUserTask(taskNumber, taskType) == null);

        return currentUser.getUserTask(taskNumber, taskType);

    }

    public State updateTaskState() {
        int listNumber = 1;
        System.out.println("Task States");
        for (State stateName : State.values()) {
            System.out.println((listNumber++) + ". " + stateName);
        }
        int stateNumber;
        do {
            System.out.println("Enter state number:");
            stateNumber = ip.nextInt();
        } while (getPriority(stateNumber) == null);
        return getState(stateNumber);
    }
}
