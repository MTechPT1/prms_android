/**
 * @author: neelima nair
 */
package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;
import android.os.Bundle;

import sg.edu.nus.iss.phoenix.Constant;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ScheduleProgram;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

public class ReviewSelectScheduleProgramController {

    private int actionType;
    private ReviewSelectScheduleProgramScreen reviewSelectScheduleProgramScreen;

    /**
     * Get actionType param
     * @return
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * Set actionType param
     * @param actionType
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * Displays the main screen again
     */
    public void startUseCase(String username) {
        Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleProgramScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * Invoked from ReviewSelectScheduleProgramScreen to fetch the list of program slots
     * for a particular week from backend
     * @param reviewSelectScheduleProgramScreen
     * @param weekId
     */
    public void retrieveScheduleProgram(ReviewSelectScheduleProgramScreen reviewSelectScheduleProgramScreen, String weekId, String year) {
        this.reviewSelectScheduleProgramScreen = reviewSelectScheduleProgramScreen;
        new RetrieveScheduleDelegate(this).execute(weekId, year);
    }

    /**
     * This is invoked from the delegate with the retrieved program slots
     * @param scheduleProgram
     */
    public void displayScheduleProgram(ScheduleProgram scheduleProgram) {
        reviewSelectScheduleProgramScreen.displayScheduleProgram(scheduleProgram);
    }

    /**
     * TODO - why is this used?
     * @param actionType
     */
    public void selectCreateSchedule(int actionType) {
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);
        MainController.displayScreen(intent);
    }


    /*
     * Redirects to a specific schedule screen based on whether EDIT, COPY or MODIFY is selected in
     * ProgramSchedule Screen
     * @param actionType-specifies the type of action being requested from screen
     */
    public void setMaintainSchedule(int actionType, ProgramSlot programSlot) {

        this.actionType = actionType;
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        intent.putExtra(Constant.SCHEDULEMODE, actionType);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PRORGRAMSLOT, programSlot);
        intent.putExtras(bundle);

        MainController.displayScreen(intent);
    }

    /**
     * Displays the error message in the toast
     * @param message
     */
    public void displayError(String message) {
        // Go back to ProgramList screen with refreshed programs.
        reviewSelectScheduleProgramScreen.displayError(message);
    }
}
