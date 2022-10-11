package com.lya_cacoi.lab7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CrawlerFabric {

    private static final LinkedList<String> urls = new LinkedList<>();
    private static final int THREADS_COUNT = 5;

    public static void main(String[] args) {
        ExecutorService crawlers = Executors.newFixedThreadPool(THREADS_COUNT);

        final ArrayList<Future<List<URLDepthPair>>> futures = new ArrayList<>();

        int maxDepth = Integer.parseInt(args[0]);
        urls.addAll(Arrays.asList(args).subList(1, args.length-1));

        for (int i = 0; i < THREADS_COUNT; i ++) {
            futures.add(crawlers.submit(new CrawlerCallable(maxDepth)));
        }
        crawlers.shutdown();

        try {
            for (Future<List<URLDepthPair>> pairs : futures) {
                System.out.println(pairs.get());
            }
        } catch (Exception ignored) {}
    }

    private static class CrawlerCallable implements Callable<List<URLDepthPair>> {

        private final int maxDepth;
        private final Crawler crawler = new Crawler();

        CrawlerCallable(int maxDepth) {
            this.maxDepth = maxDepth;
        }

        @Override
        public List<URLDepthPair> call() {
            while (!urls.isEmpty()) {
                String url;
                synchronized (urls) {
                    url = urls.removeFirst();
                }
                if (url != null) {
                    crawler.scan(url, maxDepth);
                }
            }
            return crawler.getSites();
        }
    }
}
