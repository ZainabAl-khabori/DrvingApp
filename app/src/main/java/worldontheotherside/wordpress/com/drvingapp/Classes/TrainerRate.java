package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by u106543 on 11/19/2017.
 */

public class TrainerRate {

    private boolean attendance;
    private boolean respectTime;
    private String dealing;
    private String learningEfficiency;
    private String others;


    ////////
    public void setAttendance(boolean attendance){this.attendance = attendance;}
    public void setRespectTime(boolean respectTime){this.respectTime = respectTime;}
    public void setDealing(String dealing){this.dealing = dealing;}
    public void setLearningEfficiency(String learningEfficiency){this.learningEfficiency = learningEfficiency;}
    public void setOthers(String others){this.others = others;}

    //////
    public boolean getAttendance(){return attendance;}
    public boolean getRespectTime(){return respectTime;}
    public String getDealing(){return dealing;}
    public String getLearningEfficiency(){return learningEfficiency;}
    public String getOthers(){return others;}
}
