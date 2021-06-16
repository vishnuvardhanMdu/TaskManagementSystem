/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

/**
 *
 * @author Administrator
 */
public class TaskDeletion {

    public static void deteleTask(User currentUser) {

        System.out.println("Select the task to detele");
        Task task = Task.displayAndGetTask(currentUser, "ownTask");
        if (task == null) {
            System.err.println("No existing task");
            return;
        }
        
        if(!currentUser.getUserEmail().equals(task.getTaskOwner())){
            System.err.println("Only task owner can only delete task");
            return;
        }
        System.out.println("Are you sure do you want to delete " + task.getTaskName() + "\n1. Yes\n2. No");
        char confirmDeleteTask = TaskManagementSystem.ip.next().charAt(0);
        if (confirmDeleteTask == '1') {
            Task.deleteTask(task.getTaskId());
            System.out.println("Task deleted successfully");
        } else if (confirmDeleteTask == '2') {
            System.out.println("Task deletion aborted");
        }
    }

}
