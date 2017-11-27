package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

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

    public Price(DataSnapshot dataSnapshot)
    {
        Price price = dataSnapshot.getValue(Price.class);

        byHour = price.byHour;
        byPeriod = price.byPeriod;
    }

    public void setByHour(double price){byHour = price;}
    public void setByPeriod(double price){byPeriod = price;}

    public double getByHour(){return byHour;}
    public double getByPeriod(){return byPeriod;}
}
