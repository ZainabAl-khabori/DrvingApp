package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Adapters.MyInstructorsRecyclerAdapter;
import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contracts;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

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

    private String userType;
    private String trainingAreas;
    private static final String TAG = "HomeActivity";
    private Trainer trainer;
    private String spokenLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        trainer = new Trainer();
        if(StartActivity.startActivity != null)
            StartActivity.startActivity.finish();

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

        userType = appData.getUserType();
        if(getIntent().hasExtra("type")) {
            userType = getIntent().getStringExtra("type");
            appData.setUserType(userType);
        }

        Log.v("ZAINAB", "user type: "+userType);
        Log.v("USERTYPE", appData.getUserType());

        areasList = getIntent().getStringArrayListExtra("Areas");
        languagesList = getIntent().getStringArrayListExtra("Languages");

        Log.v("AREASLIST", getIntent().getStringArrayListExtra("Areas").toString());
        Log.v("LANGUAGESLIST", getIntent().getStringArrayListExtra("Languages").toString());

        spinnerTrainingAreas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                areasList.toArray(new String[areasList.size()])));
        spinnerLanguages.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                languagesList.toArray(new String[languagesList.size()])));

/*        DatabaseManip.getData(AppAPI.AREAS, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trainingAreas = "";
                ArrayList<String> areasList = new Areas(dataSnapshot).getAreas();
                final List<KeyPairBoolData> list0 = new ArrayList<>();
                for (int i = 0; i < areasList.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(areasList.get(i));
                    h.setSelected(false);
                    list0.add(h);
                }

                spinnerTrainingAreas.setItems(list0, -1, new SpinnerListener() {

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {

                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                trainingAreas += items.get(i).getName() + ", ";

                            }
                        }
                        String area = trainingAreas.substring(0, trainingAreas.length() - 2);
                        //Toast.makeText(context, lang, Toast.LENGTH_SHORT).show();
                        //trainer.setTrainingAreas(area);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AREAS_ERROR", databaseError.getMessage());
            }
        });*/


/*        DatabaseManip.getData(AppAPI.LANGUAGES, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spokenLanguage = "";
                List<String> languagesList = new Languages(dataSnapshot).getLanguages();
                final List<KeyPairBoolData> list1 = new ArrayList<>();
                for (int i = 0; i < languagesList.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(languagesList.get(i));
                    h.setSelected(false);
                    list1.add(h);
                }

                spinnerLanguages.setItems(list1, -1, new SpinnerListener() {

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {

                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                spokenLanguage += items.get(i).getName() + ", ";

                            }
                        }
                        String lang = spokenLanguage.substring(0, spokenLanguage.length() - 2);
                        //Toast.makeText(context, lang, Toast.LENGTH_SHORT).show();
                       // trainer.setSpokenLanguage(lang);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("LANGUAGES_ERROR", databaseError.getMessage());
            }

        });*/



        if((userType.equals(AppKeys.PREV_TRAINEE)) || (userType.equals(AppKeys.NEW_TRAINEE)))
        {
            DatabaseManip.findData(AppAPI.CONTRACT_BY_TRAINEE, "traineeId", Long.valueOf(user.getDisplayName()),
                    new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Contracts contracts = new Contracts(dataSnapshot);
                    contractsList = contracts.getContracts();

                    adapter = new MyInstructorsRecyclerAdapter(getSupportFragmentManager(), contractsList, appData.getUserType());
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

                            adapter = new MyInstructorsRecyclerAdapter(getSupportFragmentManager(), contractsList,
                                    appData.getUserType());
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

    @Override
    public void OnClick(View view, int position) {

    }
}
