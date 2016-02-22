package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CINEMA-8K on 25/01/2016.
 */
public class JSONParserMenu extends AsyncTask<String, String, String> {

    private JSONObject jsonObject = new JSONObject();

    public JSONParserMenu(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection menuPOST = null;
        DataOutputStream postDataMenu;
            /*POST JSON*/
        URL urlPOST = null;
        JSONObject obj = null;

        try {
            urlPOST = new URL(params[0] + "/fogo_controllers/"+JSONParser.magic_ID+"/machines/new");
            menuPOST = (HttpURLConnection) urlPOST.openConnection();
            menuPOST.setRequestMethod("POST");
            menuPOST.setDoOutput(true);
            menuPOST.setRequestProperty("Accept-Encoding", "identity");
            menuPOST.setRequestProperty("Content-Type", "application/json");
            Log.e("PASSEI: ", "POST MENU");

            postDataMenu = new DataOutputStream(menuPOST.getOutputStream());
            postDataMenu.writeBytes(jsonObject.toString());
            postDataMenu.flush();
            postDataMenu.close();
            Log.e("PASSEI: ", "POST finished");


            DataInputStream inputStream = new DataInputStream(menuPOST.getInputStream());
            StringBuffer buffer = new StringBuffer();
            String linePost;
            while ((linePost = inputStream.readLine()) != null){
                buffer.append(linePost);
            }
            obj = new JSONObject(buffer.toString());
            Log.e("JSON RESPONSE", obj.toString());

            int resposeCode = menuPOST.getResponseCode();
            Log.e("RESONSE CODE: ", String.valueOf(resposeCode));
        } catch (MalformedURLException e) {
            Log.e("CATCH", "Erro POST URL");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("CATCH", "Erro POST URL Connection");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (menuPOST != null) {
                menuPOST.disconnect();
            }
        }
        return String.valueOf(obj);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
