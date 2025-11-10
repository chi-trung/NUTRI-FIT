package com.example.nutrifit.data.model

import com.google.firebase.firestore.Exclude

// Dữ liệu cho một bài tập, tương ứng với cấu trúc trên Firestore
data class Exercise(
    @get:Exclude var id: String = "", // ID của document trên Firestore
    val name: String = "",
    val description: String = "",
    val difficulty: String = "",
    val imageUrl: String = "",
    val muscleGroup: String = "",
    val targets: List<String> = emptyList(),
    val videoUrl: String = "",

    // Trường này chỉ dùng ở phía client, @get:Exclude để Firestore bỏ qua khi đọc/ghi
    @get:Exclude var isCompleted: Boolean = false 
) {
    // Constructor trống bắt buộc cho việc chuyển đổi của Firestore
    constructor() : this("", "", "", "", "", "", emptyList(), "", false)
}
