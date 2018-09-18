package sg.edu.nus.iss.phoenix.schedule.android.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ProgramSlot {

    String assignedBy;
    String startTime;
    int duration;
    Presenter presenter;
    Producer producer;
    RadioProgram radioProgram;


    public ProgramSlot(RadioProgram radioProgram, Presenter presenter, Producer producer, int duration, String assignedBy, String startTime) {
        this.radioProgram = radioProgram;
        this.presenter = presenter;
        this.producer = producer;
        this.duration = duration;
        this.assignedBy = assignedBy;
        this.startTime = startTime;
    }

    public void createProgramSlot(ProgramSlot programSlot) {
    }

    public String getAttribute() {
        return radioProgram.getRadioProgramName();
    }

    public void isProgramSlotAssigned(int index) {
    }

    public void modifyProgramSlot(ProgramSlot programSlot) {
    }

    public void removeProgramSlot(ProgramSlot programSlot) {
    }

    public void setAttribute() {
    }
}
