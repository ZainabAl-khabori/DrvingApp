package worldontheotherside.wordpress.com.drvingapp;

/**
 * Created by u106543 on 11/19/2017.
 */

public class Contract {

    private String time;
    private double price;
    private String type;



    //////
    public void setTime(String time){this.time = time;}
    public void setPrice(double price){this.price = price;}
    public void setType(String type){this.type = type;}


    //////
    public String getTime(){return time;}
    public double getPrice(){return price;}
    public String getType(){return type;}


    //////
    public void Select(){}
}
