package com.gsatria.a2kang.screen.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton // Ditambahkan
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily

private val BluePrimary = Color(0xFF1E80FF)
private val OrangePrimary = Color(0xFFF98B31)

@Composable
fun VerificationTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Kembali",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(onClick = onBackClick)
                .size(24.dp)
        )

        Text(
            text = "Verifikasi Berkas",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000),
            )
        )
    }
}

@Composable
fun InputPengalamanScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        VerificationTopBar(onBackClick)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            UploadRequirements(sora = SoraFontFamily)

            Spacer(modifier = Modifier.height(24.dp))

            UploadCard(onAddFileClick = { /* Handle file picker */ })

            Spacer(modifier = Modifier.weight(1f))

            PrimaryActionButton(
                text = "Simpan dan lanjutkan",
                blueColor = BluePrimary,
                onClick = { /* Handle simpan dan navigasi */ }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun UploadRequirements(sora: FontFamily) {
    Text(
        text = "Pengalaman",
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = sora,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Daftar Persyaratan
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "• Isi dengan format JPG, PNG, atau PDF",
            style = TextStyle(fontSize = 10.sp, fontFamily = sora, color = Color(0xFF000000))
        )
        Text(
            text = "• Tulis dengan format nama_pengalaman.pdf",
            style = TextStyle(fontSize = 10.sp, fontFamily = sora, color = Color(0xFF000000))
        )
        Text(
            text = "• Data tidak lebih dari 2 MB",
            style = TextStyle(fontSize = 10.sp, fontFamily = sora, color = Color(0xFF000000))
        )
    }
}

@Composable
fun UploadCard(onAddFileClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0x80E9E6E6), shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Ikon Plus Oranye (Asumsi R.drawable.ic_plus ada)
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Tambah",
                tint = OrangePrimary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TOMBOL BARU: OutlinedButton dengan stroke oranye
            OutlinedButton(
                onClick = onAddFileClick,
                border = BorderStroke(1.dp, OrangePrimary),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    "Tambah File",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = OrangePrimary
                    )
                )
            }
        }
    }
}

@Composable
fun PrimaryActionButton(text: String, blueColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = blueColor,
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
    }
}