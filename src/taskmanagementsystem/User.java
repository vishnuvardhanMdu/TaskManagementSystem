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

    public String userName, userEmail, userPassword;
    private static final Map<String, User> userList = new HashMap<>();
    public final ArrayList<Task> userTaskList;
    public final ArrayList<Task> userAssignedTaskList;

    User newUser;

    public User() {
        this.userTaskList = new ArrayList<>();
        this.userAssignedTaskList = new ArrayList<>();
    }

    public User(String userName, String userEmail, String userPassword) {
        this.userTaskList = new ArrayList<>();
        this.userAssignedTaskList = new ArrayList<>();
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public static void addUser(String userName, String userEmail, String userPassword) {
        User newUser = new User(userName, userEmail, userPassword);
        userList.put(userEmail, newUser);

    }

    public User checkSignIn(String userEmail, String userPassword) {

        if (userList.containsKey(userEmail)) {
            String password = userList.get(userEmail).userPassword;
            if (password.equals(userPassword)) {
                return userList.get(userEmail);
            }
        }
        return null;
    }

    public User getUser(String userEmail) {
        if (userList.containsKey(userEmail)) {
            return userList.get(userEmail);
        }
        return null;
    }

    public boolean emailExists(String userEmail) {
        return userList.containsKey(userEmail);
    }

//    public void printOtherUsers(String currentUserEmail) {
//        if (userList.isEmpty()) {
//            System.out.println("No users exist");
//            return;
//        }
//
//        System.out.println("List of users...");
//        Iterator<String> itr = userList.keySet().iterator();
//        while (itr.hasNext()) {
//            if (!currentUserEmail.equals(itr.next())) {
//                System.out.println(itr.next());
//            }
//        }
//        System.out.println();
//    }
    public static void printUsers() {
        System.out.println("List of users...");
        Iterator<String> itr = userList.keySet().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());

        }
        System.out.println();
    }

    public void addTask(Task newTask) {
        userTaskList.add(newTask);

    }

    public void addAssignedTask(Task assignedTask) {
        userAssignedTaskList.add(assignedTask);
    }

    public Task getUserTask(int taskNumber, String taskList) {
        final String OWN_TASK = "ownTask";
        final String ASSIGNED_TASK = "assignedTask";

        if (taskList.equals(OWN_TASK)) {
            if (taskNumber <= 0 || taskNumber > userTaskList.size()) {
                return null;
            }
            return this.userTaskList.get(taskNumber - 1);
        } else {
            if (taskNumber <= 0 || taskNumber > userAssignedTaskList.size()) {
                return null;
            }
            return this.userAssignedTaskList.get(taskNumber - 1);

        }

    }

}
