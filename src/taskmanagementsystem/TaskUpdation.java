/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class TaskUpdation {

    public static void updateOldTask(User currentUser) {
        System.out.println("Select the task to update");
        Task updateTask = Task.displayAndGetTask(currentUser, Task.getTaskType());

        if (updateTask == null) {
            System.err.println("No existing task");
            return;
        }

        if (updateTask.getTaskOwner().equals(currentUser.getUserEmail())) {
            System.out.println("\n1. Task Name \n2. Task Description\n3. Assign/Forward Task\n4. Task Due Date"
                    + "\n5. Task Category\n6. Task Priority\n7. Task State\nQ. Logout");
            System.out.println("\n--------------------------------------\nEnter an option\n--------------------------------------");
            char taskAttribute = TaskManagementSystem.ip.next().charAt(0);
            switch (taskAttribute) {
                case '1' -> {
                    updateTask.setTaskName(TaskCreation.getTaskDetails("New Task Name ")); 
                }
                case '2' -> {
                    updateTask.setTaskDescription(TaskCreation.getTaskDetails("New Task Description "));
                }
                case '3' -> {
                    if (updateTask.getAssignedTo() == null) {
                        updateTask.setTaskState(Task.State.ASSIGNED);
                        System.out.println("Task assigned successfully");

                    } else {
                        System.out.println("Task forwarded successfully");
                        updateTask.setTaskState(Task.State.FORWARDED);                        
                    }
                    updateTask.setAssignedTo(Task.assignTask());
                }
                case '4' -> {
                    boolean invalidDate;
                    do {
                        invalidDate = false;
                        Date taskStartDate = updateTask.getTaskStartDate();
                        Date taskDueDate = TaskCreation.getDateInput("Due Date");
                        if (TaskCreation.validateTaskDates(taskStartDate, taskDueDate)) {
                            System.err.println("Start date must be before the due date");
                            invalidDate = true;
                            continue;
                        }
                        updateTask.setTaskDueDate(taskDueDate);
                    } while (invalidDate);
                }
                case '5' -> {
                    updateTask.setTaskCategory(TaskCreation.getTaskCategoryInput());
                }
                case '6' -> {
                    updateTask.setTaskPriority(TaskCreation.getTaskPriority());
                }
                case '7' -> {
                    int listNumber = 1;
                    System.out.println("Task States");
                    for (Task.State stateName : Task.State.values()) {
                        if (stateName.equals(Task.State.READY) || stateName.equals(Task.State.ASSIGNED) || stateName.equals(Task.State.FORWARDED)) {
                            continue;
                        }
                        System.out.println((listNumber++) + ". " + stateName);
                    }
                    int stateNumber;
                    do {
                        System.out.println("Enter state number:");
                        stateNumber = TaskManagementSystem.ip.nextInt();
                    } while (Task.getState(stateNumber) == null);
                    updateTask.setTaskState(Task.getState(stateNumber));
                }
                case 'Q' -> {
                    return;
                }

                default ->
                    System.err.println("\nIncorrect input Try Again :( ");
            }

        } else if (updateTask.getAssignedTo().equals(currentUser.getUserEmail())) {
            System.out.println("Enter task completed level (0-100) ");
            boolean invalidTaskCompletionLevel;
            do{
                invalidTaskCompletionLevel = false;
                int taskCompletionLevel = TaskManagementSystem.ip.nextInt();
                if(taskCompletionLevel >100 || taskCompletionLevel <0){
                    invalidTaskCompletionLevel = true;
                    System.err.println("Enter a valid task completed level (0-100) ");
                }
                updateTask.setTaskCompletedLevel(taskCompletionLevel);
            }while(invalidTaskCompletionLevel);
        }
        System.out.println("Task " + updateTask.getTaskName()+ " updated successfully");
    }
}
