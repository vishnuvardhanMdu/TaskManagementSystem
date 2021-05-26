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
        Application:
        for (;;) {
//            User.printUsers();
            
            
            System.out.println("\nA. SignIn\nB. SignUp\nQ. Quit");
            System.out.println("\n========================================\nEnter an option\n========================================");

            char signInChoice = ip.next().charAt(0);
            switch (signInChoice) {
                case 'A' -> {
                    
                    Login login = new Login(currentUser, ip);
                    User user = login.signIn();
                    if (user != null) {
                        System.out.print("\nLogged in successfully :) \n");
                        taskManagementSystem.taskManagementApp(user);
                        
                    }
                }
                case 'B' -> {
                    SignUp signUp = new SignUp(currentUser, ip);
                    signUp.newSignUp();

                }
                case 'Q' -> {
                    
                    break Application;
                }
                default ->
                    System.err.println("\nIncorrect input Try Again :( ");
            }
        }

    }

    public void taskManagementApp(User user) {
        Task task = new Task(user, ip);

        System.out.println("WELCOME " + user.userName + " !!! ");
        for (;;) {
            System.out.println("\n1. Create Task\n2. Display Task\nQ. Logout");
            System.out.println("\n========================================\nEnter an option\n========================================");

            char userChoice = ip.next().charAt(0);

            switch (userChoice) {
                case '1' -> {
                    System.out.println("create task");
                    task.createTask();
                }
                case '2' -> {
                    System.out.println("display task");
                }
                case 'Q' -> {
                    return;
                }
                default ->
                    System.out.println("\nIncorrect input Try Again :( ");
            }

        }

    }

}
