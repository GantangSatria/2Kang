package com.gsatria.a2kang.screen.user.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import com.gsatria.a2kang.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OrderConfirmationScreen(
    tukangId: Int,
    onBackClick: () -> Unit = {},
    onOrderSuccess: () -> Unit = {},
    viewModel: OrderViewModel = viewModel()
) {
    val orderCreated = viewModel.orderCreated.collectAsState()
    val loading = viewModel.loading.collectAsState()
    val error = viewModel.error.collectAsState()

    // State for date selection
    val selectedDate = remember { mutableStateOf<Date?>(null) }
    val selectedTime = remember { mutableStateOf<String?>(null) }

    // State for address
    var address by remember { mutableStateOf("Jl. MT. Haryono No. 17A, Dinoyo") }
    var notes by remember { mutableStateOf("") }

    // State for payment method
    var selectedPaymentMethod by remember { mutableStateOf("cash") }

    // Calculate dates (next 7 days)
    val dates = remember {
        val calendar = Calendar.getInstance()
        val dateList = mutableListOf<Date>()
        repeat(7) {
            dateList.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        dateList
    }

    // Time slots
    val timeSlots = listOf("09.00", "10.00", "11.00", "14.00", "15.00", "16.00")

    // Calculate price
    val basePrice = 100000.0
    val travelFee = 7000.0
    val adminFee = 5000.0
    val totalPrice = basePrice + travelFee + adminFee

    LaunchedEffect(orderCreated.value) {
        if (orderCreated.value != null) {
            onOrderSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        TopBar(onBackClick = onBackClick)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                // Pilih Tanggal & Waktu Section
                DateTimeSelectionSection(
                    dates = dates,
                    selectedDate = selectedDate.value,
                    onDateSelected = { selectedDate.value = it },
                    timeSlots = timeSlots,
                    selectedTime = selectedTime.value,
                    onTimeSelected = { selectedTime.value = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Alamat Section
                AddressSection(
                    address = address,
                    onAddressChange = { address = it },
                    notes = notes,
                    onNotesChange = { notes = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Estimasi Biaya Section
                CostEstimateSection(
                    basePrice = basePrice,
                    travelFee = travelFee,
                    adminFee = adminFee,
                    totalPrice = totalPrice
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Metode Pembayaran Section
                PaymentMethodSection(
                    selectedMethod = selectedPaymentMethod,
                    onMethodSelected = { selectedPaymentMethod = it }
                )
            }
        }

        // Error Message
        if (error.value != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    if (selectedDate.value != null && selectedTime.value != null) {
                        val calendar = Calendar.getInstance().apply {
                            time = selectedDate.value!!
                            val (hour, minute) = selectedTime.value!!.split(".").map { it.toInt() }
                            set(Calendar.HOUR_OF_DAY, hour)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, 0)
                        }
                        viewModel.createOrder(
                            tukangId = tukangId,
                            scheduledAt = calendar.time,
                            address = address,
                            paymentMethod = selectedPaymentMethod,
                            price = totalPrice,
                            notes = notes.takeIf { it.isNotBlank() }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !loading.value && selectedDate.value != null && selectedTime.value != null && address.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2D8CFF)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (loading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Konfirmasi Pesanan",
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
                text = "Konfirmasi Pesanan",
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DateTimeSelectionSection(
    dates: List<Date>,
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    timeSlots: List<String>,
    selectedTime: String?,
    onTimeSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pilih Tanggal & Waktu",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dates.take(4).forEach { date ->
                    val dateFormat = SimpleDateFormat("EEEE dd", Locale("id", "ID"))
                    val dayName = dateFormat.format(date).split(" ")[0]
                    val dayNumber = dateFormat.format(date).split(" ")[1]
                    val isSelected = selectedDate == date

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .background(
                                color = if (isSelected) Color(0xFF2D8CFF) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = if (isSelected) Color.Transparent else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onDateSelected(date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dayName,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.White else Color.Gray
                            )
                            Text(
                                text = dayNumber,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time Selection
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                timeSlots.forEach { time ->
                    val isSelected = selectedTime == time
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp)
                            .background(
                                color = if (isSelected) Color(0xFF2D8CFF) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = if (isSelected) Color.Transparent else Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onTimeSelected(time) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddressSection(
    address: String,
    onAddressChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Alamat Kamu",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address,
                onValueChange = onAddressChange,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Red
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2D8CFF),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = onNotesChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Catatan Lainnya (Opsional)",
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2D8CFF),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
private fun CostEstimateSection(
    basePrice: Double,
    travelFee: Double,
    adminFee: Double,
    totalPrice: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Estimasi Biaya",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Biaya Layanan Dasar",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Rp ${String.format("%,.0f", basePrice)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Biaya Perjalanan",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Rp ${String.format("%,.0f", travelFee)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Biaya Admin",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Rp ${String.format("%,.0f", adminFee)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Biaya",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Rp ${String.format("%,.0f", totalPrice)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D8CFF)
                )
            }
        }
    }
}

@Composable
private fun PaymentMethodSection(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Metode Pembayaran",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentMethodOption(
                method = "cash",
                label = "Bayar di Tempat",
                description = "Bayar langsung kepada tukang setelah layanan selesai.",
                isSelected = selectedMethod == "cash",
                onSelected = { onMethodSelected("cash") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentMethodOption(
                method = "transfer",
                label = "Transfer Bank",
                description = "Bayar langsung kepada tukang setelah layanan selesai.",
                isSelected = selectedMethod == "transfer",
                onSelected = { onMethodSelected("transfer") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentMethodOption(
                method = "qris",
                label = "QRIS",
                description = "Bayar langsung kepada tukang setelah layanan selesai.",
                isSelected = selectedMethod == "qris",
                onSelected = { onMethodSelected("qris") }
            )
        }
    }
}

@Composable
private fun PaymentMethodOption(
    method: String,
    label: String,
    description: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF2D8CFF)
            )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}