package com.example.gold.memowithnodejs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gold on 2017. 3. 24..
 */

public class Remote {

    public static String postJson(String siteUrl, String data){
        String result = "";
        if(!siteUrl.startsWith("http")){
            siteUrl = "http://" + siteUrl;
        }
        try {
            URL url = new URL(siteUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //-------------- post 데이터 전송처리 ----------------
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();

            os.write(data.getBytes());

            os.flush();
            os.close();
            // -------------------------------------------------
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 서버연결로 부터 스트림을 얻고, 버퍼래퍼로 감싼다
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // 반복문을 돌면서 버퍼의 데이터를 읽어온다
                StringBuilder temp = new StringBuilder();
                String lineOfData = "";
                while ((lineOfData = br.readLine()) != null) {
                    temp.append(lineOfData);
                }
                result = temp.toString();
            } else {
                result = "Error Code : " + responseCode;
            }
        }catch(Exception e){
            result = "Exception :" + e.getMessage();
        }

        return result;
    }

    public static String getUrlByGet(String siteUrl){
        String result = "";
        if(!siteUrl.startsWith("http")){
            siteUrl = "http://" + siteUrl;
        }
        try {
            URL url = new URL(siteUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 서버연결로 부터 스트림을 얻고, 버퍼래퍼로 감싼다
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // 반복문을 돌면서 버퍼의 데이터를 읽어온다
                StringBuilder temp = new StringBuilder();
                String lineOfData = "";
                while ((lineOfData = br.readLine()) != null) {
                    temp.append(lineOfData);
                }
                result = temp.toString();
            } else {
                result = "Error Code : " + responseCode;
            }
        }catch(Exception e){
            result = "Exception :" + e.getMessage();
        }

        return result;
    }
}
