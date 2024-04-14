package com.andrew.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andrew.compose.ui.main.MainScreen
import com.andrew.compose.ui.modify.ModifyStudentScreen
import com.andrew.compose.ui.profile.ProfileScreen
import com.andrew.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            MainScreen(navController)
                        }
                        composable("profile_screen") {
                            ProfileScreen()
                        }
                        composable("modify_student_screen/{studentId}", arguments = listOf(
                            navArgument("studentId") {
                                type = NavType.IntType
                            }
                        )) {
                            val studentId = remember {
                                it.arguments?.getInt("studentId") ?: -1
                            }
                            ModifyStudentScreen(
                                navController = navController,
                                studentId = studentId
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Page1() {
    Text(text = "Page 1")
}

@Composable
fun Page2() {
    Text(text = "Page 2")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen(rememberNavController())
        }
    }
}