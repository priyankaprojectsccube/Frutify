package com.app.fr.fruiteefy.user_client.helperClass;

public class SharedPref {
    private static final SharedPref ourInstance = new SharedPref();

    public static SharedPref getInstance() {
        return ourInstance;
    }

    private SharedPref() {
    }
}
