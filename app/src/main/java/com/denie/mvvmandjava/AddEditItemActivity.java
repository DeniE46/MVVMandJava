package com.denie.mvvmandjava;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditItemActivity extends AppCompatActivity {

    private EditText addTitle;
    private EditText addDescription;
    private NumberPicker numberPicker;

    public static final String EXTRA_ID = "com.denie.mvvandjava.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.denie.mvvmandjava.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.denie.mvvmandjava.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.denie.mvvmandjava.EXTRA_PRIORITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        addTitle = findViewById(R.id.add_title);
        addDescription = findViewById(R.id.add_description);
        numberPicker = findViewById(R.id.add_priority);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            addTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            addDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            setTitle("Edit Note");
        }
        else{
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();
        int priority = numberPicker.getValue();
        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "title and description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }
}
