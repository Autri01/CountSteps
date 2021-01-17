package com.example.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tvremaining,tvsteps;
    SensorManager sensorManager;

    boolean running =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvremaining=(TextView)findViewById(R.id.tvremaining);
        tvsteps=(TextView) findViewById(R.id.tvsteps);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvsteps.setText("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        running=true;
        Sensor countSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor!=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this,"Sensor not available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running=false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            tvsteps.setText(String.valueOf(event.values[0]));
            tvremaining.setText(String.valueOf(10000-event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}