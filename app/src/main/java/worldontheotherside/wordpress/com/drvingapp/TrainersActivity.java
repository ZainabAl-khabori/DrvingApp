package worldontheotherside.wordpress.com.drvingapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import worldontheotherside.wordpress.com.drvingapp.Adapters.InstructorsRecyclerAdapter;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainers;

public class TrainersActivity extends AppCompatActivity implements InstructorsRecyclerAdapter.OnItemClickListener {

    private RecyclerView recyclerViewInstrctors;
    private InstructorsRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private InstructorsRecyclerAdapter.OnItemClickListener onItemClickListener;
    private Context context;
    private ArrayList<Trainer> trainersList;

    private SearchView searchViewInstructors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers);

        context = this;
        onItemClickListener = this;

        recyclerViewInstrctors = (RecyclerView) findViewById(R.id.recyclerViewInstructors);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewInstrctors.setLayoutManager(layoutManager);

        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("InstructorSearchParams");

        DatabaseManip.findData(AppAPI.TRAINERS, hashMap, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trainers trainers = new Trainers(dataSnapshot);
                trainersList = trainers.getTrainers();

                Log.v("TRAINERSLIST", String.valueOf(trainersList.size()));

                adapter = new InstructorsRecyclerAdapter(trainersList);
                recyclerViewInstrctors.setAdapter(adapter);
                adapter.setOnItemClickListener(onItemClickListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("TRAINERS_FIND_ERROR", databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.search, menu);

        searchViewInstructors = (SearchView) menu.findItem(R.id.searchViewInstructors).getActionView();

        searchViewInstructors.setActivated(true);
        searchViewInstructors.setQueryHint("Search by name");
        searchViewInstructors.onActionViewExpanded();
        searchViewInstructors.setIconified(true);
        searchViewInstructors.clearFocus();
        searchViewInstructors.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onClick(View view, int position) {

    }
}
