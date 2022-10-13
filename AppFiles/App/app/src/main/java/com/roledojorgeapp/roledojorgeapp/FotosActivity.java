package com.roledojorgeapp.roledojorgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
    }

    public void botaoReturnOnClick (View v){
        Intent telareturn = new Intent(this, MainActivity2.class);
        startActivity(telareturn);
    }
}