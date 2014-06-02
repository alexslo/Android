package com.camsoft.CameraBenchmark;

/**
 * Created by alex on 26.02.14.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GadgetActivity extends Activity
{
    private TextView BackCamPointsText, FrontCamPointsText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget_test);

        BackCamPointsText = (TextView)findViewById(R.id.value_back_camera);
        FrontCamPointsText = (TextView)findViewById(R.id.value_front_camera);
        Button StartDynamicTButton = (Button) findViewById(R.id.button_dynamic);
        Button StartStaticTButton = (Button) findViewById(R.id.button_static);

        StartStaticTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestModel mTestModel = new TestModel();
                mTestModel.RunStaticTests(1);
                BackCamPointsText.setText(mTestModel.GetFinalPoints());
                FrontCamPointsText.setText(mTestModel.GetFinalPointsLog());
            }
        });
    }
}
