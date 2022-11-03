package com.uniftec.randompeople;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TextView nome;
    private TextView sobrenome;
    private TextView latitude;
    private TextView longitude;

    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pessoa);

        nome = (TextView)findViewById(R.id.nameInfo);
        sobrenome = (TextView)findViewById(R.id.lastNameInfo);
        latitude = (TextView)findViewById(R.id.latitudeInfo);
        longitude = (TextView) findViewById(R.id.longitudeInfo);




    }
    public void acionaRandom(View view){
        GetJson download = new GetJson();
        //Chama Async Task
        download.execute();
    }


    private class GetJson extends AsyncTask<Void, Void, PessoaObj> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected PessoaObj doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacao("https://randomuser.me/api/");
        }

        @Override
        protected void onPostExecute(PessoaObj pessoa){
            nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
            sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
            latitude.setText(pessoa.getLatitude());
            longitude.setText(pessoa.getLongitude());

            load.dismiss();
        }
    }
}
