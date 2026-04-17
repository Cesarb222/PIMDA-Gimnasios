package com.example.appgimnasio.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

//Esta clase la he creado para generar el codigo QR
class ZXingQR {
    //esta funcion recibe el contenido o datos, y el tamaño de la imagen en este caso 512x512
    //Nos devuelve una imagen o null si falla
    fun generarQr(contenido: String, size: Int = 512): Bitmap? {
        return try {
            //Crea el generador QR el objeto nos permite convertir el texto en un qr
            val writer = QRCodeWriter()
            //Convierte el text en una matriz de bits
            val bitMatrix = writer.encode(contenido, BarcodeFormat.QR_CODE, size, size)
            //Guardamos el ancho y el alto
            val width = bitMatrix.width
            val height = bitMatrix.height
            //Se crea la imagen
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            // y se pinta la imagen pixel a pixel
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            null
        }
    }
}