/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Miikka Andersson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.example.avjindersinghsekhon.minimaltodo;

import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;


import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * JUnit tests to verify functionality of ToDoItem class.
 */
public class TestTodoItem {
    private final Date CURRENT_DATE = new Date();
    private final String TEXT_BODY = "This is some text";
    private final boolean REMINDER_OFF = false;
    private final boolean REMINDER_ON = true;

     /**
      * Check we can construct a ToDoItem object using the three parameter constructor
      */
     @Test
    public void testThreeParameterConstructor() {
        ToDoItem toDoItem = getToDoItem(REMINDER_OFF);
        Assert.assertEquals(TEXT_BODY, toDoItem.getToDoText());
        Assert.assertEquals(REMINDER_OFF, toDoItem.hasReminder());
        Assert.assertEquals(CURRENT_DATE, toDoItem.getToDoDate());
    }

     /**
      * Ensure we can marshall ToDoItem objects to Json
      */
     @Test
    public void testObjectMarshallingToJson() {
        ToDoItem toDoItem = getToDoItem(REMINDER_ON);

        try {
            JSONObject json = toDoItem.toJSON();
            Assert.assertEquals(TEXT_BODY, json.getString("todotext"));
            Assert.assertEquals(REMINDER_ON, json.getBoolean("todoreminder"));
            Assert.assertEquals(String.valueOf(CURRENT_DATE.getTime()), json.getString("tododate"));
        } catch (JSONException e) {
            Assert.fail("Exception thrown during test execution: " + e.getMessage());
        }
    }

    /**
    * Ensure we can create ToDoItem objects from Json data by using the json constructor
    */
    @Test
    public void testObjectUnmarshallingFromJson() {
        ToDoItem originalItem = getToDoItem(REMINDER_OFF);

        try {
            JSONObject json = originalItem.toJSON();
            ToDoItem itemFromJson = new ToDoItem(json);

            Assert.assertEquals(originalItem.getToDoText(), itemFromJson.getToDoText());
            Assert.assertEquals(originalItem.getToDoDate(), itemFromJson.getToDoDate());
            Assert.assertEquals(originalItem.hasReminder(), itemFromJson.hasReminder());
            Assert.assertEquals(originalItem.getIdentifier(), itemFromJson.getIdentifier());

        } catch (JSONException e) {
            Assert.fail("Exception thrown during test execution: " + e.getMessage());
        }
    }

    private ToDoItem getToDoItem(boolean hasReminder) {
        return new ToDoItem(TEXT_BODY,TEXT_BODY, hasReminder, CURRENT_DATE);
    }
}
