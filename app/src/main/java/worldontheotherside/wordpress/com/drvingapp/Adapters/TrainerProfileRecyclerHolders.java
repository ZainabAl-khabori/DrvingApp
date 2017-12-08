package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by wafooy on 08/12/17.
 */

public class TrainerProfileRecyclerHolders extends RecyclerView.ViewHolder {

    private static final String TAG = TrainerProfileRecyclerHolders.class.getSimpleName();

    public TextView profileHeader;

    public TextView headerContent;

    public TrainerProfileRecyclerHolders(final View itemView) {
        super(itemView);
        profileHeader = (TextView)itemView.findViewById(R.id.heading);
        headerContent = (TextView) itemView.findViewById(R.id.headingContent);
    }
}
