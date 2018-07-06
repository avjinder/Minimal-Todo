package com.example.avjindersinghsekhon.minimaltodo.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable {
    private String mToDoText;
    private String mToDoDescription;
    private boolean mHasReminder;
    //    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    private UUID mTodoIdentifier;
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
    //    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";
    private static final String TODODESCRIPTION = "tododescription";


    public ToDoItem(String todoBody,String todoDescription, boolean hasReminder, Date toDoDate) {
        mToDoText = todoBody;
        mToDoDescription = todoDescription;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
    }

    public ToDoItem(JSONObject jsonObject) throws JSONException {
        mToDoText = jsonObject.getString(TODOTEXT);
        mToDoDescription = jsonObject.getString(TODODESCRIPTION);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));

//        if(jsonObject.has(TODOLASTEDITED)){
//            mLastEdited = new Date(jsonObject.getLong(TODOLASTEDITED));
//        }
        if (jsonObject.has(TODODATE)) {
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODODESCRIPTION,mToDoDescription);
        jsonObject.put(TODOREMINDER, mHasReminder);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if (mToDoDate != null) {
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }


    public ToDoItem() {
        this("Clean my room","My new description", true, new Date());
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


    public UUID getIdentifier() {
        return mTodoIdentifier;
    }

    public String getmToDoDescription() {
        return mToDoDescription;
    }

    public void setmToDoDescription(String mToDoDescription) {
        this.mToDoDescription = mToDoDescription;
    }
}

