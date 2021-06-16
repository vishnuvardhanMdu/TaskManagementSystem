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
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
public class TaskVisualization {

    public static boolean displayAllTask(User currentUser, String taskType) {

        ArrayList<Task> userTaskList = getTaskMap(currentUser, taskType);

        if (userTaskList == null || userTaskList.isEmpty()) {
            System.out.println("No Task available");
            return false;
        }

        System.out.println("TaskId.   Task Name");
        userTaskList.forEach(
                task -> System.out.println(task.getTaskId() + ".     " + task.getTaskName()));

        return true;
    }

    public static void displayTask(User currentUser) {

        String taskType = Task.getTaskType();
        ArrayList<Task> userTaskList = getTaskMap(currentUser, taskType);

        if (userTaskList == null || userTaskList.isEmpty()) {
            System.out.println("No Task available");
            return;
        }

        char sortBy;
        System.out.println("View by\n1. Priority\n2. Due Date\n3. State\n4. Category\n5. View Task Details");
        sortBy = TaskManagementSystem.ip.next().charAt(0);

        switch (sortBy) {

            case '1' -> {
                displaySortedTask(currentUser, "priority", taskType, userTaskList);
            }
            case '2' -> {
                displaySortedTask(currentUser, "category", taskType, userTaskList);
            }
            case '3' -> {
                displaySortedTask(currentUser, "state", taskType, userTaskList);
            }
            case '4' -> {
                displaySortedTask(currentUser, "dueDate", taskType, userTaskList);
            }

            default -> {
                if (displayAllTask(currentUser, taskType)) {
                    viewTaskDetails();
                }
            }
        }

    }

    public static void displaySortedTask(User currentUser, String sortBy, String taskType, ArrayList<Task> userTaskList) {

        char sortOrder;
        System.out.println("Enter Sort Order\n1. Ascending Order\n2. Descending Order");
        sortOrder = TaskManagementSystem.ip.next().charAt(0);

        System.out.println("----------------------------------------------------\n");
        switch (sortBy) {
            case "priority" -> {
                userTaskList = getPriorityFilter(userTaskList);

                if (sortOrder == '2') {
                    Collections.sort(userTaskList, (new TaskPriorityComparator()
                            .thenComparing(new TaskDueDateComparator()
                            )).reversed());
                } else {
                    Collections.sort(userTaskList, (new TaskPriorityComparator()
                            .thenComparing(new TaskDueDateComparator()
                            )));
                }
            }
            case "category" -> {
                if (sortOrder == '2') {
                    Collections.sort(userTaskList, new TaskCategoryComparator()
                            .thenComparing(new TaskDueDateComparator()
                                    .thenComparing(new TaskPriorityComparator())
                            ).reversed());
                } else {
                    Collections.sort(userTaskList, new TaskCategoryComparator()
                            .thenComparing(new TaskDueDateComparator()
                                    .thenComparing(new TaskPriorityComparator())
                            ));
                }
            }
            case "state" -> {

                if (sortOrder == '2') {
                    Collections.sort(userTaskList, new TaskStateComparator()
                            .thenComparing(new TaskDueDateComparator()
                                    .thenComparing(new TaskPriorityComparator())
                            ).reversed());
                } else {
                    Collections.sort(userTaskList, new TaskStateComparator()
                            .thenComparing(new TaskDueDateComparator()
                                    .thenComparing(new TaskPriorityComparator())
                            ));
                }
            }

            case "dueDate" -> {
                if (sortOrder == '2') {
                    Collections.sort(userTaskList, new TaskDueDateComparator()
                            .thenComparing(new TaskPriorityComparator()).reversed()
                    );
                } else {
                    Collections.sort(userTaskList, new TaskDueDateComparator()
                            .thenComparing(new TaskPriorityComparator())
                    );
                }

            }
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Task Id | Task Name | Due Date | Priority | Category | State");
        userTaskList.forEach(task -> {
            System.out.println(task.getTaskId() + "     |  " + task.getTaskName() + "  |  " + dateFormat.format(task.getTaskDueDate())
                    + "  |  " + task.getTaskPriority()
                    + "  |  " + task.getTaskCategory()
                    + "  |  " + task.getTaskState()
            );
        });

        System.out.println("----------------------------------------------------");
    }

    private static void viewTaskDetails() {
        String taskId;
        Task task;
        do {
            System.out.println("Enter task id");
            taskId = TaskManagementSystem.ip.next();
            task = Task.getUserTask(taskId);
        } while (task == null);

        System.out.println("TASK DETAILS");

        System.out.println("Task Id              : " + task.getTaskId());
        System.out.println("Task Name            : " + task.getTaskName());
        System.out.println("Task Description     : " + task.getTaskDescription());
        System.out.println("Task Owner           : " + task.getTaskOwner());
        System.out.printf("Task Assigned to     : %s\n", (task.getAssignedTo() == null) ? "Not assigned" : task.getAssignedTo());
        System.out.println("Task Category        : " + task.getTaskCategory());
        System.out.println("Task Priority        : " + task.getTaskPriority());
        System.out.println("Task State           : " + task.getTaskState());
        System.out.println("Task Completed Level : " + task.getTaskCompletedLevel() + "%");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Task Start Date      : " + dateFormat.format(task.getTaskStartDate()));
        System.out.println("Task Due Date        : " + dateFormat.format(task.getTaskDueDate()));
        System.out.printf("Task Completed Date  : %s\n", (task.getTaskCompletionDate() == null) ? "Not Completed" : dateFormat.format(task.getTaskCompletionDate()));
    }

    private static ArrayList<Task> getTaskMap(User currentUser, String taskType) {

//        if (taskType.equals("ownTask")) {
//            return (ArrayList<Task>) Task.getUserTaskMap(currentUser, "ownTask");
//        } else {
//            if (taskType.equals("assignedTask")) {
//                return (ArrayList) Task.getUserTaskMap(currentUser, "assignedTask");
//            }
//        }
        if (taskType.equals("ownTask")) {
            return Task.getUserTaskMap(currentUser, "ownTask");
        } else {
            if (taskType.equals("assignedTask")) {
                return Task.getUserTaskMap(currentUser, "assignedTask");
            }
        }
        return null;
    }

    private static ArrayList<Task> getPriorityFilter(ArrayList<Task> userTaskList) {

        System.out.println("Apply filter 1.VERY HIGH 2.HIGH 3.MEDIUM 4.LOW 5.ALL");
        char filterType = TaskManagementSystem.ip.next().charAt(0);

        switch (filterType) {
            case '1' -> {
                userTaskList = applyPriorityFilter(userTaskList, Task.Priority.VERY_HIGH);
            }
            case '2' -> {
                userTaskList = applyPriorityFilter(userTaskList, Task.Priority.HIGH);
            }
            case '3' -> {
                userTaskList = applyPriorityFilter(userTaskList, Task.Priority.MEDIUM);
            }
            case '4' -> {
                userTaskList = applyPriorityFilter(userTaskList, Task.Priority.LOW);
            }
        }
        return userTaskList;
    }

    private static ArrayList<Task> applyPriorityFilter(ArrayList<Task> userTaskList, Task.Priority priority) {
        return (ArrayList<Task>) userTaskList.stream()
                .filter(task -> task.getTaskPriority().equals(priority))
                .collect(Collectors.toList());
    }

}

class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.getTaskPriority().getValue() > t2.getTaskPriority().getValue()) {
            return -1;
        } else if (t1.getTaskPriority().getValue() < t2.getTaskPriority().getValue()) {
            return 1;
        }

        return 0;

    }
}

class TaskCategoryComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.getTaskCategory().getOrder() < t2.getTaskCategory().getOrder()) {
            return -1;
        } else if (t1.getTaskCategory().getOrder() > t2.getTaskCategory().getOrder()) {
            return 1;
        }
        return 0;
    }

}

class TaskStateComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.getTaskState().getOrder() < t2.getTaskState().getOrder()) {
            return -1;
        } else if (t1.getTaskState().getOrder() > t2.getTaskState().getOrder()) {
            return 1;
        } else {
            return 0;
        }

    }
}

class TaskDueDateComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        if (t1.getTaskDueDate().compareTo(t2.getTaskDueDate()) > 0) {
            return 1;
        } else if (t1.getTaskDueDate().compareTo(t2.getTaskDueDate()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
