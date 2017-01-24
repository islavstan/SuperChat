package com.internship.supercoders.superchat.api;

import java.util.Random;

public class ApiConstant {
    public final static String APPLICATION_ID = "52759";
    public final static String AUTH_KEY = "SjepDACMbyVzDZ-";
    public final static String AUTH_SECRET = "sRqmdU7HYpmY4L2";
    private static Long tsLong = System.currentTimeMillis() / 1000;
    public final static String TS = tsLong.toString();
    public final static int RANDOM_ID = new Random().nextInt();

}
