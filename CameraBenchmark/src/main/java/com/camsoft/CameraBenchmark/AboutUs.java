package com.camsoft.CameraBenchmark;

/**
 * Created by alex on 05.03.14.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AboutUs extends Activity
{
    private EditText EditTextName, EditTextMail, EditTextMessage;
    private Button SendButton;
    private ProgressDialog pDialog;

    AlexUtils mUtils = new AlexUtils();

    String _server_url = "http://vfokuce.ru/CamBanch_errors.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_camera_benchmark);

        EditTextName = (EditText)findViewById(R.id.editTextName);
        EditTextMail = (EditText)findViewById(R.id.editTextMail);
        EditTextMessage = (EditText)findViewById(R.id.editTextMessage);

        SendButton = (Button)findViewById(R.id.sendButton);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlexUtils.isOnline(AboutUs.this)) {
                    new CreateNewFeedBack().execute();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AboutUs.this);
                    builder.setTitle("ОШИБКА!")
                            .setMessage("Нет доступа в интернет!")
                            .setIcon(R.drawable.ic_launcher)
                            .setCancelable(false)
                            .setNegativeButton("ОК, проверю",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });



    }

    class CreateNewFeedBack extends AsyncTask<String, String, String> {
        /**
         * Перед согданием в фоновом потоке показываем прогресс диалог
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AboutUs.this);
            pDialog.setMessage(EditTextName.getText().toString() + "! \n" + "Идет оправка данных...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected String doInBackground(String... args) {
            String Model = android.os.Build.MODEL + "; " + Build.DEVICE;
            String MessageErrors = EditTextName.getText().toString() +'\n' + EditTextMail.getText().toString() + '\n' + EditTextMessage.getText().toString();
            String TimeStamp = mUtils.getTimeStamp();

            // Заполняем параметры
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("model", Model));
            params.add(new BasicNameValuePair("message_errors", MessageErrors));
            params.add(new BasicNameValuePair("session_date", TimeStamp));

            mUtils.sendToServer(params, _server_url);




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
