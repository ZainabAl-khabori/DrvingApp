package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.View;

import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/18/2017.
 */

public class VerifyCodeDialogFragment extends DialogFragment {

    private TextInputEditText editTextVerify;

    public interface VerifyButtonClickedListener
    {
        void onVerifyClick(DialogFragment dialog);
    }

    public VerifyCodeDialogFragment()
    {
        //
    }

    public static VerifyCodeDialogFragment newVerifyCodeDialogFragment(String title)
    {
        VerifyCodeDialogFragment dialog = new VerifyCodeDialogFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("TITLE"));

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_verify_code_dialog, null);

        builder.setView(view);
        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VerifyButtonClickedListener listener = (VerifyButtonClickedListener) getActivity();
            }
        });

        editTextVerify = (TextInputEditText) view.findViewById(R.id.editTextVerify);

        return builder.create();
    }

    public String getVerificationCode()
    {
        return editTextVerify.getText().toString();
    }

    public void setErrorSimple(String message) {
        if (getVerificationCode().isEmpty())
            editTextVerify.setError(message);
    }
}
