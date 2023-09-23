package com.manojkumar.todo.utils;

import com.manojkumar.todo.utils.Constants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public interface MessageReader {
    static String getMessage(String key) throws MissingResourceException{
        ResourceBundle rb= ResourceBundle.getBundle(Constants.PROPERTY_FILE_NAME);
        return rb.getString(key);
    }
}
