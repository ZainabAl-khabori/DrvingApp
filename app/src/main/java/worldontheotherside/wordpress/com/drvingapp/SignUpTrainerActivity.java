package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

import worldontheotherside.wordpress.com.drvingapp.Classes.NewTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Fragments.VerifyCodeDialogFragment;

public class SignUpTrainerActivity extends AppCompatActivity implements VerifyCodeDialogFragment.VerifyButtonClickedListener {

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

    private VerifyCodeDialogFragment dialogFragment;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean verificationInProgress = false;
    private String verificationId;

    private FirebaseAuth auth;
    private AppData appData;

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
        appData = new AppData(this);

        if(getIntent().getStringExtra("type").equals("phone"))
        {
            editTextEmail.setVisibility(View.GONE);
            editTextPassword.setVisibility(View.GONE);

            callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    Log.v("VERIFICATION_COMPLETED", phoneAuthCredential.toString());

                    verificationInProgress = false;

                    signUpWithMobileNumber(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.v("VERIFICATION_FAILED", e.getMessage());

                    verificationInProgress = false;

                    if(e instanceof FirebaseAuthInvalidCredentialsException)
                        editTextMobileNo.setError("Invalid mobile number");
                    else if(e instanceof FirebaseTooManyRequestsException)
                        Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    Log.v("CODE_SENT", s);

                    verificationId = s;

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    dialogFragment = VerifyCodeDialogFragment
                            .newVerifyCodeDialogFragment("Enter verification code:");
                    dialogFragment.show(fragmentManager, "Verify code");
                }
            };
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getIntent().getStringExtra("type").equals("phone") && verificationInProgress)
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(editTextMobileNo.getText().toString(),
                    90, TimeUnit.SECONDS, SignUpTrainerActivity.this, callbacks);

            verificationInProgress = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(getIntent().getStringExtra("type").equals("phone"))
            outState.putBoolean("verification_in_progress", verificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(getIntent().getStringExtra("type").equals("phone"))
            verificationInProgress = savedInstanceState.getBoolean("verification_in_progress");
    }

    public void signUpWithMobileNumber(PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(SignUpTrainerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            appData.setUserType(AppKeys.NEW_TRAINEE);

                            FirebaseUser user = auth.getCurrentUser();

                            DatabaseManip.updateUserProfile(user, editTextCivilNo.getText().toString(), null,
                                    null, null, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Do nothing
                                        }
                                    });

                            Trainer trainer = new Trainer();
                            trainer.setName(editTextUsername.getText().toString());
                            trainer.setEmail(editTextEmail.getText().toString());
                            trainer.setPhone(editTextMobileNo.getText().toString());
                            if(radioButtonFemale.isChecked())
                                trainer.setGender("Female");
                            else if(radioButtonMale.isChecked())
                                trainer.setGender("Male");
                            trainer.setPassword(editTextPassword.getText().toString());
                            trainer.setCivilNo(Long.valueOf(editTextCivilNo.getText().toString()));
                            trainer.setCarNo(editTextCarNo.getText().toString());

                            appData.setUserType(AppKeys.INSTRUCTOR);

                            DatabaseManip.updateData(AppAPI.USERTYPES, user.getDisplayName(), AppKeys.NEW_TRAINEE,
                                    new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            //
                                        }
                                    });

                            DatabaseManip.addData(AppAPI.CURRENT_TRAINEES, trainer, new DatabaseReference
                                    .CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    Toast.makeText(SignUpTrainerActivity.this, "New trainee added", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });

                            Intent intent = new Intent(SignUpTrainerActivity.this, HomeActivity.class);
                            intent.putStringArrayListExtra("Areas", getIntent()
                                    .getStringArrayListExtra("Areas"));
                            intent.putStringArrayListExtra("Languages", getIntent()
                                    .getStringArrayListExtra("Languages"));
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUpTrainerActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signUpAction(View view)
    {
        if(getIntent().getStringExtra("type").equals("email"))
        {
            if(editTextUsername.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() ||
                    editTextAge.getText().toString().isEmpty() || editTextMobileNo.getText().toString().isEmpty() ||
                    editTextPassword.getText().toString().isEmpty() || editTextCivilNo.getText().toString().isEmpty() ||
                    editTextCarNo.getText().toString().isEmpty() ||
                    ((!radioButtonMale.isChecked()) && (!radioButtonFemale.isChecked())))
            {
                Toast.makeText(this, "Please fill all of the fields first", Toast.LENGTH_LONG).show();
            }
            else
            {
                if(editTextPassword.getText().length() > 5)
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

                                        Trainer trainer = new Trainer();
                                        trainer.setName(editTextUsername.getText().toString());
                                        trainer.setEmail(editTextEmail.getText().toString());
                                        trainer.setPhone(editTextMobileNo.getText().toString());
                                        if(radioButtonFemale.isChecked())
                                            trainer.setGender("Female");
                                        else if(radioButtonMale.isChecked())
                                            trainer.setGender("Male");
                                        trainer.setPassword(editTextPassword.getText().toString());
                                        trainer.setCivilNo(Long.valueOf(editTextCivilNo.getText().toString()));
                                        trainer.setCarNo(editTextCarNo.getText().toString());

                                        appData.setUserType(AppKeys.INSTRUCTOR);

                                        DatabaseManip.updateData(AppAPI.USERTYPES, user.getDisplayName(), AppKeys.INSTRUCTOR,
                                                new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                        //
                                                    }
                                                });

                                        DatabaseManip.addData(AppAPI.TRAINERS, trainer, new DatabaseReference
                                                .CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                Toast.makeText(SignUpTrainerActivity.this, "Trainer added", Toast.LENGTH_SHORT)
                                                        .show();

                                                Intent intent = new Intent(SignUpTrainerActivity.this, HomeActivity.class);
                                                intent.putStringArrayListExtra("Areas", getIntent()
                                                        .getStringArrayListExtra("Areas"));
                                                intent.putStringArrayListExtra("Languages", getIntent()
                                                        .getStringArrayListExtra("Languages"));
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUpTrainerActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                                        Log.v("SIGNUP FAILED", task.getException().getMessage());
                                    }
                                }
                            });
                }
                else
                {
                    editTextPassword.setError("Password should be longer than 5 characters");
                }
            }
        }
        else
        {
            if(editTextUsername.getText().toString().isEmpty() || editTextAge.getText().toString().isEmpty() ||
                    editTextMobileNo.getText().toString().isEmpty() || editTextCivilNo.getText().toString().isEmpty() ||
                    editTextCarNo.getText().toString().isEmpty() ||
                    ((!radioButtonMale.isChecked()) && (!radioButtonFemale.isChecked())))
            {
                Toast.makeText(this, "Please fill all of the fields first", Toast.LENGTH_LONG).show();
            }
            else
            {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(editTextMobileNo.getText().toString(),
                        90, TimeUnit.SECONDS, SignUpTrainerActivity.this, callbacks);

                verificationInProgress = true;
            }
        }
    }

    public void goLoginAction(View view)
    {
        Intent i = new Intent(this, LoginActivity.class);
        i.putStringArrayListExtra("Areas", getIntent()
                .getStringArrayListExtra("Areas"));
        i.putStringArrayListExtra("Languages", getIntent()
                .getStringArrayListExtra("Languages"));
        i.putExtra("type", getIntent().getStringExtra("type"));
        startActivity(i);
    }

    @Override
    public void onVerifyClick(DialogFragment dialog) {
        if(dialogFragment.getVerificationCode().isEmpty())
            dialogFragment.setErrorSimple("This field shouldn't be empty");
        else
        {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, dialogFragment.getVerificationCode());
            signUpWithMobileNumber(credential);
            dialogFragment.dismiss();
        }
    }
}
