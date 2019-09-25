package com.chen.concise.example01.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenyongnan
 */
public class InputUtils {
    /**
     * Verify phone number legitimacy
     *
     * param phone
     * return
     */
    public static boolean validatePhone(String phone) {
        String pattern = "0?(13|14|15|18)[0-9]{9}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        return m.matches();
    }

    /**
     * Verify the qq number
     *
     * param qq
     * return
     */
    public static boolean validateQQ(String qq) {
        String pattern = "[1-9]([0-9]{5,11})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(qq);
        return m.matches();
    }

    /**
     * Verify the id card
     * param card
     * return
     */
    public static boolean validateCard(String card) {
        String pattern = "\\d{17}[\\d|x]|\\d{15}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(card);
        return m.matches();
    }
    /**
     * Verify the legitimacy of the mailbox
     * param email
     * return
     */
    public static boolean validateEmail(String email){
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        return m.matches();
    }

    /**
     * Verify whether the time format
     * param time
     * return
     */
    public static boolean validateTime(String time){
        String pattern = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(time);
        return m.matches();
    }

    /**
     * Whether the home phone
     * return
     */
    public static boolean validaTetelephone(String Tetelephone){
        String pattern = "[0-9-()（）]{7,18}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Tetelephone);
        return m.matches();
    }
}
