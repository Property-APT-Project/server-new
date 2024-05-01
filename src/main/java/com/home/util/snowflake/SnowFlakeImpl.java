package com.home.util.snowflake;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeImpl implements SnowFlake {

    private static final int UNUSED_BITS = 1;
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final int maxNodeId = (int) (Math.pow(2, NODE_ID_BITS) - 1);
    private static final int maxSequence = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

    // Custom Epoch (2024-05-01T04:12:48.160494Z)
    private static final long CUSTOME_EPOCH = 1714536768160L;

    private final int nodeId;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    public SnowFlakeImpl(int nodeId) {
        if (nodeId < 0 || nodeId > maxNodeId) {
            throw new IllegalArgumentException(
                    String.format("NodeId must be between %d and %d", 0, maxNodeId));
        }
        this.nodeId = nodeId;
    }

    public SnowFlakeImpl() {
        this.nodeId = createNodeId();
    }

    @Override
    public synchronized long generateSnowFlake() {
        long currentTimestamp = timestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalArgumentException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                // Sequence exhausted, wait till next millisecond.
                currentTimestamp = waitNextMills(currentTimestamp);
            } else {
                // reset sequence to start with zero for the next millisecond
                sequence = 0;
            }
        }

        lastTimestamp = currentTimestamp;

        long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS);
        id |= ((long) nodeId << SEQUENCE_BITS);
        id |= sequence;
        return id;
    }

    private long timestamp() {
        return Instant.now().toEpochMilli() - CUSTOME_EPOCH;
    }

    private long waitNextMills(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
    }

    private int createNodeId() {
        int nodeId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X", mac[i]));
                    }
                }
            }
            nodeId = sb.toString().hashCode();
        } catch (Exception e) {
            nodeId = new SecureRandom().nextInt();
        }
        nodeId = nodeId & maxNodeId;
        return nodeId;
    }
}
