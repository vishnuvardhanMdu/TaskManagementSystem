/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class User {

    public String userName, userEmail, userPassword;
    private static final Map<String, User> userMapDetails = new HashMap<>();

    public User() {
    }

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public static void addUser(String userName, String userEmail, String userPassword) {
        User newUser = new User(userName, userEmail, userPassword);
        userMapDetails.put(userEmail, newUser);

    }

    public static User checkSignIn(String userEmail, String userPassword) {

        if (userMapDetails.containsKey(userEmail)) {
            String password = userMapDetails.get(userEmail).userPassword;
            if (password.equals(userPassword)) {
                return userMapDetails.get(userEmail);
            }
        }
        return null;
    }

    public static User getUser(String userEmail) {
        if (userMapDetails.containsKey(userEmail)) {
            return userMapDetails.get(userEmail);
        }
        return null;
    }

    public static boolean emailExists(String userEmail) {
        return userMapDetails.containsKey(userEmail);
    }

    public static void printUsers() {
        System.out.println("List of users...");
        Iterator<String> itr = userMapDetails.keySet().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());

        }
        System.out.println();
    }

}
