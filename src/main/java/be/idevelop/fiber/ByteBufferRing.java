package be.idevelop.fiber;

import java.nio.ByteBuffer;

enum ByteBufferRing {

    BYTE_BUFFER_RING;

    private static final int MB_128 = 1024 * 1024 * 128;

    private final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(MB_128);

    int start = byteBuffer.position();

    public synchronized ByteBuffer allocate(int bufferSize) {
        if (byteBuffer.limit() - start < bufferSize) {
            start = byteBuffer.position();
        }
        int end = start + bufferSize;
        ByteBuffer allocated = (ByteBuffer) byteBuffer.slice().position(start).limit(end);
        start = end;
        return allocated;
    }
}
