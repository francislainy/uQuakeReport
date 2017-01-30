package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.net.URL;
import java.util.List;

/**
 * Created by Francislainy on 25/01/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Earthquake> loadInBackground() {

        // Create URL object
        URL url = QueryUtils.createUrl(mUrl);
        // Make HTTP request

        String jsonResponse = QueryUtils.makeHttpRequest(url);
        Log.i(LOG_TAG, "######################################### json = " + jsonResponse);

        List<Earthquake> list = QueryUtils.extractFeaturesFromEarthquakes(jsonResponse);

        return list;

    }
}