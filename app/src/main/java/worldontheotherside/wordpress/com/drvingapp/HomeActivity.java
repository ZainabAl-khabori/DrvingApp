package worldontheotherside.wordpress.com.drvingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Contract contract = new Contract();

        contract.setEnd("12/1/2017");
        contract.setStart("1/7/2016");
        contract.setPrice(6.000);
        contract.setTraineeId(1234);
        contract.setTrainerId(5678);
        contract.setType("by hour");

        DatabaseManip.addData(AppAPI.CONTRACTS, contract, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //
            }
        });
    }
}
