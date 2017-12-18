package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by زينب on 12/3/2017.
 */

public class AppData {

    private SharedPreferences sharedPreferences;
    private Context context;

    public AppData(Context context)
    {
        sharedPreferences = context.getSharedPreferences("app_shared_prefs", Context.MODE_PRIVATE);
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() { return sharedPreferences; }

    private SharedPreferences.Editor edit() { return getSharedPreferences().edit(); }

    public String getUserType() { return getSharedPreferences().getString("UserType", ""); }
    public void setUserType(String type) { edit().putString("UserType", type).apply(); }

    private String getAutoCompleteEmail() { return getSharedPreferences().getString("EmailsList", ""); }
    private void setAutoCompleteEmail(String listJson) { edit().putString("EmailsList", listJson).apply(); }
    public ArrayList<String> getInputList()
    {
        ArrayList<String> list;

        String json = getAutoCompleteEmail();
        if(json.equals(""))
            list = new ArrayList<>();
        else
        {
            String[] inputs = new Gson().fromJson(json, String[].class);
            list = new ArrayList<>(Arrays.asList(inputs));
        }

        return list;
    }
    public void addInputEmail(String input)
    {
        ArrayList<String> inputList = getInputList();
        inputList.add(input);
        String json = new Gson().toJson(inputList);
        setAutoCompleteEmail(json);
    }
}
