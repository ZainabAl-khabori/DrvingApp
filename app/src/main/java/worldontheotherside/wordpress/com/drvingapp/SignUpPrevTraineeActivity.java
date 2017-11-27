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
import com.google.firebase.database.FirebaseDatabase;

import worldontheotherside.wordpress.com.drvingapp.Classes.PreviousTrainee;

public class SignUpPrevTraineeActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextMobileNo;
    private RadioButton radioButtonFemale;
    private RadioButton radioButtonMale;
    private EditText editTextPassword;
    private EditText editTextCivilNo;
    private Button buttonLogin;
    private TextView textViewAlreadyHaveAccount;

    //User registration and authorization
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_prev_trainee);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioButtonMale = (RadioButton) findViewById(R.id.radioMale);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCivilNo = (EditText) findViewById(R.id.editTextCivilNo);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewAlreadyHaveAccount = (TextView) findViewById(R.id.textViewAlreadyHaveAccount);

        //Get Firebase authentication object
        auth = FirebaseAuth.getInstance();      //Apparently, this is a shared preference
    }

    public void signUpAction(View v)
    {
        //Create new user using auth with the email and password obtained from user
        auth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //Add a listener that activates on task completion
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) //If task (that is signing up) completed successfully
                        {
                            //User will be automatically logged in. Obtain current logged in user's info
                            //FirebaseUser is a class that contains default firebase user's info (unique name, email, password, photo)
                            FirebaseUser user = auth.getCurrentUser();

                            //Complete the rest of the user profile
                            DatabaseManip.updateUserProfile(user, editTextCivilNo.getText().toString(), null,
                                    null, null, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Do nothing
                                        }
                                    });

                            //Create new previous trainee using obtained info
                            PreviousTrainee previousTrainee = new PreviousTrainee();
                            previousTrainee.setUsername(editTextUsername.getText().toString());
                            previousTrainee.setEmail(editTextEmail.getText().toString());
                            previousTrainee.setPhone(editTextMobileNo.getText().toString());
                            if(radioButtonFemale.isChecked())
                                previousTrainee.setGender("Female");
                            else if(radioButtonMale.isChecked())
                                previousTrainee.setGender("Male");
                            previousTrainee.setPassword(editTextPassword.getText().toString());
                            previousTrainee.setCivilNo(Long.valueOf(editTextCivilNo.getText().toString()));

                            DatabaseManip.addData(AppAPI.FORMER_TRAINEES, previousTrainee, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    Toast.makeText(SignUpPrevTraineeActivity.this, "User added", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else //If task failed
                        {
                            Toast.makeText(SignUpPrevTraineeActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                            Log.v("SIGNUP FAILED", task.getException().getMessage());
                        }
                    }
                });
    }

    public void goLoginAction(View view)
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
