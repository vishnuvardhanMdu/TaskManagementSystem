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
public class Login {

    public User signIn(Scanner ip) {
        System.out.print("Enter Email : ");
        String userEmail = ip.next();
        System.out.print("Enter Password : ");
        String userPassword = ip.next();
        User user = User.checkSignIn(userEmail, userPassword);
        if (user == null) {
            System.err.println("Incorrect username or password");
        } else {
            System.out.println("\nLogged in successfully :) \n");
        }
        return user;
    }

}
