package sg.edu.nus.iss.phoenix.radioprogram.entity;

import java.io.Serializable;

public class RadioProgram implements Serializable {

    private String radioProgramName;
    private String radioProgramDescription;
    private String radioProgramDuration;

    public RadioProgram() {

    }

    public RadioProgram(String radioProgramName, String radioProgramDescription, String radioProgramDuration) {
        this.radioProgramName = radioProgramName;
        this.radioProgramDescription = radioProgramDescription;
        this.radioProgramDuration = radioProgramDuration;
    }

    public String getRadioProgramName() {
        return radioProgramName;
    }

    public void setRadioProgramName(String radioProgramName) {
        this.radioProgramName = radioProgramName;
    }

    public String getRadioProgramDescription() {
        return radioProgramDescription;
    }

    public String getRadioProgramDuration() {
        return radioProgramDuration;
    }

    public void setRadioProgramDescription(String radioProgramDescription) {
        this.radioProgramDescription = radioProgramDescription;
    }

    public void setRadioProgramDuration(String radioProgramDuration) {
        this.radioProgramDuration = radioProgramDuration;
    }
}
