package edu.unc.dominno.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

/**
 * Created by Franz on 2/22/2016.
 */
public class PlotView extends View {
    private ArrayList<Float> l = new ArrayList<>();
    private String type;
    private int start = 0;

    public PlotView(Context con) {
        super(con);
    }

    public PlotView(Context con, AttributeSet att) {
        super(con, att);
    }

    public PlotView(Context con, AttributeSet att, int s) {
        super(con, att, s);
    }

    public PlotView(Context con, AttributeSet att, int s1, int s2) {
        super(con, att, s1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p1 = new Paint();
        Paint p2 = new Paint();
        Paint p3 = new Paint();
        Paint p4 = new Paint();
        p1.setColor(Color.BLACK);
        p1.setStrokeWidth(10);
        p1.setTextSize(25);
        p2.setColor(Color.RED);
        p3.setColor(Color.GREEN);
        p4.setColor(Color.BLUE);

        // y-axis
        canvas.drawLine(100, 0, 100, getHeight(), p1);

        // x-axis
        canvas.drawLine(0, getHeight() - 100, getWidth(), getHeight() - 100, p1);

        // x-axis increments
        canvas.drawLine(100+(((getWidth()-100)/9)*1), getHeight() - 125, 100+(((getWidth()-100)/9)*1), getHeight() - 75, p1);
        canvas.drawLine(100 +(((getWidth()-100)/9)*2), getHeight() - 125, 100+(((getWidth()-100)/9)*2), getHeight() - 75, p1);
        canvas.drawLine(100 +(((getWidth()-100)/9)*3), getHeight() - 125, 100+(((getWidth()-100)/9)*3), getHeight() - 75, p1);
        canvas.drawLine(100 + (((getWidth() - 100) / 9) * 4), getHeight() - 125, 100 + (((getWidth() - 100) / 9) * 4), getHeight() - 75, p1);
        canvas.drawLine(100 + (((getWidth() - 100) / 9) * 5), getHeight() - 125, 100 + (((getWidth() - 100) / 9) * 5), getHeight() - 75, p1);
        canvas.drawLine(100 + (((getWidth() - 100) / 9) * 6), getHeight() - 125, 100 + (((getWidth() - 100) / 9) * 6), getHeight() - 75, p1);
        canvas.drawLine(100+(((getWidth()-100)/9)*7), getHeight() - 125, 100+(((getWidth()-100)/9)*7), getHeight() - 75, p1);
        canvas.drawLine(100+(((getWidth()-100)/9)*8), getHeight() - 125, 100+(((getWidth()-100)/9)*8), getHeight() - 75, p1);
        canvas.drawLine(100+(((getWidth()-100)/9)*9), getHeight() - 125, 100+(((getWidth()-100)/9)*9), getHeight() - 75, p1);

        // y-axis increments
        canvas.drawLine(75, ((getHeight()-100)/10)*0, 125, ((getHeight()-100)/10)*0, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*1, 125, ((getHeight()-100)/10)*1, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*2, 125, ((getHeight()-100)/10)*2, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*3, 125, ((getHeight()-100)/10)*3, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*4, 125, ((getHeight()-100)/10)*4, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*5, 125, ((getHeight()-100)/10)*5, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*6, 125, ((getHeight()-100)/10)*6, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*7, 125, ((getHeight() - 100) / 10) * 7, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*8, 125, ((getHeight() - 100) / 10) * 8, p1);
        canvas.drawLine(75, ((getHeight()-100)/10)*9, 125, ((getHeight()-100)/10)*9, p1);

        canvas.drawText("Time (sec)",getWidth()/2, getHeight()-10,p1);

        if (type != null) {
            if (type.equals("Accelerometer")) {
                canvas.drawText("10", 25, (((getHeight()-100)/10)*8)+10, p1);
                canvas.drawText("20", 25, (((getHeight()-100)/10)*6)+10, p1);
                canvas.drawText("30", 25, (((getHeight()-100)/10)*4)+10, p1);
                canvas.drawText("40", 25, (((getHeight()-100)/10)*2)+10, p1);

                canvas.drawText("" + (start+2), 100 + (((getWidth() - 100) / 9) * 2)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+4), 100 + (((getWidth() - 100) / 9) * 4)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+6), 100 + (((getWidth() - 100) / 9) * 6)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+8), 100 + (((getWidth() - 100) / 9) * 8)-10,getHeight()-40, p1);

                for (int i = 0; i < l.size(); i++) {
                    float sum, mean, dsum, dmean, sd;
                    float sum2, mean2, dsum2, dmean2, sd2;
                    sum = 0; mean = 0; dsum = 0; dmean = 0; sd = 0;
                    sum2 = 0; mean2 = 0; dsum2 = 0; dmean2 = 0; sd2 = 0;
                    if (l.size() > 10) {

                    } else {
                        for (int j = i; j >= 0; j--) {
                            sum += l.get(j);
                        }
                        mean = sum / (i + 1);
                        for (int k = i; k >= 0; k--) {
                            dsum += Math.pow(l.get(k) - mean,2);
                        }
                        dmean = dsum / (i + 1);
                        sd = (float)Math.sqrt(dmean);

                        for (int j = i-1; j >= 0; j--) {
                            sum2 += l.get(j);
                        }
                        mean2 = sum2 / (i);
                        for (int k = i-1; k >= 0; k--) {
                            dsum2 += Math.pow(l.get(k) - mean2,2);
                        }
                        dmean2 = dsum2 / (i);
                        sd2 = (float)Math.sqrt(dmean2);
                    }

                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/50)*mean), 10, p3);
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/50)*sd), 10, p4);
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/50)*l.get(i)), 10, p2);
                    if (i > 0) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*(i-1)),(getHeight()-100)-(((getHeight()-100)/50)*mean2),100+(((getWidth()-100)/9)*(i)),(getHeight()-100)-(((getHeight()-100)/50)*mean),p3);
                        canvas.drawLine(100+(((getWidth()-100)/9)*(i-1)),(getHeight()-100)-(((getHeight()-100)/50)*sd2),100+(((getWidth()-100)/9)*(i)),(getHeight()-100)-(((getHeight()-100)/50)*sd),p4);
                    }
                    if (i < l.size() - 1) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/50)*l.get(i)),100+(((getWidth()-100)/9)*(i+1)),(getHeight()-100)-(((getHeight()-100)/50)*l.get(i+1)),p2);
                    }

                    //Log.v("DOMINNO","Float value stored is " + l.get(i));


                }
            } else if (type.equals("Light")) {
                canvas.drawText("100", 25, (((getHeight()-100)/10)*8)+10, p1);
                canvas.drawText("200", 25, (((getHeight()-100)/10)*6)+10, p1);
                canvas.drawText("300", 25, (((getHeight()-100)/10)*4)+10, p1);
                canvas.drawText("400", 25, (((getHeight()-100)/10)*2)+10, p1);

                canvas.drawText("" + (start+2), 100 + (((getWidth() - 100) / 9) * 2)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+4), 100 + (((getWidth() - 100) / 9) * 4)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+6), 100 + (((getWidth() - 100) / 9) * 6)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+8), 100 + (((getWidth() - 100) / 9) * 8)-10,getHeight()-40, p1);

                for (int i = 0; i < l.size(); i++) {
                    float sum, mean, dsum, dmean, sd;
                    float sum2, mean2, dsum2, dmean2, sd2;
                    sum = 0; mean = 0; dsum = 0; dmean = 0; sd = 0;
                    sum2 = 0; mean2 = 0; dsum2 = 0; dmean2 = 0; sd2 = 0;
                    if (l.size() > 10) {

                    } else {
                        for (int j = i; j >= 0; j--) {
                            sum += l.get(j);
                        }
                        mean = sum / (i + 1);
                        for (int k = i; k >= 0; k--) {
                            dsum += Math.pow(l.get(k) - mean,2);
                        }
                        dmean = dsum / (i + 1);
                        sd = (float)Math.sqrt(dmean);

                        for (int j = i-1; j >= 0; j--) {
                            sum2 += l.get(j);
                        }
                        mean2 = sum2 / (i);
                        for (int k = i-1; k >= 0; k--) {
                            dsum2 += Math.pow(l.get(k) - mean2,2);
                        }
                        dmean2 = dsum2 / (i);
                        sd2 = (float)Math.sqrt(dmean2);
                    }

                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/500)*mean), 10, p3);
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/500)*sd), 10, p4);
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/500)*l.get(i)), 10, p2);
                    if (i > 0) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*(i-1)),(getHeight()-100)-(((getHeight()-100)/500)*mean2),100+(((getWidth()-100)/9)*(i)),(getHeight()-100)-(((getHeight()-100)/500)*mean),p3);
                        canvas.drawLine(100+(((getWidth()-100)/9)*(i-1)),(getHeight()-100)-(((getHeight()-100)/500)*sd2),100+(((getWidth()-100)/9)*(i)),(getHeight()-100)-(((getHeight()-100)/500)*sd),p4);
                    }
                    if (i < l.size() - 1) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/500)*l.get(i)),100+(((getWidth()-100)/9)*(i+1)),(getHeight()-100)-(((getHeight()-100)/500)*l.get(i+1)),p2);
                    }
                    //Log.v("DOMINNO","Float value stored is " + l.get(i));
                }
            } else if (type.equals("Gravity")) {
                canvas.drawText("2", 25, (((getHeight()-100)/10)*8)+10, p1);
                canvas.drawText("4", 25, (((getHeight()-100)/10)*6)+10, p1);
                canvas.drawText("6", 25, (((getHeight()-100)/10)*4)+10, p1);
                canvas.drawText("8", 25, (((getHeight()-100)/10)*2)+10, p1);

                canvas.drawText("" + (start+2), 100 + (((getWidth() - 100) / 9) * 2)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+4), 100 + (((getWidth() - 100) / 9) * 4)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+6), 100 + (((getWidth() - 100) / 9) * 6)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+8), 100 + (((getWidth() - 100) / 9) * 8)-10,getHeight()-40, p1);

                for (int i = 0; i < l.size(); i++) {
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/10)*l.get(i)), 10, p2);
                    if (i < l.size() - 1) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/10)*l.get(i)),100+(((getWidth()-100)/9)*(i+1)),(getHeight()-100)-(((getHeight()-100)/10)*l.get(i+1)),p2);
                    }
                    //Log.v("DOMINNO","Float value stored is " + l.get(i));
                }
            } else if (type.equals("Gyroscope")) {
                canvas.drawText("0.1", 25, (((getHeight()-100)/10)*8)+10, p1);
                canvas.drawText("0.2", 25, (((getHeight()-100)/10)*6)+10, p1);
                canvas.drawText("0.3", 25, (((getHeight()-100)/10)*4)+10, p1);
                canvas.drawText("0.4", 25, (((getHeight()-100)/10)*2)+10, p1);

                canvas.drawText("" + (start+2), 100 + (((getWidth() - 100) / 9) * 2)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+4), 100 + (((getWidth() - 100) / 9) * 4)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+6), 100 + (((getWidth() - 100) / 9) * 6)-10,getHeight()-40, p1);
                canvas.drawText("" + (start+8), 100 + (((getWidth() - 100) / 9) * 8)-10,getHeight()-40, p1);

                for (int i = 0; i < l.size(); i++) {
                    canvas.drawCircle(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/0.5f)*l.get(i)), 10, p2);
                    if (i < l.size() - 1) {
                        canvas.drawLine(100+(((getWidth()-100)/9)*i),(getHeight()-100)-(((getHeight()-100)/0.5f)*l.get(i)),100+(((getWidth()-100)/9)*(i+1)),(getHeight()-100)-(((getHeight()-100)/0.5f)*l.get(i+1)),p2);
                    }
                    //Log.v("DOMINNO","Float value stored is " + l.get(i));
                }
            }
        }


    }

    public void clearList() {
        l.clear();
    }

    public void addPoint(float x) {
        if (l.size() >= 10) {
            l.remove(0);
            l.add(x);
            start++;
        } else {
            l.add(x);
        }
        //Log.v("DOMINNO", "ArrayList.size() = " + l.size());
    }

    public void setType(String s) {
        type = s;
    }
}
