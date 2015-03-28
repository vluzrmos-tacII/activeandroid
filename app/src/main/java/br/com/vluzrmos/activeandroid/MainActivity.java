package br.com.vluzrmos.activeandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Delete;

import java.util.ArrayList;

import br.com.vluzrmos.activeandroid.models.Book;



public class MainActivity extends BaseActivity {

    public ArrayList<Book> books = new ArrayList<Book>();
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        books = Book.all(Book.class);

        visibilityOfButtonClearList();

        final LayoutInflater inflater = LayoutInflater.from(this);

        listView = (ListView) findViewById(R.id.books_list);

        listView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return books.size();
            }

            @Override
            public Object getItem(int position) {
                return books.get(position);
            }

            @Override
            public long getItemId(int position) {
                return books.get(position).getId();
            }

            public void notifyDataSetChanged(){
                super.notifyDataSetChanged();

                visibilityOfButtonClearList();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                TextViewBookTag textViewBookTag;

                if(view == null){
                    textViewBookTag = new TextViewBookTag();

                    view    = inflater.inflate(R.layout.book_list_item, null);

                    textViewBookTag.title = (TextView) view.findViewById(R.id.title);
                    textViewBookTag.description = (TextView) view.findViewById(R.id.description);

                    view.setTag(textViewBookTag);
                }
                else{
                    textViewBookTag = (TextViewBookTag) view.getTag();
                }

                Book book = books.get(position);

                textViewBookTag.title.setText(book.title);
                textViewBookTag.description.setText(book.description);

                return view;
            }
        });
    }

    public void visibilityOfButtonClearList(){
        Button btn = (Button)findViewById(R.id.btn_clear_book_list);

        if(books.size()==0){
            btn.setVisibility(View.GONE);
        }
        else{
            btn.setVisibility(View.VISIBLE);
        }
    }

    public void buttonAddBook(View view){
        Intent addBookIntent = new Intent(getApplicationContext(), AddBookActivity.class);

        startActivity(addBookIntent);
    }

    public void buttonClearBookList(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle(getString(R.string.clear_books_alert_title));
        alertDialogBuilder.setMessage(getString(R.string.clear_books_alert_message));

        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(getString(R.string.yes),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
               performClearBookList();
            }
        });

        // set negative button: No message
        alertDialogBuilder.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    private void performClearBookList(){
        new Delete().from(Book.class).execute();
        books = Book.all(Book.class);

        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

        showToast(getString(R.string.books_deleted_message), TOAST_LENGTH_LONG);
    }

}


class TextViewBookTag {
    public TextView title;
    public TextView description;
}