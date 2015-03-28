package br.com.vluzrmos.activeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.vluzrmos.activeandroid.models.Book;

public class AddBookActivity extends BaseActivity {

    private EditText description;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_form);

        title = (EditText) findViewById(R.id.book_edt_title);
        description = (EditText) findViewById(R.id.book_edt_description);
    }

    public void buttonAddBook(View view){
        if(validate()) {
            new Book(title.getText().toString(), description.getText().toString()).save();

            showToast(getString(R.string.book_added_message), TOAST_LENGTH_LONG);

            startMainActivity();
        }
    }

    private boolean validate(){
        boolean valid_title = !title.getText().toString().trim().isEmpty();
        boolean valid_description = true;//description.getText().toString().trim().isEmpty();

        if(!valid_title){
            title.setError(getString(R.string.validation_book_title));
        }

        if(!valid_description){
            description.setError(getString(R.string.validation_book_description));
        }

        return valid_title;
    }

    public void buttonCancel(View view){
        startMainActivity();
    }

    private void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);

        finish();
    }
}
