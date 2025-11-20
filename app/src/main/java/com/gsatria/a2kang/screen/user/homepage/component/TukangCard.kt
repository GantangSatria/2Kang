package com.gsatria.a2kang.screen.user.homepage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.model.domain.Tukang

@Composable
fun TukangCard(
    modifier: Modifier = Modifier,
    tukang: Tukang,
    onItemClick: (Tukang) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(tukang) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // Foto placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Image",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            // Rating
            Text(
                text = "★ ${String.format("%.1f", tukang.rating ?: 0f)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1ABC9C),
                modifier = Modifier.padding(top = 12.dp)
            )

            // Name
            Text(
                text = tukang.name ?: "Unknown",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Category badge
            if (!tukang.category.isNullOrBlank()) {
                BadgeItem(
                    text = tukang.category,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Services
            if (!tukang.services.isNullOrBlank()) {
                Text(
                    text = tukang.services,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            // Bio
            if (!tukang.bio.isNullOrBlank()) {
                Text(
                    text = tukang.bio,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BadgeItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF1ABC9C).copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1ABC9C)
        )
    }
}
