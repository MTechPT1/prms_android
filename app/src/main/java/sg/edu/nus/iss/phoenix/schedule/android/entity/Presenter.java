package sg.edu.nus.iss.phoenix.schedule.android.entity;

public class Presenter {

    int id;
    String address;
    String employmentDate;
    String name;

    public Presenter(int id, String name, String address, String employmentDate) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.employmentDate = employmentDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
