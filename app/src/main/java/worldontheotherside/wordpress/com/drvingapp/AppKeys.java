package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by زينب on 12/3/2017.
 */

public class AppKeys {
    // This class is for the strings, ints, booleans, etc... that will be used in the app frequently

    public static String PREV_TRAINEE = "previous trainee";
    public static String NEW_TRAINEE = "new trainee";
    public static String INSTRUCTOR = "instructor";

    public static final int SELECT_PICTURE = 2000;



    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String AGE = "Age";
    public static final String CIVIL_NO = "CivilNo";
    public static final String GENDER = "Gender";
    public static final String CAR_NO = "CarNo";
    public static final String PLACES = "Places";
    public static final String LANGUAGES = "Language";
    public static final String VEHICLE_TYPE = "vehicleType";
    public static final String PRICE = "Price";
    public static final String PHONE_NUMBER = "Phone Number";

    public static boolean isValidEmail(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    public static void displayMessageToast(Context context, String displayMessage){
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }

}
