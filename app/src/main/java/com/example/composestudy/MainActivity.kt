package com.example.composestudy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestudy.ui.theme.ComposeStudyTheme
import com.example.composestudy.ui.theme.Purple200
import com.example.composestudy.ui.theme.Purple500
import com.example.composestudy.ui.theme.Teal200

class MainActivity : ComponentActivity() {
    var clicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Button(onClick = {
                startActivity(Intent(this, Week1Activity::class.java))
            }) {
                Text(text = "1주차")
            }
        }
    }
}
