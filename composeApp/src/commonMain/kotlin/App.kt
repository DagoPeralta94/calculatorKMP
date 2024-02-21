import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
        val screenModel = rememberScreenModel { HomeScreenModel() }
        var numberVisible by remember {
            mutableStateOf("")
        }
        var numberCache by remember { mutableStateOf(0) }
        var flagVisible by remember { mutableStateOf(false) }
        fun updateFlag() { flagVisible = false }
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(0.dp, 150.dp))
            if (flagVisible) {
                Text(
                    numberCache.toString(),
                    textAlign = TextAlign.End,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    numberVisible,
                    textAlign = TextAlign.End,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Button(onClick = {
                    screenModel.setNumbers()
                    numberCache = 0
                    numberVisible = "" }) {
                    Text("C")
                }
                Button(onClick = { numberVisible = "-$numberVisible" }) {
                    Text("+/-")
                }
                Button(onClick = { numberVisible = "Comming" }) {
                    Text("%")
                }
                Button(onClick = {
                    numberCache = screenModel.dividir(numberVisible.toInt())
                    flagVisible = true
                    numberVisible = ""
                }) {
                    Text("/")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("7") }) { Text("7")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("8") }) { Text("8")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("9") }) { Text("9")
                }
                Button(onClick = {
                    numberCache = screenModel.multiplicar(numberVisible.toInt())
                    flagVisible = true
                    numberVisible = ""
                }) { Text("X") }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("4") }) { Text("4")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("5") }) { Text("5")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("6") }) { Text("6")
                }
                Button(onClick = {
                    numberCache = screenModel.resta(numberVisible.toInt())
                    flagVisible = true
                    numberVisible = ""
                }) { Text("-") }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("1") }) { Text("1")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("2") }) { Text("2")
                }
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("3") }) { Text("3")
                }
                Button(onClick = {
                    numberCache = screenModel.suma(numberVisible.toInt())
                    flagVisible = true
                    numberVisible = ""
                }) { Text("+") }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            ) {
                Button(onClick = {
                    updateFlag()
                    numberVisible = numberVisible.plus("0") }) { Text("0")
                }
                Button(onClick = { numberVisible = numberVisible.plus(",") }) { Text(",") }
                Button(onClick = {
                    numberVisible = screenModel.result(numberVisible.toInt()).toString()
                }) { Text("=") }
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