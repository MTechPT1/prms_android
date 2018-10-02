package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.util.List;

public class ScheduleProgram {

    List<ProgramSlot> programSlots;

    /**
     * Constructor for scheduleProgram
     */
    public ScheduleProgram() {

    }

    /**
     * Constructor for scheduleProgram
     */
    public ScheduleProgram(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }

    /**
     * To get the list of program slot
     */
    public List<ProgramSlot> getProgramSlots() {
        return programSlots;
    }

    /**
     * To set the list of program slot
     */
    public void setProgramSlots(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }
}
