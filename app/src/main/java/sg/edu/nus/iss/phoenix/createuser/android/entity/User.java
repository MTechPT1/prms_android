package sg.edu.nus.iss.phoenix.createuser.android.entity;

public class User {
    private String userId;
    private String userName;
    private String joinDate;
    private boolean isPresenter;
    private boolean isProducer;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isPresenter() {
        return isPresenter;
    }

    public void setPresenter(boolean isPresenter){
        this.isPresenter = isPresenter;
    }

    public void setProducer(boolean isProducer){
        this.isProducer = isProducer;
    }

    public boolean isProducer(){
        return isProducer;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
