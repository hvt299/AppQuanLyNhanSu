package com.example.quanlynhansu.firebase

import java.security.MessageDigest

fun encodeToMD5(password: String): String {
    val md = MessageDigest.getInstance("MD5")
    md.update(password.toByteArray())
    val byteData = md.digest()

    // convert the byte to hex format
    val hexString = StringBuilder()
    for (byte in byteData) {
        val hex = Integer.toHexString(0xff and byte.toInt())
        if (hex.length == 1) hexString.append('0')
        hexString.append(hex)
    }
    return hexString.toString()
}