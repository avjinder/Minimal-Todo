package com.example.avjindersinghsekhon.minimaltodo.About;

public class AboutPresenter implements AboutContract.Presenter {

    AboutContract.View view;

    public AboutPresenter(AboutContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.showVersion();
        view.setContactMe();
    }
}
