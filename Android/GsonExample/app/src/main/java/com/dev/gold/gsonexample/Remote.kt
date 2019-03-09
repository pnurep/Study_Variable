package com.dev.gold.gsonexample

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object Remote {

    fun getData(inputUrl: String): String {

        val sb = StringBuilder()
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        var connection: HttpURLConnection? = null

        try {
            val url = URL(inputUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            when (connection.responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    isr = InputStreamReader(connection.inputStream)
                    br = BufferedReader(isr)
                    var temp: String

                    while (br.readLine().also { temp = it } != null) {
                        sb.append(temp).append("\n")
                    }

                    br.close()
                    isr.close()

                }
                else -> Log.e("ServerError", connection.responseCode.toString())
            }

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Error", e.toString());
        } finally {
            isr?.close()
            br?.close()
            connection?.disconnect()
        }

        return sb.toString()
    }

}