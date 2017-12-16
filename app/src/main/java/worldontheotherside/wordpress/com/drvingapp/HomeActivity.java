package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import worldontheotherside.wordpress.com.drvingapp.Adapters.MyInstructorsRecyclerAdapter;
import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contracts;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;

public class HomeActivity extends AppCompatActivity implements MyInstructorsRecyclerAdapter.OnItemClickListener {

    private Spinner spinnerTrainingAreas;
    private EditText editTextAgeFrom;
    private EditText editTextAgeTo;
    private RadioButton radioButtonFemale;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonHour;
    private RadioButton radioButtonPeriod;
    private Spinner spinnerLanguages;
    private Button buttonGetInstructors;
    private RecyclerView recyclerViewMyInstructors;

    private MyInstructorsRecyclerAdapter adapter;
    private MyInstructorsRecyclerAdapter.OnItemClickListener onItemClickListener;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private ArrayList<Contract> contractsList;
    private ArrayList<String> areasList;
    private ArrayList<String> languagesList;

    private FirebaseUser user;
    private AppData appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spinnerTrainingAreas = (Spinner) findViewById(R.id.spinnerTrainingAreas);
        editTextAgeFrom = (EditText) findViewById(R.id.editTextAgeFrom);
        editTextAgeTo = (EditText) findViewById(R.id.editTextAgeTo);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonHour = (RadioButton) findViewById(R.id.radioButtonHour);
        radioButtonPeriod = (RadioButton) findViewById(R.id.radioButtonPeriod);
        spinnerLanguages = (Spinner) findViewById(R.id.spinnerLanguages);
        buttonGetInstructors = (Button) findViewById(R.id.buttonGetInstructors);
        recyclerViewMyInstructors = (RecyclerView) findViewById(R.id.recyclerViewMyInstructors);

        context = this;
        onItemClickListener = this;

        layoutManager = new LinearLayoutManager(this);
        recyclerViewMyInstructors.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        appData = new AppData(this);

        Log.v("USERTYPE", appData.getUserType());

        areasList = getIntent().getStringArrayListExtra("Areas");
        languagesList = getIntent().getStringArrayListExtra("Languages");

        spinnerTrainingAreas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                areasList.toArray(new String[areasList.size()])));
        spinnerLanguages.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                languagesList.toArray(new String[languagesList.size()])));

/*        DatabaseManip.getData(AppAPI.AREAS, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> areasList = new Areas(dataSnapshot).getAreas();
                areasList.add(0, "Any area");
                spinnerTrainingAreas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        areasList.toArray(new String[areasList.size()])));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AREAS_ERROR", databaseError.getMessage());
            }
        });

        DatabaseManip.getData(AppAPI.LANGUAGES, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> languagesList = new Languages(dataSnapshot).getLanguages();
                languagesList.add(0, "Any language");
                spinnerLanguages.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        languagesList.toArray(new String[languagesList.size()])));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("LANGUAGES_ERROR", databaseError.getMessage());
            }
        });*/

        if((appData.getUserType().equals(AppKeys.PREV_TRAINEE)) || (appData.getUserType().equals(AppKeys.NEW_TRAINEE)))
        {
            DatabaseManip.findData(AppAPI.CONTRACT_BY_TRAINEE, "traineeId", Long.valueOf(user.getDisplayName()),
                    new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Contracts contracts = new Contracts(dataSnapshot);
                    contractsList = contracts.getContracts();

                    adapter = new MyInstructorsRecyclerAdapter(contractsList, appData.getUserType());
                    recyclerViewMyInstructors.setAdapter(adapter);
                    adapter.setOnItemClickListener(onItemClickListener);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.v("TRAINEE_FINDING_ERROR", databaseError.getMessage());
                }
            });
        }
        else if(appData.getUserType().equals(AppKeys.INSTRUCTOR))
        {
            DatabaseManip.findData(AppAPI.CONTRACT_BY_TRAINER, "trainerId", Long.valueOf(user.getDisplayName()),
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Contracts contracts = new Contracts(dataSnapshot);
                            contractsList = contracts.getContracts();

                            adapter = new MyInstructorsRecyclerAdapter(contractsList, appData.getUserType());
                            recyclerViewMyInstructors.setAdapter(adapter);
                            adapter.setOnItemClickListener(onItemClickListener);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.v("INSTRUCTOR_FIND_ERROR", databaseError.getMessage());
                        }
                    });
        }
    }

    @Override
    public void OnClick(View view, int position) {

    }

    public void getInstructorsAction(View view)
    {
        HashMap<String, String> hashMap = new HashMap<>();

        if(spinnerTrainingAreas.getSelectedItemPosition() != 0)
            hashMap.put("Area", spinnerTrainingAreas.getSelectedItem().toString());
        if(!editTextAgeFrom.getText().toString().isEmpty())
            hashMap.put("From", editTextAgeFrom.getText().toString());
        if(!editTextAgeTo.getText().toString().isEmpty())
            hashMap.put("To", editTextAgeTo.getText().toString());
        if(radioButtonFemale.isChecked())
            hashMap.put("Gender", "Female");
        else if(radioButtonMale.isChecked())
            hashMap.put("Gender", "Male");
        if(radioButtonHour.isChecked())
            hashMap.put("Contract", "byHour");
        else if(radioButtonPeriod.isChecked())
            hashMap.put("Contract", "byPeriod");
        if(spinnerLanguages.getSelectedItemPosition() != 0)
            hashMap.put("Language", spinnerLanguages.getSelectedItem().toString());

        Intent intent = new Intent(this, TrainersActivity.class);
        intent.putExtra("InstructorSearchParams", hashMap);
        startActivity(intent);
    }
}
