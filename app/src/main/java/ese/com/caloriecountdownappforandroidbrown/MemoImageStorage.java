package ese.com.caloriecountdownappforandroidbrown;

/**
 * Pure-Java helpers that keep memo images persistable in the memo table.
 *
 * Memo images are stored as base64 TEXT in the memo row. Android reads query results
 * through a CursorWindow (~2MB). A full-resolution gallery photo encodes to several MB,
 * so the row could be written but never read back: on API 26-27 the cursor comes back
 * empty (memo looks "not saved"), on API 28+ it throws SQLiteBlobTooBigException.
 *
 * New images are downscaled before saving, and existing images are read in chunks so
 * previously saved oversized memos still load.
 */
final class MemoImageStorage {

    /** Longest edge (px) for a memo image before it is encoded for storage. */
    static final int MAX_DIMENSION_PX = 1280;

    /** Characters read per substr() query — well under the 2MB CursorWindow limit. */
    static final int READ_CHUNK_CHARS = 512 * 1024;

    /** Reads {@code length} characters starting at 1-based {@code start} (SQLite substr semantics). */
    interface ChunkReader {
        String read(int start, int length);
    }

    private MemoImageStorage() {}

    /**
     * Returns {width, height} scaled so the longest edge is at most {@code maxDimension},
     * preserving aspect ratio. Images already within the limit are returned unchanged.
     */
    static int[] scaledSize(int width, int height, int maxDimension) {
        int longest = Math.max(width, height);
        if (width <= 0 || height <= 0 || longest <= maxDimension) {
            return new int[]{width, height};
        }
        float scale = (float) maxDimension / longest;
        return new int[]{
                Math.max(1, Math.round(width * scale)),
                Math.max(1, Math.round(height * scale))
        };
    }

    /**
     * Reassembles a stored value of {@code totalLength} characters by reading it in chunks.
     *
     * @return the full value, or null if a chunk could not be read
     */
    static String readInChunks(int totalLength, int chunkSize, ChunkReader reader) {
        StringBuilder sb = new StringBuilder(Math.max(totalLength, 0));
        for (int start = 1; start <= totalLength; start += chunkSize) {
            String chunk = reader.read(start, Math.min(chunkSize, totalLength - start + 1));
            if (chunk == null || chunk.isEmpty()) {
                return null;
            }
            sb.append(chunk);
        }
        return sb.toString();
    }
}
