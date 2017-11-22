package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by u106543 on 11/19/2017.
 */

public class Trainer {

    private String username;
    private String email;
    private int age;
    private String phone;
    private String gender;
    private String password;
    private long carNo;
    private String place;
    private String vehicleType;
    private String language;


    ///////
    public void setUsername(String username){this.username = username;}
    public void setEmail(String email){this.email = email;}
    public void setAge(int age){this.age = age;}
    public void setPhone(String phone){this.phone = phone;}
    public void setGender(String gender){this.gender = gender;}
    public void setPassword(String password){this.password = password;}
    public void setCarNo(Long carNo){this.carNo = carNo;}
    public void setPlace(String place){this.place = place;}
    public void setVehicleType(String vehicleType){this.vehicleType = vehicleType;}
    public void setLanguage(String language){this.language = language;}

    /////
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public int getAge(){return age;}
    public String getPhone(){return phone;}
    public String getGender(){return gender;}
    public String getPassword(){return password;}
    public long getCarNo(){return carNo;}
    public String getPlace(){return place;}
    public String getVehicleType(){return vehicleType;}
    public String getLanguage(){return language;}

    ////
    public void Login(){}
    public void SignUp(){}
    public void RequestAccept(){}
    public void Rate(){}



}
