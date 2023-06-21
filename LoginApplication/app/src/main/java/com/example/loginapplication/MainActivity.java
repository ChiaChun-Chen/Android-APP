package com.example.loginapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.sql.Timestamp;
import java.util.logging.XMLFormatter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    String challenge = "Rf0KFLkuCpwUoGcvdvVo3TyYmvqSV0rBUFxv2sDa";
    String cookie = "";
    String publicKey = "eWi1PFI2XKAVfT30C08UQfTKgKCPUrUi6FI5X5Pq";
    String userPassword = "0987uiop";
    String timeStamp = "1677721112867";
    String loginPassword_str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            FileReader fileReader = new FileReader("fileInRes.txt");
            /*BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp = "";
            int c;
            while (bufferedReader.ready()){
                System.out.println(bufferedReader.readLine());
            }
            Log.d("parseFile", "ParseXML: "+temp);
            fileReader.close();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            FormBody formBody = new FormBody.Builder()
                    .add("Action", "request")
                    .add("Username", "Admin")
                    .build();

            System.out.println(MediaType.parse("text/xml; charset=utf-8"));


            RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"),
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soap:Envelope>\n" +
                            "    <soap:Body>\n" +
                            "        <Login>\n" +
                            "            <Action>request</Action>\n" +
                            "            <Username>Admin</Username>\n" +
                            "            <LoginPassword/>\n" +
                            "            <Captcha/>\n" +
                            "            <SaltHash/>\n" +
                            "        </Login>\n" +
                            "    </soap:Body>\n" +
                            "</soap:Envelope>");
            //System.out.println(requestBody.contentType());
            Request request = new Request.Builder()
                    .url("http://192.168.0.1/DHMAPI/")
                    .addHeader("Host", "192.168.0.1")
                    .addHeader("Content-Type", "text/xml; charset=utf-8")
                    .addHeader("API-Action", "Login")
                    //.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .post(requestBody)
                    .build();

            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
                System.out.println(buffer.readUtf8());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println();

            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                String responseString = response.body().string();

                ParseXML parseXML = new ParseXML(responseString);
                challenge = parseXML.challenge;
                cookie = parseXML.cookie;
                publicKey = parseXML.publicKey;

                LoginPassword loginPassword = new LoginPassword(
                        "Login", challenge, publicKey, userPassword, timeStamp);
                loginPassword.loginAdmin();
                loginPassword_str = loginPassword.getLogin_password();
                System.out.println(responseString);

                new Thread(runnable_step2).start();
            } catch (IOException | ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }

        }
    };

    Runnable runnable_step2 = new Runnable() {
        @Override
        public void run() {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"),
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                            "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "<soap:Body>\n" +
                            " <Login >\n" +
                            " <Action>login</Action>\n" +
                            " <Username>Admin</Username>\n" +
                            " <LoginPassword>"+loginPassword_str+"\n" +
                            "</LoginPassword>\n" +
                            " <Captcha/>\n" +
                            " </Login>\n" +
                            "</soap:Body>\n" +
                            "</soap:Envelope>");

            Request request = new Request.Builder()
                    .url("http://192.168.0.1/DHMAPI/")
                    .addHeader("Host", "192.168.0.1")
                    .addHeader("Content-Type", "text/xml; charset=utf-8")
                    .addHeader("API-Action", "Login")
                    .addHeader("Cookie", "uid="+cookie)
                    .post(requestBody)
                    .build();

            Call call = client.newCall(request);

            try {
                Response response = call.execute();
                System.out.println(response.body().string());

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println(timestamp.getTime());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

}