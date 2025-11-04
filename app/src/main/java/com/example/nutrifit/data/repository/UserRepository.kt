package com.example.nutrifit.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.nutrifit.R
import com.example.nutrifit.data.model.MealSuggestion
import com.example.nutrifit.data.model.TargetState
import com.example.nutrifit.data.model.WorkoutSuggestion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("nutrifit_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // User Profile
    fun saveUserProfile(name: String, gender: String, age: String, height: String, weight: String) {
        prefs.edit()
            .putString("user_name", name)
            .putString("user_gender", gender)
            .putString("user_age", age)
            .putString("user_height", height)
            .putString("user_weight", weight)
            .apply()
    }

    fun getUserProfile(): Map<String, String> {
        return mapOf(
            "name" to (prefs.getString("user_name", "") ?: ""),
            "gender" to (prefs.getString("user_gender", "...") ?: "..."),
            "age" to (prefs.getString("user_age", "...") ?: "..."),
            "height" to (prefs.getString("user_height", "...") ?: "..."),
            "weight" to (prefs.getString("user_weight", "...") ?: "...")
        )
    }

    // Target
    fun saveTarget(target: TargetState) {
        val json = gson.toJson(target)
        prefs.edit().putString("user_target", json).apply()
    }

    fun getTarget(): TargetState {
        val json = prefs.getString("user_target", null)
        return if (json != null) {
            gson.fromJson(json, TargetState::class.java)
        } else {
            TargetState("Tăng cơ", "Mục tiêu: 3000 calo/ngày", true)
        }
    }

    // Meal Suggestions (Mock Data)
    fun getMealSuggestions(mealType: String, goal: String): Flow<List<MealSuggestion>> = flow {
        val suggestions = when ("$mealType-$goal") {
            "Sáng-Tăng cơ" -> listOf(
                MealSuggestion("Trứng ốp la & Bánh mì", listOf("2 trứng", "1 ổ bánh mì", "dưa leo"), 228, R.drawable.trungvabanhmi),
                MealSuggestion("Yến mạch & Sữa tươi", listOf("4 muỗng yến mạch", "200ml sữa tươi không đường"), 250, R.drawable.yenmachsuatuoi),
                MealSuggestion("Khoai lang & Ức Gà", listOf("150g khoai lang", "80g ức gà áp chảo", "rau xà lách"), 260, R.drawable.khoailangucga),
                MealSuggestion("Sữa chua Hy Lạp Trái cây", listOf("100g sữa chua Hy Lạp", "50g việt quất", "1 thìa hạt chia"), 210, R.drawable.suachuatraicay)
            )
            "Trưa-Tăng cơ" -> listOf(
                MealSuggestion("Cơm & Ức gà", listOf("2 bát cơm", "200g ức gà", "rau xanh"), 750, R.drawable.thucdon),
                MealSuggestion("Đậu phụ & Thịt bò", listOf("150g đậu phụ", "100g thịt bò", "rau củ"), 650, R.drawable.thucdon)
            )
            // Add more combinations as needed
            else -> listOf(
                MealSuggestion("Bữa ăn mẫu", listOf("Thành phần mẫu"), 300, R.drawable.thucdon)
            )
        }
        emit(suggestions)
    }

    // Workout Suggestions (Mock Data)
    fun getWorkoutSuggestions(goal: String): Flow<List<WorkoutSuggestion>> = flow {
        val suggestions = when (goal) {
            "Tăng cơ" -> listOf(
                WorkoutSuggestion("Squat với tạ", 4, "8–10 lần", "Đùi, mông, bụng", R.drawable.squat),
                WorkoutSuggestion("Bench Press", 4, "8–10 lần", "Ngực, tay sau, vai trước", R.drawable.bench),
                WorkoutSuggestion("Deadlift", 4, "6–8 lần", "Lưng, mông, đùi sau", R.drawable.deadlift),
                WorkoutSuggestion("Pull-up", 4, "tối đa lần", "Lưng xô, tay trước", R.drawable.pullup)
            )
            // Add more goals
            else -> listOf(
                WorkoutSuggestion("Bài tập mẫu", 3, "10 lần", "Toàn thân", R.drawable.baitap)
            )
        }
        emit(suggestions)
    }

    // Settings
    fun saveSettings(language: String, units: String, notifications: Boolean, twoFactor: Boolean) {
        prefs.edit()
            .putString("settings_language", language)
            .putString("settings_units", units)
            .putBoolean("settings_notifications", notifications)
            .putBoolean("settings_two_factor", twoFactor)
            .apply()
    }

    fun getSettings(): Map<String, Any> {
        return mapOf(
            "language" to (prefs.getString("settings_language", "Tiếng Việt") ?: "Tiếng Việt"),
            "units" to (prefs.getString("settings_units", "kg / cm") ?: "kg / cm"),
            "notifications" to prefs.getBoolean("settings_notifications", false),
            "twoFactor" to prefs.getBoolean("settings_two_factor", false)
        )
    }
}
