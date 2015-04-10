package com.uika.siak.helper;

public class ConstantValues {
	public static String[] session_timeout_options = { "10 Menit", "15 Menit",
			"30 Menit", "60 Menit", "Selalu ingat saya" };

	public static String PREFERENCES_NAME = "com.uika.siak";
	public static String PREFERENCES_KEY_SESSION = "com.uika.siak.SESSION";

	// public static String IP = "192.168.1.5";
	public static String IP = "192.168.8.100";
	public static String URI_ROOT = "/siak_uika/app/api/";
	public static String API_TYPE_LOGIN = "login.php";
	public static String API_TYPE_CHANGE_PASSWORD = "change_password.php";
	public static String API_TYPE_KIRIM_SARAN = "saran.php";
	public static String API_TYPE_STUDENT = "student.php";
	public static String API_TYPE_JADWAL_KULIAH = "jadwal_kuliah.php";

	public static String URI_LOGIN = "http://" + IP + URI_ROOT + API_TYPE_LOGIN;
	public static String URI_CHANGE_PASSWORD = "http://" + IP + URI_ROOT
			+ API_TYPE_CHANGE_PASSWORD;
	public static String URI_KIRIM_SARAN = "http://" + IP + URI_ROOT
			+ API_TYPE_KIRIM_SARAN;
	public static String URI_GET_PROFIL = "http://" + IP + URI_ROOT
			+ API_TYPE_STUDENT;
	public static String URI_GET_JADWAL_KULIAH = "http://" + IP + URI_ROOT
			+ API_TYPE_JADWAL_KULIAH;

}
