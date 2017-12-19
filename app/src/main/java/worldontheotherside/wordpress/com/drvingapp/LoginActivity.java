package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Fragments.EmailLoginFragment;
import worldontheotherside.wordpress.com.drvingapp.Fragments.PhoneLoginFragment;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonVerify;
    private EmailLoginFragment emailLoginFragment;
    private PhoneLoginFragment phoneLoginFragment;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean verificationInProgress = false;
    private String verificationId;

    private FirebaseAuth auth;
    private AppData appData;

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonVerify = (Button) findViewById(R.id.buttonVerify);

        Log.v("TYPE", getIntent().getStringExtra("type"));

        emailLoginFragment = new EmailLoginFragment();
        phoneLoginFragment = new PhoneLoginFragment();

        auth = FirebaseAuth.getInstance();
        appData = new AppData(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(getIntent().getStringExtra("type").equals("email"))
            fragmentTransaction.replace(R.id.frameLayoutPlaceholder, emailLoginFragment);
        else
        {
            buttonLogin.setText("Send SMS");
            fragmentTransaction.replace(R.id.frameLayoutPlaceholder, phoneLoginFragment);

            callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    Log.v("VERIFICATION_COMPLETED", phoneAuthCredential.toString());

                    verificationInProgress = false;

                    signInwithMobileNumber(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.v("VERIFICATION_FAILED", e.getMessage());

                    verificationInProgress = false;
                    phoneLoginFragment.setErrorMessage(e);
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    Log.v("CODE_SENT", s);

                    verificationId = s;

                    phoneLoginFragment.setVerificationVisible();
                    buttonLogin.setVisibility(View.GONE);
                    buttonVerify.setVisibility(View.VISIBLE);
                }
            };
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getIntent().getStringExtra("type").equals("phone") && verificationInProgress)
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneLoginFragment.getPhoneNumber(),
                    90, TimeUnit.SECONDS, LoginActivity.this, callbacks);

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

    public void loginAction(View view)
    {
        if(getIntent().getStringExtra("type").equals("email"))
        {
            if(emailLoginFragment.getEmail().isEmpty() || emailLoginFragment.getPassword().isEmpty())
                emailLoginFragment.setErrorMessage("This field shouldn't be empty");
            else
            {
                appData.addInputEmail(emailLoginFragment.getEmail());
                auth.signInWithEmailAndPassword(emailLoginFragment.getEmail(), emailLoginFragment.getPassword())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putStringArrayListExtra("Areas", getIntent()
                                            .getStringArrayListExtra("Areas"));
                                    intent.putStringArrayListExtra("Languages", getIntent()
                                            .getStringArrayListExtra("Languages"));
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        else
        {
            if(phoneLoginFragment.getPhoneNumber().isEmpty())
                phoneLoginFragment.setErrorSimple("This field shouldn't be empty");
            else
            {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneLoginFragment.getPhoneNumber(),
                        90, TimeUnit.SECONDS, LoginActivity.this, callbacks);

                verificationInProgress = true;
            }
        }
    }

    public void verifyAction(View view)
    {
        if(phoneLoginFragment.getVerificationCode().isEmpty())
            phoneLoginFragment.setErrorSimple("This field shouldn't be empty");
        else
        {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, phoneLoginFragment.getVerificationCode());
            signInwithMobileNumber(credential);
        }
    }

    private void signInwithMobileNumber(final PhoneAuthCredential credential)
    {
        // check if mobile number exists in database
        DatabaseManip.findData(AppAPI.TRAINERS, "phone", phoneLoginFragment.getPhoneNumber(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trainer trainer = new Trainer(dataSnapshot);

                if(!dataSnapshot.hasChildren())
                {
                    Log.v("TRAINER_NULL", "trainer is null");

                    DatabaseManip.getData(AppAPI.TRAINEES, new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<DataSnapshot> snapshots = new ArrayList<>();
                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                                snapshots.add(snapshot);

                            ArrayList<String> numbers = new ArrayList<>();
                            final ArrayList<String> userTypes = new ArrayList<>();
                            for(int i = 0; i < snapshots.size(); i++)
                            {
                                for(DataSnapshot snapshot: snapshots.get(i).getChildren())
                                {
                                    if(!snapshot.hasChild("email"))
                                    {
                                        if(snapshot.hasChild("drivingLicense"))
                                            userTypes.add(AppKeys.NEW_TRAINEE);
                                        else
                                            userTypes.add(AppKeys.PREV_TRAINEE);
                                        numbers.add(snapshot.child("phone").getValue(String.class));
                                    }
                                }
                            }

                            if(numbers.contains(phoneLoginFragment.getPhoneNumber()))
                            {
                                appData.setUserType(userTypes.get(numbers.indexOf(phoneLoginFragment.getPhoneNumber())));
                                userType = userTypes.get(numbers.indexOf(phoneLoginFragment.getPhoneNumber()));
                                auth.signInWithCredential(credential)
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    intent.putExtra("type", userType);
                                                    intent.putStringArrayListExtra("Areas", getIntent()
                                                            .getStringArrayListExtra("Areas"));
                                                    intent.putStringArrayListExtra("Languages", getIntent()
                                                            .getStringArrayListExtra("Languages"));
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else
                                Toast.makeText(LoginActivity.this, "You didn't sign up with a phone number", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.v("PHONE_FINDING_ERROR", databaseError.getMessage());
                        }
                    });
                }
                else
                {
                    Log.v("TRAINER_NULL", String.valueOf(dataSnapshot.hasChildren()));
                    if(trainer.getEmail() == null)
                    {
                        appData.setUserType(AppKeys.INSTRUCTOR);
                        userType = AppKeys.INSTRUCTOR;
                        auth.signInWithCredential(credential)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("type", userType);
                                            intent.putStringArrayListExtra("Areas", getIntent()
                                                    .getStringArrayListExtra("Areas"));
                                            intent.putStringArrayListExtra("Languages", getIntent()
                                                    .getStringArrayListExtra("Languages"));
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                        Toast.makeText(LoginActivity.this, "You didn't sign up with a phone number", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("PHONE_FINDING_ERROR", databaseError.getMessage());
            }
        });
    }

    public void goSignUpAction(View v)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putStringArrayListExtra("Areas", getIntent()
                .getStringArrayListExtra("Areas"));
        intent.putStringArrayListExtra("Languages", getIntent()
                .getStringArrayListExtra("Languages"));
        startActivity(intent);
    }

    // TODO: progress bar while doing server work
}
