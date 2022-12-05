package com.api.bancodedadoslocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddLista extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static TextView pageTitle, nomeListaTitle, dataListaTitle,nomeListaContent,dataListaContent;
    Button salvarBT, deletarBT;

    public static TextView totalValorContent;


    List<Produto> listaProdutos = new ArrayList<Produto>();

    Lista listaOld;

    int ListaId;

    DataBaseHelper dataBaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lista);

        ListaId = getIntent().getIntExtra("ID_Lista",-1);

        pageTitle = findViewById(R.id.listaTitle);
        nomeListaTitle = findViewById(R.id.nomeAddListaTitle);
        nomeListaContent = findViewById(R.id.nomeAddListaContent);
        dataListaTitle = findViewById(R.id.dataAddListaTitle);
        dataListaContent = findViewById(R.id.dataAddListaContent);
        totalValorContent = findViewById(R.id.totalValorContent);

        salvarBT = findViewById(R.id.salvarAddListaButton);
        deletarBT = findViewById(R.id.deleteAddListaButton);
        dataBaseHelper = new DataBaseHelper(AddLista.this);



        if (dataBaseHelper.getCountProdutos() == 0){
            populaProdutos();
        }

        recyclerView = (RecyclerView) findViewById(R.id.listaProdutos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listaProdutos = dataBaseHelper.getAllProduto(ListaId);
        mAdapter = new RecyclerViewAdapter(listaProdutos,AddLista.this, ListaId);
        recyclerView.setAdapter(mAdapter);

        if (ListaId == -1) {
            dataListaContent.setText(util.sysdate());
        }else{
            listaOld = getListaDB(ListaId);
            nomeListaContent.setText(listaOld.getNomeLista());
            dataListaContent.setText(listaOld.getDataLista());
        }

        deletarBT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (ListaId != -1) {
                    deletarLista(ListaId);
                }
                openMainActivity();
            }
        });


        salvarBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (validaAddLista()) {


                    Lista lista;
                    if (ListaId == -1) {

                        try {
                            lista = new Lista(-1, nomeListaContent.getText().toString(), dataListaContent.getText().toString());
                            boolean success = dataBaseHelper.addOne(lista);
                            if (success) {
                                Toast.makeText(AddLista.this, "Lista " + lista.getNomeLista().toUpperCase() + " cirada com sucesso", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddLista.this, "Erro Inesperado tenha um bom dia ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(AddLista.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            lista = new Lista(-1, null, null);
                        }
                    }else{
                        updateListaTable(ListaId,nomeListaContent.getText().toString(), dataListaContent.getText().toString());
                    }

                    openMainActivity();
                }
            }
        });




        atualizaListaProdutos(ListaId);

    }

    public void updateTotal(double valor){

        totalValorContent.setText("R$ " + Double.toString(valor));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Lista getListaDB(int id){
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            return dataBaseHelper.getListaDB(id);
        }catch (Exception e){
            Toast.makeText(AddLista.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void atualizaListaProdutosatualizaListaProdutos(int listaId){
        try {
            DataBaseHelper db = new DataBaseHelper(this);
            listaProdutos = db.getAllProduto(ListaId);
            mAdapter = new RecyclerViewAdapter(listaProdutos,AddLista.this,ListaId);
            recyclerView.setAdapter(mAdapter);

        }catch (Exception e){
            Toast.makeText(AddLista.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validaAddLista(){

        try{
            if (nomeListaContent.getText().toString().equals("")){
                throw new RuntimeException("Necessario indicar um nome para a lista");
            }
        }catch (Exception e){
            Toast.makeText(AddLista.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void openMainActivity(){
        try {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,"erro: "+ e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void populaProdutos(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddLista.this);

        try {
            dataBaseHelper.addProduto(new Produto(1, "Aroz", 2.69,false));
            dataBaseHelper.addProduto(new Produto(2, "Leite Longa Vida", 2.70,false));
            dataBaseHelper.addProduto(new Produto(3, "Carne Friboi", 16.70,false));
            dataBaseHelper.addProduto(new Produto(4, "Feijão carioquinha 1 Kg", 3.38,false));
            dataBaseHelper.addProduto(new Produto(5, "Refrigerante coca-cola 2 litros", 3.00,false));
        }catch (Exception e){
            Toast.makeText(AddLista.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void updateListaTable(int ID, String nome, String data){
        try {
            dataBaseHelper.updateListaTable(ID, nome, data);
        }catch (Exception e){
            Toast.makeText(AddLista.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void deletarLista(int ID){
        try {
            dataBaseHelper.deletarLista(ID);
        }catch (Exception e){
            Toast.makeText(AddLista.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }



}