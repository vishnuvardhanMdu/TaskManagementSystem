/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class User {

    public String userName, userEmail;
    private String userPassword;
    private static final Map<String, User> userList = new HashMap<>();
    User newUser;

    public User() {

    }

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public void addUser(String userName, String userEmail, String userPassword) {
        newUser = new User(userName, userEmail, userPassword);
        userList.put(userEmail, newUser);

    }

    public User checkSignIn(String userEmail, String userPassword) {

        if (userList.containsKey(userEmail)) {
            String password = userList.get(userEmail).userPassword;
            if (password.equals(userPassword)) {
                return userList.get(userEmail);
            }
        }

        System.err.println("Incorrect username or password");
        return null;
    }

    public boolean emailExists(String userEmail) {
        return userList.containsKey(userEmail);
    }

    public void printOtherUsers(String currentUserEmail) {
        if(userList.isEmpty()){
            System.out.println("No users exist"); 
            return;
        }
        
        System.out.println("List of users...");
        Iterator<String> itr = userList.keySet().iterator();
        while (itr.hasNext()) {
            if (!currentUserEmail.equals(itr.next())) {
                System.out.println(itr.next());
            }
        }
        System.out.println();
    }

    public static void printUsers() {
        System.out.println("List of users...");
        Iterator<String> itr = userList.keySet().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());

        }
        System.out.println();

    }

}
