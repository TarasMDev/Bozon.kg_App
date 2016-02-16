package bozon.kg.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by TarasM .
 */
public class AppControl extends Application {
    private static AppControl mInstances;
    public ImageLoader mImageLoaders;
    private RequestQueue mRequestQueues;

    public static synchronized AppControl getmInstance() {
        return mInstances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstances = this;
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueues == null) {
            mRequestQueues = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueues;
    }

    public ImageLoader getmImageLoader() {
        getmRequestQueue();
        if (mImageLoaders == null) {
            mImageLoaders = new ImageLoader(this.mRequestQueues, new AppBitmapCache());
        }
        return this.mImageLoaders;
    }


    public <T> void addToRequesQueue(Request<T> request) {
        getmRequestQueue().add(request);
    }


}
