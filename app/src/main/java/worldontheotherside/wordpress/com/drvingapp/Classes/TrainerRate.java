package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by u106543 on 11/19/2017.
 */

public class TrainerRate {

    private double attendance;
    private double respectTime;
    private double dealing;
    private double learningEfficiency;
    private String others;
    private double ratingAverage;

    public TrainerRate() { /* */ }

    public TrainerRate(DataSnapshot dataSnapshot) {
        TrainerRate rate = dataSnapshot.getValue(TrainerRate.class);

        attendance = rate.attendance;
        respectTime = rate.respectTime;
        dealing = rate.dealing;
        learningEfficiency = rate.learningEfficiency;
        others = rate.others;
        ratingAverage = rate.ratingAverage;
    }


    ////////
    public void setAttendance(double attendance) {
        this.attendance = attendance;
    }

    public void setRespectTime(double respectTime) {
        this.respectTime = respectTime;
    }

    public void setDealing(double dealing) {
        this.dealing = dealing;
    }

    public void setLearningEfficiency(double learningEfficiency) {
        this.learningEfficiency = learningEfficiency;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public void setRatingAverage(double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    //////
    public double getAttendance() {
        return attendance;
    }

    public double getRespectTime() {
        return respectTime;
    }

    public double getDealing() {
        return dealing;
    }

    public double getLearningEfficiency() {
        return learningEfficiency;
    }

    public String getOthers() {
        return others;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }


}
