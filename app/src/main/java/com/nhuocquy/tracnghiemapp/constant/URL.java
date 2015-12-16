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
    public static String LOGIN_IDACCOUNT = "http://%s:8080/tracnghiem/login?idAccount=%s";
    /**LOGIN_USERNAME_PASSWORD
     * 1: IP
     * 2: username
     * 3: password
     */
    public static String LOGIN_USERNAME_PASSWORD = "http://%s:8080/tracnghiem/login?username=%s&password=%s";
    /**SYNC_DATA_KHOA
     *1: IP
     */
    public static String SYNC_DATA_KHOA = "http://%s:8080/tracnghiem/syncdatakhoa";
    /**REGISTER
     * 1: IP
     */
    public static String REGISTER = "http://%s:8080/tracnghiem/register";

    /**THITHU
     * 1: IP
     * 2: idMonHoc
     * 3: doKho
     */
    public static String THITHU = "http://%s:8080/tracnghiem/thithu?idMonHoc=%s&doKho=%s";
    /**XEP_HANG_WITH_LOGIN
     * 1: IP
     * 2: idAccount
     * 3: idMonHoc
     * 4: doKho
     */
    public static String XEP_HANG_WITH_LOGIN = "http://%s:8080/tracnghiem/getxephangthithu?idAccount=%s&idMonHoc=%s&doKho=%s";
    /**XEP_HANG_WITH_LOGIN
     * 1: IP
     * 2: idMonHoc
     * 3: doKho
     */
    public static String XEP_HANG_WITHOUT_LOGIN = "http://%s:8080/tracnghiem/getxephangthithu?idMonHoc=%s&doKho=%s";
    /**LIST_XE_PHANG
     * 1: IP
     * 2: idAccount
     */
    public static String LIST_XE_PHANG = "http://%s:8080/tracnghiem/listxephangthithu?idAccount=%s";
    /**SUMMIT_DIEM_THITHU
     * 1: IP
     */
    public static String SUMMIT_DIEM_THITHU = "http://%s:8080/tracnghiem/luudiemthithu";

    public static String GET_IMAGE = "http://www2.hcmuaf.edu.vn/data/quoctuan/";
}
