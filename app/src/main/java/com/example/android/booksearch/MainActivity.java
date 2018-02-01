package com.example.android.booksearch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText searchEditText;
    TextView nodatatextview;
    ListView listView;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "connected to" + networkInfo.getTypeName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_LONG).show();
        }
        nodatatextview = (TextView) findViewById(R.id.no_data_text_view);
        search = (Button) findViewById(R.id.search);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(nodatatextview);
    }

    private void UpdateData(String jsonData) {
        ArrayList<Book> book = JsonDataAddtion.ExtractData(jsonData);
        BookListAdapter adapter = new BookListAdapter(this, 0, book);
        listView.setAdapter(adapter);
    }

    public void search(View view) {
        String editText = searchEditText.getText().toString();
        if (!editText.isEmpty()) {
            search.setEnabled(false);
            String urlfetched = searchEditText.getText().toString();
            BookTask task = new BookTask();
            task.execute("https://www.googleapis.com/books/v1/volumes?q=" + urlfetched + "&maxResults=10");
        }
        search.setEnabled(true);
    }

    class BookTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder JsonData = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            URL url = null;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader((inputStream));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    JsonData.append(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return JsonData.toString();
        }

        @Override
        protected void onPostExecute(String o) {
            UpdateData(o);
            super.onPostExecute(o);
        }
    }
}
