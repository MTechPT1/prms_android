package sg.edu.nus.iss.phoenix.createuser.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.MaintainUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.createuser.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.createuser.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectPresenterProducerScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

public class MaintainUserController {
    private UserListScreen userListScreen;
    private MaintainUserScreen maintainUserScreen;
    private ScheduleScreen scheduleScreen;
    private ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen;

    private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void startUsecase() {
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        new MaintainUserDelegate(this).execute("all");
    }

    public void onDisplayPresenterProducerList(ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen) {
        this.reviewSelectPresenterProducerScreen = reviewSelectPresenterProducerScreen;
        new MaintainUserDelegate(this).execute("all");
    }

    public void setMaintainUser(int actionType) {
        this.actionType = actionType;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void getPresenterProducerScreen(int actionType, ScheduleScreen scheduleScreen) {
        this.scheduleScreen = scheduleScreen;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
        intent.putExtra(Constant.PRESENTERPRODUCER, actionType);
        MainController.displayScreen(intent);
    }

    public void selectedUser(int role, User user) {
        scheduleScreen.selectedPresenterProducer(role, user);
    }

}
