package com.lya_cacoi.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class SocketClient {
    private Socket mySocket;

    /**
     * open socket with port=80 (for http, not https)
     * */
    public void openSocketForHttp(String host) {
        try {
            mySocket = new Socket(host, 80);
            mySocket.setSoTimeout(5000);
        } catch (IOException e) {
            closeSocket();
        }
    }

    public void closeSocket() {
        try {
            if (mySocket != null) {
                mySocket.close();
            }
        } catch (IOException e) {
            System.out.println("got exception " + e + " when closing socket");
        }
    }

    /**
     * open socket, request http, close socket.
     * The response is passed through callback
     * */
    public void requestSingleHttp(String path, String host, SocketCallback callback) {
        try {
            openSocketForHttp(host);
            if (mySocket.isConnected()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                HttpRequestHelper.requestHttp1_1(mySocket.getOutputStream(), path, host);
                callback.onSuccess(in);
            } else {
                callback.onError(new SocketException("unable to connect to socket"));
            }
        } catch (Exception e) {
            callback.onError(e);
        } finally {
            closeSocket();
        }
    }

    /**
     * callback for getting the result of request sending with socket
     * */
    interface SocketCallback {
        void onSuccess(BufferedReader in);
        void onError(Exception e);
    }
}
