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
public class DisplayTask {

    public boolean displayAllTask(User currentUser, String taskType) {

        ArrayList<Task> filteredUserTaskList = getTaskMap(currentUser, taskType);

        if (filteredUserTaskList == null || filteredUserTaskList.isEmpty()) {
            System.out.println("No Task available");
            return false;
        }

        System.out.println("TaskId.   Task Name");
        filteredUserTaskList.forEach(
                task -> System.out.println(task.taskId + ".     " + task.taskName));

        return true;
    }

    public void displayTask(User currentUser, Scanner ip) {

        final String BY_PRIORITY = "priority";
        final String BY_DUE_DATE = "dueDate";
        final String BY_CATEGORY = "category";
        final String BY_STATE = "state";

        String taskType = Task.getTaskType(ip);
        char sortBy;
        System.out.println("View by\n1. Priority\n2. Due Date\n3. State\n4. Category\n5. View Task Details");
        sortBy = ip.next().charAt(0);

        switch (sortBy) {

            case '1' -> {
                displaySortedTask(currentUser, BY_PRIORITY, taskType);
            }
            case '2' -> {
                displaySortedTask(currentUser, BY_CATEGORY, taskType);
            }
            case '3' -> {
                displaySortedTask(currentUser, BY_STATE, taskType);
            }
            case '4' -> {
                displaySortedTask(currentUser, BY_DUE_DATE, taskType);
            }

            default -> {
                if (displayAllTask(currentUser, taskType)) {
                    viewTaskDetails(currentUser, ip, taskType);
                }
            }

        }

    }

    public void displaySortedTask(User currentUser, String filter, String taskType) {
        final String BY_PRIORITY = "priority";
        final String BY_DUE_DATE = "dueDate";
        final String BY_CATEGORY = "category";
        final String BY_STATE = "state";

        ArrayList<Task> filteredUserTaskList = getTaskMap(currentUser, taskType);

        if (filteredUserTaskList == null || filteredUserTaskList.isEmpty()) {
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

        System.out.println("Task Id | Task Name | Due Date | Priority | Category | State");
        filteredUserTaskList.forEach(task -> {
            System.out.println(task.taskId + "     |  " + task.taskName + "  |  " + dateFormat.format(task.taskDueDate)
                    + "  |  " + task.taskPriority
                    + "  |  " + task.taskCategory
                    + "  |  " + task.taskState
            );
        });

        System.out.println("----------------------------------------------------");
    }

    private void viewTaskDetails(User currentUser, Scanner ip, String taskType) {
        String taskId;
        Task task;
        do {
            System.out.println("Enter task id");
            taskId = ip.next();
            task = Task.getUserTask(taskId);
        } while (task == null);

        System.out.println("TASK DETAILS");

        System.out.println("Task Id              : " + task.taskId);
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

    private ArrayList<Task> getTaskMap(User currentUser, String taskType) {
        final String OWN_TASK = "ownTask";
        final String ASSIGNED_TASK = "assignedTask";
        if (taskType.equals(OWN_TASK)) {
            return (ArrayList<Task>) Task.getUserTaskMap(currentUser, OWN_TASK);
        } else {
            if (taskType.endsWith(ASSIGNED_TASK)) {
                return (ArrayList<Task>) Task.getUserTaskMap(currentUser, ASSIGNED_TASK);
            }
        }

        return null;
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
