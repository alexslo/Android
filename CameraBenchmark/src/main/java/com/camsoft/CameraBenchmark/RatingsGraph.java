package com.camsoft.CameraBenchmark;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.LineGraphView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.LinearLayout;

import android.app.Activity;
import android.os.Bundle;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 30.03.14.
 */
public class RatingsGraph extends Activity{

    private ProgressDialog pDialog;
    AlexUtils mUtils = new AlexUtils();
    List<NameValuePair> CloudDataBuf;
    String Server_URL = "http://vfokuce.ru/CamBanch_sum_read_id_V_1_00.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activite_ratings_graph);

        mUtils.getFromServer(Server_URL,"2");

        // Линейный график
        GraphViewSeries exampleSeries = new GraphViewSeries(
                new GraphViewData[] { new GraphViewData(1, 300.0d),
                                      new GraphViewData(2, 300.0d)  });

        GraphView graphView = new BarGraphView(this, "Рейтинг устройств:");
        graphView.addSeries(exampleSeries);
        graphView.setHorizontalLabels(new String[] {"Samsung", "Test_BD"});
        graphView.setManualYAxisBounds(900,0);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ratingsL);
        layout.addView(graphView);



    }


    class getCloudData extends AsyncTask<String, String, String> {
        /**
         * Перед согданием в фоновом потоке показываем прогресс диалог
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RatingsGraph.this);
            pDialog.setMessage("Получаем печеньки с сервера...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected String doInBackground(String... args) {
            CloudDataBuf = mUtils.getFromServer(Server_URL,"2");
            String TestPoints = CloudDataBuf.get(2).getValue();

            return null;
        }

        /**
         * После оконачния скрываем прогресс диалог
         **/
        protected void onPostExecute(String file_url) {
            //waitTimer(2000);
            //MainText.setText(TestLog);
            pDialog.dismiss();
        }
    }
}
