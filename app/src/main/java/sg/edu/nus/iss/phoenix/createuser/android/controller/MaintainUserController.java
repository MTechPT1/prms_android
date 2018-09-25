package sg.edu.nus.iss.phoenix.createuser.android.controller;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.RetrievePresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.ModifyUserDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.createuser.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.createuser.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectPresenterProducerScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

import static android.support.constraint.Constraints.TAG;

public class MaintainUserController {

    private UserListScreen userListScreen;
    private MaintainUserScreen maintainUserScreen;

    private User user = new User(); //  edit user and delete user

    private ScheduleScreen scheduleScreen;
    private ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen;
    static private String PRMS_BASE_URL = "https://localhost";

    private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void startUsecase() {
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        new RetrieveUsersDelegate(this).execute("ALL");
    }

    public void onDisplayPresenterProducerList(ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen) {
        this.reviewSelectPresenterProducerScreen = reviewSelectPresenterProducerScreen;
        new RetrievePresenterProducerDelegate(this).execute("ALL");
    }

    public void DisplayUserListScreen(ArrayList<User> userList) {
        this.userListScreen.displayAllUsers(userList);
    }

    public void DisplayPresenterProducerScreen(ArrayList<User> userList) {
        this.reviewSelectPresenterProducerScreen.AllUsersRetrieved(userList);
    }

    public void setMaintainUser(int actionType, User user) {
        this.actionType = actionType;
        this.user = user != null ? user:new User();
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


    public void processCreateUser(User user) {
        new CreateUserDelegate(this).execute(user);


    }

    public void processDeleteUser(User user) {
        new DeleteUserDelegate(this).execute(user);

    }

    public void processModifyUser(User user) {
        new ModifyUserDelegate(this).execute(user);
    }

    public void userCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUsecase();
    }

    public void userDeleted(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUsecase();
    }

    public void userModified(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUsecase();
    }
}
