/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class UpdateTask {

    User currentUser;
    Scanner ip;

    public UpdateTask(User currentUser, Scanner ip) {
        this.currentUser = currentUser;
        this.ip = ip;
    }

    public void updateOldTask() {

        Task task = new Task(currentUser, ip);
        DisplayUserTask displayUserTask = new DisplayUserTask(ip, currentUser);
        String taskType = displayUserTask.getTaskType();
        if (!displayUserTask.displayAllTask(taskType)) {
            return;
        }
        Task updateTask = task.getTask(taskType);
        System.out.println("\n1. Assign/Forward Task\n2. Task State\n3. Task Completion Level\nQ. Logout");
        System.out.println("\n--------------------------------------\nEnter an option\n--------------------------------------");
        char taskAttribute = ip.next().charAt(0);

        switch (taskAttribute) {
            case '1' -> {
                if (updateTask.taskOwner.equals(currentUser.userEmail)) {
                    if (updateTask.assignedTo == null) {
                        updateTask.taskState = Task.State.ASSIGNED;
                    } else {
                        updateTask.taskState = Task.State.FORWARDED;
                    }
                    updateTask.assignedTo = task.assignTask();
                    System.out.println("Task assigned/forwarded successfully");

                } else {
                    System.out.println("Only task owner can assign/forward task ");
                }

            }
            case '2' -> {
                if (updateTask.taskOwner.equals(currentUser.userEmail)) {

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
                        stateNumber = ip.nextInt();
                    } while (Task.getState(stateNumber) == null);
                    updateTask.taskState = Task.getState(stateNumber);

                    System.out.println("Task state updated successfully");

                } else {
                    System.out.println("Only task owner can update task state");
                }

            }
            case '3' -> {
                if (updateTask.assignedTo == null) {
                    System.out.println("Task not assigned");
                    return;
                }

                if (updateTask.assignedTo.equals(currentUser.userEmail)) {
                    int level;
                    do {
                        System.out.println("Enter task Completion Level (0-100)");
                        level = ip.nextInt();
                    } while (level < 0 || level > 100);

                    updateTask.taskCompletedLevel = level;
                    System.out.println("Task level updated successfully");
                } else {
                    System.out.println("Only task assignie can update task level");
                }
            }

            case 'Q' -> {
                return;
            }

            default ->
                System.out.println("\nIncorrect input Try Again :( ");
        }
    }

}