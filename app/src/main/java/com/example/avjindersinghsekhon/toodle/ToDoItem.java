package com.example.avjindersinghsekhon.toodle;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable{
    private String mToDoText;
    private boolean mHasReminder;
    private Date mLastEdited;
    private String mTodoColor;
    private Date mToDoDate;
    private UUID mTodoIdentifier;

    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = "#ffffff";
        mTodoIdentifier = UUID.randomUUID();
    }

    public ToDoItem(){
        this("Clean my room", true, new Date());
    }

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }

    public String getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(String mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }

    public Date getLastEdited() {
        return mLastEdited;
    }

    public void setLastEdited(Date mLastEdited) {
        this.mLastEdited = mLastEdited;
    }
    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}

