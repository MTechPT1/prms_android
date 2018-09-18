package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.util.ArrayList;

public class ScheduleProgram {

    ArrayList<ProgramSlot> programSlots;


    public ScheduleProgram(ArrayList<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }

    public void RetrieveScheduleProgram() {

    }

    public String getAttribute(int index) {
        return programSlots.get(index-1).getAttribute();
    }

}
