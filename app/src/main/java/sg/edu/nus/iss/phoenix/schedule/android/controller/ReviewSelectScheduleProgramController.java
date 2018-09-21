package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

public class ReviewSelectScheduleProgramController {

     private int actionType;

    private ReviewSelectScheduleProgramScreen reviewSelectScheduleProgramScreen;

    /**
     * TODO - why is this used?
     * @return
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * TODO - why is this used?
     * @param actionType
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * Displays the main screen again
     */
    public void startUseCase(){
         Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleProgramScreen.class);
         MainController.displayScreen(intent);
     }

    /**
     * Invoked from ReviewSelectScheduleProgramScreen to fetch the list of program slots
     * for a particular week from backend
     * @param reviewSelectScheduleProgramScreen
     * @param weekId
     */
    public void  retrieveScheduleProgram(ReviewSelectScheduleProgramScreen reviewSelectScheduleProgramScreen, String weekId){
        this.reviewSelectScheduleProgramScreen = reviewSelectScheduleProgramScreen;
        new RetrieveScheduleDelegate(this).execute(weekId);
    }

    /**
     * This is invoked from the delegate with the retrieved program slots
     * @param scheduleProgram
     */
    public void displayScheduleProgram(ScheduleProgram scheduleProgram){
        reviewSelectScheduleProgramScreen.displayScheduleProgram(scheduleProgram);
    }

    /**
     * TODO - why is this used?
     * @param actionType
     */
    public void selectCreateSchedule(int actionType){
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);
        MainController.displayScreen(intent);

    }

    /**
     * TODO - why is this used?
     * @param actionType
     */
    public void setMaintainSchedule(int actionType){
        this.actionType = actionType;
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);
        MainController.displayScreen(intent);
    }
}
