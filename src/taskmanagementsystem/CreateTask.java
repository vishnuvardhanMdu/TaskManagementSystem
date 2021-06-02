/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class CreateTask {

    public void createNewTask(User currentUser, Scanner ip) {
        System.out.println("---------------------------------------------------\n");
        System.out.println("Enter task details ");
        String taskName = getTaskInput("Task Name", ip);
        String taskDescription = getTaskInput("Task Description", ip);

        Task.Category taskCategory = getTaskCategory(ip);
        Task.Priority taskPriority = getTaskPriority(ip);

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = ip.next().charAt(0);

        String assignedTo;
        Task.State taskState;
        if (assignTaskOwnerInterest == '1') {
            assignedTo = Task.assignTask(ip);
            taskState = Task.State.ASSIGNED;
        } else {
            assignedTo = null;
            taskState = Task.State.INACTIVE;
        }

        String taskOwner = currentUser.userEmail;
        int taskCompletedLevel = 0;
        Date taskStartDate, taskDueDate;

        boolean invalidDate;
        do {
            invalidDate = false;
            taskStartDate = getDateInput("Start Date", ip);
            taskDueDate = getDateInput("Due Date", ip);
            if (validateTaskDates(taskStartDate, taskDueDate)) {
                System.err.println("Start date must be before the due date");
                invalidDate = true;
            }
        } while (invalidDate);

        Date taskCompletionDate = null;
        String taskId = generateTaskId(taskName);
        Task newTask = new Task(taskName, taskDescription, taskId, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        Task.addTask(taskId, newTask);
        System.out.println(taskName + " created successfully :) ");

    }

    public void copyTask(User currentUser, Scanner ip) {
        final String OWN_TASK = "ownTask";

        Task oldTask = Task.displayGetTask(currentUser, ip, OWN_TASK);

        if (oldTask == null) {
            System.err.println("No existing task");
            return;
        }
        String taskName = getTaskInput("Task Name", ip);
        String taskDescription = getTaskInput("Task Description", ip);
        Task.Category taskCategory = oldTask.taskCategory;
        Task.Priority taskPriority = oldTask.taskPriority;

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = ip.next().charAt(0);
        String assignedTo;
        Task.State taskState;
        if (assignTaskOwnerInterest == '1') {
            assignedTo = Task.assignTask(ip);
            taskState = Task.State.ASSIGNED;
        } else {
            assignedTo = null;
            taskState = Task.State.INACTIVE;
        }
        int taskCompletedLevel = 0;
        String taskOwner = currentUser.userEmail;

        Date taskStartDate;
        Date taskDueDate;

        do {
            taskStartDate = getDateInput("Start Date", ip);
            taskDueDate = getDateInput("Due Date", ip);
            if (validateTaskDates(taskStartDate, taskDueDate)) {
                System.err.println("Start date must be before the due date");
            }
        } while (validateTaskDates(taskStartDate, taskDueDate));

        Date taskCompletionDate = null;
        String taskId = generateTaskId(taskName);

        Task newTask = new Task(taskName, taskDescription, taskId, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        Task.addTask(taskId, newTask);

        System.out.println(taskName + " created successfully :) ");
    }

    private String getTaskInput(String inputString, Scanner ip) {
        System.out.println("Enter " + inputString + " :");
        ip.nextLine();
        return ip.nextLine();
    }

    private Task.Category getTaskCategory(Scanner ip) {
        int listNumber = 1;
        System.out.println("Category list");

        for (Task.Category categoryName : Task.Category.values()) {
            System.out.println((listNumber++) + ". " + categoryName);
        }
        int categoryNumber;
        do {
            System.out.println("Enter category number:");
            categoryNumber = ip.nextInt();
        } while (getCategory(categoryNumber) == null);
        return getCategory(categoryNumber);
    }

    private Task.Category getCategory(int categoryNumber) {

        switch (categoryNumber) {

            case 1 -> {
                return Task.Category.PLANNING;
            }
            case 2 -> {
                return Task.Category.DEFINING_REQUIREMENT;
            }
            case 3 -> {
                return Task.Category.DESIGNING;
            }
            case 4 -> {
                return Task.Category.DEVELOPING;
            }
            case 5 -> {
                return Task.Category.TESTING;
            }
            case 6 -> {
                return Task.Category.DEPLOYING;
            }
            case 7 -> {
                return Task.Category.MAINTAINING;
            }

            default -> {
                return null;
            }
        }
    }

    private Task.Priority getPriority(int priorityNumber) {
        switch (priorityNumber) {
            case 1 -> {
                return Task.Priority.VERY_HIGH;
            }
            case 2 -> {
                return Task.Priority.HIGH;
            }
            case 3 -> {
                return Task.Priority.MEDIUM;
            }
            case 4 -> {
                return Task.Priority.LOW;
            }
            default -> {
                return null;
            }
        }
    }

    private Task.Priority getTaskPriority(Scanner ip) {
        int listNumber = 1;
        System.out.println("Priority list");
        for (Task.Priority priorityName : Task.Priority.values()) {
            System.out.println((listNumber++) + ". " + priorityName);
        }
        int priorityNumber;
        do {
            System.out.println("Enter priority number:");
            priorityNumber = ip.nextInt();
        } while (getPriority(priorityNumber) == null);
        return getPriority(priorityNumber);
    }

    private Date getDateInput(String dateType, Scanner ip) {
        boolean vaidDate;
        Date date = null;

        do {
            System.out.println("Enter task " + dateType + "(dd/MM/yyyy) ");
            try {
                String startDate = ip.next();
                date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                vaidDate = true;
            } catch (ParseException e) {
                System.err.println("Invalid date input ");
                vaidDate = false;
            }
        } while (!vaidDate);

        return date;
    }

    private boolean validateTaskDates(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    public String generateTaskId(String taskName) {
        return taskName.substring(0, 2).toUpperCase() + String.valueOf(Task.gettaskCount());
    }

}
