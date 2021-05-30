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
public class TaskManagementSystem {

    /**
     * @param args the command line arguments
     */
    static User currentUser = new User();

    static Scanner ip = new Scanner(System.in);

    public static void main(String[] args) {
        TaskManagementSystem taskManagementSystem = new TaskManagementSystem();
        System.out.println("****** WELCOME TO TASK MANAGEMENT APP ******");
        char signInChoice;
        Application:
        do {
//            User.printUsers();

            System.out.println("\na. SignIn\nb. SignUp\nQ. Close App");
            System.out.println("\n========================================\nEnter an option\n========================================");

            signInChoice = ip.next().charAt(0);

            switch (signInChoice) {
                case 'a' -> {
                    System.out.println("Current Useremail "+currentUser.userEmail);
                    char quitSignIn;
                    Login login = new Login(currentUser, ip);
                    do {
                        User user = login.signIn();
                        if (user == null) {
                            System.err.println("Incorrect username or password");
                        } else {
                            System.out.println("\nLogged in successfully :) \n");
                            taskManagementSystem.taskManagementApp(user);
                        }
                        System.out.println("Do you want to quit SignIn\nIf YES press 'Q' else press 'N' ");
                        quitSignIn = ip.next().charAt(0);
                    } while (quitSignIn != 'Q');

                }
                case 'b' -> {
//                    SignUp signUp = new SignUp(currentUser, ip);
                    SignUp signUp = new SignUp();

                    signUp.newSignUp(ip);

                }
                case 'Q' -> {

                    break Application;
                }
                default ->
                    System.err.println("\nIncorrect input Try Again :( ");
            }
        } while (signInChoice != 'Q');

    }

    public void taskManagementApp(User user) {

        System.out.println("WELCOME " + user.userName + " !!! ");
        char userChoice;
        do {
            System.out.println("\n1. Create Task\n2. Display Task\n3. Update Task\nQ. Logout");
            System.out.println("\n========================================\nEnter an option\n========================================");

            userChoice = ip.next().charAt(0);

            switch (userChoice) {
                case '1' -> {
                    Task task = new Task(user, ip);
                    System.out.println("\n1. Create new task\n2. Copy exisiting task");
                    char createTaskType = ip.next().charAt(0);
                    if (createTaskType == '1') {
                        task.createNewTask();
                    } else if (createTaskType == '2') {
                        task.copyTask();

                    }
                }
                case '2' -> {
                    DisplayUserTask displayUserTask = new DisplayUserTask(ip, user);
                    displayUserTask.displayTask();
                }

                case '3' -> {

                    UpdateTask updateTask = new UpdateTask(user, ip);
                    updateTask.updateOldTask();
                }

                case 'Q' -> {
                    return;
                }
                default ->
                    System.out.println("\nIncorrect input Try Again :( ");
            }

        } while (userChoice != 'Q');

    }

}
