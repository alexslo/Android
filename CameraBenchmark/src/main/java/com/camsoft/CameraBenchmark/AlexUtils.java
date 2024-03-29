package com.camsoft.CameraBenchmark;

/**
 * Created by alex on 15.03.14.
 */

import android.net.ConnectivityManager;
import android.util.Log;
import android.text.format.Time;

import java.util.List;

import com.parser.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class AlexUtils {
    public static String ErrorLog;

    public static boolean isOnline(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }

    public void waitTimer(long _time_ms) {
        try {
            Thread.sleep(_time_ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Integer sendToServer(List<NameValuePair> _params, String _server_URL)
    {
        JSONParser jsonParser = new JSONParser();

        JSONObject json = jsonParser.makeHttpRequest(_server_URL, "POST", _params);
        //Log.d("Create Response", json.toString());

        try {
            int success = json.getInt("success");

            if (success == 1) {
                return 1;
            }
            else
            {
                ErrorLog += "При отправке данных произошла ошибка (сервер получил неправильные данные), проверьте соединение с интернетом и";
                return 0;
            }
        } catch (JSONException e) {
            ErrorLog += "При отправке данных произошла ошибка (сервер не ответил)"
                    + "/n" +e.toString();
            e.printStackTrace();
            return 0;
        }

    }

    public List<NameValuePair> getFromServer(String _server_URL, String _TableID)
    {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList <NameValuePair>();
        //params.add(new BasicNameValuePair("pid", _TableID));


        try {
            // Список параметров

            // получаем продукт по HTTP запросу
            JSONObject json = jsonParser.makeHttpRequest(_server_URL, "GET", params);


            //Log.d("Single Product Details", json.toString());
            int success = json.getInt("success");
            /*
            if (success == 1) {
                // Успешно получинна детальная информация о продукте
                JSONArray productObj = json.getJSONArray("products"); //???

                // получаем первый обьект с JSON Array
                JSONObject product = productObj.getJSONObject(0);

                JSONArray GraphNames = product.names();
                for (int i = 0; i < GraphNames.length(); i++)
                {
                    String GraphName = GraphNames.getString(i);
                    _params.add(new BasicNameValuePair(GraphName, product.getString(GraphName)));

                }


            }

        */
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;



    }

    public String getTimeStamp()
    {
        Time now = new Time();
        now.setToNow();
        return now.toString();

    }


}
