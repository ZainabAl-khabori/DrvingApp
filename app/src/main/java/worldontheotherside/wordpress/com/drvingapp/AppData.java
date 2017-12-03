package worldontheotherside.wordpress.com.drvingapp;

import android.content.Context;
import android.content.SharedPreferences;

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
}
