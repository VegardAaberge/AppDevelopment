package com.example.composecourse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecourse.models.WindowInfo
import com.example.composecourse.models.rememberWindowInfo
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ScreenSizeScreen() {
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(10){
                Text1(it)
            }
            items(10){
                Text2(it)
            }
        }
    }else {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            LazyColumn(
                modifier = Modifier.weight(1f)
            ){
                items(10){
                    Text1(it)
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ){
                items(10){
                    Text2(it)
                }
            }
        }
    }
}

@Composable
fun Text1(itemIndex : Int) {
    Text(
        text = "Items ${itemIndex}",
        fontSize = 25.sp,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan)
            .padding(16.dp)

    )
}

@Composable
fun Text2(itemIndex : Int) {
    Text(
        text = "Items ${itemIndex}",
        fontSize = 25.sp,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp)

    )
}

