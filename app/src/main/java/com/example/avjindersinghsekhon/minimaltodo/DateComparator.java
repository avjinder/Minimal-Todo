package com.example.avjindersinghsekhon.minimaltodo;

import java.util.Comparator;

/**
 * This class compare two to do items based on date
 */

public class DateComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2){
        ToDoItem task1 = (ToDoItem) o1;
        ToDoItem task2 = (ToDoItem) o2;

        if((!task1.hasReminder()) && (!task2.hasReminder())){
            return 0;
        }
        else if(!task1.hasReminder() || task1.getToDoDate() == null){
            return 1;
        }
        else if(!task2.hasReminder() || task2.getToDoDate() == null){
            return -1;
        }
        else{
            return task1.getToDoDate().compareTo(task2.getToDoDate());
        }
    }
}
