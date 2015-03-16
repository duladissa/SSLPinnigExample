package com.litedreamz.sslpinnigexample.util;

/**
 * Created by Dulan Dissanayake on 3/8/2015.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;



public class CustomSSLSocketFactory extends SSLSocketFactory {

    private static final String BOUNCY_CASTLE = "BKS";
	SSLContext sslContext = SSLContext.getInstance("TLS");

	public CustomSSLSocketFactory(InputStream resourceStream,char[] password) throws NoSuchAlgorithmException,
            KeyManagementException, KeyStoreException,
            UnrecoverableKeyException, IOException, CertificateException {
		
		super(null);

        KeyStore keyStore = KeyStore.getInstance(BOUNCY_CASTLE);
        keyStore.load(resourceStream, password);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        TrustManager[] trustManagers = {new SSLPinningTrustManager(keyStore)};

        kmf.init(keyStore, password);

		sslContext.init(kmf.getKeyManagers(), trustManagers,
				new SecureRandom());

	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}

}
