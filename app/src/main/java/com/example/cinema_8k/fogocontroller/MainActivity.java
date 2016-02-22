package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonIP, buttonWIFI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edIP);
        buttonIP = (Button) findViewById(R.id.buttonIP);
        buttonWIFI = (Button) findViewById(R.id.buttonWIFI);
    }

    public void onClickIP(View view) {
        buttonIP.setEnabled(true);
        if (checkedConnection("")) {

            String ip = editText.getText().toString();

            /*GET macAdress ANDROID*/
            WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();

            /*Creating instance JSONParser Class to get OjectJSON*/
            JSONParser jsonParser = (JSONParser) new JSONParser(address).execute(ip);
            try {
                if (jsonParser.get() != null) {
                    ArrayList<Computer> machines = new ArrayList<>();
                    try {
                        /*Get result of the method doInBackground of the JSONParser and to catch at a String*/
                        String result = jsonParser.get();
                        Log.e("JSON", result.toString());
                        Log.e("TO AQUI"," adasdasd");
                        /*Transform String in a JSON Object*/
                        JSONObject object = (JSONObject) new JSONTokener(result).nextValue();
                        /*Divide the Object JSON in a JSONArray by types*/
                        JSONArray names = object.getJSONArray("fogo_machines");
                        Log.e("SIZE", String.valueOf(names.length()));

                        Gson gson = new Gson();
                        for (int x = 0; x < names.length(); x++) {
                            JSONObject jsonObject = names.getJSONObject(x);
                            Computer computer = gson.fromJson(jsonObject.toString(), Computer.class);
                            machines.add(computer);
                            Log.e("Device ", computer.getName() + " " + computer.getIp() + " " + computer.isChecked());
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("OnClick: ", "passei do Json");
                        /*Passing MainActivity to Menu Activity and sending data*/
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("arrayMachines", machines);
                    intent.putExtra("IP", ip);
                    intent.putExtra("MAC", address);
                    startActivity(intent);
                    buttonIP.setEnabled(false);

                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Falha ao conectar-se com servidor";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    buttonIP.setEnabled(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickWIFI(View view) {
        if (checkedConnection("wifi")){
        }

    }

    public boolean checkedConnection(String type) {
        if (type == "wifi") {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                Context context = getApplicationContext();
                CharSequence text = "Wifi  conectado!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Wifi não conectado!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
        } else {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return true;
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Sem conexão com internet!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return false;
            }
        }
    }

    @Override
    protected void onPause() {
        buttonIP.setEnabled(true);
        super.onPause();
    }
}