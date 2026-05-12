package ese.com.caloriecountdownappforandroidbrown;

/**
 * Represents a single food note item during the 4PM processing workflow.
 * Holds both original user-entered values and AI-enriched values for review.
 * This is an in-memory model only — DB is not written until the user confirms.
 */
public class FoodNoteProcessingItem {

    public int noteId;
    public String noteDate;
    public String food;
    public String originalCalories;  // The value as stored in DB (may be empty)
    public String quantity;
    public int finalCalories;        // Post-AI value (or original if AI not needed)
    public int finalPoints;          // Points value (1:1 with calories in this system)
    public String aiUpdatedFields;   // Comma-separated fields AI updated, e.g. "calories"
    public boolean aiProcessed;      // True if AI was invoked for this note

    public FoodNoteProcessingItem(int noteId, String noteDate, String food,
                                   String originalCalories, String quantity) {
        this.noteId = noteId;
        this.noteDate = noteDate != null ? noteDate : "";
        this.food = food != null ? food : "";
        this.originalCalories = originalCalories != null ? originalCalories : "";
        this.quantity = quantity != null ? quantity : "";
        this.finalCalories = 0;
        this.finalPoints = 0;
        this.aiUpdatedFields = "";
        this.aiProcessed = false;
    }
}
