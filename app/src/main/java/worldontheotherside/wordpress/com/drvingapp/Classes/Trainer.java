package worldontheotherside.wordpress.com.drvingapp.Classes;

import java.util.ArrayList;

/**
 * Created by u106543 on 11/19/2017.
 */

public class Trainer {

    private String name;
    private int civilId;
    private String email;
    private int age;
    private String phone;
    private String gender;
    private String password;
    private long carNo;
    private ArrayList<String> places;
    private String vehicleType;
    private ArrayList<String> languages;
    private TrainerRate rate;
    private Price price;


    ///////
    public void setName(String name){this.name = name;}
    public void setCivilId(int civilId){this.civilId = civilId;}
    public void setEmail(String email){this.email = email;}
    public void setAge(int age){this.age = age;}
    public void setPhone(String phone){this.phone = phone;}
    public void setGender(String gender){this.gender = gender;}
    public void setPassword(String password){this.password = password;}
    public void setCarNo(Long carNo){this.carNo = carNo;}
    public void setPlaces(ArrayList<String> places){this.places = places;}
    public void setVehicleType(String vehicleType){this.vehicleType = vehicleType;}
    public void setLanguages(ArrayList<String> languages){this.languages = languages;}
    public void setRate(TrainerRate rate){this.rate = rate;}
    public void setPrice(Price price){this.price = price;}

    /////
    public String getName(){return name;}
    public int getCivilId(){return civilId;}
    public String getEmail(){return email;}
    public int getAge(){return age;}
    public String getPhone(){return phone;}
    public String getGender(){return gender;}
    public String getPassword(){return password;}
    public long getCarNo(){return carNo;}
    public ArrayList<String> getPlaces(){return places;}
    public String getVehicleType(){return vehicleType;}
    public ArrayList<String> getLanguages(){return languages;}
    public TrainerRate getRate(){return rate;}
    public Price getPrice(){return price;}

    ////
    public void Login(){}
    public void SignUp(){}
    public void RequestAccept(){}
    public void Rate(){}



}
