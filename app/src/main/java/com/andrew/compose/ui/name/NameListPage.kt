package com.andrew.compose.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrew.compose.ui.MainViewModel

@Composable
fun NameListPage(viewModel: MainViewModel) {
    val inputName by remember {
        viewModel.inputText
    }
    val names = remember {
        viewModel.names.value ?: emptyList()
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
                viewModel.changeInputText(it)
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
                viewModel.addName(inputName)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
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