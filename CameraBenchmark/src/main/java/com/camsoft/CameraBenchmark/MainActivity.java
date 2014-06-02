package com.camsoft.CameraBenchmark;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Build;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.widget.TextView;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import java.util.ArrayList;
import java.util.List;






public class MainActivity extends ActionBarActivity {

    private TextView MainText;
    Button RatingsButton, GoOutButton, RunTests, AboutUsButton;

    private ProgressDialog pDialog;

    CameraModel mCameraModel = new CameraModel();
    AlexUtils mUtils = new AlexUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainText = (TextView)findViewById(R.id.textView);

        RatingsButton=(Button)findViewById(R.id.button_reitings);
        RatingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RatingsGraph.class);
                startActivity(intent);
                /*
                if (AlexUtils.isOnline(MainActivity.this)) {
                    new CreateNewProduct().execute();
                }
                else {
                    MainText.setText("ОШИБКА! Похоже, что ваше устройство не имеет доступа в интернет." +
                            " Пожалуйста подключитесь к сети интернет и повторите попытку отправки данных.");
                }
                */

            }
        });
        GoOutButton=(Button)findViewById(R.id.button_go_out);
        GoOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        RunTests=(Button)findViewById(R.id.button_tests);
        RunTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GadgetActivity.class);
                startActivity(intent);
            }
        });

        AboutUsButton=(Button)findViewById(R.id.about_us);
        AboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class CreateNewProduct extends AsyncTask<String, String, String> {
        /**
         * Перед согданием в фоновом потоке показываем прогресс диалог
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Отправка данных...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected String doInBackground(String... args) {
            String Model = android.os.Build.MODEL + "; " + Build.DEVICE;
            String AndroidVers = Build.VERSION.RELEASE + "; SDK:" + Build.VERSION.SDK_INT;
            String BackCamParam = mCameraModel.getAllCameraParams(1);
            String FrontCamParam = mCameraModel.getAllCameraParams(2);

            // Заполняем параметры
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("model", Model));
            params.add(new BasicNameValuePair("android_version", AndroidVers));
            params.add(new BasicNameValuePair("parameters_back", BackCamParam));
            params.add(new BasicNameValuePair("parameters_front", FrontCamParam));


            mUtils.sendToServer(params, "http://vfokuce.ru/php_script.php");




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
