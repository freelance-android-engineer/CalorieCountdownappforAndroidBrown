package ese.com.caloriecountdownappforandroidbrown;

/**
 * Generic model for manual health readings: Blood Pressure, Heart Rate, Blood Sugar.
 *
 * For Blood Pressure:  value1 = systolic, value2 = diastolic
 * For Heart Rate:      value1 = bpm,      value2 = 0 (unused)
 * For Blood Sugar:     value1 = mg_dl,    value2 = 0 (unused)
 */
public class HealthReadingModel {

    private int id;
    private String date;
    private String time;
    private int value1;
    private int value2;
    private String createdAt;

    public HealthReadingModel(int id, String date, String time, int value1, int value2, String createdAt) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.value1 = value1;
        this.value2 = value2;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getValue1() { return value1; }
    public int getValue2() { return value2; }
    public String getCreatedAt() { return createdAt; }

    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setValue1(int value1) { this.value1 = value1; }
    public void setValue2(int value2) { this.value2 = value2; }
}
