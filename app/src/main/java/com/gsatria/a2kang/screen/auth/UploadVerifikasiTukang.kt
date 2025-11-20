package com.gsatria.a2kang.screen.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gsatria.a2kang.R
import com.gsatria.a2kang.ui.theme.SoraFontFamily

@Composable
fun UploadVerifikasiTukangScreen(
    onBackClick: () -> Unit = {},
    onSaveAndContinue: (Uri?) -> Unit = {}
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val orangeColor = Color(0xFFFF9800)
    val blueColor = Color(0xFF2D8CFF)
    val lightGrayColor = Color(0xFFF5F5F5)
    val borderGrayColor = Color(0xFFE0E0E0)

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        TopBarVerifikasi(
            onBackClick = onBackClick
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Heading "Pengalaman"
            Text(
                text = "Pengalaman",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Instructions
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                InstructionItem("Isi dengan format JPG, PNG, atau PDF")
                Spacer(modifier = Modifier.height(8.dp))
                InstructionItem("Tulis dengan format nama_pengalaman.pdf")
                Spacer(modifier = Modifier.height(8.dp))
                InstructionItem("Data tidak lebih dari 2 MB")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // File Upload Area
            FileUploadArea(
                selectedFileUri = selectedFileUri,
                orangeColor = orangeColor,
                lightGrayColor = lightGrayColor,
                borderGrayColor = borderGrayColor,
                onAddFileClick = {
                    filePickerLauncher.launch("*/*")
                }
            )
        }

        // Bottom Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Button(
                onClick = {
                    onSaveAndContinue(selectedFileUri)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blueColor
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Simpan dan lanjutkan",
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

@Composable
private fun TopBarVerifikasi(
    onBackClick: () -> Unit
) {
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
            text = "Verifikasi Berkas",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF000000)
            )
        )
    }
}

@Composable
private fun InstructionItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "•",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SoraFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        )
    }
}

@Composable
private fun FileUploadArea(
    selectedFileUri: Uri?,
    orangeColor: Color,
    lightGrayColor: Color,
    borderGrayColor: Color,
    onAddFileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = lightGrayColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = borderGrayColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Orange Circle with Plus Icon
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = orangeColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add File Button
        Button(
            onClick = onAddFileClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = orangeColor
            ),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = orangeColor
            )
        ) {
            Text(
                text = "Tambah File",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = orangeColor
                )
            )
        }

        // Show selected file name if file is selected
        selectedFileUri?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "File dipilih: ${it.lastPathSegment ?: "File"}",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = SoraFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUploadVerifikasiTukangScreen() {
    UploadVerifikasiTukangScreen()
}
