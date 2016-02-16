package bozon.kg.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by TarasM .
 */
public class AppAdapter extends BaseAdapter {
    ImageLoader imageLoaderVar = AppControl.getmInstance().getmImageLoader();
    private LayoutInflater inflater;
    private Activity activityProducts;
    private List<AppItem> itemsProducts;

    public AppAdapter(Activity activity, List<AppItem> items) {
        this.activityProducts = activity;
        this.itemsProducts = items;
    }

    @Override
    public int getCount() {
        return itemsProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activityProducts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout, null);
        }
        imageLoaderVar = AppControl.getmInstance().getmImageLoader();

        NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.image_view_category);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);

        //getting data for row
        AppItem item = itemsProducts.get(position);
        imageView.setImageUrl(item.getIcon(), imageLoaderVar);
        //title
        title.setText(item.getTitle());


        return convertView;
    }
}