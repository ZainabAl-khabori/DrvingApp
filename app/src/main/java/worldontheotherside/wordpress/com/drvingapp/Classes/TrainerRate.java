package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by u106543 on 11/19/2017.
 */

public class TrainerRate {

    private Float attendance;
    private Float respectTime;
    private Float dealing;
    private Float learningEfficiency;
    private String others;
    private String ratingDate;
    private Float ratingAverage;

    public TrainerRate() { /* */ }

    public TrainerRate(DataSnapshot dataSnapshot) {
        TrainerRate rate = dataSnapshot.getValue(TrainerRate.class);

        attendance = rate.attendance;
        respectTime = rate.respectTime;
        dealing = rate.dealing;
        learningEfficiency = rate.learningEfficiency;
        others = rate.others;
        ratingAverage = rate.ratingAverage;
        ratingDate = rate.ratingDate;
    }


    ////////
    public void setAttendance(Float attendance) {
        this.attendance = attendance;
    }

    public void setRespectTime(Float respectTime) {
        this.respectTime = respectTime;
    }

    public void setDealing(Float dealing) {
        this.dealing = dealing;
    }

    public void setLearningEfficiency(Float learningEfficiency) {
        this.learningEfficiency = learningEfficiency;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public void setRatingAverage(Float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    //////
    public Float getAttendance() {
        return attendance;
    }

    public Float getRespectTime() {
        return respectTime;
    }

    public Float getDealing() {
        return dealing;
    }

    public Float getLearningEfficiency() {
        return learningEfficiency;
    }

    public String getOthers() {
        return others;
    }

    public Float getRatingAverage() {
        return ratingAverage;
    }


    public String getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }
}
