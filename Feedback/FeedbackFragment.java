package com.example.avjindersinghsekhon.minimaltodo.Feedback;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.About.AboutFragment;
import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Awamry on 12/14/2018.
 */

public class FeedbackFragment extends AppDefaultFragment {
    private Button sendAttachment;
    private AnalyticsApplication app;
    ImageView imageView;
    EditText feedbackText;
    TextView noAttachments;
    Button sendEmail;
    Uri uri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        app = (AnalyticsApplication) getActivity().getApplication();
        app.send(this);
        sendAttachment = (Button) view.findViewById(R.id.attach_photo);
        sendEmail = (Button) view.findViewById(R.id.send_email);
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        feedbackText = (EditText) view.findViewById(R.id.feedback_text);
        noAttachments = (TextView)view.findViewById(R.id.no_attachment_view);
        sendAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = feedbackText.getText().toString();
                String[] recipients = new String[]{"avisekhon2@gmail.com"};
                Intent emailIntent  = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("application/image");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,recipients);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback Report");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,message);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);     //this line is added to your code
                startActivity(emailIntent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode==0&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
           noAttachments.setVisibility(View.INVISIBLE);
            uri = data.getData();
           try{
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
               imageView.setImageBitmap(bitmap);

           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_feedback;
    }

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }
}
