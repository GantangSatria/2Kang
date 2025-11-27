package com.gsatria.a2kang.screen.tukang.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import com.gsatria.a2kang.viewmodel.TukangHomeViewModel

// Data Models
data class TukangProfile(
    val name: String,
    val profileImageUrl: String? = null,
    val bio: String,
    val rate: Long
)

data class JobStatistics(
    val unprocessedJobs: Int,
    val completedJobs: Int
)

data class HomepageTukangData(
    val profile: TukangProfile,
    val statistics: JobStatistics
)

@Composable
fun HomepageTukang(
    viewModel: TukangHomeViewModel = viewModel(),
    onEditProfileClick: () -> Unit = {},
    onJobClick: (Int) -> Unit = {},
    onPermintaanClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val profileData = viewModel.profileData.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    // Convert data to UI model
    val homepageData = profileData.value?.let { profile ->
        HomepageTukangData(
            profile = TukangProfile(
                name = profile.profile.name ?: "Tukang",
                bio = profile.profile.bio ?: "Belum ada bio",
                rate = 50000L // Default rate
            ),
            statistics = JobStatistics(
                unprocessedJobs = 0,
                completedJobs = profile.reviews?.size ?: 0
            )
        )
    }

    if (loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            item {
                error.value?.let { errorMessage ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            homepageData?.let { data ->
                item {
                    HeaderSection(
                        name = data.profile.name,
                        profileImageUrl = data.profile.profileImageUrl,
                        onPermintaanClick = onPermintaanClick
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    ProfileCard(
                        profile = data.profile,
                        onEditClick = onEditProfileClick,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    StatisticsCards(
                        statistics = data.statistics,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun HeaderSection(
    name: String,
    profileImageUrl: String?,
    modifier: Modifier = Modifier,
    onPermintaanClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Selamat datang,",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Permintaan Button (Bell Icon)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3F2FD))
                    .clickable(onClick = onPermintaanClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Permintaan",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF2D8CFF)
                )
            }

            // Profile Picture (Circular)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUrl != null) {
                    Text(
                        text = "Image",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        text = name.take(1).uppercase(),
                        fontSize = 20.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(
    profile: TukangProfile,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (profile.profileImageUrl != null) {
                    Text(
                        text = "Image",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        text = profile.name.take(1).uppercase(),
                        fontSize = 24.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = profile.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = profile.bio,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    ),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rp ${formatCurrency(profile.rate)}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onEditClick,
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2D8CFF)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Edit",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
private fun StatisticsCards(
    statistics: JobStatistics,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatisticCard(
            icon = Icons.Default.DateRange,
            count = statistics.unprocessedJobs,
            label = "Pekerjaan yang Belum Diproses"
        )

        StatisticCard(
            icon = Icons.Default.DateRange,
            count = statistics.completedJobs,
            label = "Pekerjaan yang Telah Selesai"
        )
    }
}

@Composable
private fun StatisticCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF2D8CFF),
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = count.toString(),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}

private fun formatCurrency(amount: Long): String {
    return String.format("%,d", amount).replace(",", ".")
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    HomepageTukang()
}