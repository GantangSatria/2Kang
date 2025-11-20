package com.gsatria.a2kang.screen.tukang.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow

@Composable
fun EditLayananScreen(
    onBackClick: () -> Unit = {}
) {

    var jenisPekerjaan by remember { mutableStateOf("Perbaikan Listrik") }
    var keahlianInput by remember { mutableStateOf("") }
    var hargaLayanan by remember { mutableStateOf("Rp 10.000") }
    var deskripsi by remember { mutableStateOf("") }

    val bluePrimary = Color(0xFF2D8CFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        EditLayananTopBar(onBackClick)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                FormLabel(text = "Jenis Pekerjaan")
                Spacer(modifier = Modifier.height(8.dp))
                RoundTextField(
                    value = jenisPekerjaan,
                    onValueChange = { jenisPekerjaan = it },
                    placeholder = "Perbaikan Listrik"
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel(text = "Keterampilan dan Keahlian")
                Spacer(modifier = Modifier.height(8.dp))
                RoundTextField(
                    value = keahlianInput,
                    onValueChange = { keahlianInput = it },
                    placeholder = "Tambahkan keahlian"
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowLayoutStatic()

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel(text = "Harga Layanan")
                Spacer(modifier = Modifier.height(8.dp))
                RoundTextField(
                    value = hargaLayanan,
                    onValueChange = { hargaLayanan = it },
                    placeholder = "Rp 10.000"
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormLabel(text = "Deskripsi")
                Spacer(modifier = Modifier.height(8.dp))
                DescriptionTextArea(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    placeholder = "Deskripsikan diri kamu..."
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        PrimaryActionButton(
            text = "Simpan",
            blueColor = bluePrimary,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowLayoutStatic() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Daftar Keahlian yang terlihat
        listOf("Saluran Air", "Listrik", "Perabotan", "Perbaikan AC").forEach { skill ->
            // Menentukan warna statis sesuai desain (Biru muda untuk 2 chip awal, Abu-abu untuk sisanya)
            val isSelectedStyle = skill == "Saluran Air" || skill == "Listrik" || skill == "Perabotan" || skill == "Perbaikan AC"

            SkillChipStatic(skill = skill, isSelectedStyle = isSelectedStyle)
        }
    }
}

@Composable
fun SkillChipStatic(skill: String, isSelectedStyle: Boolean = false) {

    val backgroundColor = if (isSelectedStyle) Color(0xFFE0E0FF) else Color(0xFFF0F0F0)
    val textColor = if (isSelectedStyle) Color(0xFF1E80FF) else Color(0xFF000000)
    val borderColor = if (isSelectedStyle) Color(0xFF1E80FF) else Color.Transparent

    Box(
        modifier = Modifier
            .height(32.dp)
            .background(backgroundColor, shape = RoundedCornerShape(100.dp))
            .border(1.dp, borderColor, RoundedCornerShape(100.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp), // Hapus .clickable()
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
fun EditLayananTopBar(onBackClick: () -> Unit) {
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
            text = "Edit Layanan",
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
fun FormLabel(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF000000),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
}

@Composable
fun RoundTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, style = TextStyle(fontSize = 14.sp, fontFamily = SoraFontFamily, color = Color.Gray)) },
        shape = RoundedCornerShape(100.dp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .border(width = 1.dp, color = Color(0xFFD9D9D9), shape = RoundedCornerShape(100.dp))
            .background(Color.White, shape = RoundedCornerShape(100.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}

@Composable
fun DescriptionTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, style = TextStyle(fontSize = 14.sp, fontFamily = SoraFontFamily, color = Color.Gray)) },
        shape = RoundedCornerShape(12.dp),
        minLines = 4,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 140.dp)
            .border(width = 1.dp, color = Color(0xFFD9D9D9), shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}

@Composable
fun PrimaryActionButton(text: String, blueColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = blueColor,
        ),
        shape = RoundedCornerShape(16.dp),
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
