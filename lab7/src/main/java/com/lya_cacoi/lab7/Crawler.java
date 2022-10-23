package com.lya_cacoi.lab7;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * web-scanner
 */
public class Crawler {

    private final LinkedList<URLDepthPair> linksToSearch = new LinkedList<>();
    private final LinkedList<URLDepthPair> viewedLinks = new LinkedList<>();

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        String url;
        int maxDepth;
        try {
            url = args[0];
            maxDepth = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.printf("unexpected arguments. Expected '[string], [int]', got %s", Arrays.toString(args));
            return;
        }
        System.out.println("found: " + crawler.scan(url, maxDepth));
    }

    /**
     * get found and valid links. Should not be called until scan function.
     */
    public LinkedList<URLDepthPair> getSites() {
        return viewedLinks;
    }

    /**
     * search all links in html document for the url, request these links documents and loop operation
     *
     * @return all found valid links.
     */
    public LinkedList<URLDepthPair> scan(String startUrl, int maxDepth) {
        try {
            linksToSearch.add(new URLDepthPair(startUrl, 0));
            while (!linksToSearch.isEmpty()) {
                URLDepthPair currentPair = linksToSearch.removeFirst();
                if (currentPair.loadingDeps < maxDepth) {
                    processSinglePath(currentPair);
                } else {
                    viewedLinks.add(currentPair);
                }
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("usage: java Crawler " + startUrl + " " + maxDepth + "\n exception: " + e);
        }
        return getSites();
    }

    private void processSinglePath(final URLDepthPair currentPair) throws IOException {
        if (isKnownUrl(currentPair.getUrl())) {
            return;
        }
        SocketClient socketClient = new SocketClient();
        socketClient.requestSingleHttp(currentPair.getPath(), currentPair.getHost(), new SocketClient.SocketCallback() {
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

    /**
     * @return true if the given url has been founded
     */
    private boolean isKnownUrl(final String url) {
        return (isListContainsUrl(linksToSearch, url) || isListContainsUrl(viewedLinks, url));
    }

    private boolean isListContainsUrl(List<URLDepthPair> list, String url) {
        for (URLDepthPair urlPair : list) {
            if (url.equals(urlPair.getUrl())) {
                return true;
            }
        }
        return false;
    }

}