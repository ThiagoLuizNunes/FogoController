package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//public class MenuActivity extends AppCompatActivity implements MyFragment.SendData {
public class MenuActivity extends AppCompatActivity {

    private ArrayList<Computer> myArrayComputerSelect;
    private MyAdapterMenu menuAdapter;
    private ListView listViewMenu;
    private String IP, MAC;
    private Button buttonSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonSelect = (Button) findViewById(R.id.selectButton);

        /*Receiving String IP from MainActivity*/
        IP = (String) getIntent().getSerializableExtra("IP");
        /*Receiving String MAC addres MainActivity*/
        MAC = (String) getIntent().getSerializableExtra("MAC");
        /*Receiving serializable ArrayDevices from MainActivity*/
        menuAdapter = new MyAdapterMenu(this, (ArrayList<Computer>) getIntent().getSerializableExtra("arrayMachines"));
        myArrayComputerSelect = new ArrayList<>();

        /*Populate ListView with data comes ArrayDevices*/
        listViewMenu = (ListView) findViewById(R.id.myListMenu);
        listViewMenu.setAdapter(menuAdapter);
    }

    public void onClickSelect(View view) {

        myArrayComputerSelect = menuAdapter.getArrayDevices();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < myArrayComputerSelect.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("ip", myArrayComputerSelect.get(i).getIp());
                object.put("name", myArrayComputerSelect.get(i).getName());
                object.put("mac", myArrayComputerSelect.get(i).getMac());
                object.put("magic_id", myArrayComputerSelect.get(i).getMagic_id());
                object.put("checked", myArrayComputerSelect.get(i).isChecked());
                jsonArray.put(object);
                Log.e("JSONArray: ", object.toString());
            }
            //jsonObject.put("magic_id", JSONParser.magic_ID);
            jsonObject.put("fogo_machines", jsonArray);
            Log.e("JSON FINAL: ", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*Send JSON via POST requisition*/
        JSONParserMenu jsonParserMenu = (JSONParserMenu) new JSONParserMenu(jsonObject).execute(IP);
        try {
            if (jsonParserMenu.get() != null) {

                ArrayList<Computer> fogo_machines = new ArrayList<>();
                try {
                    JSONObject object = (JSONObject) new JSONTokener(jsonParserMenu.get()).nextValue();
                    JSONArray machines = object.getJSONArray("fogo_machines");
                    Gson gson = new Gson();
                    for (int i = 0; i < machines.length(); i++) {
                        JSONObject json = machines.getJSONObject(i);
                        Computer computer = gson.fromJson(json.toString(), Computer.class);
                        fogo_machines.add(computer);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Erro: ", "JSON MENU");
                }
                Intent intent = new Intent(getApplicationContext(), ControllerActivity.class);
                intent.putExtra("dataMenu", fogo_machines);
                intent.putExtra("IP", IP);
                startActivity(intent);
                buttonSelect.setEnabled(false);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Falha ao conectar-se com servidor";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        } catch (InterruptedException e) {
            Context context = getApplicationContext();
            CharSequence text = "Falha ao conectar-se com servidor";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Context context = getApplicationContext();
            CharSequence text = "Falha ao conectar-se com servidor";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        buttonSelect.setEnabled(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        buttonSelect.setEnabled(true);
        super.onStop();
    }
}

