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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import worldontheotherside.wordpress.com.drvingapp.Adapters.MyInstructorsRecyclerAdapter;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        disableNavigationViewScrollbars(navigationView);


       /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {


                } else if (id == R.id.nav_profile) {
                    Intent i = new Intent(HomeActivity.this, TrainerProfileActivity.class);
                    startActivity(i);
                    finish();

                } else if (id == R.id.nav_notifications) {

                } else if (id == R.id.nav_about) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });*/

              /*  if(StartActivity.startActivity != null)
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

        Log.v("ZAINAB", "user type: "+appData.getUserType());
        Log.v("USERTYPE", appData.getUserType());

        areasList = getIntent().getStringArrayListExtra("Areas");
        languagesList = getIntent().getStringArrayListExtra("Languages");

        Log.v("AREASLIST", getIntent().getStringArrayListExtra("Areas").toString());
        Log.v("LANGUAGESLIST", getIntent().getStringArrayListExtra("Languages").toString());

        spinnerTrainingAreas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                areasList.toArray(new String[areasList.size()])));
        spinnerLanguages.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                languagesList.toArray(new String[languagesList.size()])));

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
        }*/
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.profile, menu);
        inflater.inflate(R.menu.edit_profile, menu);
       // return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.nav_home) {


        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(HomeActivity.this, TrainerProfileActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_about) {

        }

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

}
