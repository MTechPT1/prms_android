package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

public class ReviewSelectScheduleProgramController {

     private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void startUseCase(){
         Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleProgramScreen.class);
         MainController.displayScreen(intent);
     }

    public void  retrieveScheduleProgram(){

    }

    public void selectCreateSchedule(int actionType){
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);
        MainController.displayScreen(intent);

    }

    public void setMaintainSchedule(int actionType){
        this.actionType = actionType;
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);
        MainController.displayScreen(intent);
    }
}
