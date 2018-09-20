package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveAllUsersDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveUserDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectPresenterProducerScreen;

public class ReviewSelectPresenterProducerController {


    public void startUseCase(int role, String name) {
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
        intent.putExtra(Constant.USERNAME, name);
        intent.putExtra(Constant.USERROLE, role);
        MainController.displayScreen(intent);
    }

    public void retrieveAllUsers() {
        RetrieveAllUsersDelegate retrieveAllUserDelegate = new RetrieveAllUsersDelegate();
        //retrieveAllUserDelegate.execute();
    }

    public void retrieveUser(int userID) {
        RetrieveUserDelegate retrieveUserDelegate = new RetrieveUserDelegate();
        //retrieveUserDelegate.execute();
    }
}
