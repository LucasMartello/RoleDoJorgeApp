package com.api.bancodedadoslocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    Button addListButton;
    ListView listasList;

    ArrayAdapter arrayAdapterListaListas;

    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView2);

        addListButton = (Button) findViewById(R.id.AddListButton);
        listasList = (ListView) findViewById(R.id.listasList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        arrayAdapterListaListas = new ArrayAdapter<Lista>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getAllListas());

        atualisaListaListas();

        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });


        listasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lista lista_temp = (Lista) arrayAdapterListaListas.getItem(i);

                Intent intent = new Intent(MainActivity.this,AddLista.class);
                intent.putExtra("ID_Lista", lista_temp.getIdLista());
                startActivity(intent);

            }
        });


        atualisaListaListas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualisaListaListas();
    }

    private void atualisaListaListas(){
        listasList.setAdapter(arrayAdapterListaListas);
    }


    public void openAddActivity(){
        try {
            Intent intent = new Intent(this,AddLista.class);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,"erro: "+ e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

}