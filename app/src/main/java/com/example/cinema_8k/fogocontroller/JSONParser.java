package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Milton on 21/01/2016.
 */
public class JSONParser extends AsyncTask<String, String, String> {

    private String macAdress;
    public static String magic_ID = new String();
    public JSONParser(String mac) {
        this.macAdress = mac;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connectionGET = null;
        HttpURLConnection connectionPOST = null;
        BufferedReader reader = null;
        DataOutputStream postData;
            /*POST JSON*/
        URL urlPOST = null;

        JSONObject jsonPOST = new JSONObject();
        String mac = "mac";
        try {
            jsonPOST.put(mac,macAdress);
            urlPOST = new URL(params[0] + "/fogo_controllers/new");
            connectionPOST = (HttpURLConnection) urlPOST.openConnection();
            connectionPOST.setRequestMethod("POST");
            connectionPOST.setDoOutput(true);
            connectionPOST.setRequestProperty("Accept-Encoding", "identity");
            connectionPOST.setRequestProperty("Content-Type", "application/json");

            postData = new DataOutputStream(connectionPOST.getOutputStream());
            postData.writeBytes(jsonPOST.toString());
            postData.flush();
            postData.close();

            DataInputStream inputStream = new DataInputStream(connectionPOST.getInputStream());
            StringBuffer buffer = new StringBuffer();
            String linePost;
            while ((linePost = inputStream.readLine()) != null){
                buffer.append(linePost);
            }
            JSONObject obj = new JSONObject(buffer.toString());
            magic_ID = obj.getString("magic_id");
            Log.e("MAGIC: ", magic_ID);

            int resposeCode = connectionPOST.getResponseCode();
            Log.e("RESONSE CODE: ", String.valueOf(resposeCode));

        } catch (MalformedURLException e) {
            Log.e("CATCH", "Erro POST URL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("CATCH", "Erro POST URL Connection");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (connectionPOST != null) {
                connectionPOST.disconnect();
            }
        }

        try {
            /*GET JSON */
            URL urlGET = new URL(params[0] + "/fogo_machines");
            connectionGET = (HttpURLConnection) urlGET.openConnection();
            connectionGET.connect();
            InputStream stream = connectionGET.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            Log.e("CATCH", "Erro GET URL");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("CATCH", "Erro GET URL Connection");
            e.printStackTrace();
        } finally {
            if ((connectionGET != null)) {

                connectionGET.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Log.e("JSON", result.toString());
    }
}
