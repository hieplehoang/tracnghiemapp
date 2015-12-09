package com.nhuocquy.tracnghiemapp.constant;

/**
 * Created by NhuocQuy on 12/9/2015.
 */
public class URL {

    public static String IP = "192.168.56.1";
    /**LOGIN_IDACCOUNT
     * 1: IP
     * 2: idAccount
     */
    public static String LOGIN_IDACCOUNT = "http://%s:8080/tracnghiem/login?idAccount=%s";  /**LOGIN_IDACCOUNT
     * 1: LOGIN_USERNAME_PASSWORD
     * 2: username
     * 3: password
     */
    public static String LOGIN_USERNAME_PASSWORD = "http://%s:8080/tracnghiem/login?username=%s&password=%s";
}
