package com.lya_cacoi.lab8;

import com.lya_cacoi.lab7.URLDepthPair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Resource with urls required to be scanned and already scannedUrls
 * */
public class URLPool {
    private final int maxDepth;
    /** amount of workers waiting for urls */
    private int sleepWorkersCount;
    /** max workers count */
    private final int threadsCount;

    private final Set<String> seenUrls = new HashSet<>();

    private int counter = 0;

    private final LinkedList<URLDepthPair> linksToSearch = new LinkedList<>();
    private final LinkedList<URLDepthPair> viewedPairs = new LinkedList<>();

    public URLPool(int maxDepth, int threadsCount) {
        this.maxDepth = maxDepth;
        this.threadsCount = threadsCount;
    }

    /** add url to search collection if it has to be processed or add in result collection */
    public synchronized void putUrlPairToSearch(URLDepthPair pair) {
        if (isUnknownUrl(pair.getUrl())) {
            if (pair.loadingDeps < maxDepth) {
                linksToSearch.add(pair);
                notify();
            } else {
                viewedPairs.add(pair);
            }
        }
    }

    /** add several urls to search collection if it has to be processed or add in result collection */
    public synchronized void putUrlPairsToSearch(List<URLDepthPair> pairs) {
        for (URLDepthPair pair : pairs) {
            if (isUnknownUrl(pair.getUrl())) {
                if (pair.loadingDeps < maxDepth) {
                    linksToSearch.add(pair);
                } else {
                    viewedPairs.add(pair);
                }
            }
        }
        notifyAll();
    }

    /** add several urls to result collection */
    public synchronized void putViewedUrlPairs(List<URLDepthPair> pairs) {
        viewedPairs.addAll(pairs);
    }

    /** get first url required to be processed */
    public synchronized URLDepthPair pollUrlPair() {
        sleepWorkersCount++;
        try {
            while (linksToSearch.isEmpty()) {
                wait();
            }
            counter+=1;
            System.out.println(counter);
        } catch (InterruptedException ignored) {
        }
        sleepWorkersCount--;
        return linksToSearch.removeFirst();
    }

    /** @return false if all urls have been processed */
    public boolean isInProgress() {
        return sleepWorkersCount != threadsCount;
    }

    /**
     * @return all found pairs
     * */
    public List<URLDepthPair> getResult() {
        return viewedPairs;
    }

    /**
     * @return true if given url has been founded
     */
    private boolean isUnknownUrl(final String url) {
        return seenUrls.add(url);
    }
}
