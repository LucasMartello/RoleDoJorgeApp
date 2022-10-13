package com.roledojorgeapp.roledojorgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void botaoConvidadosOnClick (View v){
        Intent telaConvidados = new Intent(this, ConvidadosActivity.class);
        startActivity(telaConvidados);
    }

    public void botaoDescricaoOnClick (View v1){
        Intent telaDescricao = new Intent(this, DescricaoActivity.class);
        startActivity(telaDescricao);
    }

    public void botaoFotosOnClick (View v2){
        Intent telaFotos = new Intent(this, FotosActivity.class);
        startActivity(telaFotos);
    }

    public void button_ReturnOnCLick (View v){
        Intent telareturn = new Intent(this, MainActivity.class);
        startActivity(telareturn);
    }

}