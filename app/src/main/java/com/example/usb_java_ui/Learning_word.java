package com.example.usb_java_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Learning_word extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_word);

        Button learning_word = (Button) findViewById(R.id.lw_dotNum);
        learning_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dot_num.class);
                startActivity(intent);
            }
        });
        Button korean = (Button) findViewById(R.id.lw_korean);
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Korean.class);
                startActivity(intent);
            }
        });
        Button apple = (Button) findViewById(R.id.lw_word);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), apple.class);
                startActivity(intent);
            }
        });
    }
}
