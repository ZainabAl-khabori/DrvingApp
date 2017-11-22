package worldontheotherside.wordpress.com.drvingapp.Classes;

/**
 * Created by زينب on 11/22/2017.
 */

public class Price {

    private double byHour;
    private double byPeriod;

    public Price()
    {
        byHour = 0;
        byPeriod = 0;
    }

    public void setByHour(double price){byHour = price;}
    public void setByPeriod(double price){byPeriod = price;}

    public double getByHour(){return byHour;}
    public double getByPeriod(){return byPeriod;}
}
