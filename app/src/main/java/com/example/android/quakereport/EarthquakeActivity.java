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
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * JSON response for a USGS query
     */
    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private TextView mEmptyStateView;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        getLoaderManager().initLoader(1, null, this);
        Log.i(LOG_TAG, getLoaderManager().initLoader(1, null, this) + " getLoaderManager");

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);

        // Check network Connectivity
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


//        EarthquakeAsyncTask x = new EarthquakeAsyncTask();
//        x.execute();

    }

    private void updateUI(List<Earthquake> earthquakes) {
        Log.i(LOG_TAG, "######################################### list = " + earthquakes);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setEmptyView(mEmptyStateView);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, new EarthquakeLoader(this, USGS_REQUEST_URL) + " onCreateLoader");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.i(LOG_TAG, " OnLoadFinished");

        if (isConnected == true)
            // Set empty state text to display "No earthquake found.";
            mEmptyStateView.setText("No earthquakes found.");
        else
            mEmptyStateView.setText("No internet connection");

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        updateUI(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i(LOG_TAG, " OnLoaderReset");
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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