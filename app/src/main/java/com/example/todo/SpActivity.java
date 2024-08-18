package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sp);

        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SpActivity.this,MainActivity.class));
            finish();
        },3000);
    }
}
