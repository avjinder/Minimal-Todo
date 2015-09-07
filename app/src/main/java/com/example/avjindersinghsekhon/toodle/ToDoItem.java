package com.example.avjindersinghsekhon.toodle;

import java.util.Date;

/**
 * Created by avjindersinghsekhon on 9/7/15.
 */
public class ToDoItem {
    private String mToDoText;
    private boolean mHasReminder;
    private Date mToDoDate;

    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
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

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }
}
