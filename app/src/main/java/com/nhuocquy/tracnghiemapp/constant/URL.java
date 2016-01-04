package com.nhuocquy.tracnghiemapp.constant;

/**
 * Created by NhuocQuy on 12/9/2015.
 */
public class URL {

//    public static String IP = "192.168.56.1";
    public static String IP = "tracnghiem.mybluemix.net";
    /**LOGIN_IDACCOUNT
     * 1: IP
     * 2: idAccount
     */
    public static String LOGIN_IDACCOUNT = "http://%s:80/login?idAccount=%s";
    /**LOGIN_USERNAME_PASSWORD
     * 1: IP
     * 2: username
     * 3: password
     */
    public static String LOGIN_USERNAME_PASSWORD = "http://%s:80/login?username=%s&password=%s";
    /**SYNC_DATA_KHOA
     *1: IP
     */
    public static String SYNC_DATA_KHOA = "http://%s:80/syncdatakhoa";
    /**REGISTER
     * 1: IP
     */
    public static String REGISTER = "http://%s:80/register";

    /**THITHU
     * 1: IP
     * 2: idMonHoc
     * 3: doKho
     */
    public static String THITHU = "http://%s:80/thithu?idMonHoc=%s&doKho=%s";
    /**XEP_HANG_WITH_LOGIN
     * 1: IP
     * 2: idAccount
     * 3: idMonHoc
     * 4: doKho
     */
    public static String XEP_HANG_WITH_LOGIN = "http://%s:80/getxephangthithu?idAccount=%s&idMonHoc=%s&doKho=%s";
    /**XEP_HANG_WITH_LOGIN
     * 1: IP
     * 2: idMonHoc
     * 3: doKho
     */
    public static String XEP_HANG_WITHOUT_LOGIN = "http://%s:80/getxephangthithu?idMonHoc=%s&doKho=%s";
    /**LIST_XE_PHANG
     * 1: IP
     * 2: idAccount
     */
    public static String LIST_XEP_PHANG = "http://%s:80/listxephangthithu?idAccount=%s";
    /**SUMMIT_DIEM_THITHU
     * 1: IP
     */
    public static String SUMMIT_DIEM_THITHU = "http://%s:80/luudiemthithu";

    public static String GET_IMAGE= "http://didongz.esy.es/tracnghiemimage/";
}
