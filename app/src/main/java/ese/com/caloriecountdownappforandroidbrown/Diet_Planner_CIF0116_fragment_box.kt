package ese.com.caloriecountdownappforandroidbrown

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.UUID

/**
 * Diet Planner and Generator (Credit screen button). Version 2.0.0.
 *
 * Wizard: Diet Name (e.g. Keto Diet) → Monday … Sunday → six meal boxes, each filled with food and
 * drink items searched in the cloud backend (local food database when offline). Missing items can
 * be added with the existing Add New Food Item screen. Plans are stored in SQLite.
 *
 * Credit a Meal: the Subscriber picks a saved day and meal; the meal is returned to the Credit
 * screen, which credits it through the existing Credit flow and formula.
 */
class Diet_Planner_CIF0116_fragment_box : AppCompatActivity() {

    companion object {
        const val EXTRA_PLAN_ID = "diet_planner_plan_id"
        const val EXTRA_DAY = "diet_planner_day"
        const val EXTRA_MEAL = "diet_planner_meal"
        const val EXTRA_EVENT_ID = "diet_planner_event_id"
        private const val REQUEST_CODE_ADD_NEW_ITEM = 3
        private const val DEFAULT_GRAMS = 100
    }

    private lateinit var db: SQLDatabase_Food_Items_CIF6

    private lateinit var homeSection: View
    private lateinit var wizardSection: View
    private lateinit var planSpinner: Spinner
    private lateinit var noPlansText: TextView
    private lateinit var wizardName: TextView
    private lateinit var wizardStep: TextView
    private lateinit var daySpinner: Spinner
    private lateinit var mealSpinner: Spinner
    private lateinit var boxItems: ListView
    private lateinit var boxTotal: TextView
    private lateinit var searchInput: EditText
    private lateinit var searchProgress: ProgressBar
    private lateinit var searchButton: Button
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    private val planIds = ArrayList<Long>()
    private var plan: Diet_Planner_CIF0116? = null
    private var day = 0
    private var meal = 0
    private var unsavedChanges = false
    private var lastQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_planner_cif0116)
        db = SQLDatabase_Food_Items_CIF6(this)

        homeSection = findViewById(R.id.dietHomeSection)
        wizardSection = findViewById(R.id.dietWizardSection)
        planSpinner = findViewById(R.id.dietPlanSpinner)
        noPlansText = findViewById(R.id.dietNoPlansText)
        wizardName = findViewById(R.id.dietWizardName)
        wizardStep = findViewById(R.id.dietWizardStep)
        daySpinner = findViewById(R.id.dietDaySpinner)
        mealSpinner = findViewById(R.id.dietMealSpinner)
        boxItems = findViewById(R.id.dietBoxItems)
        boxTotal = findViewById(R.id.dietBoxTotal)
        searchInput = findViewById(R.id.dietSearchInput)
        searchProgress = findViewById(R.id.dietSearchProgress)
        searchButton = findViewById(R.id.btnDietSearch)
        previousButton = findViewById(R.id.btnDietPrevious)
        nextButton = findViewById(R.id.btnDietNext)

        findViewById<Button>(R.id.btnDietNewPlan).setOnClickListener { askDietName() }
        findViewById<Button>(R.id.btnDietEditPlan).setOnClickListener { selectedPlanId()?.let { editPlan(it) } }
        findViewById<Button>(R.id.btnDietCreditMeal).setOnClickListener { selectedPlanId()?.let { chooseDayToCredit(it) } }
        findViewById<Button>(R.id.btnDietDeletePlan).setOnClickListener { selectedPlanId()?.let { confirmDelete(it) } }
        findViewById<Button>(R.id.btnDietClose).setOnClickListener { setResult(Activity.RESULT_CANCELED); finish() }

        daySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Diet_Planner_CIF0116.DAYS)
        mealSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Diet_Planner_CIF0116.MEAL_BOXES)
        daySpinner.onItemSelectedListener = selectionListener { day = it; showBox() }
        mealSpinner.onItemSelectedListener = selectionListener { meal = it; showBox() }

        searchButton.setOnClickListener { search(searchInput.text.toString()) }
        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) { search(searchInput.text.toString()); true } else false
        }
        findViewById<Button>(R.id.btnDietAddNewFood).setOnClickListener { launchAddNewFoodItem() }
        boxItems.setOnItemClickListener { _, _, position, _ -> editItem(position) }
        previousButton.setOnClickListener { moveStep(-1) }
        nextButton.setOnClickListener { moveStep(1) }
        findViewById<Button>(R.id.btnDietSave).setOnClickListener { savePlan() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (wizardSection.visibility == View.VISIBLE) leaveWizard() else finish()
            }
        })

        showHome()
    }

    // ── Home ────────────────────────────────────────────────────────────────

    private fun showHome() {
        plan = null
        unsavedChanges = false
        wizardSection.visibility = View.GONE
        homeSection.visibility = View.VISIBLE
        title = getString(R.string.cancel4a1)

        val plans = db.getDietPlanNames()
        planIds.clear()
        planIds.addAll(plans.keys)
        planSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ArrayList(plans.values))
        val hasPlans = plans.isNotEmpty()
        noPlansText.visibility = if (hasPlans) View.GONE else View.VISIBLE
        planSpinner.visibility = if (hasPlans) View.VISIBLE else View.GONE
        findViewById<Button>(R.id.btnDietEditPlan).isEnabled = hasPlans
        findViewById<Button>(R.id.btnDietCreditMeal).isEnabled = hasPlans
        findViewById<Button>(R.id.btnDietDeletePlan).isEnabled = hasPlans
    }

    private fun selectedPlanId(): Long? {
        val position = planSpinner.selectedItemPosition
        if (position < 0 || position >= planIds.size) {
            Toast.makeText(this, "Please create a Diet Plan first.", Toast.LENGTH_SHORT).show()
            return null
        }
        return planIds[position]
    }

    /** Step 1 of the wizard: the Diet Name. Any name is allowed; common diets are suggested. */
    private fun askDietName() {
        val input = AutoCompleteTextView(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        input.hint = "e.g. Keto Diet"
        input.threshold = 1
        input.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Diet_Planner_CIF0116.EXAMPLE_DIETS))

        val dialog = AlertDialog.Builder(this)
                .setTitle("Diet Name")
                .setMessage("What would you like to call this diet? For example Keto Diet, Mediterranean, "
                        + "Carnivore, Atkins, South Beach, 5:2 or Intermittent Fasting.")
                .setView(input)
                .setPositiveButton("Next", null) // set below so invalid input keeps the dialog open
                .setNegativeButton("Cancel", null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val name = Diet_Planner_CIF0116.cleanDietName(input.text.toString())
                if (name == null) {
                    Toast.makeText(this, "Please enter a Diet Name (up to 60 characters).", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (db.getDietPlanNames().values.any { it.equals(name, ignoreCase = true) }) {
                    Toast.makeText(this, "A Diet Plan named \"$name\" already exists. Choose another name "
                            + "or edit the existing plan.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                dialog.dismiss()
                openWizard(Diet_Planner_CIF0116(name))
            }
        }
        dialog.show()
    }

    private fun editPlan(planId: Long) {
        val loaded = db.getDietPlan(planId)
        if (loaded == null) {
            Toast.makeText(this, "This Diet Plan could not be loaded.", Toast.LENGTH_LONG).show()
            showHome()
            return
        }
        openWizard(loaded)
    }

    private fun confirmDelete(planId: Long) {
        val name = planSpinner.selectedItem?.toString() ?: return
        AlertDialog.Builder(this)
                .setTitle("Delete Diet Plan")
                .setMessage("Delete \"$name\"? This cannot be undone. Meals already credited are not affected.")
                .setPositiveButton("Delete") { _, _ ->
                    if (!db.deleteDietPlan(planId)) {
                        Toast.makeText(this, "The Diet Plan could not be deleted.", Toast.LENGTH_LONG).show()
                    }
                    showHome()
                }
                .setNegativeButton("Cancel", null)
                .show()
    }

    // ── Wizard ──────────────────────────────────────────────────────────────

    private fun openWizard(newPlan: Diet_Planner_CIF0116) {
        plan = newPlan
        unsavedChanges = newPlan.id <= 0
        day = 0
        meal = 0
        homeSection.visibility = View.GONE
        wizardSection.visibility = View.VISIBLE
        wizardName.text = newPlan.dietName
        title = newPlan.dietName
        daySpinner.setSelection(0)
        mealSpinner.setSelection(0)
        showBox()
    }

    private fun showBox() {
        val current = plan ?: return
        val items = current.getBox(day, meal)
        val step = day * Diet_Planner_CIF0116.MEAL_BOXES.size + meal + 1
        val steps = Diet_Planner_CIF0116.DAYS.size * Diet_Planner_CIF0116.MEAL_BOXES.size
        wizardStep.text = "Step $step of $steps — ${Diet_Planner_CIF0116.DAYS[day]} · ${Diet_Planner_CIF0116.MEAL_BOXES[meal]}"
        boxItems.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                if (items.isEmpty()) listOf("No food or drink in this meal yet — search below to add.") else items.map { describe(it) })
        boxItems.isEnabled = items.isNotEmpty()
        boxTotal.text = "Total: ${Diet_Planner_CIF0116.mealCredits(items)} Cr"
        previousButton.isEnabled = step > 1
        nextButton.isEnabled = step < steps
    }

    private fun describe(item: Food_Item_CIF4): String {
        item.Make_calorie_value()
        return "${item.Get_food_item_name()} — ${item.Get_weight()} g — ${item.Get_calorie_value()} Cr"
    }

    private fun moveStep(delta: Int) {
        val boxes = Diet_Planner_CIF0116.MEAL_BOXES.size
        val steps = Diet_Planner_CIF0116.DAYS.size * boxes
        val step = (day * boxes + meal + delta).coerceIn(0, steps - 1)
        day = step / boxes
        meal = step % boxes
        daySpinner.setSelection(day)
        mealSpinner.setSelection(meal)
        showBox()
    }

    /** Searches the cloud backend (local food database when offline or nothing is found online). */
    private fun search(text: String) {
        val query = text.trim()
        if (query.isEmpty()) {
            Toast.makeText(this, "Please type a food or drink to search for.", Toast.LENGTH_SHORT).show()
            return
        }
        lastQuery = query
        searchButton.isEnabled = false
        searchProgress.visibility = View.VISIBLE
        val wanted = Food_Item_CIF4()
        wanted.Set_food_item_name(query)
        MIF4_Data_Model_Adapter(wanted, this).FetchMatches(object : FoodSearchCallback {
            override fun onResult(results: ArrayList<Food_Item_CIF4>) {
                runOnUiThread {
                    if (isFinishing || isDestroyed) return@runOnUiThread
                    searchButton.isEnabled = true
                    searchProgress.visibility = View.GONE
                    showResults(query, results.filter { !it.Get_food_item_name().isNullOrBlank() })
                }
            }
        })
    }

    private fun showResults(query: String, results: List<Food_Item_CIF4>) {
        if (results.isEmpty()) {
            AlertDialog.Builder(this)
                    .setTitle("Not found")
                    .setMessage("\"$query\" was not found in the food database. Would you like to add it as a "
                            + "New Food Item? Once added, search for it again to put it in this meal.")
                    .setPositiveButton("Add New Food Item") { _, _ -> launchAddNewFoodItem() }
                    .setNegativeButton("Cancel", null)
                    .show()
            return
        }
        val labels = results.map { "${it.Get_food_item_name()} — ${it.Get_calories_per_100g().toInt()} kcal per 100 g" }
        AlertDialog.Builder(this)
                .setTitle("Choose for ${Diet_Planner_CIF0116.MEAL_BOXES[meal]}")
                .setItems(labels.toTypedArray()) { _, which -> askGrams(results[which], null) }
                .setNeutralButton("Not listed? Add New Food Item") { _, _ -> launchAddNewFoodItem() }
                .setNegativeButton("Cancel", null)
                .show()
    }

    /** Asks for the grams eaten; Credits use the existing formula calories_per_100g × grams / 100. */
    private fun askGrams(item: Food_Item_CIF4, existingPosition: Int?) {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        val serving = item.Get_grams_per_serving_portion().toInt()
        val suggested = when {
            existingPosition != null && item.Get_weight() > 0 -> item.Get_weight()
            serving in 1..5000 -> serving
            else -> DEFAULT_GRAMS
        }
        input.setText(suggested.toString())
        input.setSelection(input.text.length)

        val dialog = AlertDialog.Builder(this)
                .setTitle(item.Get_food_item_name())
                .setMessage("How many grams (or ml) of this will you have in ${Diet_Planner_CIF0116.DAYS[day]}'s "
                        + "${Diet_Planner_CIF0116.MEAL_BOXES[meal]}?\n\n"
                        + "${item.Get_calories_per_100g().toInt()} kcal per 100 g")
                .setView(input)
                .setPositiveButton("OK", null) // set below so invalid input keeps the dialog open
                .setNegativeButton("Cancel", null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val grams = Diet_Planner_CIF0116.parseGrams(input.text.toString())
                if (grams == null) {
                    Toast.makeText(this, "Please enter a whole number of grams from 1 to 5000.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                item.Set_weight(grams)
                item.Make_calorie_value()
                val box = plan?.getBox(day, meal) ?: return@setOnClickListener
                if (existingPosition == null) box.add(item)
                unsavedChanges = true
                dialog.dismiss()
                searchInput.setText("")
                showBox()
            }
        }
        dialog.show()
    }

    private fun editItem(position: Int) {
        val box = plan?.getBox(day, meal) ?: return
        if (position !in box.indices) return
        val item = box[position]
        AlertDialog.Builder(this)
                .setTitle(item.Get_food_item_name())
                .setItems(arrayOf("Change grams", "Remove from ${Diet_Planner_CIF0116.MEAL_BOXES[meal]}")) { _, which ->
                    if (which == 0) {
                        askGrams(item, position)
                    } else {
                        box.removeAt(position)
                        unsavedChanges = true
                        showBox()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
    }

    private fun launchAddNewFoodItem() {
        startActivityForResult(Intent(this, Add_New_Item_Activity_CIF12::class.java), REQUEST_CODE_ADD_NEW_ITEM)
    }

    @Deprecated("Matches the startActivityForResult pattern used across the app")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_NEW_ITEM && wizardSection.visibility == View.VISIBLE) {
            if (lastQuery.isNotEmpty()) searchInput.setText(lastQuery)
            Toast.makeText(this, "Search for your new food item to add it to this meal.", Toast.LENGTH_LONG).show()
        }
    }

    private fun savePlan() {
        val current = plan ?: return
        if (current.isEmpty) {
            Toast.makeText(this, "Please add at least one food or drink before saving.", Toast.LENGTH_LONG).show()
            return
        }
        when (db.saveDietPlan(current)) {
            -2L -> Toast.makeText(this, "A Diet Plan named \"${current.dietName}\" already exists.", Toast.LENGTH_LONG).show()
            -1L -> Toast.makeText(this, "The Diet Plan could not be saved. Please try again.", Toast.LENGTH_LONG).show()
            else -> {
                Toast.makeText(this, "\"${current.dietName}\" saved.", Toast.LENGTH_SHORT).show()
                showHome()
            }
        }
    }

    private fun leaveWizard() {
        if (!unsavedChanges) {
            showHome()
            return
        }
        AlertDialog.Builder(this)
                .setTitle("Unsaved changes")
                .setMessage("Leave without saving this Diet Plan?")
                .setPositiveButton("Leave") { _, _ -> showHome() }
                .setNegativeButton("Stay", null)
                .show()
    }

    // ── Credit a Meal ───────────────────────────────────────────────────────

    private fun chooseDayToCredit(planId: Long) {
        val saved = db.getDietPlan(planId)
        if (saved == null) {
            Toast.makeText(this, "This Diet Plan could not be loaded.", Toast.LENGTH_LONG).show()
            return
        }
        // Calendar: Sunday = 1 … Saturday = 7; plan: Monday = 0 … Sunday = 6
        val today = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7
        val labels = Diet_Planner_CIF0116.DAYS.mapIndexed { i, name -> if (i == today) "$name (today)" else name }
        AlertDialog.Builder(this)
                .setTitle("${saved.dietName} — choose a day")
                .setItems(labels.toTypedArray()) { _, which -> chooseMealToCredit(saved, which) }
                .setNegativeButton("Cancel", null)
                .show()
    }

    private fun chooseMealToCredit(saved: Diet_Planner_CIF0116, dayIndex: Int) {
        val labels = Diet_Planner_CIF0116.MEAL_BOXES.mapIndexed { m, name ->
            val items = saved.getBox(dayIndex, m)
            if (items.isEmpty()) "$name (empty)" else "$name — ${Diet_Planner_CIF0116.mealCredits(items)} Cr (${items.size} items)"
        }
        AlertDialog.Builder(this)
                .setTitle("${Diet_Planner_CIF0116.DAYS[dayIndex]} — choose a meal")
                .setItems(labels.toTypedArray()) { _, which ->
                    if (saved.getBox(dayIndex, which).isEmpty()) {
                        Toast.makeText(this, "This meal has no food or drink in your plan.", Toast.LENGTH_SHORT).show()
                    } else {
                        confirmCredit(saved, dayIndex, which)
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
    }

    /** Shows the meal and its Credits; on confirm returns it to the Credit screen once. */
    private fun confirmCredit(saved: Diet_Planner_CIF0116, dayIndex: Int, mealIndex: Int) {
        val eventId = UUID.randomUUID().toString()
        val items = saved.getBox(dayIndex, mealIndex)
        val message = StringBuilder()
        items.forEach { message.append(describe(it)).append("\n") }
        message.append("\nTotal Credit: ").append(Diet_Planner_CIF0116.mealCredits(items)).append(" Cr")
                .append("\n\nThis will be added to your Main Countdown Balance.")

        val dialog = AlertDialog.Builder(this)
                .setTitle("${saved.dietName}: ${Diet_Planner_CIF0116.DAYS[dayIndex]} ${Diet_Planner_CIF0116.MEAL_BOXES[mealIndex]}")
                .setMessage(message.toString())
                .setPositiveButton("Credit", null) // set below so a double tap cannot submit twice
                .setNegativeButton("Cancel", null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { button ->
                button.isEnabled = false
                dialog.dismiss()
                val result = Intent()
                result.putExtra(EXTRA_PLAN_ID, saved.id)
                result.putExtra(EXTRA_DAY, dayIndex)
                result.putExtra(EXTRA_MEAL, mealIndex)
                result.putExtra(EXTRA_EVENT_ID, eventId)
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
        dialog.show()
    }

    private fun selectionListener(onSelected: (Int) -> Unit) = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) = onSelected(position)
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}
