package com.example.appgimnasio.Componets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoaderData(modifier: Modifier, image: Int) {

    // Cargamos la composición de Lottie desde el recurso raw
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image))

    // Reproducimos la animación de Lottie
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}