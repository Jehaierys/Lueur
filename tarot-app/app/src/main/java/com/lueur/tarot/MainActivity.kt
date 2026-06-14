package com.lueur.tarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lueur.tarot.domain.TarotViewModel
import com.lueur.tarot.ui.TarotApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: TarotViewModel = viewModel()
            TarotApp(viewModel = vm)
        }
    }
}