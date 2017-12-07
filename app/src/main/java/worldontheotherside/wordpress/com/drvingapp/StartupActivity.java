package worldontheotherside.wordpress.com.drvingapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import worldontheotherside.wordpress.com.drvingapp.Adapters.StartupSliderAdapter;
import worldontheotherside.wordpress.com.drvingapp.Classes.Images;

public class StartupActivity extends AppCompatActivity {

    private StartupSliderAdapter adapter;
    private LockableViewPager viewPagerImageSlider;
    private ArrayList<String> imagesList;
    private int currentImage;
    private int imagesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        viewPagerImageSlider = (LockableViewPager) findViewById(R.id.viewPagerImageSlider);
        currentImage = 0;
        imagesNo = 0;

/*        final ArrayList<String> urls = new ArrayList<>();
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fa-0005.jpg?alt=media&token=1211a1ec-b5c7-4f11-95b3-1e3a3af8f57c");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fbronze.png?alt=media&token=678e9ebe-64a8-48be-bb48-ccb41ebf8729");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2FButterfly-Line-Art-3-2400px.png?alt=media&token=355b268b-fc3c-4522-bfdd-eed816f4cd2d");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2FCartoon_Tree_PNG_Clipart-2416.png?alt=media&token=d556c5f0-be82-44f9-b1f2-24a18115c4e2");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2FGlobal-Business-Coalition-Digital.jpg?alt=media&token=8ee614c8-3e5a-42b8-b3e1-c865ad8ac410");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fgold.png?alt=media&token=8472ba08-f9a5-4f54-a4c1-a51068e02002");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fplay.png?alt=media&token=8f1188a1-cf32-4c6b-bbb8-7469f3c871b8");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fsilver.png?alt=media&token=0977cb61-47e3-47bc-b1bc-7f65da2677cf");
        urls.add("https://firebasestorage.googleapis.com/v0/b/drivingapp-5d80c.appspot.com/o/startup_images%2Fstop.png?alt=media&token=c5646a05-b0fd-4e30-9b5e-288a32cd28a9");

        for(int i=0; i<urls.size(); i++)
        {
            final int n = i;
            DatabaseManip.addData(AppAPI.STARTUP_IMAGES, urls.get(i), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Log.v("URLS", urls.get(n));
                }
            });
        }*/

        DatabaseManip.getData(AppAPI.STARTUP_IMAGES, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Images images = new Images(dataSnapshot);
                imagesList = images.getImages();

                adapter = new StartupSliderAdapter(StartupActivity.this, imagesList);
                viewPagerImageSlider.setAdapter(adapter);
                viewPagerImageSlider.setSwipeable(false);

                imagesNo = imagesList.size();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("IMAGES_GETTING_ERROR", databaseError.getMessage());
            }
        });

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentImage == imagesNo)
                    currentImage = 0;

                viewPagerImageSlider.setCurrentItem(currentImage++, false);
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000);
    }
}
