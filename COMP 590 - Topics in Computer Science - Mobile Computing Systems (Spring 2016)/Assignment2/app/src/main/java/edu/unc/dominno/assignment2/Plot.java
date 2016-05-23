package edu.unc.dominno.assignment2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Plot extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s;
    private List<Sensor> l;

    long lastPrinted = 0, lastPrinted2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);

        Intent sense = getIntent();
        String whichSensor = sense.getExtras().getString("which");

        TextView t = (TextView)findViewById(R.id.plotTitle);
        PlotView p = (PlotView)findViewById(R.id.graphplot);

        switch (whichSensor) {
            case "accelerometerButton":
                s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                t.setText("Accelerometer");
                p.setType("Accelerometer");
                break;
            case "lightButton":
                s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
                t.setText("Light");
                p.setType("Light");
                break;
            case "gravityButton":
                s = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
                t.setText("Gravity");
                p.setType("Gravity");
                break;
            case "gyroscopeButton":
                s = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                t.setText("Gyroscope");
                p.setType("Gyroscope");
                break;
        }

        //s = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sm.registerListener(this, s, 1000000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        PlotView plot = (PlotView)findViewById(R.id.graphplot);

        if (event.timestamp - lastPrinted >= 1e9 && event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            lastPrinted = event.timestamp;
            plot.addPoint(event.values[0]);
            plot.invalidate();
        } else if (event.timestamp - lastPrinted >= 1e9 && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            lastPrinted = event.timestamp;
            plot.addPoint((float)Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)));
            plot.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    public void goBack(View v) {
        Intent x = new Intent(this,MainActivity.class);
        setResult(RESULT_OK, x);
        finish();
        //startActivity(x);
    }
}
