package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by u106543 on 11/19/2017.
 */

public class Contract {

    private String start;
    private String end;
    private double price;
    private String type;
    private int traineeId;
    private int trainerId;


    public Contract(){ /* */ }

    public Contract(DataSnapshot dataSnapshot)
    {
        Contract contract = dataSnapshot.getValue(Contract.class);

        start = contract.start;
        end = contract.end;
        price = contract.price;
        type = contract.type;
        traineeId = contract.traineeId;
        trainerId = contract.trainerId;
    }


    //////
    public void setStart(String start){this.start = start;}
    public void setEnd(String end){this.end = end;}
    public void setPrice(double price){this.price = price;}
    public void setType(String type){this.type = type;}
    public void setTraineeId(int id){traineeId = id;}
    public void setTrainerId(int trainerId){this.trainerId = trainerId;}

    //////
    public double getPrice(){return price;}
    public String getType(){return type;}
    public String getStart(){return start;}
    public String getEnd(){return end;}
    public int getTraineeId(){return traineeId;}
    public int getTrainerId(){return trainerId;}

    //////
    public void Select(){}
}
