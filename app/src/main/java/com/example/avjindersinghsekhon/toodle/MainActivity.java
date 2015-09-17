package com.example.avjindersinghsekhon.toodle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    private ArrayList<ToDoItem> mToDoItemsArrayList;
    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.avjindersinghsekhon.toodle.MainActivity";
    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT = "MMM d, yyyy  h:mm a";
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;
    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";

    private String[] testStrings = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };



    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData){
        ArrayList<ToDoItem> items = null;

        try {
            items  = storeRetrieveData.loadFromFile();
            Log.d("OskarSchindler", "Data Retrieved: "+items.size());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(items == null){
            items = new ArrayList<>();
        }
        return items;

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(ReminderActivity.EXIT, false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ReminderActivity.EXIT,false);
            editor.apply();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(CHANGE_OCCURED, false)){
            Log.d("OskarSchindler", "Change occured");

            mToDoItemsArrayList = getLocallyStoredData(storeRetrieveData);
            adapter = new BasicListAdapter(mToDoItemsArrayList);
            mRecyclerView.setAdapter(adapter);
            setAlarms();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHANGE_OCCURED, false);
//            editor.commit();
            editor.apply();


        }
    }

    private void setAlarms(){
        if(mToDoItemsArrayList!=null){
            for(ToDoItem item : mToDoItemsArrayList){
                if(item.hasReminder() && item.getToDoDate()!=null){
                    Intent i = new Intent(this, TodoNotificationService.class);
                    i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                    i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                    createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
                    Log.d("OskarSchindler", "Alarm Updated");
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Aller_Regular.tff").setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_main);
        Log.d("OskarSchindler", "onCreate Called");

//        if(getIntent().getBooleanExtra(ReminderActivity.EXIT,false)){
//            Log.d("OskarSchindler", "Close app");
//            finish();
//        }

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
//        editor.commit();
        editor.apply();

        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
        mToDoItemsArrayList =  getLocallyStoredData(storeRetrieveData);
        adapter = new BasicListAdapter(mToDoItemsArrayList);
        setAlarms();


//        adapter.notifyDataSetChanged();
//        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
//
//        try {
//            mToDoItemsArrayList = storeRetrieveData.loadFromFile();
////            Log.d("OskarSchindler", "Arraylist Length: "+mToDoItemsArrayList.size());
//        } catch (IOException | JSONException e) {
////            Log.d("OskarSchindler", "IOException received");
//            e.printStackTrace();
//        }
//
//        if(mToDoItemsArrayList==null){
//            mToDoItemsArrayList = new ArrayList<>();
//        }
//

//        mToDoItemsArrayList = new ArrayList<>();
//        makeUpItems(mToDoItemsArrayList, testStrings.length);

        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent newTodo = new Intent(MainActivity.this, AddToDoActivity.class);
                ToDoItem item = new ToDoItem("", false, null);
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setTodoColor(color);
                //noinspection ResourceType
//                String color = getResources().getString(R.color.primary_ligher);
                newTodo.putExtra(TODOITEM, item);
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });


//        mRecyclerView = (RecyclerView)findViewById(R.id.toDoRecyclerView);
        mRecyclerView = (RecyclerViewEmptySupport)findViewById(R.id.toDoRecyclerView);
        mRecyclerView.setEmptyView(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {

                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }

            @Override
            public void hide() {

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };
        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);


        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        mRecyclerView.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutMeMenuItem:
                Intent i = new Intent(this, AboutActivity.class);
                i.putExtra(TodoNotificationService.TODOUUID, mToDoItemsArrayList.get(0).getIdentifier());
//                finish();
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            ToDoItem item =(ToDoItem) data.getSerializableExtra(TODOITEM);
            if(item.getToDoText().length()<=0){
                return;
            }
            boolean existed = false;

            if(item.hasReminder() && item.getToDoDate()!=null){
                Intent i = new Intent(this, TodoNotificationService.class);
                i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
                Log.d("OskarSchindler", "Alarm created" + item.getToDoText());
//                Log.d("OskarSchindler", "Alarm Created: "+item.getToDoText()+" at "+item.getToDoDate());
            }

            for(int i = 0; i<mToDoItemsArrayList.size();i++){
                if(item.getIdentifier().equals(mToDoItemsArrayList.get(i).getIdentifier())){
                    mToDoItemsArrayList.set(i, item);
                    existed = true;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            if(!existed) {
                addToDataStore(item);
            }


        }
    }

    private AlarmManager getAlarmManager(){
        return (AlarmManager)getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }

    private void createAlarm(Intent i, int requestCode, long timeInMillis){
        AlarmManager am = getAlarmManager();
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());
    }
    private void deleteAlarm(Intent i, int requestCode){
        if(doesPendingIntentExist(i, requestCode)){
            PendingIntent pi = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            getAlarmManager().cancel(pi);
//            Log.d("OskarSchindler", "Nonexistant PI cannot be cancelled");
        }
    }

    private void addToDataStore(ToDoItem item){
        mToDoItemsArrayList.add(item);
        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);

    }


    public void makeUpItems(ArrayList<ToDoItem> items, int len){
        for (String testString : testStrings) {
            ToDoItem item = new ToDoItem(testString, false, new Date());
            //noinspection ResourceType
//            item.setTodoColor(getResources().getString(R.color.red_secondary));
            items.add(item);
        }

    }

    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
        private ArrayList<ToDoItem> items;

        @Override
        public void onItemMoved(int fromPosition, int toPosition) {
           if(fromPosition<toPosition){
               for(int i=fromPosition; i<toPosition; i++){
                   Collections.swap(items, i, i+1);
               }
           }
            else{
               for(int i=fromPosition; i > toPosition; i--){
                   Collections.swap(items, i, i-1);
               }
           }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRemoved(final int position) {
            mJustDeletedToDoItem =  items.remove(position);
            mIndexOfDeletedToDoItem = position;
            Intent i = new Intent(MainActivity.this,TodoNotificationService.class);
            deleteAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode());
            notifyItemRemoved(position);

//            String toShow = (mJustDeletedToDoItem.getToDoText().length()>20)?mJustDeletedToDoItem.getToDoText().substring(0, 20)+"...":mJustDeletedToDoItem.getToDoText();
            String toShow = "Todo";
            Snackbar.make(mCoordLayout, "Deleted "+toShow,Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                            if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasReminder()){
                                Intent i = new Intent(MainActivity.this, TodoNotificationService.class);
                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
                                i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());
                                createAlarm(i, mJustDeletedToDoItem.getIdentifier().hashCode(), mJustDeletedToDoItem.getToDoDate().getTime());
                            }
                            notifyItemInserted(mIndexOfDeletedToDoItem);
                        }
                    }).show();
        }

        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            ToDoItem item = items.get(position);

            if(item.hasReminder() && item.getToDoDate()!=null){
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
//                holder.mToDoTextview.setVisibility(View.GONE);
            }
            else{
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(2);
            }
            holder.mToDoTextview.setText(item.getToDoText());
//            holder.mColorTextView.setBackgroundColor(Color.parseColor(item.getTodoColor()));

//            TextDrawable myDrawable = TextDrawable.builder().buildRoundRect(item.getToDoText().substring(0,1),Color.RED, 10);
            //We check if holder.color is set or not
//            if(item.getTodoColor() == null){
//                ColorGenerator generator = ColorGenerator.MATERIAL;
//                int color = generator.getRandomColor();
//                item.setTodoColor(color+"");
//            }
//            Log.d("OskarSchindler", "Color: "+item.getTodoColor());
            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0,1),item.getTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
            holder.mColorImageView.setImageDrawable(myDrawable);
            if(item.getToDoDate()!=null){
                holder.mTimeTextView.setText(AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT, item.getToDoDate()));
            }


        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        BasicListAdapter(ArrayList<ToDoItem> items){

            this.items = items;
        }


        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            TextView mToDoTextview;
//            TextView mColorTextView;
            ImageView mColorImageView;
            TextView mTimeTextView;
//            int color = -1;

            public ViewHolder(View v){
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());
                        Intent i = new Intent(MainActivity.this, AddToDoActivity.class);
                        i.putExtra(TODOITEM, item);
                        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView)v.findViewById(R.id.toDoListItemTextview);
                mTimeTextView = (TextView)v.findViewById(R.id.todoListItemTimeTextView);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
                mColorImageView = (ImageView)v.findViewById(R.id.toDoListItemColorImageView);
            }


        }
    }

    //Used when using custom fonts
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private void saveDate(){
        try {
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Log.d("OskarSchindler", "MainAct Data Saved");
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }
}
