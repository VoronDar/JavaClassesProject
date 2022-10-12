package com.lya_cacoi.lab8;

import com.lya_cacoi.lab7.HtmlParser;
import com.lya_cacoi.lab7.HttpRequestMeta;
import com.lya_cacoi.lab7.SocketClient;
import com.lya_cacoi.lab7.URLDepthPair;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

/**
 * single web-scanner to process one url
 */
public class CrawlerUnit {

    private final HttpRequestMeta requestMeta;

    public CrawlerUnit(HttpRequestMeta requestMeta) {
        this.requestMeta = requestMeta;
    }

    /**
     * connect to given url,
     * add founded urls to linksToSearch,
     * add given url to viewedLinks,
     * can process 301 moved permanently error
     *
     * @throws MalformedURLException for invalid urls
     */
    public void processSinglePath(
            final URLDepthPair currentPair,
            final LinkedList<URLDepthPair> linksToSearch,
            final LinkedList<URLDepthPair> viewedLinks
    ) throws MalformedURLException {
        SocketClient socketClient = new SocketClient();
        socketClient.requestSingleHttp(currentPair.getPath(), currentPair.getHost(), requestMeta, new SocketClient.SocketCallback() {
            @Override
            public void onSuccess(BufferedReader in) {
                try {
                    // scan for 301 error. scan links in document if the document is correct
                    String redirectLocation = HtmlParser.scanForRedirect(currentPair.getUrl(), in);
                    if (redirectLocation != null) {
                        currentPair.setUrl(redirectLocation);
                        linksToSearch.add(currentPair);
                    } else {
                        for (String url : HtmlParser.scanHttpLinks(in)) {
                            linksToSearch.add(new URLDepthPair(url, currentPair.loadingDeps + 1));
                        }
                        viewedLinks.add(currentPair);
                    }
                } catch (IOException e) {
                    currentPair.setScanningException(e);
                    viewedLinks.add(currentPair);
                }
            }

            @Override
            public void onError(Exception e) {
                currentPair.setScanningException(e);
                viewedLinks.add(currentPair);
            }
        });
    }
}
