package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.support.v7.widget.RecyclerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ItemTouchHelperClassTest {
    ItemTouchHelperClass itemTouchHelperClass;

    @Before
    public void init() {
        itemTouchHelperClass = new ItemTouchHelperClass(new ItemTouchHelperClass.ItemTouchHelperAdapter() {
            @Override
            public void onItemMoved(int fromPosition, int toPosition) {

            }

            @Override
            public void onItemRemoved(int position) {

            }
        });
    }

    @Test
    public void isLongPressDragEnabled() {
        Assert.assertEquals(true,itemTouchHelperClass.isLongPressDragEnabled());
    }

    @Test
    public void isItemViewSwipeEnabled() {
        Assert.assertEquals(true,itemTouchHelperClass.isItemViewSwipeEnabled());
    }

    @Test
    public void getMovementFlags() {
        RecyclerView mock = Mockito.mock(RecyclerView.class);
        RecyclerView.ViewHolder viewHolder = Mockito.mock(RecyclerView.ViewHolder.class);
        int i = itemTouchHelperClass.getMovementFlags(mock,viewHolder);
        Assert.assertTrue(i > 0);
    }

    @Test
    public void onMove() {
        RecyclerView mock = Mockito.mock(RecyclerView.class);
        RecyclerView.ViewHolder viewHolder = Mockito.mock(RecyclerView.ViewHolder.class);

        itemTouchHelperClass.onMove(mock,viewHolder,viewHolder);
    }

    @Test
    public void onSwiped() {
        RecyclerView mock = Mockito.mock(RecyclerView.class);
        RecyclerView.ViewHolder viewHolder = Mockito.mock(RecyclerView.ViewHolder.class);
        itemTouchHelperClass.onSwiped(viewHolder,1);
    }
}