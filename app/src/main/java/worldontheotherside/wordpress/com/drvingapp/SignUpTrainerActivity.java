package worldontheotherside.wordpress.com.drvingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SignUpTrainerActivity extends AppCompatActivity {

    private Button buttonSignup;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextMobileNo;
    private EditText editTextPassword;
    private EditText editTextCarNo;
    private TextView textViewAlreadyHaveAccount;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_trainer);

        buttonSignup = (Button) findViewById(R.id.buttonLogin);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCarNo = (EditText) findViewById(R.id.editTextCarNo);
    }
}
