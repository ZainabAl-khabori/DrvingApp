package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by u106543 on 11/19/2017.
 */

public class NewTrainee {

    private String username;
    private String email;
    private int age;
    private String phone;
    private String gender;
    private String password;
    private long civilNo;
    private long drivingLicense;
    private String place;      // TODO: fix this to map
    private Boolean drumsPass;
    private Boolean slopePass;
    private Boolean roadPass;
    private Trainer currentTrainer;



    ////
    public void setUsername(String username){this.username = username;}
    public void setEmail(String email){this.email = email;}
    public void setAge(int age){this.age = age;}
    public void setPhone(String phone){this.phone = phone;}
    public void setGender(String gender){this.gender = gender;}
    public void setPassword(String password){this.password = password;}
    public void setCivilNo(long civilNo){this.civilNo = civilNo;}
    public void setDrivingLicense(long drivingLicense){this.drivingLicense = drivingLicense;}
    public void setPlace(String place){this.place = place;}
    public void setDrumsPass(Boolean drumsPass) {this.drumsPass = drumsPass;}
    public void setSlopePass(Boolean slopePass) {this.slopePass = slopePass;}
    public void setRoadPass(Boolean roadPass) {this.roadPass = roadPass;}
    public void setCurrentTrainer(Trainer currentTrainer) {this.currentTrainer = currentTrainer;}

    ////
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public int getAge(){return age;}
    public String getPhone(){return phone;}
    public String getGender(){return gender;}
    public String getPassword(){return password;}
    public long getCivilNo(){return civilNo;}
    public long getDrivingLicense(){return drivingLicense;}
    public String getPlace(){return place;}
    public Boolean getDrumsPass() {return drumsPass;}
    public Boolean getSlopePass() {return slopePass;}
    public Boolean getRoadPass() {return roadPass;}
    public Trainer getCurrentTrainer() {return currentTrainer;}

    ////
    public void BookTainer(){}
    public void Login(){}
    public void SignUp(){}
}
