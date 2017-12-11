package worldontheotherside.wordpress.com.drvingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import worldontheotherside.wordpress.com.drvingapp.Classes.PreviousTrainee;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextViewEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewCreateAccountSignup;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autoCompleteTextViewEmail = (AutoCompleteTextView) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewCreateAccountSignup = (TextView) findViewById(R.id.textViewCreateAccountSignup);

        auth = FirebaseAuth.getInstance();
    }

    public void loginAction(View view)
    {
        auth.signInWithEmailAndPassword(autoCompleteTextViewEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            final FirebaseUser user = auth.getCurrentUser();

                            // I fixed getData method that includes a search parameter to become findData, included a search field as well
                            DatabaseManip.findData(AppAPI.FORMER_TRAINEE_BY_ID, "civilNo", Long.valueOf(user.getDisplayName()), new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    PreviousTrainee previousTrainee = new PreviousTrainee(dataSnapshot);
                                    Toast.makeText(LoginActivity.this, "Name: "+previousTrainee.getUsername(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, TrainerProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(LoginActivity.this, "Finding user failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            /*DatabaseManip.findData(AppAPI.TRAINERS, "trainees", Long.valueOf(user.getDisplayName()), new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Trainer trainer = new Trainer(dataSnapshot);
                                    Toast.makeText(LoginActivity.this, "Name: "+trainer.getName(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, TrainerProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(LoginActivity.this, "Finding user failed", Toast.LENGTH_SHORT).show();
                                }
                            });*/
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
