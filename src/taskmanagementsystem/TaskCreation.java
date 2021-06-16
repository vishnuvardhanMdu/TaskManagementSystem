/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class TaskCreation {

    public static void createNewTask(User currentUser) {
        System.out.println("---------------------------------------------------\n");
        System.out.println("Enter task details ");
        String taskName = getTaskDetails("Task Name");
        String taskDescription = getTaskDetails("Task Description");

        Task.Category taskCategory = getTaskCategoryInput();
        Task.Priority taskPriority = getTaskPriority();

        System.out.println("Do you want to assign task \n1. YES\n2. NO");
        char assignTaskOwnerInterest = TaskManagementSystem.ip.next().charAt(0);

        String assignedTo;
        Task.State taskState;
        if (assignTaskOwnerInterest == '1') {
            assignedTo = Task.assignTask();
            taskState = Task.State.ASSIGNED;
        } else {
            assignedTo = null;
            taskState = Task.State.INACTIVE;
        }

        String taskOwner = currentUser.getUserEmail();
        int taskCompletedLevel = 0;
        Date taskStartDate, taskDueDate;

        boolean invalidDate;
        do {
            invalidDate = false;
            taskStartDate = getDateInput("Start Date");
            taskDueDate = getDateInput("Due Date");
            if (validateTaskDates(taskStartDate, taskDueDate)) {
                System.err.println("Start date must be before the due date");
                invalidDate = true;
            }
        } while (invalidDate);

        Date taskCompletionDate = null;
        String taskId = generateTaskId();
        Task newTask = new Task(taskName, taskDescription, taskId, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        Task.addTask(taskId, newTask);
        System.out.println(taskName + " created successfully :) ");

    }

    public static void copyTask(User currentUser) {

        Task oldTask = Task.displayAndGetTask(currentUser, "ownTask");

        if (oldTask == null) {
            System.err.println("No existing task");
            return;
        }

        String taskName = oldTask.getTaskName();
        String taskDescription = oldTask.getTaskDescription();
        Task.Category taskCategory = oldTask.getTaskCategory();
        Task.Priority taskPriority = oldTask.getTaskPriority();
        String assignedTo = oldTask.getAssignedTo();
        Task.State taskState = oldTask.getTaskState();
        Date taskStartDate = oldTask.getTaskStartDate();
        Date taskDueDate = oldTask.getTaskDueDate();

        TaskModification:
        do {
            System.out.println("Select the details that you want to change from old task"
                    + "\n1. Task Name \n2. Task Description\n3. Assign Task\n4. Task Start/Due Date"
                    + "\n5. Task Category\n6. Task Priority\n7. Create task\n");
            char userChoice = TaskManagementSystem.ip.next().charAt(0);
            switch (userChoice) {

                case '1' -> {
                    taskName = getTaskDetails("Task Name");

                }
                case '2' -> {
                    taskDescription = getTaskDetails("Task Description");
                }
                case '3' -> {
                    System.out.println("Do you want to assign task \n1. YES\n2. NO");
                    char assignTaskOwnerInterest = TaskManagementSystem.ip.next().charAt(0);
                    if (assignTaskOwnerInterest == '1') {
                        assignedTo = Task.assignTask();
                        taskState = Task.State.ASSIGNED;
                    } else {
                        assignedTo = null;
                        taskState = Task.State.INACTIVE;
                    }
                }
                case '4' -> {
                    do {
                        taskStartDate = getDateInput("Start Date");
                        taskDueDate = getDateInput("Due Date");
                        if (validateTaskDates(taskStartDate, taskDueDate)) {
                            System.err.println("Start date must be before the due date");
                        }
                    } while (validateTaskDates(taskStartDate, taskDueDate));
                }
                case '5' -> {
                    taskCategory = getTaskCategoryInput();
                }
                case '6' -> {
                    taskPriority = getTaskPriority();

                }
                case '7' -> {
                    break TaskModification;
                }

                default ->
                    System.err.println("\nIncorrect input Try Again :( ");

            }

        } while (true);

        int taskCompletedLevel = 0;
        String taskOwner = currentUser.getUserEmail();

        Date taskCompletionDate = null;
        String taskId = generateTaskId();

        Task newTask = new Task(taskName, taskDescription, taskId, taskOwner, assignedTo, taskCompletedLevel, taskStartDate, taskDueDate, taskCompletionDate, taskCategory, taskPriority, taskState);
        Task.addTask(taskId, newTask);

        System.out.println(taskName + " created successfully :) ");
    }

    public static String getTaskDetails(String inputString) {
        System.out.println("Enter " + inputString + " :");
        TaskManagementSystem.ip.nextLine();
        return TaskManagementSystem.ip.nextLine();
    }

    public static Task.Category getTaskCategoryInput() {
        int listNumber = 1;
        System.out.println("Category list");

        for (Task.Category categoryName : Task.Category.values()) {
            System.out.println((listNumber++) + ". " + categoryName);
        }
        int categoryNumber;
        do {
            System.out.println("Enter category number:");
            categoryNumber = TaskManagementSystem.ip.nextInt();
        } while (getCategory(categoryNumber) == null);
        return getCategory(categoryNumber);
    }

    public static Task.Category getCategory(int categoryNumber) {

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

    public static Task.Priority getPriority(int priorityNumber) {
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

    public static Task.Priority getTaskPriority() {
        int listNumber = 1;
        System.out.println("Priority list");
        for (Task.Priority priorityName : Task.Priority.values()) {
            System.out.println((listNumber++) + ". " + priorityName);
        }
        int priorityNumber;
        do {
            System.out.println("Enter priority number:");
            priorityNumber = TaskManagementSystem.ip.nextInt();
        } while (getPriority(priorityNumber) == null);
        return getPriority(priorityNumber);
    }

    public static Date getDateInput(String dateType) {
        boolean vaidDate;
        Date date = null;

        do {
            System.out.println("Enter task " + dateType + "(dd/MM/yyyy) ");
            try {
                String startDate = TaskManagementSystem.ip.next();
                date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                vaidDate = true;
            } catch (ParseException e) {
                System.err.println("Invalid date input ");
                vaidDate = false;
            }
        } while (!vaidDate);

        return date;
    }

    public static boolean validateTaskDates(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    static String generateTaskId() {
        return "TK" + String.valueOf(Task.gettaskCount());
    }
}
