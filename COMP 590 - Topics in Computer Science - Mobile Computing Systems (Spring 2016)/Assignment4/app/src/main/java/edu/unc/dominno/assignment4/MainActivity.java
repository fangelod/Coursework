package edu.unc.dominno.assignment4;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Bean b;
    Handler h = new Handler();
    int delay = 100; //milliseconds
    boolean start;
    int time;
    ArrayList<data> out;
    String activity;
    String position;
    String FILENAME;
    boolean activityPicked;
    boolean positionPicked;
    int SERIAL;
    int STANDING_WAIST = 1;
    int STANDING_WRIST = 1;
    int STANDING_SHOE = 1;
    int SITTING_WAIST = 1;
    int SITTING_WRIST = 1;
    int SITTING_SHOE = 1;
    int WALKING_WAIST = 1;
    int WALKING_WRIST = 1;
    int WALKING_SHOE = 1;
    int RUNNING_WAIST = 1;
    int RUNNING_WRIST = 1;
    int RUNNING_SHOE = 1;
    int UPSTAIRS_WAIST = 1;
    int UPSTAIRS_WRIST = 1;
    int UPSTAIRS_SHOE = 1;
    int DOWNSTAIRS_WAIST = 1;
    int DOWNSTAIRS_WRIST = 1;
    int DOWNSTAIRS_SHOE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BeanManager.getInstance().startDiscovery(bdl);

        time = 0;
        out = new ArrayList<data>();
    }

    BeanDiscoveryListener bdl = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean, int rssi) {
            Log.v("TT", "" + bean.getDevice() + ", " + rssi);
            b = bean; // Bean b was declared in Activity.
        }

        @Override
        public void onDiscoveryComplete() {
            b.connect(getApplicationContext(), blsnr);
        }
    };

    BeanListener blsnr = new BeanListener() {
        @Override
        public void onConnected() {
            Log.v("TT", "We are connected to: " + b.getDevice().getName());
            TextView t = (TextView)findViewById(R.id.connectStatus);
            t.setText("Connected to " + b.getDevice().getName());
        }

        @Override
        public void onConnectionFailed() {

        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onSerialMessageReceived(byte[] data) {

        }

        @Override
        public void onScratchValueChanged(ScratchBank bank, byte[] value) {

        }

        @Override
        public void onError(BeanError error) {

        }
    };

    public void pressStart(View v) {
        if (b.isConnected() && activityPicked && positionPicked) {
            Log.v("DOMINNO", "START button pressed");
            start = true;

            h.postDelayed(new Runnable() {
                public void run() {
                    //do something
                    if (start) {
                        if (b.isConnected()) {
                            b.readAcceleration(new Callback<Acceleration>() {
                                @Override
                                public void onResult(Acceleration result) {
                                    Log.v("Kobe Bean", "" + time + " " + result.x() + " " + result.y() + " " + result.z());
                                    out.add(new data(time, result.x(), result.y(), result.z()));
                                }
                            });
                            time += 100;
                            if (out.size() == 200) {
                                showPop("Reached 200 values");
                            }
                        } else {
                            Log.v("DOMINNO", "SHOWING DATA...............................................");
                        }
                        h.postDelayed(this, delay);
                    }
                }
            }, delay);
        } else if (b.isConnected() && !activityPicked && positionPicked) {
            showPop("Choose an activity first!");
        } else if (b.isConnected() && activityPicked && !positionPicked) {
            showPop("Choose a position first!");
        } else if (b.isConnected() && !activityPicked && !positionPicked) {
            showPop("Choose an activity and position first!");
        } else {
            showPop("Not connected to the bean!");
        }

    }

    public void pressStop(View v) {
        Log.v("DOMINNO", "STOP button pressed");
        start = false;
    }

    public void pressSave(View v) {
        Log.v("DOMINNO", "SAVE button pressed");
        //Log.v("Kobe Bean", "Recorded " + out.size() + " accelerometer readings");
        //Log.v("Kobe Bean", "(X, Y, Z) Value at index 0 → (" + out.get(0).getX() + ", " + out.get(0).getY() + ", " + out.get(0).getZ() + ")");

        time = 0;

        if (activity.equals("STANDING") && position.equals("WAIST")) {
            SERIAL = STANDING_WAIST;
            STANDING_WAIST++;
        } else if (activity.equals("STANDING") && position.equals("WRIST")) {
            SERIAL = STANDING_WRIST;
            STANDING_WRIST++;
        } else if (activity.equals("STANDING") && position.equals("SHOE")) {
            SERIAL = STANDING_SHOE;
            STANDING_SHOE++;
        } else if (activity.equals("SITTING") && position.equals("WAIST")) {
            SERIAL = SITTING_WAIST;
            SITTING_WAIST++;
        } else if (activity.equals("SITTING") && position.equals("WRIST")) {
            SERIAL = SITTING_WRIST;
            SITTING_WRIST++;
        } else if (activity.equals("SITTING") && position.equals("SHOE")) {
            SERIAL = SITTING_SHOE;
            SITTING_SHOE++;
        } else if (activity.equals("WALKING") && position.equals("WAIST")) {
            SERIAL = WALKING_WAIST;
            WALKING_WAIST++;
        } else if (activity.equals("WALKING") && position.equals("WRIST")) {
            SERIAL = WALKING_WRIST;
            WALKING_WRIST++;
        } else if (activity.equals("WALKING") && position.equals("SHOE")) {
            SERIAL = WALKING_SHOE;
            WALKING_SHOE++;
        } else if (activity.equals("RUNNING") && position.equals("WAIST")) {
            SERIAL = RUNNING_WAIST;
            RUNNING_WAIST++;
        } else if (activity.equals("RUNNING") && position.equals("WRIST")) {
            SERIAL = RUNNING_WRIST;
            RUNNING_WRIST++;
        } else if (activity.equals("RUNNING") && position.equals("SHOE")) {
            SERIAL = RUNNING_SHOE;
            RUNNING_SHOE++;
        } else if (activity.equals("UPSTAIRS") && position.equals("WAIST")) {
            SERIAL = UPSTAIRS_WAIST;
            UPSTAIRS_WAIST++;
        } else if (activity.equals("UPSTAIRS") && position.equals("WRIST")) {
            SERIAL = UPSTAIRS_WRIST;
            UPSTAIRS_WRIST++;
        } else if (activity.equals("UPSTAIRS") && position.equals("SHOE")) {
            SERIAL = UPSTAIRS_SHOE;
            UPSTAIRS_SHOE++;
        } else if (activity.equals("DOWNSTAIRS") && position.equals("WAIST")) {
            SERIAL = DOWNSTAIRS_WAIST;
            DOWNSTAIRS_WAIST++;
        } else if (activity.equals("DOWNSTAIRS") && position.equals("WRIST")) {
            SERIAL = DOWNSTAIRS_WRIST;
            DOWNSTAIRS_WRIST++;
        } else if (activity.equals("DOWNSTAIRS") && position.equals("SHOE")) {
            SERIAL = DOWNSTAIRS_SHOE;
            DOWNSTAIRS_SHOE++;
        }

        FILENAME = activity + "_" + position + "_" + SERIAL + ".txt";

        try {
            File d = new File("/sdcard/" + FILENAME);
            d.createNewFile();
            FileOutputStream fos = null;
            fos = new FileOutputStream(d);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
            String s = "";
            if (out.size() >= 200) {
                for (int i = 0; i < 200; i++) {
                    s += out.get(i).getTimestamp() + " "
                            + out.get(i).getX() + " "
                            + out.get(i).getY() + " "
                            + out.get(i).getZ() + " \n";
                }
            } else {
                for (int i = 0; i < out.size(); i++) {
                    s += out.get(i).getTimestamp() + " "
                            + out.get(i).getX() + " "
                            + out.get(i).getY() + " "
                            + out.get(i).getZ() + " \n";
                }
            }
            //String s = FILENAME;
            Log.v("DOMINNO", s);
            //fos.write(s.getBytes());
            myOutWriter.write(s);
            myOutWriter.close();
            fos.close();
            //Log.v("DOMINNO","Wrote file to Internal Storage");
            showPop("Saved " + FILENAME + " to /sdcard/");

            /*FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            Log.v("DOMINNO","" + stringBuilder.toString());
            fis.close();*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("DOMINNO", "File not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("DOMINNO", "IO exception");
        }

        out.clear();
    }

    public void chosenActivity(View v) {
        RadioButton r = (RadioButton)findViewById(v.getId());
        String s = v.getResources().getResourceEntryName(v.getId());

        activityPicked = true;

        if (s.equals("standing")) {
            Log.v("DOMINNO", "STANDING chosen");
            activity = "STANDING";
        } else if (s.equals("sitting")) {
            Log.v("DOMINNO", "SITTING chosen");
            activity = "SITTING";
        } else if (s.equals("walking")) {
            Log.v("DOMINNO", "WALKING chosen");
            activity = "WALKING";
        } else if (s.equals("running")) {
            Log.v("DOMINNO", "RUNNING chosen");
            activity = "RUNNING";
        } else if (s.equals("upstairs")) {
            Log.v("DOMINNO", "UPSTAIRS chosen");
            activity = "UPSTAIRS";
        } else if (s.equals("downstairs")) {
            Log.v("DOMINNO", "DOWNSTAIRS chosen");
            activity = "DOWNSTAIRS";
        }
    }

    public void chosenPosition(View v) {
        RadioButton r = (RadioButton)findViewById(v.getId());
        String s = v.getResources().getResourceEntryName(v.getId());

        positionPicked = true;

        if (s.equals("waist")) {
            Log.v("DOMINNO", "WAIST chosen");
            position = "WAIST";
        } else if (s.equals("wrist")) {
            Log.v("DOMINNO", "WRIST chosen");
            position = "WRIST";
        } else if (s.equals("shoe")) {
            Log.v("DOMINNO", "SHOE chosen");
            position = "SHOE";
        }
    }

    public void showPop(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }
}

class data {
    private String timestamp;
    private double x;
    private double y;
    private double z;

    public data(int time, double x, double y, double z) {
        if (time <= 9) {
            this.timestamp = "0000" + time;
        } else if (time <= 99) {
            this.timestamp = "000" + time;
        } else if (time <= 999) {
            this.timestamp = "00" + time;
        } else if (time <= 9999) {
            this.timestamp = "0" + time;
        } else if (time <= 99999) {
            this.timestamp = "" + time;
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
