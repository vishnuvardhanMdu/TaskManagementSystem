/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private String taskName, taskDescription;
    private String taskOwner, assignedTo;
    private int taskCompletedLevel;
    private Date taskStartDate, taskDueDate, taskCompletionDate;
    private Category taskCategory;
    private Priority taskPriority;
    private Status taskStatus;
    private enum Category {
        PLANNING, DEFINING_REQUIREMENT, DESIGNING, DEVELOPING, TESTING, DEPLOYING, MAINTAINING
    };

    private enum Priority {
        VERY_HIGH, HIGH, MEDIUM, LOW
    };

    private enum Status {
        INACTIVE, READY, ASSIGNED, PAUSED, VERIFIED, EXPIRED, TERMINATED, FAILED, FORWARDED, FINISHED, COMPLETED
    };
    private final ArrayList<Task> taskList = new ArrayList<>();

    public Task(User currentUser, Scanner ip) {
        this.currentUser = currentUser;
        this.ip = ip;
    }

    public Task(String taskName, String taskDescription, String taskOwner, String assignedTo, int taskCompletedLevel, Date taskStartDate, Date taskDueDate, Date taskCompletionDate, Category taskCategory, Priority taskPriority, Status taskStatus) {
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
        this.taskStatus = taskStatus;
    }

    public void createTask() {

        System.out.println("Enter Task Name :");
        ip.nextLine();
        taskName = ip.nextLine();

        System.out.println("Enter Task Description:");
        taskDescription = ip.nextLine();

        int listNumber = 1;
        for (Category categoryName : Category.values()) {
            System.out.println((listNumber++) + ". " + categoryName);
        }
        int categoryNumber;
        do {
            System.out.println("Enter category number:");
            categoryNumber = ip.nextInt();
        } while (getCategory(categoryNumber) == null);
        taskCategory = getCategory(categoryNumber);

        listNumber = 1;
        for (Priority priorityName : Priority.values()) {
            System.out.println((listNumber++) + ". " + priorityName);
        }
        int priorityNumber;
        do {
            System.out.println("Enter priority number:");
            priorityNumber = ip.nextInt();
        } while (getPriority(priorityNumber) == null);
        taskPriority = getPriority(priorityNumber);

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = ip.next().charAt(0);
        if (assignTaskOwnerInterest == '1') {
            currentUser.printOtherUsers(currentUser.userEmail);
            String userAssignedEmail;
            System.out.println("Task assigned to ");
            do {
                System.out.print("Enter user email ");
                ip.nextLine();
                userAssignedEmail = ip.nextLine();
            } while (!currentUser.emailExists(userAssignedEmail));
            assignedTo = userAssignedEmail;
            taskStatus = Status.ASSIGNED;
        } else {
            assignedTo = null;
            taskStatus = Status.INACTIVE;
        }

        taskOwner = currentUser.userEmail;
        taskCompletedLevel = 0;

        boolean vaidDate;
        do {
            try {
                System.out.print("Enter task due date(dd/MM/yyyy) ");
                String dueDate = ip.nextLine();
                taskDueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);

                vaidDate = true;
            } catch (ParseException e) {
                System.out.println("Invalid data input ");
                vaidDate = false;
            }
        } while (!vaidDate);
        try {
            LocalDateTime now = LocalDateTime.now();
            taskStartDate = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss").parse(String.valueOf(now));

        } catch (ParseException e) {
        }
        
        taskList.add(new Task(taskName, taskDescription, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority,taskStatus));
        System.out.println(taskName+" created successfully :) ");
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

}
