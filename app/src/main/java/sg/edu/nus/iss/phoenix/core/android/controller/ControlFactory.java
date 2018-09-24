package sg.edu.nus.iss.phoenix.core.android.controller;

import sg.edu.nus.iss.phoenix.authenticate.android.controller.LoginController;
import sg.edu.nus.iss.phoenix.createuser.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ReviewSelectScheduleProgramController;

public class ControlFactory {
    private static MainController mainController = null;
    private static LoginController loginController = null;
    private static ProgramController programController = null;
    private static ReviewSelectProgramController reviewSelectProgramController = null;
    private static ReviewSelectScheduleProgramController reviewSelectScheduleProgramController = null;
    private static MaintainScheduleController maintainScheduleController = null;
    private static MaintainUserController maintainUserController = null;

    public static MainController getMainController() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }

    public static LoginController getLoginController() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    public static ProgramController getProgramController() {
        if (programController == null) programController = new ProgramController();
        return programController;
    }

    public static ReviewSelectProgramController getReviewSelectProgramController() {
        if (reviewSelectProgramController == null) reviewSelectProgramController = new ReviewSelectProgramController();
        return reviewSelectProgramController;
    }

    public static ReviewSelectScheduleProgramController getReviewSelectScheduleController() {
        if (reviewSelectProgramController == null) reviewSelectScheduleProgramController = new ReviewSelectScheduleProgramController();
        return reviewSelectScheduleProgramController;
    }

    public static MaintainScheduleController getMaintainScheduleController(){
        if (maintainScheduleController==null) maintainScheduleController = new MaintainScheduleController();
        return maintainScheduleController;
    }

    public static MaintainUserController getMaintainUserController(){
        if (maintainUserController==null) maintainUserController = new MaintainUserController();
        return maintainUserController;
    }

}
