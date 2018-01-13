package com.example.avjindersinghsekhon.minimaltodo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable{
    private String mToDoText;
    private boolean mHasReminder;
    //    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    private UUID mTodoIdentifier;
    private boolean mTodoDone;
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
    //    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODODONE = "tododone";
    private static final String TODOIDENTIFIER = "todoidentifier";


    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate, boolean done){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = 1677725;
        mTodoDone = done;
        mTodoIdentifier = UUID.randomUUID();
    }

    public ToDoItem(JSONObject jsonObject) throws JSONException{
        mToDoText = jsonObject.getString(TODOTEXT);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoDone = jsonObject.getBoolean(TODODONE);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));

//        if(jsonObject.has(TODOLASTEDITED)){
//            mLastEdited = new Date(jsonObject.getLong(TODOLASTEDITED));
//        }
        if(jsonObject.has(TODODATE)){
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODOREMINDER, mHasReminder);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if(mToDoDate!=null){
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODODONE, mTodoDone);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }


    public ToDoItem(){
        this("Clean my room", true, new Date(), false);
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

    public int getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }

    public boolean getDone() {return mTodoDone;}

    public void setDone(boolean mDone){this.mTodoDone = mDone;}

    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}

