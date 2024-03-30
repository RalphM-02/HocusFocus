package com.mobdeve.s12.marquezgavanmaloles.mco

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.marquezgavanmaloles.mco.ui.theme.lightGreen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(){
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.CheckCircle,
            unselectedIcon = Icons.Outlined.CheckCircle,
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
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
                            // TODO: Navigate to page
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(lightGreen),
            horizontalArrangement = Arrangement.Center
        ){

        }
    }
}

@Composable
fun Task(){
    // TODO: Task Item Layout 
}

@Preview
@Composable
fun HomePreview(){
    Surface {
        Home()
    }
}