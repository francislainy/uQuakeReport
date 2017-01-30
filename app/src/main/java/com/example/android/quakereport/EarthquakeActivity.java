/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = "mytagggg";

    /**
     * JSON response for a USGS query
     */
    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        getLoaderManager().initLoader(1, null, this);

//        EarthquakeAsyncTask x = new EarthquakeAsyncTask();
//        x.execute();

    }

    private void updateUI(List<Earthquake> earthquakes) {
        Log.i(LOG_TAG, "######################################### list = " + earthquakes);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        updateUI(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {

    }


//private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
//
//    @Override
//    protected List<Earthquake> doInBackground(String... urls) {
//        // Create URL object
//        URL url = QueryUtils.createUrl(USGS_REQUEST_URL);
//        // Make HTTP request
//
//        String jsonResponse = QueryUtils.makeHttpRequest(url);
//        Log.i(LOG_TAG, "######################################### json = " + jsonResponse);
//
//        List<Earthquake> list = QueryUtils.extractFeaturesFromEarthquakes(jsonResponse);
//
//        return list;
//    }
//
//    @Override
//    protected void onPostExecute(List<Earthquake> earthquakes) {
//        Log.i(LOG_TAG, "######################################### post = " + earthquakes);
//
//        updateUI(earthquakes);
//    }
//}
}