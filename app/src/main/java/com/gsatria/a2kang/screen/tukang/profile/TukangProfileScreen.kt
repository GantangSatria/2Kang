package com.gsatria.a2kang.screen.tukang.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.viewmodel.TukangHomeViewModel

data class Ulasan(
    val nama: String,
    val rating: Float,
    val komentar: String
)

@Composable
fun TukangProfileScreen(
    viewModel: TukangHomeViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onEditLayananClick: () -> Unit = {},
    onEditProfilClick: () -> Unit = {}
) {
    val profileData = viewModel.profileData.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val bluePrimary = Color(0xFF2D8CFF)

    val daftarUlasan = listOf(
        Ulasan(
            nama = "Rina Amelia",
            rating = 5.0f,
            komentar = "Pelayanan sangat cepat! AC langsung dingin lagi. Tukang sangat ramah dan profesional. Sangat direkomendasikan!"
        ),
        Ulasan(
            nama = "Bambang Wijaya",
            rating = 3.5f,
            komentar = "Service nya bagus banget, walaupun agak telat sih datengnya. Kerjaannya selesai dengan sangat sempurna top deh pokoknya."
        ),
        Ulasan(
            nama = "Aldo Fernando",
            rating = 4.0f,
            komentar = "Harga sesuai, pengerjaan rapi. Hanya saja perlu sedikit lebih detail dalam komunikasi jadwal. Tapi hasilnya memuaskan!"
        ),
        Ulasan(
            nama = "Siti Nurhaliza",
            rating = 4.5f,
            komentar = "Pemasangan instalasi listrik baru berjalan lancar. Tukang Budi sangat teliti. Akan pakai jasa lagi lain kali."
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))

            ProfileHeaderCard(onEditLayananClick, onEditProfilClick)

            Spacer(modifier = Modifier.height(32.dp))

            FormLabel(text = "Keterampilan dan Keahlian", isBold = true)
            Spacer(modifier = Modifier.height(16.dp))

            FlowLayoutStatic()

            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            FormLabel(text = "Ulasan Pelanggan", isBold = true)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(daftarUlasan) { ulasan ->
            UlasanCard(ulasan = ulasan)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Spacer(modifier = Modifier.height(32.dp)) // Padding bawah
        }
    }
}

@Composable
fun ProfileHeaderCard(onEditLayananClick: () -> Unit, onEditProfilClick: () -> Unit) {
    val bluePrimary = Color(0xFF2D8CFF)

    Box(
        modifier = Modifier
            .shadow(elevation = 2.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
            .width(320.dp)
            .height(236.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_default_profile),
                contentDescription = "Foto Profil Tukang",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "Budi Santoso",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "Perbaikan Listrik",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0x80000000),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.height(20.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val ratingValue = 4.9f

                RatingStars(rating = ratingValue)

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$ratingValue ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF000000),
                    ),
                )

                Text(
                    text = "(6 Ulasan)",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0x80000000),
                    ),
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                ProfileActionButton(
                    text = "Edit Layanan",
                    backgroundColor = Color(0xFFD9D9D9),
                    textColor = Color(0xFF000000),
                    onClick = onEditLayananClick
                )

                ProfileActionButton(
                    text = "Edit Profil",
                    backgroundColor = bluePrimary,
                    textColor = Color(0xFFFFFFFF),
                    onClick = onEditProfilClick
                )
            }
        }
    }
}

@Composable
fun UlasanCard(ulasan: Ulasan) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_default_profile),
                    contentDescription = "Profil Pelanggan",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column {

                    Text(
                        text = ulasan.nama,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF000000),
                        )
                    )

                    RatingStars(rating = ulasan.rating)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = ulasan.komentar,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier.fillMaxWidth().heightIn(min = 45.dp)
            )
        }
    }
}

@Composable
fun RatingStars(rating: Float) {
    val starColor = Color(0xFFF9C848) // Warna Kuning/Oranye
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(5) { index ->
            val starIcon = when {
                index + 1 <= rating -> R.drawable.ic_star_filled
                index < rating && (rating % 1.0f) >= 0.5f -> R.drawable.ic_star_half // Bintang setengah
                else -> R.drawable.ic_star_filled
            }
            Image(
                painter = painterResource(id = starIcon),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(starColor)
            )
        }
    }
}


@Composable
fun ProfileActionButton(text: String, backgroundColor: Color, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(86.dp)
            .height(30.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(
            text,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                letterSpacing = 0.12.sp,
            )
        )
    }
}

@Composable
fun FormLabel(text: String, isBold: Boolean = false) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = SoraFontFamily,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.SemiBold,
            color = Color(0xFF000000),
        ),
        modifier = Modifier.fillMaxWidth().height(20.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowLayoutStatic() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        listOf("Saluran Air", "Listrik", "Perabotan", "Perbaikan AC").forEach { skill ->
            val isSelectedStyle = skill == "Saluran Air" || skill == "Listrik"|| skill == "Perabotan"|| skill == "Perbaikan AC"
            SkillChipStatic(skill = skill, isSelectedStyle = isSelectedStyle)
        }
    }
}

@Composable
fun SkillChipStatic(skill: String, isSelectedStyle: Boolean) {
    val backgroundColor = if (isSelectedStyle) Color(0xFFE0E0FF) else Color(0xFFF0F0F0)
    val textColor = if (isSelectedStyle) Color(0xFF1E80FF) else Color(0xFF000000)
    val borderColor = if (isSelectedStyle) Color(0xFF1E80FF) else Color.Transparent

    Box(
        modifier = Modifier
            .height(32.dp)
            .background(backgroundColor, shape = RoundedCornerShape(100.dp))
            .border(1.dp, borderColor, RoundedCornerShape(100.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = skill,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Profiletukangpreview() {
    TukangProfileScreen()
}