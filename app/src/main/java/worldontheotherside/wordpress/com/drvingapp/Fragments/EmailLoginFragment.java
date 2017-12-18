package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.AppData;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/16/2017.
 */

public class EmailLoginFragment extends Fragment {

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private AutoCompleteTextView autoCompleteTextViewEmail;
    private TextInputEditText editTextPassword;

    public EmailLoginFragment()
    {
        // Empty constructor
    }

    public EmailLoginFragment newEmailLoginFragment() { return new EmailLoginFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputLayoutEmail = (TextInputLayout) view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) view.findViewById(R.id.textInputLayoutPassword);
        autoCompleteTextViewEmail = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextViewEmail);
        editTextPassword = (TextInputEditText) view.findViewById(R.id.editTextPassword);

        AppData appData = new AppData(getActivity());

        ArrayList<String> suggestions = appData.getInputList();
        autoCompleteTextViewEmail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                suggestions.toArray(new String[suggestions.size()])));
    }

    public String getEmail() {
        return autoCompleteTextViewEmail.getText().toString();
    }

    public void setEmail(String email) {
        autoCompleteTextViewEmail.setText(email);
    }

    public String getPassword() {
        return editTextPassword.getText().toString();
    }

    public void setPassword(String password) {
        editTextPassword.setText(password);
    }

    public void setErrorMessage(String message)
    {
        if(getEmail().isEmpty())
        {
            textInputLayoutEmail.setError(message);
            textInputLayoutEmail.setErrorEnabled(true);
        }

        if(getPassword().isEmpty())
        {
            textInputLayoutPassword.setError(message);
            textInputLayoutPassword.setErrorEnabled(true);
        }
    }
}
