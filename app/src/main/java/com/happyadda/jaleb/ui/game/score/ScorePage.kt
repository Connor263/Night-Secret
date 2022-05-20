package com.happyadda.jaleb.ui.game.score

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.happyadda.jaleb.R
import com.happyadda.jaleb.ui.game.menu.MenuButton

@Composable
fun ScorePage(navController: NavController) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.night_secret3),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))

            Text(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp)
                    .alpha(0.8F)
                    .fillMaxWidth(),
                text = "You WIN!",
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                color = Color.White
            )

            Spacer(Modifier.height(128.dp))

            MenuButton(text = "Play again?") {
                navController.navigate("game") {
                    popUpTo("game") { inclusive = true }
                }
            }

            Spacer(Modifier.height(16.dp))

            MenuButton(text = "Menu") {
                navController.navigate("menu") {
                    popUpTo("menu") { inclusive = true }
                }
            }
        }
    }
}

@Preview
@Composable
fun ScorePagePreview(navController: NavController = rememberNavController()) {
    ScorePage(navController = navController)
}