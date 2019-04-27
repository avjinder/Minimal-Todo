package com.example.avjindersinghsekhon.minimaltodo.About;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.BuildConfig;
import com.example.avjindersinghsekhon.minimaltodo.MinimalToDo;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;

public class AboutFragment extends AppDefaultFragment implements AboutContract.View {

    private View view;
    private AboutContract.Presenter presenter;

    private TextView mVersionTextView;
    private TextView contactMe;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
    }

    @LayoutRes
    protected int layoutRes() {
        return R.layout.fragment_about;
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    public void showVersion() {
        mVersionTextView = (TextView) view.findViewById(R.id.aboutVersionTextView);
        mVersionTextView.setText(String.format(getResources().getString(R.string.app_version), String.valueOf(BuildConfig.VERSION_CODE)));
    }

    public void setContactMe(){

        contactMe = (TextView) view.findViewById(R.id.aboutContactMe);

        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
