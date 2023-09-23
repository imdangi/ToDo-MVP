package com.manojkumar.todo.view;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;

import static com.manojkumar.todo.utils.MessageReader.getMessage;

import com.manojkumar.todo.dto.TodoDTO;
import com.manojkumar.todo.repo.IToDoRepo;
import com.manojkumar.todo.repo.ToDoRepo;
import  com.manojkumar.todo.utils.Constants;

public class ViewToDo {


    static private Scanner scanner=new Scanner(System.in);

    public static byte takeChoiceInput() throws MissingResourceException{
        try{
            System.out.print(getMessage("input.choice"));
            byte choice=scanner.nextByte();
            scanner.nextLine();
            return choice;
        }catch (InputMismatchException e){
            scanner.nextLine();
            System.out.println(getMessage("input.invalidChoiceType"));
            return takeChoiceInput();
        }
    }
    public static void printDesign(){
        System.out.println("============================================================");
    }

    public static void main(String[] args) {
        byte flag=-1;
        while(flag!=9){
            printDesign();
            try {
                System.out.println(getMessage("view.createNewTask"));
                System.out.println(getMessage("view.updateTask"));
                System.out.println(getMessage("view.deleteTask"));
                System.out.println(getMessage("view.viewTask"));
                System.out.println(getMessage("view.searchTask"));
                System.out.println(getMessage("view.viewCompletedTasks"));
                System.out.println(getMessage("view.viewPendingTasks"));
                System.out.println(getMessage("view.viewMissedTasks"));
                System.out.println(getMessage("view.exit"));
                printDesign();
                flag=takeChoiceInput();
                printDesign();

            }catch (MissingResourceException e){
                e.printStackTrace();
                System.exit(0);
            }
            switch (flag){
                case Constants.ADD_TASK :
                    try{
                        createNewTask();
                    }catch (MissingResourceException e){
                        System.out.println("Either key or Resource file is missing ... ");
                        System.out.println("Enter 9 to exit");
                        e.printStackTrace();
                    }catch (IOException | ClassNotFoundException e){
                        e.printStackTrace();
                    }
                    break;
                case Constants.VIEW_TASK:
                    try {
                        viewTasks();
                    }catch (IOException | ClassNotFoundException | MissingResourceException e ){
                        e.printStackTrace();
                    }
                    break;
                case Constants.DELETE_TASK:
                    try {
                        deleteTask();
                    }catch (IOException | ClassNotFoundException | MissingResourceException e ){
                        e.printStackTrace();
                    }
                    break;
                case Constants.UPDATE_TASK:
                    try {
                        updateTaskStatus();
                    } catch (IOException | ClassNotFoundException | MissingResourceException e ) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.CHANGE_STATUS:
                    try {
                        changeTaskStatus();
                    } catch (IOException | ClassNotFoundException | MissingResourceException e ) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.VIEW_COMPLETED_TASKS:
                    try {
                        viewCompletedTasks();
                    } catch (IOException | ClassNotFoundException | MissingResourceException e ) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.VIEW_PENDING_TASKS:
                    try {
                        viewPendingTasks();
                    } catch (IOException | ClassNotFoundException | MissingResourceException e ) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.VIEW_MISSED_TASKS:
                    try {
                        viewMissedTasks();
                    } catch (IOException | ClassNotFoundException | MissingResourceException e ) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.EXIT:
                    System.out.println(getMessage("view.exitMessage"));
                    break;
                default:
                    System.out.println("view.defaultMessage");
                    break;
            }
        }

        scanner.close();
    }

    static void deleteTask() throws IOException,ClassNotFoundException,MissingResourceException {
        /*
            - Taking task id to change task status
            - C for completed
            - M for missed
         */

        printDesign();
        System.out.println(getMessage("delete.deleteTask"));
        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        ArrayList<TodoDTO> newTaskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        boolean result=false;
        try {
            taskLists=repo.readObject();
            if(taskLists!=null){
                System.out.print(getMessage("delete.oldId"));
                int checkingId=scanner.nextInt();
                for(int i=0;i<taskLists.size();i++){
                    if(checkingId!=taskLists.get(i).getId()){
                        newTaskLists.add(taskLists.get(i));
                        result=true;
                    }
                }
                /*
                    -- Writing result to the file
                 */
                repo.writeObject(newTaskLists);
                taskLists=null;
            }else{
                System.out.println(getMessage("viewTask.noRecordFound"));
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised . Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }

        if(result) System.out.println("Task Deleted Successfully");
        else System.out.println("No changes.");
        printDesign();
    }

    static void changeTaskStatus() throws IOException,ClassNotFoundException,MissingResourceException {
        /*
            - Taking task id to change task status
            - C for completed
            - M for missed
         */


        printDesign();
        System.out.println(getMessage("status.taskStatusFeature"));
        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        ArrayList<TodoDTO> newTaskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        boolean result=false;
        try {
            taskLists=repo.readObject();
            if(taskLists!=null){
                System.out.print(getMessage("update.oldId"));
                int checkingId=scanner.nextInt();
                for(int i=0;i<taskLists.size();i++){
                    if(checkingId==taskLists.get(i).getId()){
                        TodoDTO updatingObject=taskLists.get(i);
                        scanner.nextLine();

                        System.out.print(getMessage("status.enterM"));
                        System.out.print(getMessage("status.enterC"));
                        String statusValue=scanner.nextLine();

                        Date newDate=new Date();
                        if(statusValue.equals("M")){
                            updatingObject.setStatus(Constants.STATUS_MISSED);
                        }else if(statusValue.equals("C")){
                            updatingObject.setStatus(Constants.STATUS_COMPLETE);
                        }else{
                            System.out.println("Invalid Input. No changes");
                            return;
                        }
                        /*
                            --> Setting new values
                         */
                        updatingObject.setAssignedDate(newDate);
                        /*
                            --> adding to the result
                         */
                        result=true;
                        newTaskLists.add(updatingObject);
                    }else{
                        newTaskLists.add(taskLists.get(i));
                    }

                }
                /*
                    -- Writing result to the file
                 */
                repo.writeObject(newTaskLists);
                taskLists=null;
            }else{
                System.out.println(getMessage("viewTask.noRecordFound"));
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised . Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }

        if(result) System.out.println("Task Status Changed Successfully");
        else System.out.println("No changes.");
        printDesign();
    }

    static void updateTaskStatus() throws IOException,ClassNotFoundException,MissingResourceException {
        /*
            - Taking input
            - ask for name to update the task
         */
        printDesign();
        System.out.println(getMessage("update.taskUpdateFeature"));
        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        ArrayList<TodoDTO> newTaskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        boolean result=false;
        try {
            taskLists=repo.readObject();
            if(taskLists!=null){
                System.out.print(getMessage("update.oldId"));
                int checkingId=scanner.nextInt();
                for(int i=0;i<taskLists.size();i++){
                    if(checkingId==taskLists.get(i).getId()){
                        TodoDTO updatingObject=taskLists.get(i);
                        scanner.nextLine();

                        System.out.print(getMessage("update.newTaskName"));
                        String newTaskName=scanner.nextLine();
                        System.out.print(getMessage("update.newTaskDesc"));
                        String newDesc=scanner.nextLine();
                        Date newDate=new Date();
                        /*
                            --> Setting new values
                         */
                        updatingObject.setName(newTaskName);
                        updatingObject.setDescription(newDesc);
                        updatingObject.setAssignedDate(newDate);
                        /*
                            --> adding to the result
                         */
                        result=true;
                        newTaskLists.add(updatingObject);
                    }else{
                        newTaskLists.add(taskLists.get(i));
                    }

                }
                /*
                    -- Writing result to the file
                 */
                repo.writeObject(newTaskLists);
                taskLists=null;
            }else{
                System.out.println(getMessage("viewTask.noRecordFound"));
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised . Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }
        if(result) System.out.println("Task Updated Successfully");
        else System.out.println("No changes.");
        printDesign();
    }

    static void createNewTask() throws MissingResourceException, IOException, ClassNotFoundException {
        printDesign();
        /*
            - Taking input for TodoDto class
            - Getting messages from MessageReader static method getMessage
         */
        String taskName;
        String taskDesc;
        int taskId=100;

        System.out.println(getMessage("input.taskCreateFeature"));
        System.out.print(getMessage("input.taskName"));
        taskName=scanner.nextLine();
        System.out.print(getMessage("input.taskDesc"));
        taskDesc=scanner.nextLine();
        /*
            - Creating TodoDTO Object
            - Assigning TaskId to the TodoDTO object, default value = 100
         */
        TodoDTO todoObj=new TodoDTO(taskName,taskDesc);
        todoObj.setId(taskId);
        /*
            - Getting data from file if present
            - We don't want to lose data
            - As ObjectOutputStream does not support append feature
            - We will get the stored value from file if present and then update them in file with new value added
            - We will add one to the last id value stored
         */
        String result;
        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        try{
            taskLists=repo.readObject();
            if(taskLists!=null){
                taskId=taskLists.get(taskLists.size()-1).getId()+1;
                todoObj.setId(taskId);
            }
            /*
                - Writing the object
             */
            taskLists.add(todoObj);
            result=(repo.writeObject(taskLists))?"Task Added Successfully":"OOPS Task Not Added";

        }catch (EOFException e){
            /*
                - As the file is empty, adding first record directly
             */
            ArrayList<TodoDTO> temp=new ArrayList<>();
            temp.add(todoObj);
            result=(repo.writeObject(temp))?getMessage("add.taskAdded"):getMessage("add.taskNotAdded");
            /*
                - In case of error
                e.printStackTrace();
             */
        }

        System.out.println(result);
        printDesign();
    }


    static void viewTasks() throws IOException, ClassNotFoundException {

        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        try {
            taskLists = repo.readObject();
            if(taskLists!=null){
                for(TodoDTO task : taskLists) {
                    printDesign();
                    System.out.println("Task Id     : "+task.getId());
                    System.out.println("Task Name   : "+task.getName());
                    System.out.println("Description : "+task.getDescription());
                    System.out.println("Created ON  : "+task.getAssignedDate());
                    System.out.println("Status      : "+task.getStatus());
                    printDesign();
                }
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised .. Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }
    }

    static void viewMissedTasks() throws IOException, ClassNotFoundException {

        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        try {
            taskLists = repo.readObject();
            if(taskLists!=null){
                for(TodoDTO task : taskLists) {
                    if(task.getStatus().equals(Constants.STATUS_MISSED)) {
                        printDesign();
                        System.out.println("Task Id     : " + task.getId());
                        System.out.println("Task Name   : " + task.getName());
                        System.out.println("Description : " + task.getDescription());
                        System.out.println("Created ON  : " + task.getAssignedDate());
                        System.out.println("Status      : " + task.getStatus());
                        printDesign();
                    }
                }
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised .. Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }
    }

    static void viewPendingTasks() throws IOException, ClassNotFoundException {

        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        try {
            taskLists = repo.readObject();
            if(taskLists!=null){
                for(TodoDTO task : taskLists) {
                    if(task.getStatus().equals(Constants.STATUS_PENDING)) {
                        printDesign();
                        System.out.println("Task Id     : " + task.getId());
                        System.out.println("Task Name   : " + task.getName());
                        System.out.println("Description : " + task.getDescription());
                        System.out.println("Created ON  : " + task.getAssignedDate());
                        System.out.println("Status      : " + task.getStatus());
                        printDesign();
                    }
                }
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised .. Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }
    }

    static void viewCompletedTasks() throws IOException, ClassNotFoundException {

        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        IToDoRepo repo= ToDoRepo.getInstance();
        try {
            taskLists = repo.readObject();
            if(taskLists!=null){
                for(TodoDTO task : taskLists) {
                    if(task.getStatus().equals(Constants.STATUS_COMPLETE)) {
                        printDesign();
                        System.out.println("Task Id     : " + task.getId());
                        System.out.println("Task Name   : " + task.getName());
                        System.out.println("Description : " + task.getDescription());
                        System.out.println("Created ON  : " + task.getAssignedDate());
                        System.out.println("Status      : " + task.getStatus());
                        printDesign();
                    }
                }
            }
        }catch (EOFException e){
            /*
                - As the file is not created. EOF exception is raised .. Say no record found
             */
            System.out.println(getMessage("viewTask.noRecordFound"));
        }
    }
}
