package worldontheotherside.wordpress.com.drvingapp;

/**
 * Created by u106543 on 11/19/2017.
 */

public class TraineeRate {

    private boolean attendance;
    private boolean respectTime;
    private String dealing;
    private String learnQuickness;
    private String others;


    ////////
    public void setAttendance(boolean attendance){this.attendance = attendance;}
    public void setRespectTime(boolean respectTime){this.respectTime = respectTime;}
    public void setDealing(String dealing){this.dealing = dealing;}
    public void setLearnQuickness(String learnQuickness){this.learnQuickness = learnQuickness;}
    public void setOthers(String others){this.others = others;}

    //////
    public boolean getAttendance(){return attendance;}
    public boolean getRespectTime(){return respectTime;}
    public String getDealing(){return dealing;}
    public String getLearnQuickness(){return learnQuickness;}
    public String getOthers(){return others;}

}
