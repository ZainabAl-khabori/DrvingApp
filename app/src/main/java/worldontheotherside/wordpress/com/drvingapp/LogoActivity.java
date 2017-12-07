package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;

public class LogoActivity extends AppCompatActivity {

    private FirebaseUser user;
    private ArrayList<String> areasList;
    private ArrayList<String> languagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        enableFullScreen(true);

        user = FirebaseAuth.getInstance().getCurrentUser();

        // execute processes while displaying logo
        new Loading().execute();
    }

    //hide action bar and display full screen
    protected void enableFullScreen(boolean enabled) {
        int newVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        if(enabled) {
            newVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
        }

        getDecorView().setSystemUiVisibility(newVisibility);
    }

    //set the new visibility on the decor view
    private View getDecorView() {
        return getWindow().getDecorView();
    }

    // AsyncTask to display splash screen while app is loading
    private class Loading extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(user != null)
            {
                DatabaseManip.getData(AppAPI.AREAS, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        areasList = new Areas(dataSnapshot).getAreas();
                        areasList.add(0, "Any area");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v("AREAS_ERROR", databaseError.getMessage());
                    }
                });

                DatabaseManip.getData(AppAPI.LANGUAGES, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        languagesList = new Languages(dataSnapshot).getLanguages();
                        languagesList.add(0, "Any language");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v("LANGUAGES_ERROR", databaseError.getMessage());
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final Intent intent;

            if(user != null)
            {
                intent = new Intent(LogoActivity.this, HomeActivity.class);
                intent.putStringArrayListExtra("Areas", areasList);
                intent.putStringArrayListExtra("Languages", languagesList);
            }
            else
                intent = new Intent(LogoActivity.this, StartupActivity.class);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}
