package com.example.android.booksearch;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText searchEditText;
    TextView nodatatextview;
    ListView listView;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String editText = searchEditText.getText().toString();
            if (!editText.isEmpty()) {
                search.setEnabled(false);
                String urlfetched = searchEditText.getText().toString();
//            MyAsyncTaskLoader task = new MyAsyncTaskLoader(this, urlfetched);
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.googleapis.com/books/v1/volumes?q=" + urlfetched + "&maxResults=10");
                if (getSupportLoaderManager().getLoader(1) != null)
                    getSupportLoaderManager().restartLoader(1, bundle, MainActivity.this).forceLoad();
                else
                    getSupportLoaderManager().initLoader(1, bundle, MainActivity.this).forceLoad();
            }
            search.setEnabled(true);
        } else {
            Toast.makeText(this, "Not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        return new MyAsyncTaskLoader(this, args.getString("url"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        UpdateData(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
