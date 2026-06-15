package com.subhi.arifalkora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth().background(Color.Black),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                // هذا هو رقم وحدتك الإعلانية الحقيقي للبانر
                adUnitId = "ca-app-pub-8275739847848743/8161066785"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
