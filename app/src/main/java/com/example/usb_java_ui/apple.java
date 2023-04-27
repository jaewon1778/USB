package com.example.usb_java_ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class apple extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple);

        Button button = (Button)  findViewById(R.id.apple);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "사과";
                Context context = v.getContext();
                Intent intent = new Intent(context, Output.class);
                intent.putExtra("keyStr", str);
                context.startActivity(intent);

            }
        });

    }
}
