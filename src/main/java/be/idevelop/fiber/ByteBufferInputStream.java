package be.idevelop.fiber;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

final class ByteBufferInputStream extends InputStream {

    private final ByteBuffer byteBuffer;

    ByteBufferInputStream(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public int read() throws IOException {
        return !byteBuffer.hasRemaining() ? -1 : byteBuffer.get();
    }

    public int read(byte[] bytes, int offset, int length) throws IOException {
        if (available() > 0) {
            int count = Math.min(Math.min(byteBuffer.remaining(), length), bytes.length - offset);
            byteBuffer.get(bytes, offset, count);
            return count;
        } else {
            return -1;
        }
    }

    public int available() throws IOException {
        return byteBuffer.remaining();
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public synchronized void mark(int i) {
        this.byteBuffer.position(i);
        this.byteBuffer.mark();
    }

    @Override
    public void close() throws IOException {
        this.byteBuffer.clear();
    }

    @Override
    public synchronized void reset() throws IOException {
        this.byteBuffer.reset();
    }
}
