package com.mobdeve.s12.marquezgavanmaloles.mco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.MCOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.WELCOME_SCREEN) { Welcome( /* ... */ ) }
        composable(Routes.HOME_SCREEN) { Home( /* ... */ ) }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MCOTheme {
        Main()
    }
}