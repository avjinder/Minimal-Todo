package com.example.avjindersinghsekhon.minimaltodo.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ToDoItemTest {
    private static final String TODODESCRIPTION = "tododescription";
    private static final String TODOTEXT = "todotext";

    @Test
    public void testTodo() throws JSONException {
        Date date = new Date();
        ToDoItem toDoItem = new ToDoItem("test","test",true,date);
        JSONObject object = toDoItem.toJSON();
        Assert.assertEquals(object.get(TODOTEXT),"test");
        Assert.assertEquals(object.get(TODODESCRIPTION),"test");
        Assert.assertEquals(object.get("tododate"),date.getTime());
    }

    @Test
    public void testConstructorWithJson() throws JSONException {
        Date date = new Date();
        ToDoItem toDoItem = new ToDoItem("test","test",true,date);
        JSONObject object = toDoItem.toJSON();
        ToDoItem item = new ToDoItem(object);
        Assert.assertTrue(item!=null);

        item.setmToDoDescription(TODODESCRIPTION);
        Assert.assertEquals(item.getmToDoDescription(),TODODESCRIPTION);

        item.setHasReminder(true);
        Assert.assertTrue(item.hasReminder());

        item.setTodoColor(0);
        Assert.assertEquals(item.getTodoColor(),0);

        item.setToDoDate(date);
        Assert.assertEquals(item.getToDoDate(),date);

        item.setToDoText(TODOTEXT);
        Assert.assertEquals(item.getToDoText(),TODOTEXT);
    }

}