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
    static Scanner ip = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("****** WELCOME TO TASK MANAGEMENT APP ******");
        char signInChoice;
        Application:
        do {

            System.out.println("\na. SignIn\nb. SignUp\nQ. Close App");
            System.out.println("\n========================================\nEnter an option\n========================================");

            signInChoice = ip.next().charAt(0);

            switch (signInChoice) {
                case 'a' -> {
                    char quitSignIn;
                    Login login = new Login();
                    do {
                        User user = login.signIn();
                        if (user != null) {
                            taskManagementApp(user);
                        }
                        System.out.println("Do you want to quit SignIn\nIf YES press 'Q' else press 'N' ");
                        quitSignIn = ip.next().charAt(0);
                    } while (quitSignIn != 'Q');

                }
                case 'b' -> {
                    SignUp signUp = new SignUp();
//                    signUp.newSignUp(ip);
                    signUp.generateSignUpList();

                }
                case 'Q' -> {
                    break Application;
                }
                default ->
                    System.err.println("\nIncorrect input Try Again :( ");
            }
        } while (signInChoice != 'Q');

    }

    public static void taskManagementApp(User user) {

        System.out.println("WELCOME " + user.getUserName() + " !!! ");
        char userChoice;
        do {
            System.out.println("\n1. Create Task\n2. Display Task\n3. Update Task\n4. Delete Task\nQ. Logout");
            System.out.println("\n========================================\nEnter an option\n========================================");

            userChoice = ip.next().charAt(0);

            switch (userChoice) {
                case '1' -> {
                    System.out.println("\n1. Create new task\n2. Copy exisiting task");
                    char createTaskType = ip.next().charAt(0);
                    if (createTaskType == '1') {
                        TaskCreation.createNewTask(user);
                    } else if (createTaskType == '2') {
                        TaskCreation.copyTask(user);
                    }
                }
                case '2' -> {
                    TaskVisualization.displayTask(user);
                }

                case '3' -> {
                    TaskUpdation.updateOldTask(user);
                }

                case '4' -> {
                    TaskDeletion.deteleTask(user);
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
