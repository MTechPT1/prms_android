package sg.edu.nus.iss.phoenix.radioprogram.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ProgramListScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.UpdateProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.CreateProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.DeleteProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;

public class ProgramController {
    // Tag for logging.
    private static final String TAG = ProgramController.class.getName();

    private ProgramListScreen programListScreen;
    private MaintainProgramScreen maintainProgramScreen;
    private RadioProgram rp2edit = null;

    public void startUseCase() {
        rp2edit = null;
        Intent intent = new Intent(MainController.getApp(), ProgramListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayProgramList(ProgramListScreen programListScreen) {
        this.programListScreen = programListScreen;
        new RetrieveProgramsDelegate(this).execute("all");
    }

    public void programsRetrieved(List<RadioProgram> radioPrograms) {
        programListScreen.showPrograms(radioPrograms);
    }

    public void selectCreateProgram() {
        rp2edit = null;
        Intent intent = new Intent(MainController.getApp(), MaintainProgramScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectEditProgram(RadioProgram radioProgram) {
        rp2edit = radioProgram;
        Log.v(TAG, "Editing radio program: " + radioProgram.getRadioProgramName() + "...");

        Intent intent = new Intent(MainController.getApp(), MaintainProgramScreen.class);
/*        Bundle b = new Bundle();
        b.putString("Name", radioProgram.getRadioProgramName());
        b.putString("Description", radioProgram.getRadioProgramDescription());
        b.putString("Duration", radioProgram.getRadioProgramDuration());
        intent.putExtras(b);
*/
        MainController.displayScreen(intent);
    }

    public void onDisplayProgram(MaintainProgramScreen maintainProgramScreen) {
        this.maintainProgramScreen = maintainProgramScreen;
        if (rp2edit == null)
            maintainProgramScreen.createProgram();
        else
            maintainProgramScreen.editProgram(rp2edit);
    }

    public void selectUpdateProgram(RadioProgram rp) {
        new UpdateProgramDelegate(this).execute(rp);
    }

    public void selectDeleteProgram(RadioProgram rp) {
        new DeleteProgramDelegate(this).execute(rp.getRadioProgramName());
    }

    public void programDeleted(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void programUpdated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectCreateProgram(RadioProgram rp) {
        new CreateProgramDelegate(this).execute(rp);
    }

    public void programCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectCancelCreateEditProgram() {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void maintainedProgram() {
        ControlFactory.getMainController().maintainedProgram();
    }
}
