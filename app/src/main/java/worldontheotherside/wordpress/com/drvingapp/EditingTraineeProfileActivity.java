package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.FirebaseDatabaseHelperTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;
import worldontheotherside.wordpress.com.drvingapp.Classes.NewTrainee;

public class EditingTraineeProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditingTrainerProfileActivity";
    private MultiSpinnerSearch spinnerTrainingAreas;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private FirebaseUser user;
    private AppData appData;
    private MultiSpinnerSearch spinnerLanguages;
    private MultiSpinnerSearch spinnerVehicleType;
    private MultiSpinnerSearch spinnerContractType;
    private NewTrainee newTrainee;
    private String spokenLanguage;
    private String trainingAreas;
    private EditText editTextAge;
    private EditText editTextProfileName;
    private EditText editTextVehiclePlate;
    private EditText editTextPhone;
    private EditText editTextHourPrice;
    private EditText editTextContractPrice;
    private Button save_edit_button;
    private String previousName;
    private String previousPhone;
    private Long civilNo;
    private String gender;
    private String previousVehiclePlate;
    private Integer previousAge;
    private LinearLayout linearLayoutHourPrice;
    private LinearLayout linearLayoutContractPrice;
    public static TimePicker timePicker;
    public static String trainingTime ="";
    public static String startTime ="";
    public static String endTime ="";
    public static String formattedTime ="";
    public static Integer i =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_trainee_profile);


        newTrainee = new NewTrainee();

        spinnerTrainingAreas = (MultiSpinnerSearch) findViewById(R.id.spinnerTrainingRegion);
        spinnerLanguages = (MultiSpinnerSearch) findViewById(R.id.spinnerLanguage);
        spinnerVehicleType = (MultiSpinnerSearch) findViewById(R.id.spinnerVehicleType);
        spinnerContractType = (MultiSpinnerSearch) findViewById(R.id.spinnerContractType);
        editTextProfileName = (EditText) findViewById(R.id.editTextProfileName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextVehiclePlate = (EditText) findViewById(R.id.editTextVehiclePlate);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextHourPrice = (EditText) findViewById(R.id.editTextHourPrice);
        editTextContractPrice = (EditText) findViewById(R.id.editTextContractPrice);
        save_edit_button = (Button) findViewById(R.id.buttonSaveEdit);

        timePicker = (TimePicker) findViewById(R.id.timePicker);

        linearLayoutHourPrice = (LinearLayout) findViewById(R.id.linearLayoutHourPrice);
        linearLayoutContractPrice = (LinearLayout) findViewById(R.id.linearLayoutContractPrice);

        linearLayoutContractPrice.setVisibility(View.GONE);
        linearLayoutHourPrice.setVisibility(View.GONE);

        context = this;
        layoutManager = new LinearLayoutManager(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        appData = new AppData(this);

       /* DatabaseManip.getData(AppAPI.TRAINERS, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                previousName = new Trainer(dataSnapshot).getName();
                gender = new Trainer(dataSnapshot).getGender();
                previousAge = new Trainer(dataSnapshot).getAge();
                civilNo = new Trainer(dataSnapshot).getCivilId();
                previousPhone = new Trainer(dataSnapshot).getPhone();
                previousVehiclePlate = new Trainer(dataSnapshot).getCarNo();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Trainer_ERROR", databaseError.getMessage());
            }
        });
*/
        DatabaseManip.getData(AppAPI.AREAS, new ValueEventListener() {
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
                        newTrainee.setPlace(area);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AREAS_ERROR", databaseError.getMessage());
            }
        });

        DatabaseManip.getData(AppAPI.LANGUAGES, new ValueEventListener() {
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
                        newTrainee.setLanguage(lang);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("LANGUAGES_ERROR", databaseError.getMessage());
            }

        });






        save_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextProfileName.getText().toString();
                String age = editTextAge.getText().toString();
                String phone = editTextPhone.getText().toString();



             /* if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(vehiclePlate)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(hourPrice) || TextUtils.isEmpty(contractPrice)) {
                    AppKeys.displayMessageToast(EditingTrainerProfileActivity.this, "All fields must be filled");
                }*/
                //else {


                if (user == null) {
                    Intent firebaseUserIntent = new Intent(EditingTraineeProfileActivity.this, LoginActivity.class);
                    startActivity(firebaseUserIntent);
                    finish();
                } else {
                    String userId = user.getProviderId();
                    String id = user.getUid();
                    String profileEmail = user.getEmail();


                    if (TextUtils.isEmpty(name))
                        newTrainee.setUsername(previousName);
                    else
                        newTrainee.setUsername(name);

                    if (TextUtils.isEmpty(phone))
                        newTrainee.setPhone(previousPhone);
                    else
                        newTrainee.setPhone(phone);

                    if (TextUtils.isEmpty(age))
                        newTrainee.setAge(previousAge);
                    else
                        newTrainee.setAge(Integer.valueOf(age));



                    newTrainee.setEmail(profileEmail);
                    newTrainee.setCivilNo(civilNo);
                    newTrainee.setGender(gender);






                    FirebaseDatabaseHelperTrainee firebaseDatabaseHelperTrainee = new FirebaseDatabaseHelperTrainee();
                    firebaseDatabaseHelperTrainee.createUserInFirebaseDatabase(id, newTrainee);

                    Intent intent = new Intent(EditingTraineeProfileActivity.this, TrainerProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                //}
            }

        });
    }



}
