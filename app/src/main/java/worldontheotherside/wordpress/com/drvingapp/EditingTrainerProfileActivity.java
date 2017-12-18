package worldontheotherside.wordpress.com.drvingapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.FirebaseDatabaseHelper;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

public class EditingTrainerProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditingTrainerProfileActivity";
    private MultiSpinnerSearch spinnerTrainingAreas;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private FirebaseUser user;
    private AppData appData;
    private MultiSpinnerSearch spinnerLanguages;
    private MultiSpinnerSearch spinnerVehicleType;
    private MultiSpinnerSearch spinnerContractType;
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
        setContentView(R.layout.activity_editing_trainer_profile);


        trainer = new Trainer();

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

        DatabaseManip.getData(AppAPI.TRAINERS, new ValueEventListener() {
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

        /*ArrayAdapter<String> vehicleTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

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
*/

        final List<String> vehicleTypeList = Arrays.asList(getResources().getStringArray(R.array.vehicleTypes_array));

        final List<KeyPairBoolData> vehicleTypeListListArray = new ArrayList<>();

        for (int i = 0; i < vehicleTypeList.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(vehicleTypeList.get(i));
            h.setSelected(false);
            vehicleTypeListListArray.add(h);
        }

        spinnerVehicleType.setItems(vehicleTypeListListArray, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        trainer.setVehicleType(items.get(i).getName());
                    }
                }
            }
        });

        final List<String> contractTypeList = Arrays.asList(getResources().getStringArray(R.array.contractTypes_array));

        final List<KeyPairBoolData> contractTypeListListArray = new ArrayList<>();

        for (int i = 0; i < contractTypeList.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(contractTypeList.get(i));
            h.setSelected(false);
            contractTypeListListArray.add(h);
        }

        spinnerContractType.setItems(contractTypeListListArray, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());


                        if (items.get(i).getId() == 1) {
                            linearLayoutHourPrice.setVisibility(View.VISIBLE);
                        }
                        if (items.get(i).getId() == 2) {
                            linearLayoutContractPrice.setVisibility(View.VISIBLE);
                        } else {
                            linearLayoutContractPrice.setVisibility(View.VISIBLE);
                            linearLayoutHourPrice.setVisibility(View.VISIBLE);
                        }


                        //Toast.makeText(context, "ContractType:"+spinnerContractType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        //trainer.setContractType(items.get(i).getName());
                        trainer.setContractType(spinnerContractType.getSelectedItem().toString());

                    } else {
                        if (items.get(i).getId() == 1)
                            linearLayoutHourPrice.setVisibility(View.GONE);
                        if (items.get(i).getId() == 2)
                            linearLayoutContractPrice.setVisibility(View.GONE);
                    }

                }
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
                        trainer.setName(previousName);
                    else
                        trainer.setName(name);

                    if (TextUtils.isEmpty(phone))
                        trainer.setPhone(previousPhone);
                    else
                        trainer.setPhone(phone);

                    if (TextUtils.isEmpty(age))
                        trainer.setAge(previousAge);
                    else
                        trainer.setAge(Integer.valueOf(age));

                    if (TextUtils.isEmpty(vehiclePlate))
                        trainer.setCarNo(previousVehiclePlate);
                    else
                        trainer.setCarNo(vehiclePlate);

                    trainer.setEmail(profileEmail);
                    trainer.setCivilId(civilNo);
                    trainer.setGender(gender);
                    trainer.setVehicleType(vehiclePlate);

                    trainer.setTrainingTime(trainingTime);

                    if (linearLayoutHourPrice.getVisibility() == View.VISIBLE) {
                        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putInt("hour_price", 1).apply();
                        if (TextUtils.isEmpty(hourPrice))
                            editTextHourPrice.setError("Cannot be empty");
                            //Toast.makeText(context, "Hour Price cannot be empty!", Toast.LENGTH_SHORT).show();
                        else
                            trainer.setHourPrice(hourPrice);
                    }

                    if (linearLayoutContractPrice.getVisibility() == View.VISIBLE) {
                        SharedPreferences sharedPreferences = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putInt("contract_price", 1).apply();
                        if (TextUtils.isEmpty(contractPrice))
                            Toast.makeText(context, "Contract Price cannot be empty!", Toast.LENGTH_SHORT).show();
                        else
                            trainer.setContractPrice(contractPrice);
                    }

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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        final Calendar calender = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = calender.get(Calendar.HOUR_OF_DAY);
            int minute = calender.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timePicker.setCurrentHour(hourOfDay);
            timePicker.setCurrentMinute(minute);

            String state = "";

            if(hourOfDay > 12) {
                hourOfDay -= 12;
                state = "PM";
            }
            else
                state="AM";

            String sMinute = "00";
            if(minute < 10){
                sMinute = "0"+minute;
            } else {
                sMinute = String.valueOf(minute);
            }

            formattedTime = String.valueOf(hourOfDay)+":"+String.valueOf(sMinute)+" "+state;
            if(i==0)
                startTime = formattedTime;
            if(i==1) {
                endTime = formattedTime;
                trainingTime = startTime +" - "+endTime;
            }

            i++;
            //Toast.makeText(getContext(), formattedTime, Toast.LENGTH_SHORT).show();

        }
    }

    public void setStartTime(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void setEndTime(View v) {
        DialogFragment Fragment = new TimePickerFragment();
        Fragment.show(getSupportFragmentManager(), "timePicker2");

    }


}
