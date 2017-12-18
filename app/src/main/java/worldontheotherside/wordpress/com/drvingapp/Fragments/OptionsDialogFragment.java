package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import worldontheotherside.wordpress.com.drvingapp.HomeActivity;
import worldontheotherside.wordpress.com.drvingapp.LoginActivity;
import worldontheotherside.wordpress.com.drvingapp.R;
import worldontheotherside.wordpress.com.drvingapp.SignUpActivity;

/**
 * Created by زينب on 12/15/2017.
 */

public class OptionsDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private String action;
    private FirebaseAuth auth;

    public OptionsDialogFragment()
    {
        // Empty constructor for dialog fragment
    }

    public static OptionsDialogFragment newOptionsDialog(String action)
    {
        OptionsDialogFragment dialog = new OptionsDialogFragment();
        Bundle args = new Bundle();
        args.putString("Action", action);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_options, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        action = getArguments().getString("Action");
        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.linearLayoutEmail).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutPhone).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutAnonymous).setOnClickListener(this);

        if(action == "signup")
            view.findViewById(R.id.linearLayoutAnonymous).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(action == "signup")
            intent = new Intent(getActivity(), SignUpActivity.class);
        else
            intent = new Intent(getActivity(), LoginActivity.class);

        intent.putStringArrayListExtra("Areas", getActivity().getIntent().getStringArrayListExtra("Areas"));
        intent.putStringArrayListExtra("Languages", getActivity().getIntent().getStringArrayListExtra("Languages"));

        if(view.getId() == R.id.linearLayoutEmail)
            intent.putExtra("type", "email");
        else if(view.getId() == R.id.linearLayoutPhone)
            intent.putExtra("type", "phone");
        else
        {
            auth.signInAnonymously();

            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("type", "anonymous");
        }

        getActivity().startActivity(intent);
    }
}
