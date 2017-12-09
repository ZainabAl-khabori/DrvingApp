package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by u106543 on 11/19/2017.
 */

public class Trainer {

    private String name;
    private long civilNo;
    private String email;
    private int age;
    private String phone;
    private String gender;
    private String password;
    private String carNo;
    private HashMap<String, String> places;
    private String vehicleType;
    private HashMap<String, String> languages;
    private TrainerRate rate;
    private Price price;
    private String contractType;
    private String spokenLanguage;
    private String trainingAreas;

    public Trainer()
    {
        //
    }

    public Trainer(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            Trainer trainer = snapshot.getValue(Trainer.class);

            name = trainer.name;
            civilNo = trainer.civilNo;
            email = trainer.email;
            age = trainer.age;
            phone = trainer.phone;
            gender = trainer.gender;
            password = trainer.password;
            carNo = trainer.carNo;
            places = trainer.places;
            vehicleType = trainer.vehicleType;
            languages = trainer.languages;
            rate = trainer.rate;
            price = trainer.price;
            contractType = trainer.contractType;
            spokenLanguage = trainer.spokenLanguage;
            trainingAreas=trainer.trainingAreas;
        }
    }

    ///////
    public void setName(String name){this.name = name;}
    public void setCivilId(long civilId){this.civilNo = civilId;}
    public void setEmail(String email){this.email = email;}
    public void setAge(int age){this.age = age;}
    public void setPhone(String phone){this.phone = phone;}
    public void setGender(String gender){this.gender = gender;}
    public void setPassword(String password){this.password = password;}
    public void setCarNo(String carNo){this.carNo = carNo;}
    public void setPlaces(HashMap<String, String> places){this.places = places;}
    public void setVehicleType(String vehicleType){this.vehicleType = vehicleType;}
    public void setLanguages(HashMap<String, String> languages){this.languages = languages;}
    public void setRate(TrainerRate rate){this.rate = rate;}
    public void setPrice(Price price){this.price = price;}
    public void setContractType(String contractType) {this.contractType = contractType;}

    /////
    public String getName(){return name;}
    public long getCivilId(){return civilNo;}
    public String getEmail(){return email;}
    public int getAge(){return age;}
    public String getPhone(){return phone;}
    public String getGender(){return gender;}
    public String getPassword(){return password;}
    public String getCarNo(){return carNo;}
    public HashMap<String, String> getPlaces(){return places;}
    public String getVehicleType(){return vehicleType;}
    public HashMap<String, String> getLanguages(){return languages;}
    public TrainerRate getRate(){return rate;}
    public Price getPrice(){return price;}
    public String getContractType() {return contractType;}

    ////
    public void Login(){}
    public void SignUp(){}
    public void RequestAccept(){}
    public void Rate(){}


    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getTrainingAreas() {
        return trainingAreas;
    }

    public void setTrainingAreas(String trainingAreas) {
        this.trainingAreas = trainingAreas;
    }
}
