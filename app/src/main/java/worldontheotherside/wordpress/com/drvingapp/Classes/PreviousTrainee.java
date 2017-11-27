package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by u106543 on 11/19/2017.
 */

public class PreviousTrainee {


    private String username;
    private String email;
    private String phone;
    private String gender;
    private String password;
    private long civilNo;

    public PreviousTrainee()
    {
        // Empty public default constructor
    }

    public PreviousTrainee(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            PreviousTrainee previousTrainee = snapshot.getValue(PreviousTrainee.class);

            username = previousTrainee.username;
            email = previousTrainee.email;
            phone = previousTrainee.phone;
            gender = previousTrainee.gender;
            password = previousTrainee.password;
            civilNo = previousTrainee.civilNo;
        }
    }

    ////
    public void setUsername(String username){this.username = username;}
    public void setEmail(String email){this.email = email;}
    public void setPhone(String phone){this.phone = phone;}
    public void setGender(String gender){this.gender = gender;}
    public void setPassword(String password){this.password = password;}
    public void setCivilNo(long civilNo){this.civilNo = civilNo;}


    ////
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getGender(){return gender;}
    public String getPassword(){return password;}
    public long getCivilNo(){return civilNo;}




    ///////////
    public void Login (){}
    public void SignUp (){}
    public void ShowList (){}
    public void Rate (){}



}
