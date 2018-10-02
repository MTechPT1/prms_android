package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.io.Serializable;

import sg.edu.nus.iss.phoenix.createuser.android.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ProgramSlot implements Serializable {

    private int id;
    private int weekId;
    private int duration;
    private int year;

    private String assignedBy;
    private String startTime;

    private User presenter;
    private User producer;
    private RadioProgram radioProgram;

    /**
     * Constructor for ProgramSlot
     */
    public ProgramSlot() {

    }

    /**
     * Constructor for ProgramSlot
     */
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

    /**
     * To get the year
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * To set the year
     *
     * @param year selected year to retrieve
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * To get the ID of the program slot
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * To set the ID of the program slot
     *
     * @param id id of the program slot
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * To get the person who assign the program slot
     *
     * @return name of the person who assign the program slot
     */
    public String getAssignedBy() {
        return assignedBy;
    }

    /**
     * To set the person who assign the program slot
     *
     * @param assignedBy name of the person who assign the program slot
     */
    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    /**
     * To get the start time of the program slot
     *
     * @return start time of the program slot
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * To set the start time of the program slot
     *
     * @param startTime start time of the program slot
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * To get the duration of the program slot
     *
     * @return duration of the program slot
     */
    public int getDuration() {
        return duration;
    }

    /**
     * To set the duration of the program slot
     *
     * @param duration duration of the program slot
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * To get the presenter of the program slot
     *
     * @return presenter of the program slot
     */
    public User getPresenter() {
        return presenter;
    }

    /**
     * To set the presenter of the program slot
     *
     * @param presenter presenter of the program slot
     */
    public void setPresenter(User presenter) {
        this.presenter = presenter;
    }

    /**
     * To get the producer of the program slot
     *
     * @return producer of the program slot
     */
    public User getProducer() {
        return producer;
    }

    /**
     * To set the producer of the program slot
     *
     * @param producer producer of the program slot
     */
    public void setProducer(User producer) {
        this.producer = producer;
    }

    /**
     * To get the radio program of the program slot
     *
     * @return radio program of the program slot
     */
    public RadioProgram getRadioProgram() {
        return radioProgram;
    }

    /**
     * To set the radio program of the program slot
     *
     * @param radioProgram radio program of the program slot
     */
    public void setRadioProgram(RadioProgram radioProgram) {
        this.radioProgram = radioProgram;
    }

    /**
     * To get the week id of the program slot
     *
     * @return weekId of the program slot
     */
    public int getWeekId() {
        return weekId;
    }

    /**
     * To set the week id of the program slot
     *
     * @param weekId weekId of the program slot
     */
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }
}
