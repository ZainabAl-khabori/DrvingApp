package worldontheotherside.wordpress.com.drvingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        enableFullScreen(true);

    }


    //hide the navigation and enable full-screen
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


}
