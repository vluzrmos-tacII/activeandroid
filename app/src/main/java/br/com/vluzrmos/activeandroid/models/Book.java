package br.com.vluzrmos.activeandroid.models;

import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "books")
public class Book extends Model {

    @Column(name = "title")
    public String title;

    @Column(name = "description")
    public String description;

    public Book(){
        super();
    }

    public Book(String title, String description){
        super();

        this.title = title;
        this.description = description;

    }

    public static int count(){
        Cursor c = ActiveAndroid.getDatabase().rawQuery("SELECT COUNT(*) as total FROM " + new Book().getTableName(), null);

        int total = c.getInt(c.getColumnIndex("total"));

        c.close();

        return total;


        //return new Select().from(Book.class).execute().size();
    }
}
