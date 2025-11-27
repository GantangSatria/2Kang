package com.gsatria.a2kang.screen.tukang.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import com.gsatria.a2kang.model.response.OrderResponse
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import com.gsatria.a2kang.viewmodel.TukangOrderViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PermintaanScreen(
    onBackClick: () -> Unit = {},
    viewModel: TukangOrderViewModel = viewModel()
) {
    val orders = viewModel.orders.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()
    val actionSuccess = viewModel.actionSuccess.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    // Show success message
    LaunchedEffect(actionSuccess.value) {
        if (actionSuccess.value != null) {
            // Message will be shown via Snackbar or similar
            kotlinx.coroutines.delay(2000)
            viewModel.clearMessages()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Top Bar
        TopBar(onBackClick = onBackClick)

        // Success/Error Messages
        if (actionSuccess.value != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(
                    text = actionSuccess.value ?: "",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            }
        }

        if (error.value != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    text = error.value ?: "Terjadi kesalahan",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            }
        }

        // Content
        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (orders.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tidak ada permintaan",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { viewModel.loadOrders() }) {
                        Text("Refresh")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(orders.value) { order ->
                    OrderRequestCard(
                        order = order,
                        onAccept = { viewModel.acceptOrder(order.id) },
                        onReject = { viewModel.rejectOrder(order.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
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
                text = "Permintaan",
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
private fun OrderRequestCard(
    order: OrderResponse,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Profile and Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Profile Picture (Dummy)
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "Profile",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Gray
                    )
                }

                Text(
                    text = "Ganteng Satria", // TODO: Get user name from order
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Address
            Text(
                text = order.address,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Date and Time
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF2D8CFF)
                )
                Text(
                    text = formatDateTime(order.scheduledAt),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SoraFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Reject Button
                OutlinedButton(
                    onClick = onReject,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Tolak",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = SoraFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                // Accept Button
                Button(
                    onClick = onAccept,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D8CFF)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Setuju",
                        style = TextStyle(
                            fontSize = 14.sp,
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

private fun formatDateTime(dateTimeString: String): String {
    return try {
        // Parse ISO8601 format
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateTimeString) ?: return dateTimeString

        // Format to Indonesian
        val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy 'Pukul' HH.mm", Locale("id", "ID"))
        outputFormat.format(date)
    } catch (e: Exception) {
        // Try alternative format
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val date = inputFormat.parse(dateTimeString) ?: return dateTimeString
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy 'Pukul' HH.mm", Locale("id", "ID"))
            outputFormat.format(date)
        } catch (e2: Exception) {
            dateTimeString
        }
    }
}