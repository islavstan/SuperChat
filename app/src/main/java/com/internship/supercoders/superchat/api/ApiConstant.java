package com.internship.supercoders.superchat.api;

import java.util.Random;

public class ApiConstant {
    public final static String APPLICATION_ID = "52822";
    public final static String AUTH_KEY = "5q-7md-rsxrUHcm";
    public final static String AUTH_SECRET = "fgMa3dR7KqHxWKF";
    private static Long tsLong = System.currentTimeMillis() / 1000;
    public final static String TS = tsLong.toString();
    public final static int RANDOM_ID = new Random().nextInt();




}
