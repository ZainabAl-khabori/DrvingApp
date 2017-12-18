package worldontheotherside.wordpress.com.drvingapp;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Classes.TrainerRate;
import worldontheotherside.wordpress.com.drvingapp.Fragments.InfoTabFragment;
import worldontheotherside.wordpress.com.drvingapp.Fragments.ReviewsTabFragment;


public class TrainerViewActivity extends AppCompatActivity {

    //private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textViewTrainerName;
    private  String trainerName;
    private TrainerRate trainerRate;
    private Float ratingAvg;
    private String comments;
    private RatingBar ratingBarTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view);


      /*  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerTrainer);
        textViewTrainerName = (TextView) findViewById(R.id.textViewTrainerName);
        ratingBarTrainer = (RatingBar) findViewById(R.id.ratingBarTrainer);



        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();

        DatabaseManip.getData(AppAPI.TRAINERS, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trainerName = new Trainer(dataSnapshot).getName();
                textViewTrainerName.setText(trainerName);
                trainerRate = new Trainer(dataSnapshot).getRate();
                comments = trainerRate.getOthers();
                ratingAvg=trainerRate.getRatingAverage();

                ratingBarTrainer.setRating(ratingAvg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Trainer_ERROR", databaseError.getMessage());
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InfoTabFragment(), "Info");
        adapter.addFragment(new ReviewsTabFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }

    /*private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
*/
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}