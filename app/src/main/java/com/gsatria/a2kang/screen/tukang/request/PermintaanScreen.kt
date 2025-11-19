package com.gsatria.a2kang.screen.tukang.request

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily

data class Permintaan(
    val id: Int,
    val nama: String,
    val alamat: String,
    val waktu: String,
    val status: String
)

@Composable
fun PermintaanScreen() {

    val daftarPermintaan = listOf(
        Permintaan(1, "Bagus Wijaya", "Jl. Kembang Sari No.5, Klojen", "Selasa, 18 November 2025, 09.00", "Pending"),
        Permintaan(2, "Ganteng Satria", "Jl. MT. Haryono No.17A, Dinoyo", "Senin, 17 November 2025, 16.00", "Pending")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Permintaan",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000),
            ),
            modifier = Modifier.width(143.dp).height(30.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(daftarPermintaan) { permintaan ->
                PermintaanCard(
                    permintaan = permintaan,
                    onTolakClick = { println("Tolak ${permintaan.id}") },
                    onSetujuClick = { println("Setuju ${permintaan.id}") }
                )
            }
        }
    }
}

@Composable
fun PermintaanCard(
    permintaan: Permintaan,
    onTolakClick: () -> Unit,
    onSetujuClick: () -> Unit
) {
    val bluePrimary = Color(0xFF2D8CFF)
    val grayBorder = Color(0xFFD9D9D9)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
            .border(1.dp, grayBorder, RoundedCornerShape(size = 12.dp))
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_default_profile),
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = permintaan.nama,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = SoraFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF000000),
                            ),
                        )

                        Text(
                            text = permintaan.status,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = SoraFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD90000) // Warna Merah
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Text(
                        text = permintaan.alamat,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF000000),
                            letterSpacing = 0.12.sp,
                        ),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F1FD), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Jadwal",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = permintaan.waktu,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = bluePrimary,
                    ),
                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD9D9D9)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = onTolakClick,
                    modifier = Modifier
                        .width(92.dp)
                        .height(84.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Tolak",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onSetujuClick,
                    modifier = Modifier
                        .width(92.dp)
                        .height(84.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = bluePrimary),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Setuju",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            letterSpacing = 0.12.sp,
                        ),
                    )
                }
            }
        }
    }
}
