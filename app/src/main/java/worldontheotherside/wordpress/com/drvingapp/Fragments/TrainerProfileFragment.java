package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import worldontheotherside.wordpress.com.drvingapp.AppKeys;
import worldontheotherside.wordpress.com.drvingapp.Classes.FirebaseDatabaseHelper;
import worldontheotherside.wordpress.com.drvingapp.Classes.SimpleDividerItemDecoration;
import worldontheotherside.wordpress.com.drvingapp.EditingTrainerProfileActivity;
import worldontheotherside.wordpress.com.drvingapp.R;

public class TrainerProfileFragment extends Fragment {

    private static final String TAG = TrainerProfileFragment.class.getSimpleName();
    private ImageView profile_image;
    private TextView profile_name;
    private RecyclerView recyclerViewTrainerProfile;
    private LinearLayoutManager linearLayoutManager;

    private String id;
    private static final int REQUEST_READ_PERMISSION = 120;

    FirebaseAuth firebaseAuth;

    public TrainerProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainer_profile_fragment, container, false);
        getActivity().setTitle("My Profile");

        profile_name = (TextView)view.findViewById(R.id.profile_name);
        profile_name.setVisibility(View.GONE);

        profile_image = (ImageView)view.findViewById(R.id.circleImageViewProfile);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, AppKeys.SELECT_PICTURE);
            }
        });

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit_profile){
            Intent editProfileIntent = new Intent(getActivity(), EditingTrainerProfileActivity.class);
            getActivity().startActivity(editProfileIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("user id has entered onActivityResult ");
      /*  if (requestCode == AppKeys.SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            String imagePath = getPath(selectedImageUri);
            FirebaseStorageHelper storageHelper = new FirebaseStorageHelper(getActivity());

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
                return;
            }
            storageHelper.saveProfileImageToCloud(id, selectedImageUri, profile_image);
        }*/
    }
    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;

    }

    public String getFirebaseUserAuthenticateId() {
        String userId = null;
        if(firebaseAuth.getCurrentUser() != null){
            userId = firebaseAuth.getCurrentUser().getUid();
        }
        return userId;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), "Sorry! you can't use this app without granting this permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
