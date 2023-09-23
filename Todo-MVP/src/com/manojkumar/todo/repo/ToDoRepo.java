package com.manojkumar.todo.repo;

import com.manojkumar.todo.dto.TodoDTO;
import com.manojkumar.todo.utils.Constants;

import java.io.*;
import java.util.ArrayList;

public class ToDoRepo implements IToDoRepo,Serializable{

    public static ToDoRepo repo = null;
    File file;

    private ToDoRepo() throws IOException {
        file = new File(Constants.OUTPUT_DATA_FILE_PATH);
        file.createNewFile();
    }

    public static ToDoRepo getInstance() throws IOException {
        if (repo == null) {
            repo = new ToDoRepo();
        }
        return repo;
    }

    @Override
    public boolean writeObject(ArrayList<TodoDTO> tasks) throws IOException {
        try(FileOutputStream fos=new FileOutputStream(file)){
            try(ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(tasks);
                return true;
            }
        }
    }

    @Override
    public ArrayList<TodoDTO> readObject() throws IOException, ClassNotFoundException {

        ArrayList<TodoDTO> taskLists=new ArrayList<>();
        try(FileInputStream fis=new FileInputStream(file)){
            try(ObjectInputStream ois=new ObjectInputStream(fis)){
                taskLists= (ArrayList<TodoDTO>) ois.readObject();
            }
        }
        return taskLists;
    }
}
