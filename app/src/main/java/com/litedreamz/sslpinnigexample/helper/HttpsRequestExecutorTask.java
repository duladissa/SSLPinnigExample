package com.litedreamz.sslpinnigexample.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.litedreamz.sslpinnigexample.activities.MainActivity;
import com.litedreamz.sslpinnigexample.https.HttpsClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Dulan Dissanayake on 3/8/2015.
 */

public class HttpsRequestExecutorTask extends AsyncTask<Void, Void, String> {
    private InputStream stream;
    private char[] password;

    public HttpsRequestExecutorTask(Resources resources, int certificateRawResource, char[] password){
        this.stream = resources.openRawResource(certificateRawResource);
        this.password = password;
    }

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(Void... params) {

		String result = null;

		try {
			String url = "https://www.google.lk/";
			assert (null != url);

            InputStream inputStream = null;
            try {

                // create HttpClient
                DefaultHttpClient httpclient = new HttpsClient(stream,password);

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpPost(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null){
                    result = convertInputStreamToString(inputStream);
                }else{
                    result = "Did not work! May be error on your internet connection.";
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

		} catch (Exception ex) {
            ex.printStackTrace();

			// Log error
			Log.e("doInBackground", ex.toString());

			// Prepare return value
			result =  ex.getLocalizedMessage();
		}

		return result;
	}

	@Override
	protected void onPostExecute(String result) {

		assert (null != result);
		if (null == result)
			return;

        if(MainActivity.tvResponse != null){
            MainActivity.tvResponse.setText(result);
        }

	}

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
