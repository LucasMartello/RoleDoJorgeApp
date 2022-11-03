package com.roledojorgeapp.mapapirandompeople;

import androidx.annotation.NonNull;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView nome;
    private TextView sobrenome;
    private TextView latitude;
    private TextView longitude;
    GoogleMap map;

    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = (TextView)findViewById(R.id.nameInfo);
        sobrenome = (TextView)findViewById(R.id.lastNameInfo);
        latitude = (TextView)findViewById(R.id.latitudeInfo);
        longitude = (TextView) findViewById(R.id.longitudeInfo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    public void acionaRandom(View view){
        GetJson download = new GetJson();
        //Chama Async Task
        download.execute();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        /*LatLng latLng = new LatLng(0,0);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Zero"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
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
            LatLng latLng = new LatLng( Double.parseDouble(pessoa.getLatitude()),Double.parseDouble(pessoa.getLongitude()));

            nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
            sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
            latitude.setText(pessoa.getLatitude());
            longitude.setText(pessoa.getLongitude());

            map.addMarker(new MarkerOptions().position(latLng).title(pessoa.getNome()));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            load.dismiss();
        }
    }
}
