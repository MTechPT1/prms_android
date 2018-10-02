package sg.edu.nus.iss.phoenix.createuser.android.controller;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
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

    public void setMaintainUserScreen(MaintainUserScreen maintainUserScreen) {
        this.maintainUserScreen = maintainUserScreen;
    }

    public void startUsecase() {
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        userListScreen.showLoadingIndicator();
        new RetrieveUsersDelegate(this).execute("ALL");
    }

    public void onDisplayPresenterProducerList(ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen) {
        this.reviewSelectPresenterProducerScreen = reviewSelectPresenterProducerScreen;
        new RetrievePresenterProducerDelegate(this).execute("ALL");
    }

    public void DisplayUserListScreen(ArrayList<User> userList) {
        this.userListScreen.displayAllUsers(userList);
        userListScreen.hideLoadingIndicator();
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
        maintainUserScreen.showLoadingIndicator();
        new CreateUserDelegate(this).execute(user);


    }

    public void processDeleteUser(User user) {
        maintainUserScreen.showLoadingIndicator();
        new DeleteUserDelegate(this).execute(user);

    }

    public void processModifyUser(User user) {
        maintainUserScreen.showLoadingIndicator();
        new ModifyUserDelegate(this).execute(user);
    }

    public void userCreated(boolean success,String errorMsg) {
        // Go back to ProgramList screen with refreshed programs.
        maintainUserScreen.hideLoadingIndicator();
        if (!success){
            Toast.makeText(maintainUserScreen, errorMsg, Toast.LENGTH_SHORT).show();
        }else {
            startUsecase();
        }
    }

    public void userDeleted(boolean success,String errorMsg) {
        // Go back to ProgramList screen with refreshed programs.
        maintainUserScreen.hideLoadingIndicator();
        if (!success){
            Toast.makeText(maintainUserScreen, errorMsg, Toast.LENGTH_SHORT).show();
        }else{
            startUsecase();
        }
    }

    public void userModified(boolean success, String errorMesg) {
        // Go back to ProgramList screen with refreshed programs.
        maintainUserScreen.hideLoadingIndicator();
        if (!success){
            Toast.makeText(maintainUserScreen, "User Modification failed", Toast.LENGTH_SHORT).show();
        }else {
            startUsecase();
        }
    }


    public void maintainUser() {
        ControlFactory.getMainController().maintainUser();
    }
}
