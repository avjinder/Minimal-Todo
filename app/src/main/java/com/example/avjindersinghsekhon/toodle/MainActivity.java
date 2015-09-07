package com.example.avjindersinghsekhon.toodle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    private ArrayList<ToDoItem> mToDoItemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToDoItemsArrayList = new ArrayList<>();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToDoItemsArrayList = new ArrayList<>();
        makeUpItems(mToDoItemsArrayList, 50);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
        }
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);
        mRecyclerView = (RecyclerView)findViewById(R.id.toDoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BasicListAdapter(mToDoItemsArrayList));

    }

    public void makeUpItems(ArrayList<ToDoItem> items, int len){
        for(int i=0; i<len; i++){
            ToDoItem item = new ToDoItem(""+i, false, new Date());
            items.add(item);
        }

    }

    public static class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder>{
        private ArrayList<ToDoItem> items;

        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(BasicListAdapter.ViewHolder holder, final int position) {
            ToDoItem item = items.get(position);
            holder.mCheckBox.setChecked(item.HasReminder());
            holder.mTextview.setText(item.getToDoText());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Clicked"+position, Snackbar.LENGTH_SHORT);
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        BasicListAdapter(ArrayList<ToDoItem> items){

            this.items = items;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            CheckBox mCheckBox;
            TextView mTextview;
            public ViewHolder(View v){
                super(v);
                mView = v;
                mCheckBox = (CheckBox)v.findViewById(R.id.toDoListItemCheckBox);
                mTextview = (TextView)v.findViewById(R.id.toDoListItemTextview);
            }


        }
    }

}
