package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Classes.TrainerRate;

public class TrainerRating extends AppCompatActivity {

    private RatingBar ratingBarDealing;
    private RatingBar ratingBarRespectTime;
    private RatingBar ratingBarAttendance;
    private RatingBar ratingBarEfficiency;
    private Float Dealing;
    private Float RespectTime;
    private Float Attendance;
    private Float Efficiency;
    private Float ratingAverage;
    private TrainerRate trainerRate;
    private EditText editTextReview;
    private Trainer trainer;
    private Float DealingValue;
    private Float RespectTimeValue;
    private Float AttendanceValue;
    private Float EfficiencyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_rating);


        trainerRate = new TrainerRate();
        ratingBarDealing=(RatingBar)findViewById(R.id.ratingBarDealing) ;
        ratingBarRespectTime=(RatingBar)findViewById(R.id.ratingBarRespectTime) ;
        ratingBarAttendance=(RatingBar)findViewById(R.id.ratingBarAttendance) ;
        ratingBarEfficiency=(RatingBar)findViewById(R.id.ratingBarEfficiency) ;
        editTextReview=(EditText)findViewById(R.id.editTextReview) ;

        ratingBarDealing.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 DealingValue = (Float) ratingBarDealing.getRating();
                Dealing = (DealingValue / 5)*100;
                trainerRate.setDealing(Dealing);
                //Toast.makeText(getApplicationContext(), " Ratings1 : " + Dealing + "", Toast.LENGTH_SHORT).show();
            }
        });

        ratingBarRespectTime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


                 RespectTimeValue = (Float) ratingBarRespectTime.getRating();
                RespectTime =(RespectTimeValue / 5)*100;
                trainerRate.setRespectTime(RespectTime);

            }
        });


        ratingBarAttendance.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                 AttendanceValue = (Float) ratingBarAttendance.getRating();
                Attendance = (AttendanceValue / 5)*100;
                trainerRate.setAttendance(Attendance);

            }
        });




        ratingBarEfficiency.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                 EfficiencyValue = (Float) ratingBarEfficiency.getRating();
                Efficiency = (EfficiencyValue / 5)*100;
                trainerRate.setLearningEfficiency(Efficiency);
            }
        });





    }
    public void SubmitRating(View v)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate =  dateFormat.format(calendar.getTime());
        trainerRate.setRatingDate(strDate);

        ratingAverage=((DealingValue+RespectTimeValue+AttendanceValue+EfficiencyValue)/4);
        trainerRate.setRatingAverage(ratingAverage);
        /*SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat("Dealing", Dealing)
                .putFloat("RespectTime", RespectTime).putFloat("Attendance", Attendance)
                .putFloat("Efficiency", Efficiency).putFloat("ratingAverage", ratingAverage).apply();*/

        String strReview = editTextReview.getText().toString();

        if(!TextUtils.isEmpty(strReview))
            trainerRate.setOthers(editTextReview.getText().toString());

        trainer = new Trainer();
        trainer.setRate(trainerRate);

        DatabaseManip.updateData(AppAPI.TRAINER_RATE, trainer, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(TrainerRating.this, "trainerRate added", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TrainerRating.this, TrainerViewActivity.class);
                startActivity(i);
                finish();

            }

        });

        /*Intent intent = new Intent(TrainerRating.this, TrainerViewActivity.class);
        startActivity(intent);
        finish();
*/
        //Toast.makeText(this, "ratingAverage : " + ratingAverage, Toast.LENGTH_SHORT).show();
    }
}
