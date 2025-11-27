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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
    onEditProfilClick: () -> Unit = {}
) {
    // Mengambil state dari ViewModel
    // Pastikan viewModel.profileData bertipe StateFlow<TukangResponse?> atau sejenisnya
    val profileState = viewModel.profileData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    // Extract data safely
    val responseWrapper = profileState.value
    val tukangData = responseWrapper?.profile
    val displayName = tukangData?.name ?: "Memuat..."
    val displayBio = tukangData?.bio ?: "-"

    // Logic Split Category: String "Listrik, Air, AC" -> List ["Listrik", "Air", "AC"]
    val categoryString = tukangData?.category ?: ""
    val skillList = if (categoryString.isNotBlank()) {
        categoryString.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    } else {
        emptyList()
    }

    // Data Dummy untuk Ulasan (Belum ada di backend entity Transaction/Review)
    val daftarUlasan = listOf(
        Ulasan("Rina Amelia", 5.0f, "Pelayanan sangat cepat! Tukang sangat ramah."),
        Ulasan("Bambang Wijaya", 3.5f, "Kerjaannya selesai dengan sangat sempurna."),
        Ulasan("Aldo Fernando", 4.0f, "Harga sesuai, pengerjaan rapi.")
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

            // Pass data dinamis ke Header
            ProfileHeaderCard(
                name = displayName,
                bio = displayBio,
                rating = 0.0f, // Rating di Entity Backend Tukang ada (float32), bisa dipasang jika response support
                reviewCount = 0,
                onEditProfilClick = onEditProfilClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            FormLabel(text = "Keterampilan dan Keahlian", isBold = true)
            Spacer(modifier = Modifier.height(16.dp))

            // Pass list skill hasil split category
            SkillsFlowLayout(skills = skillList)

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
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileHeaderCard(
    name: String,
    bio: String,
    rating: Float,
    reviewCount: Int,
    onEditProfilClick: () -> Unit
) {
    val bluePrimary = Color(0xFF2D8CFF)

    Box(
        modifier = Modifier
            .shadow(elevation = 2.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
            .width(320.dp)
            // Height menyesuaikan konten
            .wrapContentHeight()
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
            .padding(16.dp) // Padding sedikit diperbesar agar lega
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_default_profile),
                contentDescription = "Foto Profil Tukang",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )

            // Nama dari Backend
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )

            // Bio dari Backend
            Text(
                text = bio,
                style = TextStyle(
                    fontSize = 14.sp, // Sedikit diperkecil agar muat jika bio panjang
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0x80000000),
                    textAlign = TextAlign.Center,
                ),
                maxLines = 2 // Batasi 2 baris agar tidak merusak layout
            )

            // Rating Section (Hardcoded placeholder visual logic for now)
            Row(verticalAlignment = Alignment.CenterVertically) {
                val displayRating = if (rating > 0) rating else 5.0f // Default dummy jika 0

                RatingStars(rating = displayRating)

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$displayRating ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF000000),
                    ),
                )

                Text(
                    text = "($reviewCount Ulasan)",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0x80000000),
                    ),
                )
            }

            // Button Section - Hanya Edit Profil
            Button(
                onClick = onEditProfilClick,
                modifier = Modifier
                    .width(120.dp) // Lebar disesuaikan karena cuma 1 tombol
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = bluePrimary),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Edit Profil",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        letterSpacing = 0.12.sp,
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsFlowLayout(skills: List<String>) {
    if (skills.isEmpty()) {
        Text(
            text = "Belum ada kategori keahlian.",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = SoraFontFamily,
                color = Color.Gray
            )
        )
    } else {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            skills.forEach { skill ->
                SkillChip(skill = skill)
            }
        }
    }
}

@Composable
fun SkillChip(skill: String) {
    // Warna default biru muda
    val backgroundColor = Color(0xFFE0E0FF)
    val textColor = Color(0xFF1E80FF)
    val borderColor = Color(0xFF1E80FF)

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
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RatingStars(rating: Float) {
    val starColor = Color(0xFFF9C848)
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(5) { index ->
            val starIcon = when {
                index + 1 <= rating -> R.drawable.ic_star_filled
                index < rating && (rating % 1.0f) >= 0.5f -> R.drawable.ic_star_half
                else -> R.drawable.ic_star_filled // Note: Harusnya ada resource ic_star_outline/empty
            }
            // Menggunakan color filter untuk tinting
            val filter = if (index + 1 > rating && (rating % 1.0f) < 0.5f) {
                androidx.compose.ui.graphics.ColorFilter.tint(Color.LightGray) // Bintang kosong abu-abu
            } else {
                androidx.compose.ui.graphics.ColorFilter.tint(starColor)
            }

            Image(
                painter = painterResource(id = starIcon),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                colorFilter = filter
            )
        }
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun Profiletukangpreview() {
    TukangProfileScreen()
}