package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.io.Serializable;

import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ProgramSlot implements Serializable {

    private int id;
    private int weekId;
    private int duration;

    private String assignedBy;
    private String startTime;

    private User presenter;
    private User producer;
    private RadioProgram radioProgram;

    public ProgramSlot(){

    }

    public ProgramSlot(int id, RadioProgram radioProgram, User presenter, User producer, int duration, String assignedBy, String startTime, int weekId) {
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

    public User getPresenter() {
        return presenter;
    }

    public void setPresenter(User presenter) {
        this.presenter = presenter;
    }

    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
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
