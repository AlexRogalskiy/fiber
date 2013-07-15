package be.idevelop.fiber;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class ByteBufferPoolTest {

    @Test
    public void testAllocateByteBuffer() {
        ByteBufferPool pool = new ByteBufferPool();

        // allocate 256MB in blocks of 1 Kb
        int start = 0;
        int end = 1024;
        for (int i = 0; i < 1024 * 256; i++) {
            ByteBuffer buffer = pool.allocate(1024);

            assertEquals(buffer.position(), start);
            assertEquals(buffer.limit(), end);
            start = end % (1024 * 1024 * 32);
            end = start + 1024;
        }
    }

}
