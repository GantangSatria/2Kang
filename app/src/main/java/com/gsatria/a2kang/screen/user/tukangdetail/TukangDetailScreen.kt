package com.gsatria.a2kang.screen.user.tukangdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import com.gsatria.a2kang.viewmodel.TukangDetailViewModel

@Composable
fun TukangDetailScreen(
    tukangId: Int,
    onBackClick: () -> Unit = {},
    onBookServiceClick: () -> Unit = {},
    viewModel: TukangDetailViewModel = viewModel()
) {
    val tukangDetail = viewModel.tukangDetail.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(tukangId) {
        viewModel.loadTukangDetail(tukangId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        TopBar(
            onBackClick = onBackClick
        )

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error.value != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = error.value ?: "Terjadi kesalahan",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadTukangDetail(tukangId) }) {
                        Text("Coba Lagi")
                    }
                }
            }
        } else {
            val tukang = tukangDetail.value
            if (tukang != null) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        // Profile Header Card
                        ProfileHeaderCard(
                            name = tukang.name ?: "Unknown",
                            rating = tukang.rating ?: 0f,
                            bio = tukang.bio ?: ""
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        // Skills and Expertise Card
                        SkillsCard(
                            category = tukang.category ?: "",
                            services = tukang.services ?: ""
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        // Service Price Card
                        ServicePriceCard()
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        // Reviews Card
                        ReviewsCard()
                    }
                }
            }
        }

        // Bottom Button - Always visible at bottom
        if (tukangDetail.value != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onBookServiceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D8CFF)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Pesan Layanan Sekarang",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClick)
                    .size(24.dp),
                tint = Color.Black
            )
            Text(
                text = "Profil Tukang",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
private fun ProfileHeaderCard(
    name: String,
    rating: Float,
    bio: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Photo (Dummy Icon)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile Photo",
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name and Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = String.format("%.1f", rating),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bio
            Text(
                text = bio.ifEmpty { "Belum ada bio" },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillsCard(
    category: String,
    services: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Keterampilan dan Keahlian",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Parse services/category into tags
            val skills = mutableListOf<String>()
            if (category.isNotBlank()) {
                skills.add(category)
            }
            if (services.isNotBlank()) {
                // Split services by comma or semicolon
                skills.addAll(services.split(",", ";").map { it.trim() }.filter { it.isNotBlank() })
            }

            if (skills.isEmpty()) {
                Text(
                    text = "Belum ada keterampilan",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        color = Color.Gray
                    )
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skills.forEach { skill ->
                        SkillTag(skill = skill)
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillTag(skill: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF2D8CFF),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2D8CFF)
            )
        )
    }
}

@Composable
private fun ServicePriceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Harga per Pertemuan",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
            Text(
                text = "Rp 100.000",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
private fun ReviewsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Ulasan",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dummy Review
            ReviewItem(
                reviewerName = "Jane Smith",
                rating = 4,
                comment = "Service nya bagus banget, walaupun agak telat sih datengnya. Kerjaannya selesai dengan sangat sempurna top deh pokoknya."
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReviewItem(
                reviewerName = "Jane Smith",
                rating = 4,
                comment = "Service nya bagus banget, walaupun agak telat sih datengnya. Kerjaannya selesai dengan sangat sempurna top deh pokoknya."
            )
        }
    }
}

@Composable
private fun ReviewItem(
    reviewerName: String,
    rating: Int,
    comment: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Profile Picture (Dummy)
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Reviewer",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = reviewerName,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating Stars
            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        modifier = Modifier.size(16.dp),
                        tint = if (index < rating) Color(0xFFFFD700) else Color(0xFFE0E0E0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = comment,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        }
    }
}