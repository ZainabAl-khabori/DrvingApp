package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import worldontheotherside.wordpress.com.drvingapp.Classes.FirebaseDatabaseHelper;
import worldontheotherside.wordpress.com.drvingapp.Classes.SimpleDividerItemDecoration;
import worldontheotherside.wordpress.com.drvingapp.R;

public class InfoTabFragment extends Fragment {
    private RecyclerView recyclerViewTrainerProfile;
    private LinearLayoutManager linearLayoutManager;

    private String id;
    private static final int REQUEST_READ_PERMISSION = 120;

    FirebaseAuth firebaseAuth;

    public InfoTabFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_tab_fragment, container, false);


        recyclerViewTrainerProfile = (RecyclerView)view.findViewById(R.id.recyclerViewTrainerProfile);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTrainerProfile.setLayoutManager(linearLayoutManager);
        recyclerViewTrainerProfile.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        /*firebaseAuth =((FirebaseApplication)getActivity().getApplication()).getFirebaseAuth();
        id = ((FirebaseApplication)getActivity().getApplication()).getFirebaseUserAuthenticateId();
*/
        firebaseAuth = FirebaseAuth.getInstance();
        id = getFirebaseUserAuthenticateId();


        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
        firebaseDatabaseHelper.isUserKeyExist(id, getActivity(), recyclerViewTrainerProfile);




        return view;
    }

    public String getFirebaseUserAuthenticateId() {
        String userId = null;
        if(firebaseAuth.getCurrentUser() != null){
            userId = firebaseAuth.getCurrentUser().getUid();
        }
        return userId;
    }
}
