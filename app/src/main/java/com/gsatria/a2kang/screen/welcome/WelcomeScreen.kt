@file:OptIn(ExperimentalFoundationApi::class)

package com.gsatria.a2kang.screen.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit = {}
) {

    val sora = FontFamily(
        Font(R.font.sora_regular, FontWeight.Normal),
        Font(R.font.sora_bold, FontWeight.Bold),
        Font(R.font.sora_extrabold, FontWeight.ExtraBold)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(150.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_2kang),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(145.dp)
                        .height(145.dp)
                )

                Spacer(modifier = Modifier.width(0.dp))

                BasicText(
                    text = "2Kang",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontFamily = sora,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            BasicText(
                text = "Ahli Perbaikan Terpercaya Anda",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = sora,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(100.dp))

            BasicText(
                text = "Halo! Selamat Datang",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = sora,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.width(228.dp)
            )

            BasicText(
                text = "Mari Kita Mulai!",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = sora,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                ),
                modifier = Modifier.width(228.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E80FF)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                BasicText(
                    "Mulai",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = sora,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                )
            }
        }
    }
}