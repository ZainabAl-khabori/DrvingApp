package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import worldontheotherside.wordpress.com.drvingapp.Classes.NewTrainee;

public class SignUpNewTraineeActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextMobileNo;
    private RadioButton radioFemale;
    private RadioButton radioMale;
    private EditText editTextPassword;
    private EditText editTextCivilNo;
    private EditText editTextDrivingLicense;
    private Button buttonLogin;
    private TextView textViewAlreadyHaveAccount;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_new_trainee);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCivilNo = (EditText) findViewById(R.id.editTextCivilNo);
        editTextDrivingLicense = (EditText) findViewById(R.id.editTextDrivingLicense);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewAlreadyHaveAccount = (TextView) findViewById(R.id.textViewAlreadyHaveAccount);

        auth = FirebaseAuth.getInstance();
    }

    public void signUpAction(View view)
    {
        if(editTextUsername.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() ||
                editTextAge.getText().toString().isEmpty() || editTextMobileNo.getText().toString().isEmpty() ||
                editTextPassword.getText().toString().isEmpty() || editTextCivilNo.getText().toString().isEmpty() ||
                editTextDrivingLicense.getText().toString().isEmpty() || ((!radioMale.isChecked()) && (!radioFemale.isChecked())))
        {
            Toast.makeText(this, "Please fill all of the fields first", Toast.LENGTH_LONG).show();
        }
        else
        {
            auth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = auth.getCurrentUser();

                                DatabaseManip.updateUserProfile(user, editTextCivilNo.getText().toString(), null,
                                        null, null, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //Do nothing
                                            }
                                        });

                                NewTrainee newTrainee = new NewTrainee();
                                newTrainee.setUsername(editTextUsername.getText().toString());
                                newTrainee.setEmail(editTextEmail.getText().toString());
                                newTrainee.setPhone(editTextMobileNo.getText().toString());
                                if(radioFemale.isChecked())
                                    newTrainee.setGender("Female");
                                else if(radioMale.isChecked())
                                    newTrainee.setGender("Male");
                                newTrainee.setPassword(editTextPassword.getText().toString());
                                newTrainee.setCivilNo(Long.valueOf(editTextCivilNo.getText().toString()));
                                newTrainee.setDrivingLicense(Long.valueOf(editTextDrivingLicense.getText().toString()));

                                DatabaseManip.addData(AppAPI.FORMER_TRAINEES, newTrainee, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(SignUpNewTraineeActivity.this, "New trainee added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(SignUpNewTraineeActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                                Log.v("SIGNUP FAILED", task.getException().getMessage());
                            }
                        }
                    });
        }
    }

    public void goLoginAction(View view)
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
