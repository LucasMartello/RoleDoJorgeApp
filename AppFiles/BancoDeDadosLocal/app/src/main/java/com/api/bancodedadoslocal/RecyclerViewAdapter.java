package com.api.bancodedadoslocal;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Produto> productList;
    Context context;
    int listaId;


    public RecyclerViewAdapter(List<Produto> productList, Context context, int listaId) {
        this.productList = productList;
        this.context = context;
        this.listaId = listaId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lineBG;
        TextView nameLine;
        TextView precoLine;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineBG = itemView.findViewById(R.id.lineBG);
            nameLine = itemView.findViewById(R.id.nameLine);
            precoLine = itemView.findViewById(R.id.precoLine);
            parentLayout = itemView.findViewById(R.id.lineProduct);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productline,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameLine.setText(productList.get(position).getNome());
        holder.precoLine.setText("R$ " + String.valueOf(productList.get(position).getValor()));
        if (productList.get(position).isSelected()){
            holder.lineBG.setBackgroundColor(Color.GREEN);
        }else {
            holder.lineBG.setBackgroundColor(Color.RED);
        }
        AddLista addlista = new AddLista();
        addlista.updateTotal(totalValue());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                try {
                    productList.get(position).setSelected(alterRelation(listaId, productList.get(position).getId()));
                    if (productList.get(position).isSelected()){
                        holder.lineBG.setBackgroundColor(Color.RED);
                    }else {
                        holder.lineBG.setBackgroundColor(Color.GREEN);
                    }

                    AddLista addlista = new AddLista();
                    addlista.updateTotal(totalValue());


                }catch (Exception e){
                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    private double totalValue(){
        double retorno = 0;

        DataBaseHelper db = new DataBaseHelper(context);
        List<Produto> lista = db.getAllProduto(listaId);
        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i).isSelected()) {
                retorno = retorno + lista.get(i).getValor();
            }
        }
        return retorno;
    }


    public boolean alterRelation(int lista, int produto){

        DataBaseHelper db = new DataBaseHelper(context);
        if (db.findRelation(lista, produto)) {
            db.deleteRelation(lista, produto);
            return true;
        } else {
            db.addRelation(lista, produto);
            return false;
        }

    }

}
