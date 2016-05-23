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
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s1, s2, s3, s4;
    private List<Sensor> ls;

    long lastPrinted = 0, lastPrinted2 = 0, lastPrinted3 = 0, lastPrinted4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        // Get sensor list
        ls = sm.getSensorList(Sensor.TYPE_ALL);

        // Print sensor list
        for (int i = 0; i < ls.size(); i++) {
            Log.v("DOMINNO", "" + (i + 1) + ": " + ls.get(i).getName() + "");
        }

        s1 = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (s1 != null) {
            ImageView i = (ImageView)findViewById(R.id.accelerometerCheck);
            i.setBackgroundResource(R.drawable.check);

            TextView name = (TextView)findViewById(R.id.accelerometerName);
            name.append(s1.getName());

            TextView range = (TextView)findViewById(R.id.accelerometerRange);
            range.append("" + s1.getMaximumRange());

            TextView res = (TextView)findViewById(R.id.accelerometerResolution);
            res.append("" + s1.getResolution());

            TextView delay = (TextView)findViewById(R.id.accelerometerDelay);
            delay.append("" + s1.getMinDelay() + " μs");

            TextView power = (TextView)findViewById(R.id.accelerometerPower);
            power.append("" + s1.getPower() + " mA");

            TextView vendor = (TextView)findViewById(R.id.accelerometerVendor);
            vendor.append(s1.getVendor());

            TextView version = (TextView)findViewById(R.id.accelerometerVersion);
            version.append("" + s1.getVersion());
        }

        s2 = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (s2 != null) {
            ImageView i = (ImageView)findViewById(R.id.lightCheck);
            i.setBackgroundResource(R.drawable.check);

            TextView name = (TextView)findViewById(R.id.sensortwoName);
            name.append(s2.getName());

            TextView range = (TextView)findViewById(R.id.sensortwoRange);
            range.append("" + s2.getMaximumRange());

            TextView res = (TextView)findViewById(R.id.sensortwoResolution);
            res.append("" + s2.getResolution());

            TextView delay = (TextView)findViewById(R.id.sensortwoDelay);
            delay.append("" + s2.getMinDelay() + " μs");

            TextView power = (TextView)findViewById(R.id.sensortwoPower);
            power.append("" + s2.getPower() + " mA");

            TextView vendor = (TextView)findViewById(R.id.sensortwoVendor);
            vendor.append(s2.getVendor());

            TextView version = (TextView)findViewById(R.id.sensortwoVersion);
            version.append("" + s2.getVersion());
        }

        s3 = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (s3 != null) {
            ImageView i = (ImageView)findViewById(R.id.sensorThreeCheck);
            i.setBackgroundResource(R.drawable.check);

            TextView name = (TextView)findViewById(R.id.sensorthreeName);
            name.append(s3.getName());

            TextView range = (TextView)findViewById(R.id.sensorthreeRange);
            range.append("" + s3.getMaximumRange());

            TextView res = (TextView)findViewById(R.id.sensorthreeResolution);
            res.append("" + s3.getResolution());

            TextView delay = (TextView)findViewById(R.id.sensorthreeDelay);
            delay.append("" + s3.getMinDelay() + " μs");

            TextView power = (TextView)findViewById(R.id.sensorthreePower);
            power.append("" + s3.getPower() + " mA");

            TextView vendor = (TextView)findViewById(R.id.sensorthreeVendor);
            vendor.append(s3.getVendor());

            TextView version = (TextView)findViewById(R.id.sensorthreeVersion);
            version.append("" + s3.getVersion());
        }

        s4 = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (s4 != null) {
            ImageView i = (ImageView)findViewById(R.id.sensorFourCheck);
            i.setBackgroundResource(R.drawable.check);

            TextView name = (TextView)findViewById(R.id.sensorfourName);
            name.append(s4.getName());

            TextView range = (TextView)findViewById(R.id.sensorfourRange);
            range.append("" + s4.getMaximumRange());

            TextView res = (TextView)findViewById(R.id.sensorfourResolution);
            res.append("" + s4.getResolution());

            TextView delay = (TextView)findViewById(R.id.sensorfourDelay);
            delay.append("" + s4.getMinDelay() + " μs");

            TextView power = (TextView)findViewById(R.id.sensorfourPower);
            power.append("" + s4.getPower() + " mA");

            TextView vendor = (TextView)findViewById(R.id.sensorfourVendor);
            vendor.append(s4.getVendor());

            TextView version = (TextView)findViewById(R.id.sensorfourVersion);
            version.append("" + s4.getVersion());
        }

        sm.registerListener(this, s1, 10000000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            if (event.timestamp - lastPrinted >= 1e9) {
                Log.v("ACCELEROMETER", "" + event.values[0]);
                lastPrinted = event.timestamp;
            }
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    public void sendToPlot(View v) {
        Log.v("DOMINNO", v.getResources().getResourceEntryName(v.getId()));

        Intent x = new Intent(this,Plot.class);
        x.putExtra("which", v.getResources().getResourceEntryName(v.getId()));
        startActivityForResult(x, 0);
    }
}
