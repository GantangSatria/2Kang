package com.gsatria.a2kang.core.util

import android.util.Base64
import org.json.JSONObject

object JwtUtils {
    /**
     * Decode JWT token dan extract role dari payload
     * @param token JWT token string
     * @return role dari token ("user" atau "tukang"), null jika tidak ditemukan
     */
    fun getRoleFromToken(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null

            // Decode payload (bagian kedua)
            val payload = parts[1]
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
            val decodedString = String(decodedBytes, Charsets.UTF_8)
            
            // Parse JSON payload
            val jsonObject = JSONObject(decodedString)
            
            // Extract role
            jsonObject.optString("role", null)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
