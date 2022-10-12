package com.lya_cacoi.lab7;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * pojo class for pair url-loading depth
 */
public class URLDepthPair {
    private String url;
    public final int loadingDeps;
    private Exception scanningException = null;

    public URLDepthPair(String url, int loadingDeps) {
        this.url = url;
        this.loadingDeps = loadingDeps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLoadingDeps() {
        return loadingDeps;
    }

    public Exception getScanningException() {
        return scanningException;
    }

    public void setScanningException(Exception scanningException) {
        this.scanningException = scanningException;
    }

    public String getHost() throws MalformedURLException {
        return new URL(url).getHost();
    }

    public String getPath() throws MalformedURLException {
        return new URL(url).getPath();
    }

    @Override
    public String toString() {
        if (scanningException != null) {
            return "\nurl='" + url
                    + ", loadingDeps=" + loadingDeps
                    + ", failed to access with exception " + scanningException;
        } else {
            return "\nurl='" + url + ", loadingDeps=" + loadingDeps;
        }
    }
}
