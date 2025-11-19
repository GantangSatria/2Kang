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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.ui.theme.SoraFontFamily

// Data Models - akan diisi dari backend
data class TukangProfile(
    val name: String,
    val profileImageUrl: String? = null,
    val bio: String,
    val rate: Long // dalam rupiah
)

data class JobStatistics(
    val unprocessedJobs: Int,
    val completedJobs: Int
)

data class UpcomingJob(
    val id: Int,
    val title: String,
    val date: String, // format: "Senin, 17 November 2025"
    val time: String // format: "14.00"
)

data class HomepageTukangData(
    val profile: TukangProfile,
    val statistics: JobStatistics,
    val upcomingJobs: List<UpcomingJob>
)

@Composable
fun HomepageTukang(
    data: HomepageTukangData? = null,
    onEditProfileClick: () -> Unit = {},
    onJobClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Default data untuk preview/testing
    val homepageData = data ?: HomepageTukangData(
        profile = TukangProfile(
            name = "Budi Santoso",
            bio = "Saya tukang yang berpengalaman dan dapat diandalkan dengan...",
            rate = 100000L
        ),
        statistics = JobStatistics(
            unprocessedJobs = 6,
            completedJobs = 30
        ),
        upcomingJobs = listOf(
            UpcomingJob(
                id = 1,
                title = "Perbaikan AC",
                date = "Senin, 17 November 2025",
                time = "14.00"
            ),
            UpcomingJob(
                id = 2,
                title = "Perbaikan Saluran Air",
                date = "Senin, 17 November 2025",
                time = "16.00"
            )
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        item {
            // Header Section
            HeaderSection(
                name = homepageData.profile.name,
                profileImageUrl = homepageData.profile.profileImageUrl
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // Profile Card
            ProfileCard(
                profile = homepageData.profile,
                onEditClick = onEditProfileClick,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            // Statistics Cards
            StatisticsCards(
                statistics = homepageData.statistics,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            // Upcoming Jobs Section Header
            Text(
                text = "Pekerjaan Mendatang",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Upcoming Jobs List
        items(homepageData.upcomingJobs) { job ->
            UpcomingJobCard(
                job = job,
                onClick = { onJobClick(job.id) },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun HeaderSection(
    name: String,
    profileImageUrl: String?,
    modifier: Modifier = Modifier
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

        // Profile Picture (Circular)
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUrl != null) {
                // TODO: Load image from URL using Coil or similar
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
            // Profile Picture (Square with rounded corners)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (profile.profileImageUrl != null) {
                    // TODO: Load image from URL
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

            // Edit Button
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
        // Unprocessed Jobs Card
        StatisticCard(
            icon = Icons.Default.DateRange,
            count = statistics.unprocessedJobs,
            label = "Pekerjaan yang Belum Diproses"
        )

        // Completed Jobs Card
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

@Composable
private fun UpcomingJobCard(
    job: UpcomingJob,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = job.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = job.date,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = job.time,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )
            }

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Detail",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Helper function untuk format currency
private fun formatCurrency(amount: Long): String {
    return String.format("%,d", amount).replace(",", ".")
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    HomepageTukang()
}