package com.example.avjindersinghsekhon.minimaltodo.About;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.DefaultPresenter;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.DefaultView;

public class AboutContract {

    public interface View extends DefaultView<Presenter>{

        void showVersion();
        void setContactMe();

    }

    public interface Presenter extends DefaultPresenter {

    }
}
