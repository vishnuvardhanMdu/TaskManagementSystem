/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class SignUp {

    User currentUser;
    Scanner ip;
    static final int MIN_USER_NAME_LENGTH = 3;
    static final int MIN_PASSWORD_LENGTH = 8;
    static final int MAX_PASSWORD_LENGTH = 25;
    static final String INPUT_TYPE_USER_NAME = "Name";
    static final String INPUT_TYPE_USER_EMAIL = "Email";
    static final String INPUT_TYPE_USER_PASSWORD = "Password";
    static final String INPUT_TYPE_USER_CONFIRM_PASSWORD = "ConfirmPassword";

    String userName, userEmail, userPassword, userConfirmPassword;
    ArrayList<String> errorList = new ArrayList<>();

    public SignUp(User currentUser, Scanner ip) {
        this.currentUser = currentUser;
        this.ip = ip;
    }

    public void newSignUp() {

        System.out.println("We are glad to add you in our family... :) ");

        userName = getUserInput(INPUT_TYPE_USER_NAME);
        userEmail = getUserInput(INPUT_TYPE_USER_EMAIL);
        userPassword = getUserInput(INPUT_TYPE_USER_PASSWORD);
        userConfirmPassword = getUserInput(INPUT_TYPE_USER_CONFIRM_PASSWORD);

        currentUser.addUser(userName, userEmail, userPassword);
        System.out.println("SignUp Success");
    }

    public String getUserInput(String inputType) {
        String inputValue;
        for (;;) {
            System.out.print("Enter " + inputType + " : ");
            inputValue = ip.next();
            validateInput(inputValue, inputType);
            if (errorList.isEmpty()) {
                break;
            }
            errorList.forEach(error -> {
                System.err.println("Invalid Input: " + error);
            });
            errorList.clear();
        }

        return inputValue;

    }

    public void validateInput(String input, String inputType) {

        //Validate Username
        if (inputType.equals(INPUT_TYPE_USER_NAME)) {
            Pattern checkSpecialChar = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

            if (input.length() < MIN_USER_NAME_LENGTH) {
                errorList.add("Username must be atleast 3 charaters");
            }
            if (checkSpecialChar.matcher(input).find()) {
                errorList.add("Username should not have any special charaters other than underscore ");
            }
        }

        //Valiate Useremail
        if (inputType.equals(INPUT_TYPE_USER_EMAIL)) {
            Pattern checkValidEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
            Matcher matcher = checkValidEmail.matcher(input);
            if (!matcher.matches()) {
                errorList.add("Enter valid email");
            }
            if (currentUser.emailExists(input)) {
                errorList.add("Email id already exists");
            }
        }

        //Valiate UserPassword
        if (inputType.equals(INPUT_TYPE_USER_PASSWORD)) {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";
            Pattern checkValidPassword = Pattern.compile(regex);
            Matcher matcher = checkValidPassword.matcher(input);
            if (!matcher.matches()) {
                errorList.add("Enter valid Password\nAtleast 1 Uppercase, Lowercase, Special character, length between 8 and 20 charactes");
            }
        }

        //Valiate UserConfirmPassword
        if (inputType.equals(INPUT_TYPE_USER_CONFIRM_PASSWORD)) {

            if (!input.equals(userPassword)) {
                errorList.add("Confirm Password doesn't match password");
            }
        }

    }

}
