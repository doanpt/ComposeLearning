package com.andrew.compose.ui.modify

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ModifyStudentScreen(
    navController: NavController,
    studentId: Int
) {
    Text(text = "Modify student: $studentId")
}