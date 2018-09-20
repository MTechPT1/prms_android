package sg.edu.nus.iss.phoenix.schedule.android.entity;

import android.support.annotation.RestrictTo;

import java.util.ArrayList;

public class ScheduleProgram {

    ArrayList<ProgramSlot> programSlots;


    public ScheduleProgram(ArrayList<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }

    public void RetrieveScheduleProgram() {

    }

    public String getAttribute(int index) {
        //TODO - Correct the logic here
        //return programSlots.get(index-1).getAttribute();
        return null;
    }

}
