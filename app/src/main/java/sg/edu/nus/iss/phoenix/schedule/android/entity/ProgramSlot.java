package sg.edu.nus.iss.phoenix.schedule.android.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ProgramSlot {

    private int id;
    private int weekId;
    private int duration;

    private String assignedBy;
    private String startTime;

    private Presenter presenter;
    private Producer producer;
    private RadioProgram radioProgram;

    public ProgramSlot(int id, RadioProgram radioProgram, Presenter presenter, Producer producer, int duration, String assignedBy, String startTime,int weekId) {
        this.id = id;
        this.radioProgram = radioProgram;
        this.presenter = presenter;
        this.producer = producer;
        this.duration = duration;
        this.assignedBy = assignedBy;
        this.startTime = startTime;
        this.weekId = weekId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public RadioProgram getRadioProgram() {
        return radioProgram;
    }

    public void setRadioProgram(RadioProgram radioProgram) {
        this.radioProgram = radioProgram;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }
}
