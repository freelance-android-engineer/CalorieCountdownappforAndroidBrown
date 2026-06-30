# Calorie Countdown App
## Quick Start Guide & User Manual

**Version:** Beta 1.9.0  
**Platform:** Android  
**Audience:** First-time testers and new users  
**Last Updated:** June 2026

---

# Table of Contents

1. [Introduction](#1-introduction)
2. [Getting Started](#2-getting-started)
3. [Home Screen](#3-home-screen)
4. [Food Notes](#4-food-notes)
5. [Daily Workflow](#5-daily-workflow)
6. [Steps Challenge](#6-steps-challenge)
7. [Cloud Features](#7-cloud-features)
8. [Progress & History](#8-progress--history)
9. [Settings & Customization](#9-settings--customization)
10. [Troubleshooting](#10-troubleshooting)
11. [Frequently Asked Questions](#11-frequently-asked-questions)
12. [Best Practices](#12-best-practices)
13. [Tips for Testers](#13-tips-for-testers)

---

# 1. Introduction

## 1.1 Purpose of the Application

Calorie Countdown is a calorie-based weight management application. Instead of counting up calories consumed, the app works in reverse: you start with a high Countdown Balance and work to reduce it to zero over time.

Every food item you eat reduces your Countdown Balance. Every step you walk reduces it further. When your Countdown Balance reaches zero, your weight loss goal has been achieved.

The app is designed for daily use and requires only a few minutes of attention at key moments during the day.

## 1.2 Core Concept

Think of your Countdown Balance like a fuel tank that empties over time:

- **Starting Balance:** A large number (e.g. 75,000 points) representing your total calorie deficit journey.
- **Daily Goal:** Reduce the balance by at least 250 points per day.
- **How it reduces:** Food you eat generates credits. Steps you walk generate debits against your food intake. Net daily reduction brings the balance down.
- **Zero = Goal Achieved:** When the balance reaches zero, you have completed your calorie countdown.

## 1.3 Main Workflow

The app follows a structured daily cycle:

```
Morning  →  Add Food Notes throughout the day
3:30 PM  →  Credit Day End (record your food notes)
4:00 PM  →  Process Food Notes (AI fills missing calories)
Evening  →  Complete your Steps Challenge
9:59 PM  →  Debit Day End (enter steps walked)
Midnight →  Automatic snapshot saved
Next day →  Repeat
```

---

# 2. Getting Started

## 2.1 Install the Application

1. Install the APK on your Android device (API level 26 or higher, Android 8.0+).
2. When prompted, allow **Notifications** permission. This is required for the 3:30 PM and 9:59 PM daily alarms.
3. If you are on Android 13 or higher, the app will request notification permission at first launch. Tap **Allow**.

## 2.2 First Launch

When you open the app for the first time you will see the **Home Screen** with:

- A large number in the centre — your Countdown Balance (starts at zero until you complete setup).
- A **Credit** button (bottom left).
- A **Debit** button (bottom right).
- A **Steps Challenge** button (above the Credit button).

## 2.3 Initial Setup — Start Weight Loss

Before you can use the app you must complete the **Start Weight Loss** setup. This is essential. Without it, the app has no opening balance and cannot track your progress.

**Steps:**

1. Tap the **three-dot overflow menu** (top right of the screen).
2. Tap **Start Weight Loss**.
3. You will be taken to the Setup screen. Fill in:
   - **Opening Countdown Balance** — the starting number your coach has given you (e.g. 75,000).
   - **Your Height** — enter your height and select your unit (centimetres, inches, feet, or metres). The app converts to centimetres automatically.
   - **Meal Times** — set your typical Breakfast, Lunch, Dinner, and Midnight times.
4. Tap **Save / Confirm** to complete setup.
5. You will return to the Home Screen. Your Countdown Balance will now display your opening number.

> **Why is Start Weight Loss important?**  
> The opening balance is the foundation of everything. All daily reductions, step challenges, estimated dates, and progress charts are calculated relative to this starting number. Without it, no feature in the app works correctly.

## 2.4 Daily Alarms

After setup is complete, two alarms are automatically scheduled every day:

| Alarm | Time | Purpose |
|---|---|---|
| Credit Day End | 3:30 PM | Reminder to process your food notes |
| Debit Day End | 9:59 PM | Reminder to enter your step count |

These alarms fire automatically. You do not need to set them manually. They reschedule themselves each time the app is opened.

---

# 3. Home Screen

## 3.1 Countdown Balance

The large number displayed in the centre of the screen is your **Countdown Balance**. This is the most important number in the application.

- It decreases as you progress through your weight loss journey.
- It is updated every time you complete a Credit or Debit cycle.
- When it reaches zero, your goal is complete.

The balance is formatted according to your settings (plain number or comma-separated).

## 3.2 Credit Button

The **Credit** button (bottom left, labelled "Credit") opens the **Food Diary**. You use this button to record what you have eaten. Tapping it starts the credit flow:

1. Opens the Food Diary Sheet where you add food notes.
2. After reviewing and confirming, the Countdown Balance is reduced.

> The Credit button is disabled for a moment after being tapped to prevent accidental double-taps.

## 3.3 Debit Button

The **Debit** button (bottom right, labelled "Debit") opens the **Debit Activity**. You use this button at the end of the day to enter your step count after completing your Steps Challenge.

After entering your steps:
1. The app calculates how many calorie points your steps have burned.
2. These points are subtracted from your Countdown Balance.
3. A Countdown Report is displayed showing your progress.

## 3.4 Steps Challenge Button

The **Steps Challenge** button (above the Credit button) calculates how many steps you need to walk today to meet your daily countdown target.

- This button becomes meaningful after you have completed your 4PM Food Notes processing.
- If tapped before 4PM processing is complete, the app will prompt you to finish that step first.
- If already calculated today, the app shows you the saved result without recalculating.

## 3.5 Navigation — Overflow Menu

Tap the **three-dot overflow menu** (top right) to access all other features:

| Menu Item | What it does |
|---|---|
| Settings | Switch between Countdown / Hold Ground / Surplus account modes |
| Quick Start Guide | Opens this guide (if implemented as in-app PDF) |
| Start Weight Loss | Initial setup / recalibrate opening balance |
| Food Diary Notes | Open the food notes table directly |
| Fitness Log Notes | Log fitness and exercise notes |
| Accrual | View the accrual journal |
| Monthly Statement | View monthly calorie and balance history |
| Debit (Fitness Log) | Open debit/exercise activity |
| Health → Blood Pressure | Log blood pressure readings |
| Health → Heart Rate | Measure heart rate via camera |
| Health → Blood Sugar | Log blood sugar readings |
| Health → Water Tracker | Log daily water intake |
| Recalibrate | Reset or recalibrate your countdown balance |
| Estimated Date to Zero | Calculate when you will reach zero |
| Progress & Forecast Charts | View visual progress charts |
| Customize Appearance | Change colours, fonts, and background |
| Client Guide | View the PDF client guide |
| Convert | Convert units (Stones, KG, Pounds) |

---

# 4. Food Notes

## 4.1 Overview

Food Notes are the records of everything you eat each day. They are entered through the **Food Diary** (Credit flow) or directly in the **Food Notes Table**.

To open the Food Notes Table directly:
- Tap overflow menu → **Food Diary Notes**.

## 4.2 Adding a Food Note

1. Open the Food Notes Table.
2. Tap **Add New Note** (or the plus button).
3. Enter:
   - **Food Name** — what you ate (e.g. "Grilled Chicken Breast").
   - **Calories** — estimated calories (optional — AI can fill this in).
   - **Quantity / Portion** — how much you had (e.g. "200g", "1 cup", "2 slices") — optional.
4. Tap **Save**.

The note is saved with the current date and time.

## 4.3 Multi Search

The **Multi Search** feature allows you to search the food database for multiple items at once:

1. In the Food Diary Sheet, enter food names in the available fields — one food per field, across multiple rows.
2. Tap **Multi Search**.
3. The app searches the food database for every item simultaneously.
4. Results are matched and calories are populated automatically.
5. A progress indicator shows while searches are running.
6. The search button is locked until all searches are complete to prevent duplicates.

> **Tip:** You do not need to type exact names. The search uses partial matching.

## 4.4 Portion Selection

Each food note includes an optional **Quantity / Portion** field.

- Enter amounts like `200g`, `1 cup`, `2 tablespoons`, `half plate`.
- If left blank, the app defaults to a standard serving size.
- Portions are saved alongside the food note and are used by the AI for more accurate calorie estimates.

## 4.5 AI Food Analysis

The app includes AI-powered calorie estimation. Two AI features are available:

### Check Before You Eat

Use this before eating to estimate the calories in a meal:

1. Open the Food Notes Table.
2. Tap **Check Before You Eat** (or the AI button, often labelled with a brain or AI icon).
3. Enter the food name and optional portion size.
4. The AI returns:
   - Estimated **Calories**
   - **Protein**, **Carbohydrates**, and **Fat**
   - A short contextual message (e.g. "High calorie item — consider a smaller portion" or "Good to go!").
5. Tap **Add to Notes** to save the item directly.

### AI Auto-Fill at 4PM

During the 4PM processing step, any food note that has no calories entered will be automatically sent to the AI for estimation. This saves you from having to manually look up every item.

### Nearest AI Button

The **Nearest AI** button finds the best available AI app on your phone and opens it:

- Checks for: ChatGPT → Gemini → Claude → Copilot → Google Assistant (in that order).
- If none are installed, opens a web browser to the relevant service.
- Use this to ask questions about food, calories, or your diet directly.

## 4.6 AI Image-Based Food Detection

You can take a photo of your meal and the AI will identify the food items:

1. In the Food Notes area, tap the camera/image icon.
2. Take a photo or select one from your gallery.
3. The AI analyses the image and identifies food items visible in the photo.
4. Detected items are listed with estimated nutritional values.
5. Review and confirm the items to add them to your food notes.

> **Note:** Image detection requires an internet connection. Quality depends on image clarity.

---

# 5. Daily Workflow

## 5.1 Step-by-Step Daily Routine

The app is designed around a structured daily cycle. Follow these steps each day for best results.

---

### Step 1 — Add Food Notes Throughout the Day

As you eat each meal or snack:

1. Open the app.
2. Tap the **Credit** button or go to **Food Diary Notes**.
3. Add a note for each item you ate (food name, optional portion).
4. Tap **Save**.
5. Repeat after each meal.

You do not need to enter calories manually. The AI fills them in during the 4PM step.

---

### Step 2 — 4PM: Process Food Notes

At **4:00 PM**, you will receive a notification: **"4PM Food Notes Processing"**.

1. Tap the notification to open the Food Notes screen.
2. The app automatically begins reviewing today's food notes.
3. For any notes missing calorie values, the AI estimates and fills them in.
4. A **Review Dialog** is displayed showing:
   - All food notes for the day.
   - Estimated calories for each item.
   - Total calorie count.
5. Review the list carefully. Make any corrections if needed.
6. Tap **Confirm** to complete the 4PM processing.

> You can also trigger this manually at any time by tapping the **4PM** button in the Food Notes table. The app prevents processing the same day twice.

---

### Step 3 — Credit Countdown

After confirming your 4PM food notes:

1. Tap the **Credit** button on the Home Screen.
2. Your food intake for the day is applied to the Countdown Balance.
3. A **Countdown Report** is displayed showing:
   - Previous Balance
   - New Balance
   - Net daily reduction
   - Kitty remaining (food budget vs. food consumed)
   - Estimated Date to Zero
4. Tap OK to close the report.

---

### Step 4 — Calculate Steps Challenge

After the Credit step is complete:

1. Tap the **Steps Challenge** button on the Home Screen.
2. The app calculates the number of steps you need to walk today.
3. The result is displayed along with the calculation breakdown.
4. The result is saved automatically — tapping again shows the saved result.

> See **Section 6** for a full explanation of how the Steps Challenge is calculated.

---

### Step 5 — Walk Your Steps

Walk the number of steps shown in the Steps Challenge.

Use any step tracking method:
- Your phone's built-in step counter.
- A fitness watch or pedometer.
- A manual count.

---

### Step 6 — Debit Steps at 9:59 PM

At **9:59 PM**, you will receive a notification: **"Debit Day End"**.

1. Tap the notification, or tap the **Debit** button on the Home Screen.
2. You will see the Debit Activity.
3. Tap **Enter Steps** (or the steps sub-option).
4. Enter the number of steps you walked today.
5. Tap **Submit**.
6. The app:
   - Converts your steps to calorie points (1 step ≈ 0.089 calories).
   - Subtracts those points from your Countdown Balance.
   - Shows a final Countdown Report for the day.

---

### What Happens at Midnight

At midnight, the app automatically saves a snapshot of:
- Your Countdown Balance.
- Today's food calorie total.
- Your kitty value (budget remaining).
- Estimated Date to Zero at that moment.

This snapshot is used for historical charts and progress tracking.

---

# 6. Steps Challenge

## 6.1 How It Works

The Steps Challenge calculates exactly how many steps you need to walk today to keep your Countdown Balance on track toward your zero target.

It is based on:
1. **Where your balance needs to be by end of day** (Day End Target).
2. **Where your balance is right now** (Current Balance).
3. **Your calorie burn rate** (BMR — base metabolic rate).
4. **How many calories each step burns** (Steps Numerator).

## 6.2 Formula

```
Day End Target   = Previous Day End Balance − 250

Steps Challenge  = (Current Balance − Day End Target − BMR)
                   ÷ Steps Numerator

Result is always rounded UP (ceiling) to the nearest whole step.
Negative values are set to 0 — you never owe steps.
```

**Where:**
- **Previous Day End Balance** — the Countdown Balance at the end of yesterday.
- **250** — the minimum daily countdown target (the balance must drop by at least 250 per day).
- **Current Balance** — your live Countdown Balance right now.
- **BMR** — your Base Metabolic Rate (2,500 for males / 2,000 for females). This represents the calories your body burns at rest.
- **Steps Numerator** — 0.089 (approximately how many countdown points one step is worth).

## 6.3 Example Calculation

| Value | Amount |
|---|---|
| Previous Day End Balance | 75,853 |
| Day End Target (75,853 − 250) | 75,603 |
| Current Balance | 79,774 |
| BMR (male) | 2,500 |
| Steps Numerator | 0.089 |

```
Steps Challenge = (79,774 − 75,603 − 2,500) ÷ 0.089
               = 1,671 ÷ 0.089
               = 18,775.28...
               → Rounded UP = 18,776 Steps
```

**Result: Walk 18,776 steps today.**

## 6.4 What the Result Means

- If the number is **high**, your food intake today was above your budget, so more steps are required to compensate.
- If the number is **low** (or zero), you have eaten well within budget and your body's natural metabolism will cover the rest.
- The app **never** shows a negative result — zero is the minimum.

## 6.5 Saving the Result

The result is saved to the database automatically. If you tap the Steps Challenge button again on the same day, you will see the previously saved result — the calculation is not repeated.

---

# 7. Cloud Features

## 7.1 Overview

The app can sync your food notes and daily data to a secure cloud backend. This allows your data to be preserved and accessed remotely by your coach or support team.

## 7.2 Add to Cloud

To sync your food notes to the cloud:

1. Open the **Food Notes Table** (overflow menu → Food Diary Notes).
2. Add your food notes as normal.
3. Tap **Add to Cloud** (visible in the food notes screen).
4. The app validates each note (name must be present, calories must be greater than zero).
5. Valid notes are sent to the cloud server.
6. A confirmation message appears when the sync is complete.

**What is synced:**
- Food item name
- Calories
- Fat, Protein, Carbohydrates
- Sugar, Salt, Fibre
- Quantity / Portion

## 7.3 Automatic Sync

The app also syncs automatically in several situations:

| When | What is synced |
|---|---|
| After saving a food note | The new note is marked and queued for sync |
| When the app is resumed | Any previously failed syncs are retried automatically |
| At 9:59 PM (Debit Day End) | Full day snapshot (balance, food total, kitty value, estimated zero date) |

You do not need to do anything to trigger automatic sync — it runs silently in the background.

## 7.4 Sync Status

- Each food note has an internal sync flag (`synced` / `pending`).
- If a sync fails (no internet), the note is flagged as pending and retried next time you open the app.
- No data is lost if you are offline — it queues and sends when connectivity is restored.

## 7.5 Fetch from Cloud

> **Note:** A dedicated "Fetch from Cloud" button to pull all data back from the server is not yet available in this version. Data flows primarily from the app to the cloud. Full two-way sync is planned for a future release.

---

# 8. Progress & History

## 8.1 Countdown History

You can view your historical balance data in several ways:

### Monthly Statement
1. Tap the overflow menu.
2. Tap **Monthly Statement and Food Journal**.
3. See a month-by-month breakdown of your transactions and meals.

### Journal / Accrual
1. Tap the overflow menu.
2. Navigate to **Accrual → Journal**.
3. View your daily accrual journal entries.

## 8.2 Estimated Date to Zero

This feature calculates when your Countdown Balance will reach zero based on your actual historical progress.

**To view:**
1. Tap the overflow menu.
2. Navigate to **Recalibrate → Estimated Date to Zero**.
3. A dialog appears showing:

| Item | Meaning |
|---|---|
| Current Balance | Your live Countdown Balance |
| Avg Daily Reduction | Your average daily reduction (from history) |
| Days Remaining | How many more days at current rate |
| Estimated Date | The calendar date you will reach zero |

**How the average is calculated:**
- The app reads all your historical daily-end balance snapshots.
- It calculates the total reduction from the oldest to the most recent snapshot.
- It divides by the number of days elapsed.
- If insufficient history exists (fewer than 2 snapshots), a default rate of 250 pts/day is used.

**Progress Forecast** (shown if history exists):
- Opening Balance vs Current Balance.
- Percentage complete.
- Whether you are ahead, on target, or behind the 250 pts/day goal.

## 8.3 Progress & Forecast Charts

The charts feature provides a visual representation of your countdown journey.

**To open:**
1. Tap the overflow menu.
2. Navigate to **Recalibrate → Progress & Forecast Charts**.
3. The chart screen loads your data in the background (you will see a loading indicator briefly).

### Progress Chart (top section)

- Displays your historical Countdown Balance as a line graph over time.
- X-axis: day number (D1, D2, D3 … most recent day).
- Y-axis: balance value.
- A green filled area under the line shows total progress.
- **Stats shown below the chart:**
  - Opening balance.
  - Current balance.
  - Total points reduced and percentage complete.
  - Average daily reduction rate.

### Forecast Chart (bottom section)

- Shows the same historical line extended with a **dashed amber forecast line**.
- The forecast line runs from your current balance down to zero.
- A red dot marks the estimated zero point.
- **Stats shown below the chart:**
  - Current balance.
  - Average daily reduction rate.
  - Estimated days to zero.
  - Estimated completion date.

**If no data is available:**  
The chart screen shows a friendly message: *"No historical Countdown Balance data found. Complete at least one Credit cycle and Day End to generate chart data."*

---

# 9. Settings & Customization

## 9.1 Opening the Settings Screen

1. Tap the overflow menu (three dots, top right).
2. Tap **Customize Appearance**.
3. The Customize Appearance screen opens.

## 9.2 Countdown Balance Text Color

1. In the Customize Appearance screen, find **Countdown Balance Color**.
2. A row of coloured circles is displayed — these are your colour options.
3. Tap any circle to select that colour.
4. A white ring appears around the selected colour.
5. The change is saved immediately.

**Available colours:** Green (default), White, Black, Red, Blue, Amber/Yellow, Orange, Purple, Pink, Cyan, Blue-Grey, Brown.

## 9.3 Font Style

1. Find the **Font Style** section.
2. Choose one of four options:
   - **Default** — standard light sans-serif.
   - **Bold** — heavier weight.
   - **Serif** — a classic serif typeface.
   - **Mono** — fixed-width monospace.
3. The selection is saved immediately.

## 9.4 Number Format

Controls how the Countdown Balance number is displayed:

- **Plain** — e.g. `70500`
- **Commas** — e.g. `70,500`

## 9.5 Balance Circle Style

You can add a decorative circle border around the Countdown Balance number:

| Option | Appearance |
|---|---|
| None | No border (default) |
| Thin Border | A thin ring around the number |
| Medium Border | A medium-weight ring |
| Thick Border | A bold thick ring |
| Transparent Glass Overlay | A frosted/glass effect |

The ring colour matches your chosen text colour.

## 9.6 Credit Button Color

1. Find the **Credit Button Color** section.
2. Tap any colour swatch to change the Credit button text colour.

## 9.7 Debit Button Color

1. Find the **Debit Button Color** section.
2. Tap any colour swatch to change the Debit button text colour.

## 9.8 Background Image

1. Find the **Background Image** section.
2. A list of preset background images is shown. Available options include:
   - Multiple Peacock designs (Peacock 1 through 22).
   - Black Rock.
   - Berries.
   - Cat.
   - Color Change.
3. Tap any option to select it.
4. A ✓ mark appears next to the currently selected background.

## 9.9 Apply Changes

- Tap **Apply & Close** to save and return to the Home Screen.
- All changes are applied immediately when you return to the Home Screen.

## 9.10 Reset to Defaults

- Tap **Reset to Defaults** to restore all appearance settings to the original values.
- A confirmation message ("Appearance reset to defaults") is shown.
- This does **not** affect your Countdown Balance or any food/step data.

---

# 10. Troubleshooting

## 10.1 Countdown Balance Shows Zero After Install

**Cause:** The Start Weight Loss setup has not been completed.  
**Fix:**
1. Tap the overflow menu.
2. Tap **Start Weight Loss**.
3. Enter your opening balance and height.
4. Tap Save.

## 10.2 Alarms Are Not Firing (3:30 PM or 9:59 PM)

**Possible causes:**
- Notification permission was denied.
- Battery optimisation is blocking the app.

**Fix:**
1. Go to Android Settings → Apps → Calorie Countdown → Permissions.
2. Enable **Notifications**.
3. Also check: Android Settings → Battery → Battery Optimisation → set Calorie Countdown to **Unrestricted** or **Not Optimised**.
4. Open the app — alarms reschedule automatically when the app is launched.

## 10.3 Steps Challenge Shows "Please Complete 4PM Processing First"

**Cause:** The 4PM Food Notes Processing has not been confirmed yet today.  
**Fix:**
1. Open the Food Notes Table (overflow menu → Food Diary Notes).
2. Tap the **4PM** button (or wait for the 4PM notification).
3. Review the food note list and tap **Confirm**.
4. Return to the Home Screen and tap Steps Challenge again.

## 10.4 Steps Challenge Has Already Been Calculated Today

**Behaviour:** Tapping the Steps Challenge button shows the previously saved result.  
**Explanation:** The app calculates the Steps Challenge only once per day to prevent inconsistent results. The saved result is shown on subsequent taps.  
**If you need to recalculate:** This is not supported in the current version to protect data integrity.

## 10.5 AI Is Not Responding / "Check Before You Eat" Returns No Result

**Possible causes:**
- No internet connection.
- The backend AI service is temporarily unavailable.

**Fix:**
1. Check your internet connection.
2. Wait a few seconds and try again.
3. If the problem persists, use the **Nearest AI** button to open a local AI app on your device (ChatGPT, Gemini, etc.) for manual calorie lookup.

## 10.6 Food Notes Not Syncing to Cloud

**Possible causes:**
- No internet connection at the time of saving.
- A temporary server issue.

**Fix:**
- No action needed. The app automatically retries failed syncs the next time you open it.
- Check your internet connection if sync continues to fail after several app restarts.

## 10.7 Database Not Populated — Food Search Returns No Results

**Cause:** The food database may not have been populated on this device.  
**Fix:**
1. Tap the overflow menu.
2. Find and tap **Populate Food Database** (in the Food Diary section of the menu).
3. Wait for the import to complete. A confirmation message will appear.
4. Food search and Multi Search will now work.

## 10.8 Progress Charts Show "No Historical Data"

**Cause:** No day-end balance snapshots have been saved yet. This requires at least one complete daily cycle (Credit + Debit Day End).  
**Fix:**
- Complete one full day cycle: add food notes → 4PM processing → Credit → Steps Challenge → enter steps at 9:59 PM.
- Repeat for at least two days.
- Charts will populate automatically.

## 10.9 App Crashes or Freezes

**Fix:**
1. Force-close the app and reopen it.
2. If the problem persists, clear the app cache:
   - Android Settings → Apps → Calorie Countdown → Storage → Clear Cache.
3. Do **not** tap "Clear Data" — this would delete your Countdown Balance and all saved records.
4. Report the crash to your testing coordinator with a description of what you were doing when it occurred.

---

# 11. Frequently Asked Questions

**Q: What does the Countdown Balance actually represent?**  
A: It represents the cumulative calorie deficit remaining on your weight loss journey. It is set to a personalised opening value by your coach and counts down to zero as you manage your food intake and physical activity.

**Q: What happens if I eat more than my daily budget?**  
A: Your Steps Challenge for that day will be higher to compensate. The app calculates the extra steps needed to keep you on track. No permanent penalty is applied — each day is a fresh opportunity.

**Q: Do I have to enter calories manually?**  
A: No. You can enter just the food name and the AI will estimate calories during the 4PM processing step. You can also use the Check Before You Eat feature for an instant estimate before eating.

**Q: What if I miss the 4PM alarm?**  
A: You can manually trigger the 4PM processing at any time by opening the Food Notes table and tapping the 4PM button. The app prevents double-processing the same day.

**Q: What if I miss the 9:59 PM alarm?**  
A: Open the app and tap the **Debit** button at any point in the evening. You can still enter your step count.

**Q: What is the "Kitty" value shown in the Countdown Report?**  
A: The Kitty is your remaining food budget for the day. It is calculated as: Daily Budget (your BMR) minus Total Food Calories consumed today. A positive Kitty means you are within budget. A negative Kitty means you went over budget — which increases your Steps Challenge accordingly.

**Q: Can I use the app if I haven't walked any steps?**  
A: Yes. Enter 0 steps in the Debit screen. The Countdown Balance will not be reduced by steps that day, but it will still reflect your food intake from the Credit step.

**Q: What happens to my data if I reinstall the app?**  
A: Data stored locally in the SQLite database will be lost if you uninstall the app unless you have synced it to the cloud. Cloud-synced food notes may be partially recoverable. Always ensure cloud sync is enabled.

**Q: What is the difference between "Credit" and "Debit"?**  
A: **Credit** refers to recording food (calories IN). **Debit** refers to recording steps (calories OUT). Both result in a reduction of the Countdown Balance, but through different mechanisms.

**Q: Can I change my opening balance?**  
A: Yes. Use overflow menu → **Recalibrate** to adjust your balance if it has been set incorrectly. Contact your coach before using this option.

**Q: How accurate are the AI calorie estimates?**  
A: The AI provides reasonable estimates based on typical serving sizes and food types. They are not a substitute for verified nutritional information but are accurate enough for daily tracking purposes.

**Q: What does BMR mean?**  
A: BMR stands for Basal Metabolic Rate — the number of calories your body burns each day at rest. The app currently uses 2,500 calories/day for males and 2,000 calories/day for females. This is factored into the Steps Challenge calculation.

---

# 12. Best Practices

## For Accurate Results

1. **Add food notes immediately after eating.** Memory fades quickly. A note added right away is more accurate than one added at the end of the day.

2. **Include the portion size.** Notes that include a quantity (e.g. "200g chicken") allow the AI to estimate calories far more accurately than notes with just the food name.

3. **Do not skip the 4PM Review.** Even if you triggered 4PM processing manually, always review the list before tapping Confirm. The AI sometimes needs a correction.

4. **Complete both Credit and Debit every day.** Skipping either step means your Countdown Balance is not updated correctly and your progress charts will have gaps.

5. **Walk your steps before 9:59 PM.** Enter steps at the end of the day when you have walked the full amount. Entering early means you may miss some steps.

6. **Keep notifications enabled.** The 3:30 PM and 9:59 PM alarms are the backbone of the daily workflow. Disabling notifications will break the routine.

## For Reliable Data

7. **Do not clear app data** in Android Settings. Clearing data deletes your entire Countdown Balance history. Use "Clear Cache" only if needed.

8. **Stay connected to the internet** when using AI features and cloud sync. Offline mode works for local data entry but AI and cloud features require connectivity.

9. **Do not use the Depopulate or Clear Database menu options** unless instructed by your support team. These permanently remove food item data.

## For Motivation

10. **Check your Estimated Date to Zero regularly.** Watching the date move closer is a powerful motivator.

11. **View your Progress Chart weekly.** The visual downward trend reinforces how much you have already achieved.

12. **Record every food item, even small ones.** Small snacks and drinks add up. Comprehensive tracking gives more accurate Steps Challenge values.

---

# 13. Tips for Testers

This section is specifically for testers evaluating the application.

## 13.1 Setting Up a Test Environment

1. **Complete Start Weight Loss first.** Use an opening balance between 50,000 and 100,000 for a realistic test. Example: 75,000.
2. **Populate the food database** via overflow menu → Populate Food Database. Without this, food search will return no results.
3. **Enable notifications** and leave them enabled throughout testing.

## 13.2 Testing the Daily Cycle

To test a complete daily cycle without waiting:

1. Add 3–5 food notes with food names (no calories needed — let AI fill them).
2. Manually tap the **4PM button** in Food Notes to trigger processing.
3. Review the AI-estimated calories and tap Confirm.
4. Return to Home Screen and tap **Credit**.
5. Review the Countdown Report.
6. Tap **Steps Challenge** — verify the formula result matches the example in Section 6.3.
7. Tap **Debit** → enter a step count → Submit.
8. Verify the Countdown Balance has decreased.

## 13.3 Testing AI Features

- **Check Before You Eat:** Try entering a common food (e.g. "banana", "grilled salmon") and verify a calorie estimate is returned.
- **Nearest AI:** Tap the Nearest AI button and confirm it opens an AI app or browser fallback.
- **4PM AI Fill:** Add notes with no calories, then run 4PM processing and confirm calories are filled in.

## 13.4 Testing Charts

- At least **two complete day cycles** are needed before charts will show data.
- After two cycles, open Progress & Forecast Charts and verify both sections render correctly.
- Test with one day of data to confirm the "insufficient data" state is handled gracefully (falls back to 250 pts/day standard rate).

## 13.5 Testing Settings

- Open Customize Appearance and change the Countdown Balance colour.
- Return to Home Screen and verify the balance number changed colour.
- Change background image and verify it applies.
- Tap Reset to Defaults and verify all settings return to original values.

## 13.6 Testing Steps Challenge

- Complete a Credit cycle first.
- Tap Steps Challenge and note the result.
- Tap Steps Challenge again — verify it shows the **saved result**, not a new calculation.
- On a different test day, verify a higher food intake produces a higher step count.

## 13.7 Known Limitations in This Version

| Feature | Status |
|---|---|
| Fetch from Cloud (pull all data from server) | Coming Soon |
| Barcode scanner (scan product barcode) | Field exists in database; scanning UI not yet implemented |
| Full two-way cloud sync | Coming Soon |
| In-app Quick Start Guide PDF | Depends on PDF viewer setup |

## 13.8 Reporting Issues

When reporting a bug or unexpected behaviour, please include:

1. **What you were doing** — which screen, which button.
2. **What you expected to happen.**
3. **What actually happened.**
4. **Your Countdown Balance at the time** (if relevant).
5. **Date and time** of the issue.
6. **Whether it is reproducible** — does it happen every time or only occasionally?

---

*End of User Manual*

---

**Calorie Countdown App — Beta 1.9.0**  
For support, contact your testing coordinator.
