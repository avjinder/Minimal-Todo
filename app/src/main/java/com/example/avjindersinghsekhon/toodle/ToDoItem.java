package com.example.avjindersinghsekhon.toodle;

import java.util.Date;

public class ToDoItem {
    private String mToDoText;
    private boolean mHasReminder;
    private String mTodoColor;
    private Date mToDoDate;

    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = "#fff";
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

    public boolean HasReminder() {
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
}
