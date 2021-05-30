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
public class SignUp {

    User currentUser;
//    Scanner ip;
    static final int MIN_USER_NAME_LENGTH = 3;
    static final int MIN_PASSWORD_LENGTH = 8;
    static final int MAX_PASSWORD_LENGTH = 25;
    static final String INPUT_TYPE_USER_NAME = "Name";
    static final String INPUT_TYPE_USER_EMAIL = "Email";
    static final String INPUT_TYPE_USER_PASSWORD = "Password";
    static final String INPUT_TYPE_USER_CONFIRM_PASSWORD = "ConfirmPassword";

//    public SignUp(User currentUser, Scanner ip) {
//        this.currentUser = currentUser;
//        this.ip = ip;
//    }

    public void newSignUp(Scanner ip) {
        
        System.out.println("We are glad to add you in our family... :) ");

        String userName = getUserInput(INPUT_TYPE_USER_NAME, ip);
        String userEmail = getUserInput(INPUT_TYPE_USER_EMAIL, ip);
        String userPassword = getUserInput(INPUT_TYPE_USER_PASSWORD, ip);
        String userConfirmPassword = getUserInput(INPUT_TYPE_USER_CONFIRM_PASSWORD, ip);

        User.addUser(userName, userEmail, userPassword);
        System.out.println("SignUp Success");
    }

    public String getUserInput(String inputType, Scanner ip) {
        String inputValue;
        do {
            System.out.print("Enter " + inputType + " : ");
            inputValue = ip.next();
        } while (!validateInput(inputValue, inputType));

        return inputValue;

    }

    public boolean validateInput(String input, String inputType) {
//
//        //Validate Username
//        if (inputType.equals(INPUT_TYPE_USER_NAME)) {
//            Pattern checkSpecialChar = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
//
//            if (input.length() < MIN_USER_NAME_LENGTH) {
//                System.err.println("Username must be atleast 3 charaters");
//                return false;
//            }
//            if (checkSpecialChar.matcher(input).find()) {
//                System.err.println("Username should not have any special charaters other than underscore ");
//                return false;
//            }
//            return true;
//        } else if (inputType.equals(INPUT_TYPE_USER_EMAIL)) {
//            Pattern checkValidEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
//            Matcher matcher = checkValidEmail.matcher(input);
//            if (!matcher.matches()) {
//                System.err.println("Enter valid email");
//                return false;
//            }
//            if (currentUser.emailExists(input)) {
//                System.err.println("Email id already exists");
//                return false;
//            }
//            return true;
//        } else if (inputType.equals(INPUT_TYPE_USER_PASSWORD)) {
//            String regex = "^(?=.*[0-9])"
//                    + "(?=.*[a-z])(?=.*[A-Z])"
//                    + "(?=.*[@#$%^&+=])"
//                    + "(?=\\S+$).{8,20}$";
//            Pattern checkValidPassword = Pattern.compile(regex);
//            Matcher matcher = checkValidPassword.matcher(input);
//            if (!matcher.matches()) {
//                System.err.println("Enter valid Password\nAtleast 1 Uppercase, Lowercase, Special character, length between 8 and 20 charactes");
//                return false;
//            }
//            return true;
//        } else if (inputType.equals(INPUT_TYPE_USER_CONFIRM_PASSWORD)) {
//
//            if (!input.equals(userPassword)) {
//                System.err.println("Confirm Password doesn't match password");
//                return false;
//            }
//            return true;
//        }
        return true;
    }

}
