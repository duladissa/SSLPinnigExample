package com.litedreamz.sslpinnigexample.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.litedreamz.sslpinnigexample.helper.HttpsRequestExecutorTask;
import com.litedreamz.sslpinnigexample.R;

public class MainActivity extends Activity {

    private static final char[] STORE_PASS = new char[]{'t', 'e', 's', 't', 'i', 'n','g'};

	private Button btnExecuteHttps;
    public static TextView tvResponse;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        intUI();
	}

    private void intUI(){
        btnExecuteHttps = (Button)findViewById(R.id.btn_executehttps);
        assert(null != btnExecuteHttps);
        btnExecuteHttps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeHttpsRequest();
            }
        });

        tvResponse = (TextView)findViewById(R.id.tv_response);

    }

	public void executeHttpsRequest() {
        tvResponse.setText(getString(R.string.string_response));
        new HttpsRequestExecutorTask(getResources(),R.raw.sslpinningexample,STORE_PASS).execute();
	}
}