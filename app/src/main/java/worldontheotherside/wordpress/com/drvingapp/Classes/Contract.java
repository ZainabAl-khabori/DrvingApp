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
    private long traineeId;
    private long trainerId;
    private boolean drumsPass;
    private boolean slopePass;
    private boolean roadPass;
    private boolean cancelled;

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
        drumsPass = contract.drumsPass;
        slopePass = contract.slopePass;
        roadPass = contract.roadPass;
    }

    //////
    public void setStart(String start){this.start = start;}
    public void setEnd(String end){this.end = end;}
    public void setPrice(double price){this.price = price;}
    public void setType(String type){this.type = type;}
    public void setTraineeId(long id){traineeId = id;}
    public void setTrainerId(long trainerId){this.trainerId = trainerId;}
    public void setDrumsPass(boolean drumsPass) {this.drumsPass = drumsPass;}
    public void setSlopePass(boolean slopePass) {this.slopePass = slopePass;}
    public void setRoadPass(Boolean roadPass) {this.roadPass = roadPass;}

    //////
    public double getPrice(){return price;}
    public String getType(){return type;}
    public String getStart(){return start;}
    public String getEnd(){return end;}
    public long getTraineeId(){return traineeId;}
    public long getTrainerId(){return trainerId;}
    public boolean getDrumsPass() {return drumsPass;}
    public boolean getSlopePass() {return slopePass;}
    public boolean getRoadPass() {return roadPass;}

    //////
    public void Select(){}
}
