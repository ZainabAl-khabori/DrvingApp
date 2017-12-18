package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/17/2017.
 */

public class PhoneLoginFragment extends Fragment {

    private TextInputLayout textInputLayoutPhone;
    private TextInputEditText editTextPhone;
    private TextInputLayout textInputLayoutVerify;
    private TextInputEditText editTextVerify;
    private TextView textViewInform;

    public PhoneLoginFragment()
    {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputLayoutPhone = (TextInputLayout) view.findViewById(R.id.textInputLayoutPhone);
        editTextPhone = (TextInputEditText) view.findViewById(R.id.editTextMobileNo);
        textInputLayoutVerify = (TextInputLayout) view.findViewById(R.id.textInputLayoutVerify);
        editTextVerify = (TextInputEditText) view.findViewById(R.id.editTextVerify);
        textViewInform = (TextView) view.findViewById(R.id.textViewInform);
    }

    public String getPhoneNumber() { return editTextPhone.getText().toString(); }

    public void setErrorMessage(FirebaseException e)
    {
        if(e instanceof FirebaseAuthInvalidCredentialsException)
        {
            textInputLayoutPhone.setError("Invalid mobile number");
            textInputLayoutPhone.setErrorEnabled(true);
        }
        else if(e instanceof FirebaseTooManyRequestsException)
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();
    }

    public void setErrorSimple(String message)
    {
        if(getPhoneNumber().isEmpty())
        {
            textInputLayoutPhone.setError(message);
            textInputLayoutPhone.setErrorEnabled(true);
        }

        if(getVerificationCode().isEmpty() && (textInputLayoutVerify.getVisibility() == View.VISIBLE))
        {
            textInputLayoutVerify.setError(message);
            textInputLayoutVerify.setErrorEnabled(true);
        }
    }

    public void setVerificationVisible()
    {
        textInputLayoutVerify.setVisibility(View.VISIBLE);
        textViewInform.setVisibility(View.VISIBLE);
    }

    public String getVerificationCode() { return editTextVerify.getText().toString(); }
}
