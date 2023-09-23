package com.manojkumar.todo.repo;

import com.manojkumar.todo.dto.TodoDTO;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public interface IToDoRepo extends Serializable {
    public boolean writeObject(ArrayList<TodoDTO> tasks) throws IOException;
    public ArrayList<TodoDTO> readObject() throws IOException, ClassNotFoundException;
}
