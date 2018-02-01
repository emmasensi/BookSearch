package com.example.android.booksearch;

public class Book {
    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    private String bookName;
    private String author;

    public String getbookName() {
        return bookName;
    }

    public void setbookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
