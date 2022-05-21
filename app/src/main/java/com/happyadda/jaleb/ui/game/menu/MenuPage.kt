package com.happyadda.jaleb.ui.game.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.happyadda.jaleb.MainActivity
import com.happyadda.jaleb.R

@Preview(showBackground = true)
@Composable
fun MenuPage(navController: NavController = rememberNavController()) {
    val activity = (LocalContext.current as? MainActivity)
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.night_secret_),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MenuButton(text = "Start", onClick = { navController.navigate("game") })
            Spacer(Modifier.size(16.dp))
            MenuButton(text = "Exit", onClick = { activity?.finish() })
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit = {}) {
    TextButton(
        modifier = Modifier
            .size(224.dp, 64.dp)
            .background(MaterialTheme.colorScheme.tertiary),
        onClick = onClick
    ) {
        Text(text = text, fontSize = 36.sp, )
    }
}