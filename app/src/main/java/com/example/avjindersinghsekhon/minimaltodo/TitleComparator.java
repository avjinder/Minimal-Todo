package com.example.avjindersinghsekhon.minimaltodo;

import java.util.Comparator;

/**
 * This class compare two to do item based on title
 */

public class TitleComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2){
        ToDoItem task1 = (ToDoItem) o1;
        ToDoItem task2 = (ToDoItem) o2;

        return task1.getToDoText().compareTo(task2.getToDoText());
    }
}
