package com.example.avjindersinghsekhon.minimaltodo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable{
    public enum PriorityType{
        LOW, MEDIUM, HIGH
    }

    private String mToDoText;
    private String mToDoDescription;
    private PriorityType mPriority;
    private boolean mHasReminder;
//    private Date mLastEdited;
    private int mTodoColor;
    private Date mToDoDate;
    private UUID mTodoIdentifier;
    private static final String TODOTEXT = "todotext";
    private static final String TODODESCRIPTION = "tododescription";
    private static final String TODOPRIORITY = "todopriority";
    private static final String TODOREMINDER = "todoreminder";
//    private static final String TODOLASTEDITED = "todolastedited";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";


    public ToDoItem(String todoBody, String todoDescription, PriorityType priority, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mToDoDescription = todoDescription;
        mPriority = priority;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
    }

    public ToDoItem(JSONObject jsonObject) throws JSONException{
        mToDoText = jsonObject.getString(TODOTEXT);
        mToDoDescription = jsonObject.getString(TODODESCRIPTION);
        int value = jsonObject.getInt(TODOPRIORITY);
        mPriority = PriorityType.values()[value];
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
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
        jsonObject.put(TODODESCRIPTION, mToDoDescription);
        int value = mPriority.ordinal();
        jsonObject.put(TODOPRIORITY, value);
//        jsonObject.put(TODOLASTEDITED, mLastEdited.getTime());
        if(mToDoDate!=null){
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }


//    public ToDoItem(){
//        this("Clean my room","", true, new Date());
//    }

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public String getToDoDescription() {
        return mToDoDescription;
    }

    public void setToDoDescription(String mToDoDescription) {
        this.mToDoDescription = mToDoDescription;
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

    public PriorityType getPriority() {
        return mPriority;
    }

    public void setPriority(PriorityType mPriority) {
        this.mPriority = mPriority;
    }

    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}