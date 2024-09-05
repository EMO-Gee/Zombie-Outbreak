package com.example.zombie_outbreakers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Reference the button
        Button myButton = findViewById(R.id.myButton);

        //Set an onClickListener to make the button interactive

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Action to perform the button is clicked
                Toast.makeText(MainActivity.this, "Hi There!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainPage.class);
                startActivity(intent);
            }

        });
    }
}