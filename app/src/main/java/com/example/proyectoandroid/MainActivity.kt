package com.example.proyectoandroid

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectoandroid.ui.theme.ProyectoAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val btnContraReloj: Button = findViewById(R.id.contraReloj)
        val btnInfinito: Button = findViewById(R.id.infinito)
        val btnDesafio: Button = findViewById(R.id.desafio)
        val btnJugar: Button = findViewById(R.id.jugar)
        val btnSonido: ImageButton = findViewById(R.id.volumen)
        val btnConfiguracion: ImageButton = findViewById(R.id.configuracion)

        btnJugar.setOnclickListener



    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoAndroidTheme {
        Greeting("Android")
    }
}