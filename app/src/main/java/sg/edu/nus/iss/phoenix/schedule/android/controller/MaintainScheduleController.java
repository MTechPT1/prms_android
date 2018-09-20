package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleScreen;

public class MaintainScheduleController {

    DeleteScheduleDelegate delDelegate;

    public void startUseCase(){
         Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
         MainController.displayScreen(intent);
     }

    public void deleteSchedule(){
        delDelegate = new DeleteScheduleDelegate(this);
        //delDelegate.execute();
    }

    public void scheduleModified(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void scheduleCopied(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void scheduleCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void scheduleDeleted(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }
}
