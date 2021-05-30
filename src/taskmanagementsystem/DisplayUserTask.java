/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class DisplayUserTask {

    Scanner ip;
    User currentUser;
    final String OWN_TASK = "ownTask";
    final String ASSIGNED_TASK = "assignedTask";

    public DisplayUserTask(Scanner ip, User currentUser) {
        this.ip = ip;
        this.currentUser = currentUser;
    }

    public boolean displayAllTask(String taskList) {

        if (taskList.equals(OWN_TASK) && (!currentUser.userTaskList.isEmpty())) {
            int taskNumber = 1;
            System.out.println("----------------------------------------------------");

            System.out.println("TaskNo. Task Name");
            for (Task task : currentUser.userTaskList) {
                System.out.println((taskNumber++) + ".      " + task.taskName);
            }
            System.out.println("----------------------------------------------------\n");

        } else if (taskList.equals(ASSIGNED_TASK) && (!currentUser.userAssignedTaskList.isEmpty())) {
            int taskNumber = 1;
            System.out.println("----------------------------------------------------");

            System.out.println("SNo. Task Name");
            for (Task task : currentUser.userAssignedTaskList) {
                System.out.println((taskNumber++) + ". " + task.taskName);
            }
            System.out.println("----------------------------------------------------\n");

        } else {
            System.out.println("No Task available");
            return false;
        }
        return true;
    }

    public String getTaskType() {

        char displayTaskType;
        System.out.println("1. Owned Task\n2. Assigned Task");
        displayTaskType = ip.next().charAt(0);
        return (displayTaskType == '1') ? OWN_TASK : ASSIGNED_TASK;

    }

    public void displayTask() {

        final String BY_PRIORITY = "priority";
        final String BY_DUE_DATE = "dueDate";
        final String BY_CATEGORY = "category";
        final String BY_STATE = "state";

        String taskList = getTaskType();
        char sortBy;
        System.out.println("View by\n1. Priority\n2. Due Date\n3. State\n4. Category\n5. View Task Details");
        sortBy = ip.next().charAt(0);

        switch (sortBy) {

            case '1' -> {
                printTask(BY_PRIORITY, taskList);
            }
            case '2' -> {
                printTask(BY_CATEGORY, taskList);
            }
            case '3' -> {
                printTask(BY_STATE, taskList);
            }
            case '4' -> {
                printTask(BY_DUE_DATE, taskList);
            }

            default -> {
                if (displayAllTask(taskList)) {
                    viewTask(taskList);
                }
            }

        }

    }

    public void printTask(String filter, String taskList) {

        final String BY_PRIORITY = "priority";
        final String BY_DUE_DATE = "dueDate";
        final String BY_CATEGORY = "category";
        final String BY_STATE = "state";

        ArrayList<Task> filteredUserTaskList;
        if (taskList.equals(OWN_TASK) && (!currentUser.userTaskList.isEmpty())) {
            filteredUserTaskList = (ArrayList<Task>) currentUser.userTaskList.clone();
        } else if (taskList.equals(ASSIGNED_TASK) && (!currentUser.userAssignedTaskList.isEmpty())) {
            filteredUserTaskList = (ArrayList<Task>) currentUser.userAssignedTaskList.clone();
        } else {
            System.out.println("No Task available");
            return;
        }

        System.out.println("----------------------------------------------------\n");
        switch (filter) {
            case BY_PRIORITY -> {
                Collections.sort(filteredUserTaskList, new TaskPriorityComparator()
                        .thenComparing(new TaskDueDateComparator()
                        ));
            }
            case BY_CATEGORY -> {
                Collections.sort(filteredUserTaskList, new TaskCategoryComparator()
                        .thenComparing(new TaskDueDateComparator()
                                .thenComparing(new TaskPriorityComparator())
                        ));
            }
            case BY_STATE -> {
                Collections.sort(filteredUserTaskList, new TaskStateComparator()
                        .thenComparing(new TaskDueDateComparator()
                                .thenComparing(new TaskPriorityComparator())
                        ));
            }

            case BY_DUE_DATE -> {
                Collections.sort(filteredUserTaskList, new TaskDueDateComparator()
                        .thenComparing(new TaskPriorityComparator())
                );
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Task Name | Due Date | Priority | Category | State");
        filteredUserTaskList.forEach(task -> {
            System.out.println(task.taskName + "  |  " + dateFormat.format(task.taskDueDate)
                    + "  |  " + task.taskPriority
                    + "  |  " + task.taskCategory
                    + "  |  " + task.taskState
            );
        });

        System.out.println("----------------------------------------------------");
    }

    public void viewTask(String taskList) {

        int taskNumber;
        do {
            System.out.println("Enter task number");
            taskNumber = ip.nextInt();
        } while (currentUser.getUserTask(taskNumber, taskList) == null);

        Task task = currentUser.getUserTask(taskNumber, taskList);

        System.out.println("TASK DETAILS");

        System.out.println("Task Name            : " + task.taskName);
        System.out.println("Task Description     : " + task.taskDescription);
        System.out.println("Task Owner           : " + task.taskOwner);
        System.out.printf("Task Assigned to     : %s\n", (task.assignedTo == null) ? "Not assigned" : task.assignedTo);
        System.out.println("Task Category        : " + task.taskCategory);
        System.out.println("Task Priority        : " + task.taskPriority);
        System.out.println("Task State           : " + task.taskState);
        System.out.println("Task Completed Level : " + task.taskCompletedLevel);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Task Start Date      : " + dateFormat.format(task.taskStartDate));
        System.out.println("Task Due Date        : " + dateFormat.format(task.taskDueDate));
        System.out.printf("Task Completed Date  : %s\n", (task.taskCompletionDate == null) ? "Not Completed" : dateFormat.format(task.taskCompletionDate));
    }

}

class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.taskPriority.getValue() > t2.taskPriority.getValue()) {
            return -1;
        } else if (t1.taskPriority.getValue() < t2.taskPriority.getValue()) {
            return 1;
        }

        return 0;

    }
}

class TaskCategoryComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.taskCategory.getOrder() < t2.taskCategory.getOrder()) {
            return -1;
        } else if (t1.taskCategory.getOrder() > t2.taskCategory.getOrder()) {
            return 1;
        }
        return 0;
    }

}

class TaskStateComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.taskState.getOrder() > t2.taskState.getOrder()) {
            return 1;
        } else if (t1.taskState.getOrder() < t2.taskState.getOrder()) {
            return -1;
        } else {
            return 0;
        }
    }
}

class TaskDueDateComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.taskDueDate.compareTo(t2.taskDueDate) > 0) {
            return 1;
        } else if (t1.taskDueDate.compareTo(t2.taskDueDate) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
