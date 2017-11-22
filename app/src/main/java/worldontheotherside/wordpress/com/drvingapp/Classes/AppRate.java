package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by u106543 on 11/22/2017.
 */

public class AppRate {
    private Integer degreeOfSatisfaction;
    private String notes;

    ////
    public void setDegreeOfSatisfaction(Integer degreeOfSatisfaction){this.degreeOfSatisfaction = degreeOfSatisfaction;}
    public void setNotes(String notes){this.notes = notes;}

    ////
    public Integer getDegreeOfSatisfaction(){return degreeOfSatisfaction;}
    public String getNotes(){return notes;}
}
