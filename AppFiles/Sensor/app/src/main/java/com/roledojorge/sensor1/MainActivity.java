package com.roledojorge.sensor1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorLum;
    private Sensor sensorProx;

    private ImageView imageView;

    private int backGroundColor = 0;
    /*
     *  Black = 0
     *  White = 1
     */

    private int imageState= 0;
    /*
     *  OFF = 0
     *  ON = 1
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        imageView = (ImageView) findViewById(R.id.imageMicrofone);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensorLum = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProx = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorManager.registerListener(new SensorLum(),sensorLum,SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(new SensorProx(),sensorProx,SensorManager.SENSOR_DELAY_UI);

        imageView.setBackgroundColor(getResources().getColor(R.color.white));
        imageView.setImageResource(R.drawable.microfone_black_off);

    }


    class SensorLum implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float sensorValue = sensorEvent.values[0];

            /*Background esta preto*/
            /*if (backGroundColor == 0) {*/
                if (sensorValue > 100) {
                    backGroundColor = 1;/*Torna variavel de controle de backgroud branca*/
                    imageView.setBackgroundColor(getResources().getColor(R.color.white));
                    if (imageState == 0){
                        imageView.setImageResource(R.drawable.microfone_black_off);
                    }else{
                        imageView.setImageResource(R.drawable.microfone_black_on);
                    }
                }
            /*}else{/*Background esta branco*/

                if (sensorValue < 100) {

                    backGroundColor = 0;/*Torna variavel de controle de backgroud preta*/
                    /*Torna o fundo preto*/
                    imageView.setBackgroundColor(getResources().getColor(R.color.black));
                    if (imageState == 0){
                        imageView.setImageResource(R.drawable.microfone_white_off);
                    }else{
                        imageView.setImageResource(R.drawable.microfone_white_on);
                    }

                }
            /*}*/

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

    }

    class SensorProx implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float sensorValue = sensorEvent.values[0];

            /*if (imageState == 0){*/
                if (sensorValue < 2){
                    imageState = 1;
                    if (backGroundColor == 0){
                        imageView.setImageResource(R.drawable.microfone_white_on);
                    }else{
                        imageView.setImageResource(R.drawable.microfone_black_on);
                    }
                }
            /*}else{*/
                if (sensorValue > 2){
                    imageState = 0;
                    if (backGroundColor == 0){
                        imageView.setImageResource(R.drawable.microfone_white_off);
                    }else{
                        imageView.setImageResource(R.drawable.microfone_black_off);
                    }
                }

            /*}*/
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

}