package com.andrew.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andrew.compose.ui.MainViewModel
import com.andrew.compose.ui.name.NameListPage
import com.andrew.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            title = "Reservation",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        )
    )
    val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItemIndex by rememberSaveable {
        viewModel.selectedPageIndex
    }
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(26.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(26.dp))
                items.forEachIndexed { index, drawerItem ->
                    NavigationDrawerItem(label = {
                        Text(text = drawerItem.title)
                    }, selected = index == selectedItemIndex, onClick = {
                        scope.launch {
                            viewModel.changeSelectedPage(index)
                            pagerState.scrollToPage(selectedItemIndex)
                            navigationState.close()
                        }
                    }, icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                drawerItem.selectedIcon
                            } else drawerItem.unselectedIcon,
                            contentDescription = drawerItem.title
                        )
                    }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                }
            }
        },
        drawerState = navigationState,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Compose App", modifier = Modifier.padding(horizontal = 20.dp))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                navigationState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                scope.launch {
                                    viewModel.changeSelectedPage(index)
                                    pagerState.scrollToPage(index)
                                }
                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector =
                                    if (selectedItemIndex == index)
                                        item.selectedIcon
                                    else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Add") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                    }
                )
            }
        ) { paddings ->
            Box(modifier = Modifier.padding(paddings)) {
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> NameListPage(viewModel = viewModel)
                        1 -> Page1()
                        2 -> Page2()
                        else -> throw IllegalArgumentException("Unknown page index: $page")
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

@Composable
fun ContentView() {
    var inputName by remember {
        mutableStateOf("")
    }
    val names = remember {
        mutableStateListOf("")
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Welcome to Compose",
            color = Color.Black,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            // style = TextStyle(
            //     color = Color.Blue,
            //     fontSize = 80.sp,
            //     fontWeight = FontWeight.Thin
            // )
            modifier = Modifier
                .padding(20.dp)
                .background(Color.Green)
                .padding(20.dp)
        )
        // OutlinedTextField(
        //     value = "",
        //     onValueChange = {
        //         // do something here
        //     },
        //     placeholder = {
        //         Text(text = "Enter a name")
        //     },
        //     colors = TextFieldDefaults.colors(
        //         cursorColor = Color.Blue,
        //         focusedContainerColor = Color.Red
        //     ),
        //     modifier = Modifier
        //         .fillMaxWidth()
        //         .padding(horizontal = 20.dp),
        // )
        TextField(
            value = inputName,
            onValueChange = {
                inputName = it
            },
            placeholder = {
                Text(text = "Enter a name")
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Blue,
                focusedContainerColor = Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                names.add(inputName)
                inputName = ""
            },
            modifier = Modifier.align(CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.DarkGray
            ),
            contentPadding = PaddingValues(horizontal = 40.dp),
        ) {
            Text(text = "Add a name")
        }
        LazyColumn {
            items(names) {
                Text(text = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContentView()
        }
    }
}