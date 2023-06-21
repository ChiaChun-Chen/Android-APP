package com.example.loginapplication;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParseXML {


    public String challenge;
    public String cookie;
    public String publicKey;

    public ParseXML() throws ParserConfigurationException, IOException, SAXException {
        FileInputStream fileInputStream = new FileInputStream("fileInRes.txt");
        String temp = "";
        int c;
        while ((c = fileInputStream.read()) != -1){
            temp = temp+Character.toString((char) c);
        }
        Log.d("parseFile", "ParseXML: "+temp);
        fileInputStream.close();
    }

    public ParseXML(String str) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(str));
        Document document = documentBuilder.parse(inputSource);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("LoginResponse");
        setResponseVar(nodeList);
    }

    private void setResponseVar(NodeList nodeList){
        this.challenge = nodeList.item(0).getChildNodes().item(1).getTextContent();
        this.cookie = nodeList.item(0).getChildNodes().item(2).getTextContent();
        this.publicKey = nodeList.item(0).getChildNodes().item(3).getTextContent();
    }
}
