package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ReviewSelectPresenterProducerScreen;

public class ReviewSelectPresenterProducerController {

     private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void startUseCase(){
         Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
         MainController.displayScreen(intent);
     }


}
