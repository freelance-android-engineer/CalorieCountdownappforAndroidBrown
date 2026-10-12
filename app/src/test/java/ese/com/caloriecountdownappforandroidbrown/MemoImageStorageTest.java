package ese.com.caloriecountdownappforandroidbrown;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MemoImageStorageTest {

    @Test
    public void scaledSize_largeLandscapePhotoIsCappedAtMaxDimension() {
        assertArrayEquals(new int[]{1280, 960}, MemoImageStorage.scaledSize(4000, 3000, 1280));
    }

    @Test
    public void scaledSize_largePortraitPhotoIsCappedAtMaxDimension() {
        assertArrayEquals(new int[]{720, 1280}, MemoImageStorage.scaledSize(2250, 4000, 1280));
    }

    @Test
    public void scaledSize_smallImageIsUnchanged() {
        assertArrayEquals(new int[]{160, 120}, MemoImageStorage.scaledSize(160, 120, 1280));
    }

    @Test
    public void readInChunks_reassemblesValueLargerThanOneChunk() {
        String stored = "ABCDEFGHIJ";
        List<int[]> calls = new ArrayList<>();
        String result = MemoImageStorage.readInChunks(stored.length(), 4, (start, length) -> {
            calls.add(new int[]{start, length});
            // SQLite substr() is 1-based
            return stored.substring(start - 1, start - 1 + length);
        });

        assertEquals(stored, result);
        assertEquals(3, calls.size());
        assertArrayEquals(new int[]{9, 2}, calls.get(2));
    }

    @Test
    public void readInChunks_returnsNullWhenAChunkCannotBeRead() {
        assertNull(MemoImageStorage.readInChunks(10, 4, (start, length) -> start == 1 ? "ABCD" : null));
    }
}
