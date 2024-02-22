import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = Head())
    }
}

class Head: Screen {
    @Composable
    override fun Content() {
        val homeScreenModel = rememberScreenModel { HomeScreenModel() }
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = homeScreenModel.display.value, style = MaterialTheme.typography.h4, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            NumberPad(homeScreenModel)
        }
    }
}

@Composable
fun NumberPad(model: HomeScreenModel) {
    // Define the layout for the calculator's buttons
    val buttons = listOf(
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "C", "0", "=", "+"
    )

    Column {
        for (row in buttons.chunked(4)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (button in row) {
                    Button(
                        onClick = {
                            when (button) {
                                "C" -> model.clear()
                                "=" -> model.calculate()
                                else -> if (button in listOf("+", "-", "*", "/")) {
                                    model.onOperation(button)
                                } else {
                                    model.onInput(button)
                                }
                            }
                                  },
                        modifier = Modifier.padding(4.dp).weight(1f)
                    ) {
                        Text(button)
                    }
                }
            }
        }
    }
}


class FirstScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var count by rememberSaveable() { mutableStateOf(0) }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("SCREEN 1", fontSize = 22.sp)
                Text("Number 10 is necessary to move other screen")
                Text(count.toString())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = {
                        count--
                    }) {
                        Text("Down number")
                    }
                    Button(onClick = {
                        count++
                    }) {
                        Text("Up number")
                    }
                }
                Button(onClick = {
                    navigator?.push(SecondScreen())
                }, enabled = count == 10) {
                    Text("Continue next screen")
                }
            }
        }
    }
}

class SecondScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("SCREEN 2")
            Button(onClick = { navigator?.pop() }) {
                Text("Back")
            }
        }
    }
}