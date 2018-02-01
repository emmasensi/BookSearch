package com.example.android.booksearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonDataAddtion {

    public static ArrayList<Book> ExtractData(String jsonData) {

        ArrayList<Book> book = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String bookName = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                StringBuilder authorsString = new StringBuilder();
                for (int j = 0; j < authors.length(); j++) {
                    authorsString.append(authors.getString(j) + " ");
                }
                Book bookarray = new Book(bookName, authorsString.toString());
                book.add(bookarray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return book;
    }
}
