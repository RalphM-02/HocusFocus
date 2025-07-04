package com.mobdeve.s12.marquezgavanmaloles.mco

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mobdeve.s12.marquezgavanmaloles.mco.model.CalendarHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DataStoreRepository
import com.mobdeve.s12.marquezgavanmaloles.mco.model.DatabaseHelper
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.MCOTheme
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var contentResolver: ContentResolver
    private lateinit var calendarHelper: CalendarHelper
    private lateinit var dataStore: DataStoreRepository
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this)
        contentResolver = getContentResolver()
        calendarHelper = CalendarHelper(contentResolver)
        dataStore = DataStoreRepository(this)
        AndroidThreeTen.init(this)
        setContent {
            val navController = rememberNavController()
            MCOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val items = listOf(
                        BottomNavigationItem(
                            title = "Home",
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home,
                        ),
                        BottomNavigationItem(
                            title = "Add Task",
                            selectedIcon = Icons.Filled.Add,
                            unselectedIcon = Icons.Outlined.Add,
                        ),
                        BottomNavigationItem(
                            title = "Rewards",
                            selectedIcon = Icons.Filled.DateRange,
                            unselectedIcon = Icons.Outlined.DateRange,
                        ),
                        BottomNavigationItem(
                            title = "Profile",
                            selectedIcon = Icons.Filled.AccountCircle,
                            unselectedIcon = Icons.Outlined.AccountCircle,
                        )
                    )
                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    Scaffold (
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed{ index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(getRouteForIndex(selectedItemIndex))
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex){
                                                    item.selectedIcon
                                                }else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        })
                                }
                            }
                        }
                    ){
                        Column {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(lightGreen),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                Text(text = "HocusFocus", style = MaterialTheme.typography.titleLarge)
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                            ){
                                val registered = dataStore.getRegistered.collectAsState(initial = false).value
                                Main(dbHelper, calendarHelper, navController, dataStore, registered!!)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getRouteForIndex(index: Int): String{
    return when(index){
        0 -> Routes.HOME_SCREEN
        1 -> Routes.ADD_TASK
        2 -> Routes.CALENDAR_SCREEN
        3 -> Routes.PROFILE_SCREEN
        else -> Routes.HOME_SCREEN
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Main(dbHelper: DatabaseHelper, calendarHelper: CalendarHelper, navController: NavHostController, dataStore: DataStoreRepository, registered: Boolean){
    NavHost(navController = navController, startDestination = if (registered) { Routes.HOME_SCREEN } else Routes.WELCOME_SCREEN) {
        composable(Routes.WELCOME_SCREEN) { Welcome(navController, dataStore) }
        composable(Routes.HOME_SCREEN) { Home( dbHelper, calendarHelper, dataStore ) }
        composable(Routes.ADD_TASK) { AddTask(dbHelper, calendarHelper) }
        composable(Routes.CALENDAR_SCREEN) { CalendarScreen() }
        composable(Routes.PROFILE_SCREEN){ Profile(navController, dataStore, registered) }
    }
}
