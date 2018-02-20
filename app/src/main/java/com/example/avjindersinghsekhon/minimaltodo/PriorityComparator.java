package com.example.avjindersinghsekhon.minimaltodo;

import java.util.Comparator;

/**
 * This class compare two to do items based on priority
 */

public class PriorityComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2){
        ToDoItem task1 = (ToDoItem) o1;
        ToDoItem task2 = (ToDoItem) o2;

        int value1 = task1.getPriority().ordinal();
        int value2 = task2.getPriority().ordinal();

        if(value1 > value2){
            return -1;
        }
        if(value1 < value2){
            return 1;
        }
        else{
            return 0;
        }
    }
}
