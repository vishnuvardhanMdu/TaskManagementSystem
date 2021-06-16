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

    private String userName, userEmail, userPassword, userId;

    private static final Map<String, User> userMapDetails = new HashMap<>();
    static int userCount=1;
 
    public User(String userName, String userEmail, String userPassword, String userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public static void addUser(String userName, String userEmail, String userPassword, String userId) {
        User newUser = new User(userName, userEmail, userPassword, userId);
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


    public static boolean userIdExists(String userId) {
        return userMapDetails.values().stream().anyMatch(user -> (user.userId.equals(userId)));
    }
   
    public static String getUser(String userId) {
        for (User user : userMapDetails.values()) {
            if (user.userId.equals(userId)) {
                return user.userEmail;
            }
        }
        return null;
    }

    public static void printUsers() {
        System.out.println("List of users...");
        Iterator<User> userIerator = userMapDetails.values().iterator();
        while (userIerator.hasNext()) {
            User currentUser = userIerator.next();
            System.out.println(currentUser.userId+". "+currentUser.userName);
        }
        System.out.println();
    }

    public static int getUserCount() {
        return userCount++;
    }
}
