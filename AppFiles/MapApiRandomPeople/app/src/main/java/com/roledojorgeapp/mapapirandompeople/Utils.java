package com.roledojorgeapp.mapapirandompeople;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public PessoaObj getInformacao(String end){
        String json;
        PessoaObj retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private PessoaObj parseJson(String json){
        try {
            PessoaObj pessoa = new PessoaObj();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data;

            JSONObject objArray = array.getJSONObject(0);

            JSONObject name = objArray.getJSONObject("name");
            pessoa.setNome(name.getString("first"));
            pessoa.setSobrenome(name.getString("last"));

            JSONObject location = objArray.getJSONObject("location");
            JSONObject coordinates = location.getJSONObject("coordinates");
            pessoa.setLatitude(coordinates.getString("latitude"));
            pessoa.setLongitude(coordinates.getString("longitude"));

            return pessoa;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


}
