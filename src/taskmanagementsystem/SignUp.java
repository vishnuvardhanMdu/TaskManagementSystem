/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class SignUp {

    public void generateSignUpList() {
        User.addUser("Vishnu", "v@gmail.com", "123", "USR100");
        User.addUser("Ram", "r@gmail.com", "123", "USR101");
        User.addUser("Kumar", "k@gmail.com", "123", "USR102");
        User.addUser("Aswin", "a@gmail.com", "123", "USR103");
        User.addUser("Sasi", "s@gmail.com", "123", "USR104");
        User.addUser("aaa", "a", "a", "USR105");
        User.addUser("bbb", "b", "b", "USR106");

    }

    public void newSignUp() {
        final String INPUT_TYPE_USER_NAME = "Name";
        final String INPUT_TYPE_USER_EMAIL = "Email";
        final String INPUT_TYPE_USER_PASSWORD = "Password";

        System.out.println("We are glad to add you in our family... :) ");
        String userName = getUserInput(INPUT_TYPE_USER_NAME);
        String userEmail = getUserInput(INPUT_TYPE_USER_EMAIL);
        String userPassword = getUserInput(INPUT_TYPE_USER_PASSWORD);
        verifyUserConfirmPassword(userPassword);
        String userId = generateUserId();
        User.addUser(userName, userEmail, userPassword, userId);
        System.out.println("SignUp Success");
    }

    public String getUserInput(String inputType) {
        String inputValue;
        do {
            System.out.print("Enter " + inputType + " : ");
            inputValue = TaskManagementSystem.ip.next();
        } while (!validateInput(inputValue, inputType));

        return inputValue;

    }

    public boolean validateInput(String input, String inputType) {
//        final String INPUT_TYPE_USER_NAME = "Name";
//        final String INPUT_TYPE_USER_EMAIL = "Email";
//        final String INPUT_TYPE_USER_PASSWORD = "Password";
//        final int MIN_USER_NAME_LENGTH = 3;
//
//        switch (inputType) {
//            case INPUT_TYPE_USER_NAME -> {
//                Pattern checkSpecialChar = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
//
//                if (input.length() < MIN_USER_NAME_LENGTH) {
//                    System.err.println("Username must be atleast 3 charaters");
//                    return false;
//                }
//                if (checkSpecialChar.matcher(input).find()) {
//                    System.err.println("Username should not have any special charaters other than underscore ");
//                    return false;
//                }
//                return true;
//            }
//            case INPUT_TYPE_USER_EMAIL -> {
//                Pattern checkValidEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
//                Matcher matcher = checkValidEmail.matcher(input);
//                if (!matcher.matches()) {
//                    System.err.println("Enter valid email");
//                    return false;
//                }
//                if (User.emailExists(input)) {
//                    System.err.println("Email id already exists");
//                    return false;
//                }
//                return true;
//            }
//            case INPUT_TYPE_USER_PASSWORD -> {
//                String regex = "^(?=.*[0-9])"
//                        + "(?=.*[a-z])(?=.*[A-Z])"
//                        + "(?=.*[@#$%^&+=])"
//                        + "(?=\\S+$).{8,20}$";
//                Pattern checkValidPassword = Pattern.compile(regex);
//                Matcher matcher = checkValidPassword.matcher(input);
//                if (!matcher.matches()) {
//                    System.err.println("Enter valid Password\nAtleast 1 Uppercase, Lowercase, Special character, length between 8 and 20 charactes");
//                    return false;
//                }
//                return true;
//            }
//
//        }
        return true;
    }

    public String verifyUserConfirmPassword(String password) {
        String inputValue;
        do {
            System.out.print("Enter Confirm password : ");
            inputValue = TaskManagementSystem.ip.next();
        } while (!validateConfirmPassword(inputValue, password));
        return inputValue;

    }

    public boolean validateConfirmPassword(String input, String userPassword) {
        if (!input.equals(userPassword)) {
            System.err.println("Confirm Password doesn't match password");
            return false;
        }
        return true;
    }

    public String generateUserId() {
        return "USR" + User.getUserCount();
    }
}
