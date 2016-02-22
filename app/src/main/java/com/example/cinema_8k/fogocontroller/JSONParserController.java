package com.example.cinema_8k.fogocontroller;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CINEMA-8K on 26/01/2016.
 */
public class JSONParserController extends AsyncTask<String, String, String> {

    private String status, method, url;

    public JSONParserController(String status, String method) {
        this.status = status;
        this.method = method;
    }

    @Override
    protected String doInBackground(String... params) {

        if (method == "ptp") {
            URL urlPTP;
            HttpURLConnection connPTP = null;
            JSONObject objPTP;
            try {
                urlPTP = new URL(params[0] + "/fogo_controllers/" + JSONParser.magic_ID + "/run_ptp/" + status);
                Log.e("URL: ", urlPTP.toString());
                connPTP = (HttpURLConnection) urlPTP.openConnection();
                connPTP.connect();

                DataInputStream inputStream = new DataInputStream(connPTP.getInputStream());
                StringBuffer buffer = new StringBuffer();
                String linePost;
                while ((linePost = inputStream.readLine()) != null){
                    buffer.append(linePost);
                }
                objPTP = new JSONObject(buffer.toString());
                Log.e("JSON RESPONSE", objPTP.toString());

            } catch (MalformedURLException e) {
                Log.e("ERRO: ", "PTP URL");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.e("ERRO: ", "PTP URL CONNECTION");
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.e("ERRO:", "ERRO JSON");
                e.printStackTrace();
                return null;
            } finally {
                if (connPTP != null) {
                    connPTP.disconnect();
                }
            }
            return status;
        }
        if (method == "buffer") {
            URL urlBuffer;
            HttpURLConnection connBuffer = null;
            JSONObject objBuffer;
            try {
                urlBuffer = new URL(params[0] + "/fogo_controllers/" + JSONParser.magic_ID + "/increase_buffer/" + status);
                Log.e("URL: ", urlBuffer.toString());
                connBuffer = (HttpURLConnection) urlBuffer.openConnection();
                connBuffer.connect();

                DataInputStream inputStream = new DataInputStream(connBuffer.getInputStream());
                StringBuffer buffer = new StringBuffer();
                String linePost;
                while ((linePost = inputStream.readLine()) != null){
                    buffer.append(linePost);
                }
                objBuffer = new JSONObject(buffer.toString());
                Log.e("JSON RESPONSE", objBuffer.toString());

            } catch (MalformedURLException e) {
                Log.e("ERRO: ", "BUFFER URL");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.e("ERRO: ", "BUFFER URL CONNECTION");
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.e("ERRO:", "ERRO JSON");
                e.printStackTrace();
                return null;
            } finally {
                if (connBuffer != null) {
                    connBuffer.disconnect();
                }
            }
            return status;
        }
        if (method == "decoder") {
            URL urlDecoder;
            HttpURLConnection connDecoder = null;
            JSONObject objDecoder;
            try {
                urlDecoder = new URL(params[0] + "/fogo_controllers/" + JSONParser.magic_ID + "/run_decoder/" + status);
                Log.e("URL: ", urlDecoder.toString());
                connDecoder = (HttpURLConnection) urlDecoder.openConnection();
                connDecoder.connect();

                DataInputStream inputStream = new DataInputStream(connDecoder.getInputStream());
                StringBuffer buffer = new StringBuffer();
                String linePost;
                while ((linePost = inputStream.readLine()) != null){
                    buffer.append(linePost);
                }
                objDecoder = new JSONObject(buffer.toString());
                Log.e("JSON RESPONSE", objDecoder.toString());

            } catch (MalformedURLException e) {
                Log.e("ERRO: ", "DECODER URL");
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.e("ERRO: ", "DECODER URL CONNECTION");
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.e("ERRO:", "ERRO JSON");
                e.printStackTrace();
                return null;
            } finally {
                if (connDecoder != null) {
                    connDecoder.disconnect();
                }
            }
            return status;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
