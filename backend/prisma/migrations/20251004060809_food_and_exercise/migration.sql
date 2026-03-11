-- CreateTable
CREATE TABLE "public"."Food_Items" (
    "id" SERIAL NOT NULL,
    "food_type" VARCHAR(100),
    "food_item_name" VARCHAR(100),
    "grams_per_serving" DOUBLE PRECISION,
    "calories_per_100g" DOUBLE PRECISION,
    "fat_per_100g" DOUBLE PRECISION,
    "saturated_fat" DOUBLE PRECISION,
    "trans_fat" DOUBLE PRECISION,
    "protein_per_100g" DOUBLE PRECISION,
    "carbs_per_100g" DOUBLE PRECISION,
    "sugar_per_100g" DOUBLE PRECISION,
    "salt_per_100g" DOUBLE PRECISION,
    "wellbeing_index" DOUBLE PRECISION,
    "fiber" DOUBLE PRECISION,
    "price_sterling" DOUBLE PRECISION,
    "category" VARCHAR(100),
    "polyunsaturated" DOUBLE PRECISION,
    "monounsaturated" DOUBLE PRECISION,
    "cholesterol_mg" DOUBLE PRECISION,
    "sodium_mg" DOUBLE PRECISION,
    "potassium_mg" DOUBLE PRECISION,
    "vitamin_a_percent" DOUBLE PRECISION,
    "vitamin_c_percent" DOUBLE PRECISION,
    "calcium_percent" DOUBLE PRECISION,
    "iron_percent" DOUBLE PRECISION,

    CONSTRAINT "Food_Items_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "public"."Exercise_Items" (
    "id" SERIAL NOT NULL,
    "exercise_name" TEXT,
    "energy_burnt_per_min" DOUBLE PRECISION,
    "client_weight" DOUBLE PRECISION,
    "client_height_cm" INTEGER,
    "cardio_or_strength" BOOLEAN,

    CONSTRAINT "Exercise_Items_pkey" PRIMARY KEY ("id")
);
