package sg.edu.nus.iss.phoenix.core.android.controller;

import android.app.Application;
import android.content.Intent;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class MainController {
    private static Application app = null;
    private MainScreen mainScreen;

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        MainController.app = app;
    }

    public static void displayScreen(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }

    public void startUseCase(String username) {
        Constant.loggedUserName = username;

        Intent intent = new Intent(MainController.getApp(), MainScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        mainScreen.showUsername(Constant.loggedUserName);
    }

    public void selectMaintainProgram() {
        ControlFactory.getProgramController().startUseCase();
    }

    public void maintainedProgram() {
        startUseCase(Constant.loggedUserName);
    }

    public void selectLogout() {
        Constant.loggedUserName = "<not logged in>";
        ControlFactory.getLoginController().logout();
    }

    public void selectMaintainSchedule() {
        ControlFactory.getReviewSelectScheduleController().startUseCase(Constant.loggedUserName);
    }

    public void maintainUser(){
        startUseCase(Constant.loggedUserName);
    }

    public void selectMaintainUser(){
        ControlFactory.getMaintainUserController().startUsecase();
    }

    // This is a dummy operation to test the invocation of Review Select Radio Program use case.
    public void selectedProgram(RadioProgram rpSelected) {
        startUseCase(Constant.loggedUserName);
    }
}
