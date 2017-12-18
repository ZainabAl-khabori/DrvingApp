package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

public class SignUpTrainerActivity extends AppCompatActivity {

    private Button buttonSignup;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextMobileNo;
    private EditText editTextPassword;
    private EditText editTextCarNo;
    private EditText editTextCivilNo;
    private TextView textViewAlreadyHaveAccount;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private RadioButton radioButtonAutomatic;
    private RadioButton radioButtonManual;

    // TODO: change car type icons to ones suitable
    // TODO: remove car type from layout

    private FirebaseAuth auth;

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
        editTextCivilNo = (EditText) findViewById(R.id.editTextCivilNo);
        textViewAlreadyHaveAccount = (TextView) findViewById(R.id.textViewAlreadyHaveAccount);
        radioButtonMale = (RadioButton) findViewById(R.id.radioMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioFemale);

        auth = FirebaseAuth.getInstance();
    }

    public void signUpAction(View view)
    {
        if(editTextUsername.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() ||
                editTextAge.getText().toString().isEmpty() || editTextMobileNo.getText().toString().isEmpty() ||
                editTextPassword.getText().toString().isEmpty() || editTextCivilNo.getText().toString().isEmpty() ||
                editTextCarNo.getText().toString().isEmpty() || ((!radioButtonMale.isChecked()) && (!radioButtonFemale.isChecked())))
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


                                DatabaseManip.updateUserProfile(user, editTextCivilNo.getText().toString(), null, null, null,
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // Do nothing
                                            }
                                        });

                                Trainer trainer = new Trainer();
                                trainer.setName(editTextUsername.getText().toString());
                                trainer.setCivilId(Long.valueOf(editTextCivilNo.getText().toString()));
                                trainer.setEmail(editTextEmail.getText().toString());
                                trainer.setAge(Integer.valueOf(editTextAge.getText().toString()));
                                trainer.setPhone(editTextMobileNo.getText().toString());
                                if(radioButtonFemale.isChecked())
                                    trainer.setGender("Female");
                                else if(radioButtonMale.isChecked())
                                    trainer.setGender("Male");
                                trainer.setPassword(editTextPassword.getText().toString());
                                trainer.setCarNo(editTextCarNo.getText().toString());

                                DatabaseManip.addData(AppAPI.TRAINERS, trainer, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Toast.makeText(SignUpTrainerActivity.this, "Trainer added", Toast.LENGTH_SHORT).show();

                                    }
                                });


                                /*Intent i = new Intent(SignUpTrainerActivity.this, TrainerProfileActivity.class);
                                startActivity(i);
                                finish();*/
                            }
                            else
                            {
                                Toast.makeText(SignUpTrainerActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
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
