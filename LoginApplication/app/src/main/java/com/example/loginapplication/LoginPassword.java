package com.example.loginapplication;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoginPassword {

    private final String challenge;
    private final String public_key;
    private final String password;
    private String private_key;
    private String login_password;
    private final String timestamp;
    private final String api_action;
    private String auth_code;

    public LoginPassword(String action, String challenge, String pubKey, String pwd, String timestamp){
        this.api_action = action;
        this.challenge = challenge;
        this.public_key = pubKey;
        this.password = pwd;
        this.timestamp = timestamp;
    }

    public String HMAC_SHA256(String data, String key) {

        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);
            byte[] bytes = sha256_HMAC.doFinal(data.getBytes());
            String hash = byteArrayToHexString(bytes);
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    private static String byteArrayToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (int i = 0; bytes!=null&&i<bytes.length; i++) {
            temp = Integer.toHexString(bytes[i]&0xFF);
            if(temp.length()==1){
                stringBuilder.append('0');
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString().toUpperCase();
    }

    public void loginAdmin(){
        String keyForPrivateKey = public_key + password;
        String DataForAuthCode = timestamp + api_action;
        this.private_key = HMAC_SHA256(challenge, keyForPrivateKey);
        //65D9F3F57CF71D84DF7E663A7B104EBC40724F0BC0B042B959051B2DA27E94F7
        this.login_password = HMAC_SHA256(challenge, private_key);
        //DD754224E434FD7A1676CFE934287BE57461E6E7737AF9BBD375A66401255C7B
        System.out.println(DataForAuthCode);
        //1429524355367SetWanSettings
        this.auth_code = HMAC_SHA256(DataForAuthCode, private_key);
        //F1E59F6A8A54F898F3F136E2D171408E5BB591B77D888C575855131ADB230CEC
    }

    public String getLogin_password(){
        return login_password;
    }
}
