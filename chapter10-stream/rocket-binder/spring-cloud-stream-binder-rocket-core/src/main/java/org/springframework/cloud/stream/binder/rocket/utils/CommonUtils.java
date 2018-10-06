package org.springframework.cloud.stream.binder.rocket.utils;

import java.nio.ByteBuffer;

public class CommonUtils {
    public static final String ROCKETMQ_RAW_DATA = "ROCKETMQ_RAW_DATA";

    public static byte[] toArray(ByteBuffer buffer) {
        return toArray(buffer, 0, buffer.limit());
    }

    /**
     * Read a byte array from the given offset and size in the buffer
     */
    public static byte[] toArray(ByteBuffer buffer, int offset, int size) {
        byte[] dest = new byte[size];
        if (buffer.hasArray()) {
            System.arraycopy(buffer.array(), buffer.arrayOffset() + offset, dest, 0, size);
        } else {
            int pos = buffer.position();
            buffer.get(dest);
            buffer.position(pos);
        }
        return dest;
    }
}
