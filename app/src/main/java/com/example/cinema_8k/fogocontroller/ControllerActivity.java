package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ControllerActivity extends AppCompatActivity {

    private ToggleButton togglePTP, toggleBuffer, toggleDecoder;
    private ArrayList<Computer> dataComputers;
    private String IP;
    private LinearLayout layoutGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        //gridView = (GridView) findViewById(R.id.layout_pcs);
        layoutGrid = (LinearLayout) findViewById(R.id.layout_pcs);

        /*GET IP from MenuActivy from MainActivity*/
        IP = (String) getIntent().getSerializableExtra("IP");

        /*GET ArrayComputerSelect from MenuActivity*/
        dataComputers = (ArrayList<Computer>) getIntent().getSerializableExtra("dataMenu");

        /*Removing the objects not selected, if checked FALSE remove it*/
        for (int i = 0; i < dataComputers.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(5, 0, 5, 0);

            LinearLayout layoutImage = new LinearLayout(this);
            layoutImage.setOrientation(LinearLayout.VERTICAL);
            layoutImage.setGravity(Gravity.CENTER_HORIZONTAL);

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(150, 150);
            imageView.setImageResource(R.drawable.fogo_icon);
            imageView.setLayoutParams(imageParams);
            layoutImage.addView(imageView);
            linearLayout.addView(layoutImage);

            LinearLayout layoutText = new LinearLayout(this);
            layoutText.setOrientation(LinearLayout.VERTICAL);
            layoutText.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textView = new TextView(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(textParams);
            textView.setTextSize(10);
            textView.setText(dataComputers.get(i).getName());
            layoutText.addView(textView);
            linearLayout.addView(layoutText);

            layoutGrid.addView(linearLayout);
        }

        //startButton = (Button) findViewById(R.id.startButton);
        togglePTP = (ToggleButton) findViewById(R.id.togglePTP);

        togglePTP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePTP.setEnabled(false);
                if (isChecked) {
                    // The toggle is enabled
                    String status = "on";
                    String method = "ptp";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "PTP Ativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            togglePTP.setChecked(false);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                } else {
                    // The toggle is disabled
                    String status = "off";
                    String method = "ptp";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "PTP Desativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            togglePTP.setChecked(true);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                togglePTP.setEnabled(true);
            }
        });
        toggleBuffer = (ToggleButton) findViewById(R.id.toggleBuffer);
        toggleBuffer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleBuffer.setEnabled(false);
                if (isChecked) {
                    // The toggle is enabled
                    String status = "on";
                    String method = "buffer";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "Increase Buffer Ativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            toggleBuffer.setChecked(false);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                } else {
                    // The toggle is disabled
                    String status = "off";
                    String method = "buffer";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "Increase Buffer desativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            toggleBuffer.setChecked(true);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                toggleBuffer.setEnabled(true);
            }
        });
        toggleDecoder = (ToggleButton) findViewById(R.id.toggleDecoder);
        toggleDecoder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleDecoder.setEnabled(false);
                if (isChecked) {
                    // The toggle is enabled
                    String status = "on";
                    String method = "decoder";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "Decoder Ativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            toggleDecoder.setChecked(false);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                } else {
                    // The toggle is disabled
                    String status = "off";
                    String method = "decoder";
                    JSONParserController controller = (JSONParserController) new JSONParserController(status, method).execute(IP);
                    try {
                        if (controller.get() != null){
                            Context context = getApplicationContext();
                            CharSequence text = "Decoder Desativado";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "Falha ao conectar-se com servidor";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            toggleDecoder.setChecked(true);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
                toggleDecoder.setEnabled(true);
            }
        });
    }
}