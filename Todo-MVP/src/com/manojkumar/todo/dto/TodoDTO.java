package com.manojkumar.todo.dto;

import java.io.Serializable;
import java.util.Date;
import com.manojkumar.todo.utils.Constants;

public class TodoDTO implements Serializable {

    private int id;
    private String name;
    private Date assignedDate;
    private String description;
    private String status;

    private TodoDTO() {
        this.assignedDate=new Date();
        this.status=Constants.STATUS_PENDING;
    }
    public TodoDTO(String name,String description){
        this();
        this.name=name;
        this.description=description;
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignedDate=" + assignedDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id){this.id=id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }
    public void setAssignedDate(Date date){
        this.assignedDate=date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
