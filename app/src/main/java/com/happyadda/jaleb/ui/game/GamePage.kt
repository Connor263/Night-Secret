package com.happyadda.jaleb.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.happyadda.jaleb.R
import com.happyadda.jaleb.ui.game.menu.MenuButton
import com.happyadda.jaleb.utils.enums.SlotType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePage(navController: NavController) {
    val viewModel: GameViewModel = viewModel()
    val score = viewModel.score
    val slots = viewModel.slots
    val gameEnd = viewModel.gameEnd
    if (gameEnd.value) {
        navController.navigate("score") {
            popUpTo("menu") { inclusive = true }
        }
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        painter = painterResource(id = R.drawable.night_secret2),
        contentDescription = null,
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.score, score),
                fontSize = 36.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(128.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                    repeat(slots.size) {
                        Spacer(Modifier.size(2.dp))
                        Box(modifier = Modifier.weight(0.25F)) {
                            ImageSlot(type = slots[it].type)
                        }
                        Spacer(Modifier.size(2.dp))
                    }
            }

            Spacer(Modifier.height(128.dp))
            MenuButton(text = "ROLL", onClick = {
                viewModel.roll()
            })
        }

    }
}

@Composable
fun ImageSlot(type: SlotType) {
    val drawableId = when (type) {
        SlotType.FIRST -> R.drawable.decal_1
        SlotType.SECOND -> R.drawable.decal_2
        SlotType.THIRD -> R.drawable.decal_3
        SlotType.FOURTH -> R.drawable.decal_4
    }
    val painter = painterResource(id = drawableId)

    Image(
        modifier = Modifier.size(80.dp),
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = null
    )
}


@Preview(showBackground = true)
@Composable
fun GamePagePreview(navController: NavController = rememberNavController()) {
    GamePage(navController = navController)
}