package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void goToSignUp(View view)
    {
        Intent intent;

        if(view.getId() == R.id.buttonNewTrainee)
            intent = new Intent(this, SignUpNewTraineeActivity.class);
        else if(view.getId() == R.id.buttonPreviousTrainee)
            intent = new Intent(this, SignUpPrevTraineeActivity.class);
        else
            intent = new Intent(this, SignUpTrainerActivity.class);

        intent.putExtra("type", getIntent().getStringExtra("type"));
        intent.putStringArrayListExtra("Areas", getIntent()
                .getStringArrayListExtra("Areas"));
        intent.putStringArrayListExtra("Languages", getIntent()
                .getStringArrayListExtra("Languages"));
        startActivity(intent);
    }
}
