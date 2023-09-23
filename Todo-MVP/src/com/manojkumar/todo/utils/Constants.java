package com.manojkumar.todo.utils;

public interface Constants {
    /*

     */
    public static String PROPERTY_FILE_NAME="com.manojkumar.todo.resources.message";
    public static String OUTPUT_DATA_FILE_PATH="F:\\Codes\\Todo-MVP\\src\\com\\manojkumar\\todo\\data\\taskList.dat";
    public static String STATUS_PENDING="In Progress";
    public static String STATUS_COMPLETE="Completed.";
    public static String STATUS_MISSED="Task Missed";

    /*
        ToDoView file constants
     */

    byte ADD_TASK=1;
    byte UPDATE_TASK=2;
    byte DELETE_TASK=3;
    byte VIEW_TASK=4;
    byte CHANGE_STATUS=5;
    byte VIEW_COMPLETED_TASKS=6;
    byte VIEW_PENDING_TASKS=7;
    byte VIEW_MISSED_TASKS=8;
    byte EXIT=9;
}
