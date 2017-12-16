package worldontheotherside.wordpress.com.drvingapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import worldontheotherside.wordpress.com.drvingapp.Classes.Images;
import worldontheotherside.wordpress.com.drvingapp.Fragments.OptionsDialogFragment;

public class StartActivity extends AppCompatActivity {

    private ImageView imageViewBackground;
    private Button buttonLogin;
    private TextView textViewSignup;
    private ArrayList<String> imagesList;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        imageViewBackground = (ImageView) findViewById(R.id.imageViewBackground);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        imagesList = getIntent().getStringArrayListExtra("Images");

        final int height = getResources().getDisplayMetrics().heightPixels;
        final int width = getResources().getDisplayMetrics().widthPixels;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(i == (imagesList.size()))
                    i = 0;

                Picasso.with(StartActivity.this).load(imagesList.get(i++))
                        .transform(new Scale(height)).into(imageViewBackground);
                imageViewBackground.scrollTo(-width, 0);

                ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(imageViewBackground, View.ALPHA, 0, 1, 1, 1, 0);
                ObjectAnimator xAnimator = ObjectAnimator.ofInt(imageViewBackground, "scrollX", -width, width);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(fadeAnimator, xAnimator);
                set.setDuration(10000);
                set.start();
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 10000, 10000);
    }

    private class Scale implements Transformation
    {
        private int size;

        public Scale(int size) { this.size = size; }

        @Override
        public Bitmap transform(Bitmap source) {
            float scale = (float) size / source.getHeight();
            int newSize = Math.round(source.getWidth() * scale);

            Log.v("NEWSIZE", String.valueOf(newSize));

            Bitmap result = Bitmap.createScaledBitmap(source, newSize, size, true);

            if(result != source)
                source.recycle();

            return result;
        }

        @Override
        public String key() { return "matchViewHeight("+size+")"; }
    }

    public void goToAction(View view)
    {
        OptionsDialogFragment dialogFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(view.getId() == R.id.buttonLogin)
            dialogFragment = OptionsDialogFragment.newOptionsDialog("login");
        else
            dialogFragment = OptionsDialogFragment.newOptionsDialog("signup");

        dialogFragment.show(fragmentManager, "Options");
    }
}
