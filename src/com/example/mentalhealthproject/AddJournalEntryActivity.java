package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddJournalEntryActivity extends Activity {

    private EditText titleEditText, contentEditText;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjournalentry);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);
        postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("title", title);
                    resultIntent.putExtra("content", content);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); // Go back to JournalActivity
                }
            }
        });
    }
}
