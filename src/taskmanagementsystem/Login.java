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

    User currentUser;
    Scanner ip;

    public Login(User currentUser, Scanner ip) {
        this.currentUser = currentUser;
        this.ip = ip;
    }

    public User signIn() {
        System.out.print("Enter Email : ");
        String userEmail = ip.next();
        System.out.print("Enter Password : ");
        String userPassword = ip.next();
        return currentUser.checkSignIn(userEmail, userPassword);
    }

}
