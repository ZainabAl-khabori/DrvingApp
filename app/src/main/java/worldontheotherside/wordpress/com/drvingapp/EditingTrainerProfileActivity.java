package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Classes.Areas;
import worldontheotherside.wordpress.com.drvingapp.Classes.Languages;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

public class EditingTrainerProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditingTrainerProfileActivity";
    private Spinner spinnerTrainingAreas;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private FirebaseUser user;
    private AppData appData;
    private MultiSpinnerSearch spinnerLanguages;
    private Spinner spinnerVehicleType;
    private Spinner spinnerContractType;
    private Trainer trainer;
    private String spokenLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_trainer_profile);


        trainer = new Trainer();
        spinnerTrainingAreas = (Spinner) findViewById(R.id.spinnerTrainingRegion);
        spinnerLanguages = (MultiSpinnerSearch) findViewById(R.id.spinnerLanguage);
        spinnerVehicleType = (Spinner) findViewById(R.id.spinnerVehicleType);
        spinnerContractType = (Spinner) findViewById(R.id.spinnerContractType);


        context = this;
        //onItemClickListener = this;
        layoutManager = new LinearLayoutManager(this);
        //recyclerViewMyInstructors.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        appData = new AppData(this);

        DatabaseManip.getData(AppAPI.AREAS, new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> areasList = new Areas(dataSnapshot).getAreas();
                areasList.add(areasList.size(), "Select Training Region");

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item,areasList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View v = super.getView(position, convertView, parent);
                        if (position == getCount()) {
                            ((TextView)v.findViewById(android.R.id.text1)).setText("");
                            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                        }
                        return v;
                    }
                    @Override
                    public int getCount() {
                        return super.getCount()-1; // doesn't display last item. It is used as hint.
                    }
                };
                spinnerTrainingAreas.setAdapter(areasAdapter);
                spinnerTrainingAreas.setSelection(areasAdapter.getCount());
               /* spinnerTrainingAreas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        areasList.toArray(new String[areasList.size()])));*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("AREAS_ERROR", databaseError.getMessage());
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        final List<KeyPairBoolData> list0 = new ArrayList<>();
        KeyPairBoolData h = new KeyPairBoolData();
        h.setId(0);
        h.setName(list.get(0));
        h.setSelected(false);
        list0.add(h);



        spinnerLanguages.setItems(list0, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
*/




      DatabaseManip.getData(AppAPI.LANGUAGES, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spokenLanguage = "" ;
                List<String> languagesList = new Languages(dataSnapshot).getLanguages();
                final List<KeyPairBoolData> list0 = new ArrayList<>();
                for (int i = 0; i < languagesList.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(languagesList.get(i));
                    h.setSelected(false);
                    list0.add(h);
                }

                spinnerLanguages.setItems(list0, -1, new SpinnerListener() {

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {

                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                //Toast.makeText(context, items.get(i).getName(), Toast.LENGTH_SHORT).show();
                                spokenLanguage += items.get(i).getName()+", ";

                            }
                        }
                        String lang = spokenLanguage.substring(0,spokenLanguage.length()-2);
                        Toast.makeText(context, lang, Toast.LENGTH_SHORT).show();
                        trainer.setSpokenLanguage(lang);
                    }
                });


                //languagesList.add(languagesList.size(), "Select Language");
                /*ArrayAdapter<String> languagesAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item,languagesList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View v = super.getView(position, convertView, parent);
                        if (position == getCount()) {
                            ((TextView)v.findViewById(android.R.id.text1)).setText("");
                            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                        }
                        return v;
                    }
                    @Override
                    public int getCount() {
                        return super.getCount()-1; // doesn't display last item. It is used as hint.
                    }
                };
                spinnerLanguages.setAdapter(languagesAdapter);
                spinnerLanguages.setSelection(languagesAdapter.getCount());
                spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        hashMap.put("Language", spinnerLanguages.getSelectedItem().toString());
                        trainer.setLanguages(spinnerLanguages.getSelectedItem().toString());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });*/
                /*spinnerLanguages.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                        languagesList.toArray(new String[languagesList.size()])));*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("LANGUAGES_ERROR", databaseError.getMessage());
            }

        });






      /*  ArrayList<String> VehicleTypes = new ArrayList<>();
        VehicleTypes.add(0, "Vehicle Type");
        VehicleTypes.add(1, "Automatic");
        VehicleTypes.add(2, "Manual");

        spinnerVehicleType.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
                VehicleTypes.toArray(new String[VehicleTypes.size()])));*/


        ArrayAdapter<String> vehicleTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        vehicleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
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
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        contractTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        contractTypeAdapter.add("By hours");
        contractTypeAdapter.add("By duration");
        contractTypeAdapter.add("Select Contract Type");
        spinnerContractType.setAdapter(contractTypeAdapter);
        spinnerContractType.setSelection(contractTypeAdapter.getCount());
        spinnerContractType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                trainer.setContractType(spinnerContractType.getSelectedItem().toString());
                /*String itemValue= trainer.getContractType();
                Toast.makeText(context, itemValue, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

}
