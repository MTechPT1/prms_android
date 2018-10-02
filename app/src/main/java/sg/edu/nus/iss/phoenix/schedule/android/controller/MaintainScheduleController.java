package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CopyScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.ModifyScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

/**
 * <p><b>MaintainScheduleController</b> controls the flow between the schedule
 * screen to the backend for creating, modifying, deleting and copying the program slots.</p>
 *
 *@author: neelima nair
 */
public class MaintainScheduleController {

    CreateScheduleDelegate createDelegate;
    ModifyScheduleDelegate modifyDelegate;
    CopyScheduleDelegate copyDelegate;
    DeleteScheduleDelegate delDelegate;
    ProgramSlot programSlot = null;
    private ScheduleScreen scheduleScreen;

    /**
     * Displays the main screen again
     */
    public void startUseCase(){
         Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleProgramScreen.class);
         MainController.displayScreen(intent);
     }

    /**
     * Invoked by the ScheduleScreen to create the program slot from backend
     * @param programSlot
     */
    public void createSchedule(ProgramSlot programSlot, ScheduleScreen scheduleScreen){
        this.scheduleScreen = scheduleScreen;
        createDelegate = new CreateScheduleDelegate(this);
        createDelegate.execute(programSlot);
    }

    /**
     * Invoked by the ScheduleScreen to modify the program slot from backend
     * @param programSlot
     */
    public void modifySchedule(ProgramSlot programSlot, ScheduleScreen scheduleScreen){
        this.scheduleScreen = scheduleScreen;
        modifyDelegate = new ModifyScheduleDelegate(this);
        modifyDelegate.execute(programSlot);
    }

    /**
     * Invoked by the ScheduleScreen to copy the program slot from backend
     * @param programSlot
     */
    public void copySchedule(ProgramSlot programSlot, ScheduleScreen scheduleScreen){
        this.scheduleScreen = scheduleScreen;
        copyDelegate = new CopyScheduleDelegate(this);
        copyDelegate.execute(programSlot);
    }

    /**
     * Displays the error message in the toast
     * @param message
     */
    public void displayError(String message) {
        // Go back to ProgramList screen with refreshed programs.
//        Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleProgramScreen.class);
//        intent.putExtra(Constant.ERRORMESSAGE,message);
//        MainController.displayScreen(intent);
        this.scheduleScreen.displayError(message);
    }

    /**
     * Invoked by the ScheduleScreen to delete the program slot from backend
     * @param programSlot
     */
    public void deleteSchedule(ProgramSlot programSlot, ScheduleScreen scheduleScreen){
        this.scheduleScreen = scheduleScreen;
        delDelegate = new DeleteScheduleDelegate(this);
        delDelegate.execute(programSlot);
    }

    /**
     * Called by the delegate after the onPostExecute
     * @param success
     */
    public void scheduleModified(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    /**
     * Called by the delegate after the onPostExecute
     * @param success
     */
    public void scheduleCopied(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    /**
     * Called by the delegate after the onPostExecute
     * @param success
     */
    public void scheduleCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    /**
     * Called by the delegate after the onPostExecute
     * @param success
     */
    public void scheduleDeleted(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }
}
