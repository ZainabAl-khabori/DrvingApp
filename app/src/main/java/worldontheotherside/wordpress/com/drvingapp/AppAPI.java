package worldontheotherside.wordpress.com.drvingapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by زينب on 11/20/2017.
 */

public class AppAPI {

    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public static String TRAINEES = db.child("trainees").toString();
    public static String CURRENT_TRAINEES = db.child("trainees").child("current_trainees").toString();
    public static String FORMER_TRAINEES = db.child("trainees").child("former_trainees").toString();
    public static String FORMER_TRAINEE_BY_ID = db.child("trainees").child("former_trainees").orderByChild("civilNo").getRef().toString();
    //public static String CURRENT_TRAINEE_BY_NAME = db.child("trainees").child("current_trainees").orderByChild("name").getRef().toString();

    public static String TRAINERS = db.child("trainers").toString();
    //public static String TRAINER_BY_NAME = db.child("trainers").orderByChild("name").getRef().toString();
    //public static String TRAINER_BY_AREA = db.child("trainers").orderByChild("places").getRef().toString();
    public static String TRAINER_BY_ID = db.child("trainers").orderByChild("civilNo").getRef().toString();

    public static String CONTRACTS = db.child("contracts").toString();
    public static String CONTRACT_BY_TRAINEE = db.child("contracts").orderByChild("traineeId").getRef().toString();
    public static String CONTRACT_BY_TRAINER = db.child("contracts").orderByChild("trainerId").getRef().toString();

    public static String TRAINER_RATE = db.child("trainer_rate").toString();

    public static String TRAINEE_RATE = db.child("trainee_rate").toString();


    public static String AREAS = db.child("areas").toString();
    public static String AREASCHILD = db.child("areas").child("-L-QY9EKooKWx_nM2JH9").toString();

    public static String LANGUAGES = db.child("languages").toString();
}
