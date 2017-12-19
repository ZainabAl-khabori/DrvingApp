package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import worldontheotherside.wordpress.com.drvingapp.AppAPI;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Classes.TrainerRate;
import worldontheotherside.wordpress.com.drvingapp.DatabaseManip;
import worldontheotherside.wordpress.com.drvingapp.R;

public class ReviewsTabFragment extends Fragment {

    private ProgressBar progressBarDealing;
    private ProgressBar progressBarRespectTime;
    private ProgressBar progressBarAttendance;
    private ProgressBar progressBarEfficiency;
    private RatingBar ratingBarReviews;
    private TextView textViewDealingPercentage;
    private TextView textViewRespectTimePercentage;
    private TextView textViewAttendancePercentage;
    private TextView textViewEfficiencyPercentage;
    private TextView textViewComments;
    private TextView textViewTrainerName;
    private Handler handler = new Handler();
    private TrainerRate trainerRate;
    private Integer dealing;
    private Integer respectTime;
    private Integer attendance;
    private Integer efficiency;
    private String comments;
    private String trainerName;
    private TextView textViewCommentDate;
    private Float ratingAvg;
    private int dealingStatus = 0;
    private int respectTimeStatus = 0;
    private int attendanceStatus = 0;
    private int efficiencyStatus = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reviews_tab_contents, container, false);
        progressBarDealing = (ProgressBar) view.findViewById(R.id.progressBarDealing);
        progressBarRespectTime = (ProgressBar) view.findViewById(R.id.progressBarRespectTime);
        progressBarAttendance = (ProgressBar) view.findViewById(R.id.progressBarAttendance);
        progressBarEfficiency = (ProgressBar) view.findViewById(R.id.progressBarEfficiency);

        ratingBarReviews = (RatingBar) view.findViewById(R.id.ratingBarReviews);


        textViewDealingPercentage = (TextView) view.findViewById(R.id.textViewDealingPercentage);
        textViewRespectTimePercentage = (TextView) view.findViewById(R.id.textViewRespectTimePercentage);
        textViewAttendancePercentage = (TextView) view.findViewById(R.id.textViewAttendancePercentage);
        textViewEfficiencyPercentage = (TextView) view.findViewById(R.id.textViewEfficiencyPercentage);
        textViewComments = (TextView) view.findViewById(R.id.textViewComments);
        textViewTrainerName = (TextView) view.findViewById(R.id.textViewTrainerName);
        textViewCommentDate = (TextView) view.findViewById(R.id.textViewCommentDate);



        DatabaseManip.getData(AppAPI.TRAINERS, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trainerName = new Trainer(dataSnapshot).getName();

                trainerRate = new Trainer(dataSnapshot).getRate();
                dealing=(trainerRate.getDealing()).intValue();
                respectTime=(trainerRate.getRespectTime()).intValue();
                attendance=(trainerRate.getAttendance()).intValue();
                efficiency=(trainerRate.getLearningEfficiency()).intValue();
                comments = trainerRate.getOthers();
                ratingAvg=trainerRate.getRatingAverage();

                //Toast.makeText(getContext(), "dealing : "+dealing ,Toast.LENGTH_SHORT).show();

                textViewTrainerName.setText(trainerName);
                Toast.makeText(getContext(), "rating : "+ratingAvg , Toast.LENGTH_SHORT).show();
                ratingBarReviews.setRating(ratingAvg);
                textViewCommentDate.setText(trainerRate.getRatingDate());
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while (dealingStatus < dealing) {
                            dealingStatus += 1;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    progressBarDealing.setProgress(dealingStatus);
                                    textViewDealingPercentage.setText(dealingStatus + "%");

                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (respectTimeStatus < respectTime) {
                            respectTimeStatus += 1;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    progressBarRespectTime.setProgress(respectTimeStatus);
                                    textViewRespectTimePercentage.setText(respectTimeStatus + "%");

                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();




                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (attendanceStatus < attendance) {
                            attendanceStatus += 1;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    progressBarAttendance.setProgress(attendanceStatus);
                                    textViewAttendancePercentage.setText(attendanceStatus + "%");

                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();



                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (efficiencyStatus < efficiency) {
                            efficiencyStatus += 1;

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    progressBarEfficiency.setProgress(efficiencyStatus);
                                    textViewEfficiencyPercentage.setText(efficiencyStatus + "%");

                                }
                            });
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


                if(!TextUtils.isEmpty(comments))
                    textViewComments.setText(comments);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Trainer_ERROR", databaseError.getMessage());
            }
        });







        return view;
    }


}


