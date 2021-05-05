package com.tvd12.mmorpg.stresstest.client;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Statistics extends EzyLoggable {

    private final AtomicLong totalFps;
    private final AtomicLong totalLatency;
    private final AtomicLong totalRequest;
    private final AtomicLong totalResponse;
    private final Map<String, Long> sentTimestampByUser;

    public static int NUMBER_OF_USERS = 300;
    public static int MILLIS_PER_SECOND = 1000;
    public static int MEASUREMENT_INTERVAL_MINUTES = 1;
    public static int EXPECTED_RESPONSES_PER_SECOND = 10;

    private static final Statistics INSTANCE = new Statistics();

    private Statistics() {
        this.totalFps = new AtomicLong();
        this.totalLatency = new AtomicLong();
        this.totalRequest = new AtomicLong();
        this.totalResponse = new AtomicLong();
        this.sentTimestampByUser = new ConcurrentHashMap<>();
    }

    public static Statistics getInstance() {
        return INSTANCE;
    }

    public void start() {
        startPrint();
    }

    private void startPrint() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            long totalRequestLong = totalRequest.get();
            long totalResponseLong = totalResponse.get();
            long totalFpsLong = totalFps.get();
            long totalLatencyLong = totalLatency.get();
            System.err.printf("[TOTAL REQUEST]: %d", totalRequestLong);
            System.err.printf(
                "[AVERAGE LATENCY] Total latency: %d -> Average Latency: %.2f ms%n",
                totalLatencyLong,
                (1.0D * totalLatencyLong) / totalResponseLong
            );
            System.err.printf(
                "[AVERAGE FPS] Average FPS: %.2f%n",
                (1.0D * totalFpsLong) / totalResponseLong
            );
            System.err.printf(
                "[AVERAGE LOST PACKETS] Total Lost Packets: %d => Average Lost Packets: %.2f %%%n",
                totalRequestLong - totalResponseLong,
                (1.0F * totalResponseLong) / totalRequestLong
            );
            totalRequest.set(0);
            totalResponse.set(0);
            totalFps.set(0);
            totalLatency.set(0);
            sentTimestampByUser.clear();

        }, MEASUREMENT_INTERVAL_MINUTES, MEASUREMENT_INTERVAL_MINUTES, TimeUnit.MINUTES);
    }

    public void onMessageSent(String user) {
        sentTimestampByUser.put(user, System.currentTimeMillis());
        totalRequest.incrementAndGet();
    }

    public void onMessageReceived(String user, EzyArray array) {
        long receivedTimestamp = System.currentTimeMillis();
        long sentTimestamp = sentTimestampByUser.compute(user, (k, v) ->
            v == null || v < 0 ? receivedTimestamp : v
        );
        totalResponse.incrementAndGet();
        totalFps.addAndGet(array.get(0, Integer.class));
        totalLatency.addAndGet(receivedTimestamp - sentTimestamp);
    }
}
