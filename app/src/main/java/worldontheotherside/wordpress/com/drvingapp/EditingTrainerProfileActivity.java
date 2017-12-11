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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
import worldontheotherside.wordpress.com.drvingapp.Classes.FirebaseDatabaseHelper;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;
import worldontheotherside.wordpress.com.drvingapp.Classes.PreviousTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

public class EditingTrainerProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditingTrainerProfileActivity";
    private MultiSpinnerSearch spinnerTrainingAreas;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private FirebaseUser user;
    private AppData appData;
    private MultiSpinnerSearch spinnerLanguages;
    private Spinner spinnerVehicleType;
    private Spinner spinnerContractType;
    private Trainer trainer;
    private String spokenLanguage;
    private String trainingAreas;
    private EditText editTextAge;
    private EditText editTextProfileName;
    private EditText editTextVehiclePlate;
    private EditText editTextPhone;
    private EditText editTextHourPrice;
    private EditText editTextContractPrice;
    private Button save_edit_button;
    private String nameFromDataB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_trainer_profile);


        trainer = new Trainer();

        spinnerTrainingAreas = (MultiSpinnerSearch) findViewById(R.id.spinnerTrainingRegion);
        spinnerLanguages = (MultiSpinnerSearch) findViewById(R.id.spinnerLanguage);
        spinnerVehicleType = (Spinner) findViewById(R.id.spinnerVehicleType);
        spinnerContractType = (Spinner) findViewById(R.id.spinnerContractType);
        editTextProfileName = (EditText) findViewById(R.id.editTextProfileName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextVehiclePlate = (EditText) findViewById(R.id.editTextVehiclePlate);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextHourPrice = (EditText) findViewById(R.id.editTextHourPrice);
        editTextContractPrice = (EditText) findViewById(R.id.editTextContractPrice);
        save_edit_button = (Button) findViewById(R.id.buttonSaveEdit);


        context = this;
        //onItemClickListener = this;
        layoutManager = new LinearLayoutManager(this);
        //recyclerViewMyInstructors.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        appData = new AppData(this);


        DatabaseManip.findData(AppAPI.FORMER_TRAINEE_BY_ID, "civilNo", Long.valueOf(user.getDisplayName()), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PreviousTrainee previousTrainee = new PreviousTrainee(dataSnapshot);
                nameFromDataB = previousTrainee.getUsername();
                //Toast.makeText(EditingTrainerProfileActivity.this, previousTrainee.getUsername(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                        trainer.setTrainingAreas(area);
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
                        trainer.setSpokenLanguage(lang);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("LANGUAGES_ERROR", databaseError.getMessage());
            }

        });

        ArrayAdapter<String> vehicleTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        vehicleTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        vehicleTypeAdapter.add("Automatic");
        vehicleTypeAdapter.add("Manual");
        vehicleTypeAdapter.add("Select Vehicle Type");
        spinnerVehicleType.setAdapter(vehicleTypeAdapter);
        spinnerVehicleType.setSelection(vehicleTypeAdapter.getCount());
        spinnerVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                trainer.setVehicleType(spinnerVehicleType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        ArrayAdapter<String> contractTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        contractTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        contractTypeAdapter.add("By hours");
        contractTypeAdapter.add("By duration");
        contractTypeAdapter.add("Select Contract Type");
        spinnerContractType.setAdapter(contractTypeAdapter);
        spinnerContractType.setSelection(contractTypeAdapter.getCount());
        spinnerContractType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                trainer.setContractType(spinnerContractType.getSelectedItem().toString());
                //String itemValue= trainer.getContractType();
                //Toast.makeText(context, itemValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        save_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextProfileName.getText().toString();
                String age = editTextAge.getText().toString();
                String vehiclePlate = editTextVehiclePlate.getText().toString();
                String phone = editTextPhone.getText().toString();
                String hourPrice = editTextHourPrice.getText().toString();
                String contractPrice = editTextContractPrice.getText().toString();


             /* if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(vehiclePlate)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(hourPrice) || TextUtils.isEmpty(contractPrice)) {
                    AppKeys.displayMessageToast(EditingTrainerProfileActivity.this, "All fields must be filled");
                }*/
                //else {


                  if (user == null) {
                      Intent firebaseUserIntent = new Intent(EditingTrainerProfileActivity.this, LoginActivity.class);
                      startActivity(firebaseUserIntent);
                      finish();
                  } else {
                      String userId = user.getProviderId();
                      String id = user.getUid();
                      String profileEmail = user.getEmail();


                      if (TextUtils.isEmpty(name))
                          trainer.setName(nameFromDataB);
                      else
                          trainer.setName(name);

                      trainer.setEmail(profileEmail);
                      trainer.setAge(Integer.valueOf(age));
                      trainer.setVehicleType(vehiclePlate);
                      trainer.setPhone(phone);
                      trainer.setHourPrice(hourPrice);
                      trainer.setContractPrice(contractPrice);
                      FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                      firebaseDatabaseHelper.createUserInFirebaseDatabase(id, trainer);

                    Intent intent = new Intent(EditingTrainerProfileActivity.this, TrainerProfileActivity.class);
                    startActivity(intent);
                    finish();
                  }
              //}
            }

        });
    }
}
