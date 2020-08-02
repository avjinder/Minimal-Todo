package com.example.avjindersinghsekhon.minimaltodo.About;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class Feedback extends AppCompatActivity {

    private EditText ouremail,subject,message;

    Button send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
     ouremail= (EditText) findViewById(R.id.email);
     subject= (EditText) findViewById(R.id.subject);
     message= (EditText) findViewById(R.id.message);
     send= (Button) findViewById(R.id.btn_send);


     send.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String ourEmail =ouremail.getText().toString();
             String ourSubjectFeedback=subject.getText().toString();
             String ourMessageFeedback=message.getText().toString();

             Intent send=new Intent(Intent.ACTION_SEND);
             send.putExtra(Intent.EXTRA_EMAIL,ourEmail);
             send.putExtra(Intent.EXTRA_SUBJECT,ourSubjectFeedback);
             send.putExtra(Intent.EXTRA_TEXT,ourMessageFeedback);
             send.setType("message/rfc822");
             send.setPackage("com.google.android.gm");
             startActivity(send);

         }
     });


    }


}

