package ese.com.caloriecountdownappforandroidbrown;

/**
 * Model for a single Cardio or Strength Training exercise item.
 * Category is either "CARDIO" or "STRENGTH_TRAINING".
 * Cardio  : calories = durationMinutes * caloriesPerUnit
 * Strength: calories = reps * caloriesPerUnit
 */
public class ExerciseItem {

    public static final String CATEGORY_CARDIO    = "CARDIO";
    public static final String CATEGORY_STRENGTH  = "STRENGTH_TRAINING";

    private long   id;
    private String category;
    private String name;
    private float  durationMinutes;   // relevant for CARDIO
    private int    reps;              // relevant for STRENGTH_TRAINING
    private float  caloriesPerUnit;   // cal/min for cardio; cal/rep for strength

    public ExerciseItem() {}

    public ExerciseItem(long id, String category, String name,
                        float durationMinutes, int reps, float caloriesPerUnit) {
        this.id              = id;
        this.category        = category;
        this.name            = name;
        this.durationMinutes = durationMinutes;
        this.reps            = reps;
        this.caloriesPerUnit = caloriesPerUnit;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public long   getId()                            { return id; }
    public void   setId(long id)                     { this.id = id; }

    public String getCategory()                      { return category; }
    public void   setCategory(String category)       { this.category = category; }

    public String getName()                          { return name; }
    public void   setName(String name)               { this.name = name; }

    public float  getDurationMinutes()               { return durationMinutes; }
    public void   setDurationMinutes(float d)        { this.durationMinutes = d; }

    public int    getReps()                          { return reps; }
    public void   setReps(int reps)                  { this.reps = reps; }

    public float  getCaloriesPerUnit()               { return caloriesPerUnit; }
    public void   setCaloriesPerUnit(float c)        { this.caloriesPerUnit = c; }

    // ── Business logic ────────────────────────────────────────────────────────

    /** Total calories burned by this item based on category. */
    public int calculateCalories() {
        if (CATEGORY_CARDIO.equals(category)) {
            return Math.round(durationMinutes * caloriesPerUnit);
        } else {
            return Math.round(reps * caloriesPerUnit);
        }
    }

    /**
     * Human-readable display line shown in the exercise list dialog.
     * Format:
     *   Cardio  : "Running (20 min) — 200 cal"
     *   Strength: "Bench Press (10 reps) — 5 cal"
     */
    public String getDisplayText() {
        int cal = calculateCalories();
        if (CATEGORY_CARDIO.equals(category)) {
            return name + " (" + (int) durationMinutes + " min)  —  " + cal + " cal";
        } else {
            return name + " (" + reps + " reps)  —  " + cal + " cal";
        }
    }

    /** Prefixed name as stored / displayed with the category prefix. */
    public String getPrefixedName() {
        if (CATEGORY_CARDIO.equals(category)) {
            return "Cardio: " + name;
        } else {
            return "Strength Training: " + name;
        }
    }
}
