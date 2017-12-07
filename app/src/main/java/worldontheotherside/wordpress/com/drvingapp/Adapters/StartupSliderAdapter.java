package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/7/2017.
 */

public class StartupSliderAdapter extends PagerAdapter {

    private ArrayList<String> imagesList;
    private Context context;
    private LayoutInflater inflater;

    public StartupSliderAdapter(Context context, ArrayList<String> imagesList)
    {
        this.imagesList = imagesList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return imagesList.size(); }

    @Override
    public boolean isViewFromObject(View view, Object object) { return view == object; }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.images_pager_viewholder, container, false);

        ImageView imageViewImage = (ImageView) view.findViewById(R.id.imageViewImage);
        Picasso.with(context).load(imagesList.get(position)).into(imageViewImage);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
