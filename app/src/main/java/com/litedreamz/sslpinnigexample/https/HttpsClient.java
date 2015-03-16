package com.litedreamz.sslpinnigexample.https;

/**
 * Created by Dulan Dissanayake on 3/16/2015.
 */
import android.util.Log;
import com.litedreamz.sslpinnigexample.util.CustomSSLSocketFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * The HttpsClient
 */
public class HttpsClient extends DefaultHttpClient {

    private InputStream stream;
    private char[] password;

    private int port = 443;
    private String httpsSchema = "https";

    /**
     * Construct a new HttpsClient with default https schema and port
     *
     */
    public HttpsClient(InputStream stream,char[] password) {
        super();
        this.stream = stream;
        this.password = password;
    }

    /**
     * Construct a new HttpsClient with Url if Url port is not default port "443"
     *
     */
    public HttpsClient(String url) {
        super();
        URL httpUrl;
        try {
            httpUrl = new URL(url);
            httpsSchema = httpUrl.getProtocol();
            if (httpUrl.getPort() != -1) {
                port = httpUrl.getPort();
            }
        } catch (MalformedURLException e) {
            Log.e("HttpsClient", e.getMessage());
        }

    }

    /**
     * Create client connection manager
     *
     * @return The manager
     */
    @Override
    protected ClientConnectionManager createClientConnectionManager(){

        Log.d("HttpsClient", "createClientConnectionManager");
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        try {
            registry.register(new Scheme(httpsSchema,
                    new CustomSSLSocketFactory(stream,password), port));
        } catch (Exception except) {
            Log.e(this.getClass().getSimpleName(), except.toString(),
                    except);
        }
        return new SingleClientConnManager(getParams(), registry);
    }
}