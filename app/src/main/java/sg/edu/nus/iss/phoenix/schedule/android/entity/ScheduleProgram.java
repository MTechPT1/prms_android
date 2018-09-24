package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.util.List;

public class ScheduleProgram {

    List<ProgramSlot> programSlots;

    public ScheduleProgram() {
        
    }

    public ScheduleProgram(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }

    public List<ProgramSlot> getProgramSlots() {
        return programSlots;
    }

    public void setProgramSlots(List<ProgramSlot> programSlots) {
        this.programSlots = programSlots;
    }
}
