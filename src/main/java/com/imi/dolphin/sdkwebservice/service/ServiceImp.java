/**
 * Copyright (c) 2014 InMotion Innovation Technology. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of InMotion
 * Innovation Technology. ("Confidential Information").<BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * IMI; other than in accordance with the written permission of IMI. <BR>
 *
 *
 */
package com.imi.dolphin.sdkwebservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imi.dolphin.sdkwebservice.builder.ButtonBuilder;
import com.imi.dolphin.sdkwebservice.builder.CarouselBuilder;
import com.imi.dolphin.sdkwebservice.builder.FormBuilder;
import com.imi.dolphin.sdkwebservice.builder.ImageBuilder;
import com.imi.dolphin.sdkwebservice.builder.QuickReplyBuilder;
import com.imi.dolphin.sdkwebservice.form.Datum;
import com.imi.dolphin.sdkwebservice.form.DatumComplaint;
import com.imi.dolphin.sdkwebservice.form.DatumRequest;
import com.imi.dolphin.sdkwebservice.form.FormComplaint;
import com.imi.dolphin.sdkwebservice.form.FormRequest;
import com.imi.dolphin.sdkwebservice.form.Formcuti;
import com.imi.dolphin.sdkwebservice.model.ButtonTemplate;
import com.imi.dolphin.sdkwebservice.model.EasyMap;
import com.imi.dolphin.sdkwebservice.model.ExtensionRequest;
import com.imi.dolphin.sdkwebservice.model.ExtensionResult;
import com.imi.dolphin.sdkwebservice.model.MailModel;
import com.imi.dolphin.sdkwebservice.model.MonthBuilder;
import com.imi.dolphin.sdkwebservice.param.ParamSdk;
import com.imi.dolphin.sdkwebservice.property.AppProperties;
import com.imi.dolphin.sdkwebservice.token.Token;
import com.imi.dolphin.sdkwebservice.util.OkHttpUtil;
import fr.plaisance.bitly.Bit;
import fr.plaisance.bitly.Bitly;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.slf4j.LoggerFactory;
import okio.ByteString;
import com.sendgrid.*;

/**
 *
 * @author reja
 *
 */
@Service
public class ServiceImp implements IService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceImp.class);

    public static final String OUTPUT = "output";
    private static final String SAMPLE_IMAGE_PATH = "https://goo.gl/SHdL8D";
    private static final String Image_cuti = "https://image.ibb.co/eAshTV/bot.jpg";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String CONSTANT_SPLIT_SYNTAX = "&split&";
    private static final String API_PARAM_NAME_COLLNAME = "?collName=";
    private static final String API_PARAM_NAME_FIELDNAME = "&fieldName=";
    private static final String API_PARAM_NAME_LATLONG = "&latlong=";
    private static final String API_PARAM_NAME_DISTANCE = "&d=";
    private static final String API_PARAM_NAME_COUNT = "&count=";
    private int type_index = 0;
    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    @Autowired
    AppProperties appProperties;

    @Autowired
    IMailService svcMailService;

    /**
     * Get parameter value from request body parameter
     *
     * @param extensionRequest
     * @param name
     * @return
     */
    private String getEasyMapValueByName(ExtensionRequest extensionRequest, String name) {
        EasyMap easyMap = extensionRequest.getParameters().stream().filter(x -> x.getName().equals(name)).findAny()
                .orElse(null);
        if (easyMap != null) {
            return easyMap.getValue();
        }
        return "";
    }

    /*
	 * Sample Srn status with static result
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getSrnResult(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getSrnResult(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        Map<String, String> output = new HashMap<>();
        StringBuilder respBuilder = new StringBuilder();
        respBuilder.append(
                "20-July-2018 16:10:32 Ahmad Mahatir Ridwan - PIC sudah onsite cek problem(printer nyala-mati)\n");
        respBuilder.append("PIC troubleshoot. restart printer(NOK), ganti kabel power(NOK)\n");
        respBuilder.append("PIC akan eskalasi ke vendor terkait.");
        output.put(OUTPUT, respBuilder.toString());
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Sample Customer Info with static result
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.imi.dolphin.sdkwebservice.service.IService#getCustomerInfo(com.imi.
	 * dolphin.sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getCustomerInfo(ExtensionRequest extensionRequest) {
        String account = getEasyMapValueByName(extensionRequest, "account");
        String name = getEasyMapValueByName(extensionRequest, "name");
        Map<String, String> output = new HashMap<>();
        StringBuilder respBuilder = new StringBuilder();
        if (account.substring(0, 1).equals("1")) {
            respBuilder.append("Ticket Number : " + extensionRequest.getIntent().getTicket().getTicketNumber() + "\n");
            respBuilder.append(" Data Customer Account " + account + "\n");
            respBuilder.append("Nama: " + name + "\n");
            respBuilder.append("Setoran tiap bulan : Rp. 500,000\n");
            respBuilder.append("Jatuh tempo berikutnya : 15 Agustus 2018");
        } else {
            respBuilder.append("Ticket Number : " + extensionRequest.getIntent().getTicket().getTicketNumber() + "\n");
            respBuilder.append(appProperties.getFormId() + " Data Customer Account " + account + "\n");
            respBuilder.append("Nama: " + name + "\n");
            respBuilder.append("Setoran tiap bulan : Rp. 1,000,000\n");
            respBuilder.append("Jatuh tempo berikutnya : 27 Agustus 2018");
        }
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        output.put(OUTPUT, respBuilder.toString());
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Modify Customer Name Entity
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#clearCustomerName(com.imi.
	 * dolphin.sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult modifyCustomerName(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String name = getEasyMapValueByName(extensionRequest, "name");
        if (name.equalsIgnoreCase("reja")) {
            clearEntities.put("name", "budi");
            extensionResult.setEntities(clearEntities);
        }
        return extensionResult;
    }

    /*
	 * Sample Product info with static value
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getProductInfo(com.imi.dolphin
	 * .sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getProductInfo(ExtensionRequest extensionRequest) {
        String model = getEasyMapValueByName(extensionRequest, "model");
        String type = getEasyMapValueByName(extensionRequest, "type");

        Map<String, String> output = new HashMap<>();
        StringBuilder respBuilder = new StringBuilder();

        respBuilder.append("Untuk harga mobil " + model + " tipe " + type + " adalah 800,000,000\n");
        respBuilder.append("Jika kak {customer_name} tertarik, bisa klik tombol dibawah ini. \n");
        respBuilder.append("Maka nanti live agent kami akan menghubungi kakak ;)");

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        output.put(OUTPUT, respBuilder.toString());
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Get messages from third party service
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getMessageBody(com.imi.dolphin
	 * .sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getMessageBody(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        StringBuilder respBuilder = new StringBuilder();

        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/comments").get().build();
            Response response = okHttpUtil.getClient().newCall(request).execute();

            JSONArray jsonArray = new JSONArray(response.body().string());

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String message = jsonObject.getString("body");
            respBuilder.append(message);
        } catch (Exception e) {

        }

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        output.put(OUTPUT, respBuilder.toString());
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Generate quick replies output
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IFormService#getQuickReplies(com.imi.
	 * dolphin.sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getQuickReplies(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        QuickReplyBuilder quickReplyBuilder = new QuickReplyBuilder.Builder("Hello").add("Hello World", "hello world")
                .add("Hello Java", "B0F63CE1-F16F-4761-8881-F44C95D2792F").build();
        output.put(OUTPUT, quickReplyBuilder.string());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Generate Forms
	 *
	 * (non-Javadoc)
	 * 
	 * @see com.imi.dolphin.sdkwebservice.service.IService#getForms(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getForms(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        FormBuilder formBuilder = new FormBuilder(appProperties.getFormIdTest());

        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("Test Form");
        button.setSubTitle("Subtitle is here");
        button.setPictureLink(appProperties.getAtmUrl());
        button.setPicturePath(appProperties.getAtmUrl());
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Label here");
        bookAction.setValue(formBuilder.build());
        actions.add(bookAction);
        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        output.put(OUTPUT, buttonBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Generate buttons output
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getButtons(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getButtons(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("This is title");
        button.setSubTitle("This is subtitle");
        button.setPictureLink(SAMPLE_IMAGE_PATH);
        button.setPicturePath(SAMPLE_IMAGE_PATH);
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Label");
        bookAction.setValue("Payload");
        actions.add(bookAction);
        button.setButtonValues(actions);

        ButtonBuilder buttonBuilder = new ButtonBuilder(button);
        output.put(OUTPUT, buttonBuilder.build());

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Generate Carousel
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getCarousel(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getCarousel(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        ButtonTemplate button = new ButtonTemplate();
        button.setPictureLink(appProperties.getAtmUrl());
        button.setPicturePath(appProperties.getAtmUrl());
        button.setTitle("Go 1");
        button.setSubTitle("Button Carousel 1");
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Go 1");
        bookAction.setValue("Thanks");
        actions.add(bookAction);
        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        ButtonTemplate button2 = new ButtonTemplate();
        button2.setPictureLink(appProperties.getAtmUrl());
        button2.setPicturePath(appProperties.getAtmUrl());
        button2.setTitle("This is title 2");
        button2.setSubTitle("This is subtitle 2");
        List<EasyMap> actions2 = new ArrayList<>();
        EasyMap bookAction2 = new EasyMap();
        bookAction2.setName("Label 2");
        bookAction2.setValue("Payload 2");
        actions2.add(bookAction2);
        button2.setButtonValues(actions2);
        ButtonBuilder buttonBuilder2 = new ButtonBuilder(button2);

        ButtonTemplate button3 = new ButtonTemplate();
        button3.setPictureLink(appProperties.getAtmUrl());
        button3.setPicturePath(appProperties.getAtmUrl());
        button3.setTitle("This is title 3");
        button3.setSubTitle("This is subtitle 3");
        button3.setButtonValues(actions2);
        ButtonBuilder buttonBuilder3 = new ButtonBuilder(button3);

        ButtonTemplate button4 = new ButtonTemplate();
        button4.setPictureLink(appProperties.getAtmUrl());
        button4.setPicturePath(appProperties.getAtmUrl());
        button4.setTitle("This is title 4");
        button4.setSubTitle("This is subtitle 4");
        button4.setButtonValues(actions2);
        ButtonBuilder buttonBuilder4 = new ButtonBuilder(button4);

        ButtonTemplate button5 = new ButtonTemplate();
        button5.setPictureLink(appProperties.getAtmUrl());
        button5.setPicturePath(appProperties.getAtmUrl());
        button5.setTitle("This is title 5");
        button5.setSubTitle("This is subtitle 5");
        button5.setButtonValues(actions2);
        ButtonBuilder buttonBuilder5 = new ButtonBuilder(button5);

        CarouselBuilder carouselBuilder = new CarouselBuilder(buttonBuilder.build(), buttonBuilder2.build(),
                buttonBuilder3.build(), buttonBuilder4.build(), buttonBuilder5.build());

        output.put(OUTPUT, carouselBuilder.build());

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Transfer ticket to agent
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#doTransferToAgent(com.imi.
	 * dolphin.sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult doTransferToAgent(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(true);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(false);
        return extensionResult;
    }

    /*
	 * Send Location
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#doSendLocation(com.imi.dolphin
	 * .sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult doSendLocation(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        QuickReplyBuilder quickReplyBuilder = new QuickReplyBuilder.Builder("")
                .add("location", "location").build();
        output.put(OUTPUT, quickReplyBuilder.string());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Generate Image
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.imi.dolphin.sdkwebservice.service.IService#getImage(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getImage(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        ButtonTemplate image = new ButtonTemplate();
        image.setPictureLink(SAMPLE_IMAGE_PATH);
        image.setPicturePath(SAMPLE_IMAGE_PATH);

        ImageBuilder imageBuilder = new ImageBuilder(image);
        output.put(OUTPUT, imageBuilder.build());

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Split bubble chat conversation
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#getSplitConversation(com.imi.
	 * dolphin.sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult getSplitConversation(ExtensionRequest extensionRequest) {
        String firstLine = "Terima kasih {customer_name}";
        String secondLine = "Data telah kami terima dan agent kami akan proses terlebih dahulu ya kak";
        Map<String, String> output = new HashMap<>();
        output.put(OUTPUT, firstLine + ParamSdk.SPLIT_CHAT + secondLine);

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /*
	 * Send mail configuration on application.properties file
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imi.dolphin.sdkwebservice.service.IService#doSendMail(com.imi.dolphin.
	 * sdkwebservice.model.ExtensionRequest)
     */
    @Override
    public ExtensionResult doSendMail(ExtensionRequest extensionRequest) {
        String recipient = getEasyMapValueByName(extensionRequest, "recipient");
        MailModel mailModel = new MailModel(recipient, "3Dolphins SDK Mail Subject", "3Dolphins SDK mail content");
        String sendMailResult = svcMailService.sendMail(mailModel);

        Map<String, String> output = new HashMap<>();
        output.put(OUTPUT, sendMailResult);
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    public Datum getForm(String bearer, String ticketNumber) {
        Datum data = new Datum();
        String baseUrl = appProperties.getUrl();
        String apiform = appProperties.getApiForm();
        String formId = appProperties.getFormIdCuti();
        String paramformId = "?formId=";
        String paramFieldName = "&fieldName=";
        String paramFieldValue = "&fieldValue=*";
        String paramStart = "&start=0";
        String paramCount = "&count=1";
        String fieldName = appProperties.getTicketNumber();

        //menggunakan ticket number
        String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue + ticketNumber + paramStart + paramCount;

        // tidak pakai ticket number
//		String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue
//				+ paramStart + paramCount;
        System.out.println(url);

        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.init(true);

        Request request = new Request.Builder().url(url).get().addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + bearer).build();

        try {
            Response response = okHttpUtil.getClient().newCall(request).execute();

            Formcuti fct = new Formcuti();
            String JsonString = response.body().string();
            // System.out.println("getform, Jsonstring : "+JsonString);

            Gson gson = new Gson();
            fct = gson.fromJson(JsonString, Formcuti.class);
            // System.out.println("From cuti data : "+fct);

            String jsonfct = gson.toJson(fct.getData());
            // System.out.println(jsonfct);

            // ambil json array
            JSONArray arrayJson = new JSONArray(jsonfct);
            // System.out.println("Array json dari form cuti data : "+arrayJson);
            JSONObject jsonObjek = arrayJson.getJSONObject(0);
            // System.out.println("Object json dari form cuti data : "+jsonObjek);

            data = gson.fromJson(jsonObjek.toString(), Datum.class);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    public DatumComplaint getFormComplaint(String bearer, String ticketNumber) {
        DatumComplaint data = new DatumComplaint();
        String baseUrl = appProperties.getUrl();
        String apiform = appProperties.getApiForm();
        String formId = appProperties.getFormIdComplaint();
        String paramformId = "?formId=";
        String paramFieldName = "&fieldName=";
        String paramFieldValue = "&fieldValue=*";
        String paramStart = "&start=0";
        String paramCount = "&count=1";
        String fieldName = appProperties.getTicketNumber();

        //menggunakan ticket number
        String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue + ticketNumber + paramStart + paramCount;

        // tidak pakai ticket number
//		String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue
//				+ paramStart + param Count;
        System.out.println(url);

        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.init(true);

        Request request = new Request.Builder().url(url).get().addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + bearer).build();

        try {
            Response response = okHttpUtil.getClient().newCall(request).execute();

            FormComplaint complaint = new FormComplaint();
            String JsonString = response.body().string();
            // System.out.println("getform, Jsonstring : "+JsonString);

            Gson gson = new Gson();
            complaint = gson.fromJson(JsonString, FormComplaint.class);
            // System.out.println("From cuti data : "+fct);

            String jsonfct = gson.toJson(complaint.getData());
            // System.out.println(jsonfct);

            // ambil json array
            JSONArray arrayJson = new JSONArray(jsonfct);
            // System.out.println("Array json dari form cuti data : "+arrayJson);
            JSONObject jsonObjek = arrayJson.getJSONObject(0);
            // System.out.println("Object json dari form cuti data : "+jsonObjek);

            data = gson.fromJson(jsonObjek.toString(), DatumComplaint.class);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    public DatumRequest getDataRequest(String bearer, String ticketNumber) {
        DatumRequest data = new DatumRequest();
        String baseUrl = appProperties.getUrl();
        String apiform = appProperties.getApiForm();
        String formId = appProperties.getFormIdRequest();
        String paramformId = "?formId=";
        String paramFieldName = "&fieldName=";
        String paramFieldValue = "&fieldValue=*";
        String paramStart = "&start=0";
        String paramCount = "&count=1";
        String fieldName = appProperties.getTicketNumber();

        //menggunakan ticket number
        String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue + ticketNumber + paramStart + paramCount;

        // tidak pakai ticket number
//		String url = baseUrl + apiform + paramformId + formId + paramFieldName + fieldName + paramFieldValue
//				+ paramStart + param Count;
        System.out.println(url);

        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.init(true);

        Request request = new Request.Builder().url(url).get().addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + bearer).build();

        try {
            Response response = okHttpUtil.getClient().newCall(request).execute();

            FormRequest complaint = new FormRequest();
            String JsonString = response.body().string();
            // System.out.println("getform, Jsonstring : "+JsonString);

            Gson gson = new Gson();
            complaint = gson.fromJson(JsonString, FormRequest.class);
            // System.out.println("From cuti data : "+fct);

            String jsonfct = gson.toJson(complaint.getData());
            // System.out.println(jsonfct);

            // ambil json array
            JSONArray arrayJson = new JSONArray(jsonfct);
            // System.out.println("Array json dari form cuti data : "+arrayJson);
            JSONObject jsonObjek = arrayJson.getJSONObject(0);
            // System.out.println("Object json dari form cuti data : "+jsonObjek);

            data = gson.fromJson(jsonObjek.toString(), DatumRequest.class);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;
    }

    public String getToken() {
        String bearer = "";
        String username = "kurnia.nugraha@mii.co.id";
        String password = "P@ssw0rd";
//        String baseUrl = appProperties.getBaseUrl();
        String baseUrl = appProperties.getUrl();
        String apiToken = appProperties.getApiToken();

        String url = baseUrl + apiToken;

        Token tk = new Token();
        tk.setUsername(username);
        tk.setPassword(password);

        Gson gson = new Gson();

        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.init(true);

        RequestBody body = RequestBody.create(JSON, gson.toJson(tk));
        // System.out.println(gson.toJson(tk).toString());

        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/json; charset=utf-8")
                .post(body).build();

        try {
            Response response = okHttpUtil.getClient().newCall(request).execute();

            JSONObject jsonObjek = new JSONObject(response.body().string());
            bearer = jsonObjek.getString("token");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bearer;
    }

    /**
     * yg mau dipake utk form cuti
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult dogetFormcuti(ExtensionRequest extensionRequest) {

        Map<String, String> output = new HashMap<>();
        String formId = appProperties.getFormIdCuti();
        FormBuilder formBuilder = new FormBuilder(formId);
        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("Form Cuti");
        button.setSubTitle("Form Cuti");
        button.setPictureLink(Image_cuti);
        button.setPicturePath(Image_cuti);
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Isi Form");
        bookAction.setValue(formBuilder.build());
//        bookAction.setValue(appProperties.getShortenFormCuti());
        actions.add(bookAction);
        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        output.put(OUTPUT, buttonBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult doGetFormRequest(ExtensionRequest extensionRequest) {

        Map<String, String> output = new HashMap<>();
        String formId = appProperties.getFormIdRequest();
        FormBuilder formBuilder = new FormBuilder(formId);
        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("Form Request");
        button.setSubTitle("Form Request");
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Isi Form");
        bookAction.setValue(formBuilder.build());
        actions.add(bookAction);
        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        output.put(OUTPUT, buttonBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult doGetFormComplaint(ExtensionRequest extensionRequest) {

        Map<String, String> output = new HashMap<>();
        String formId = appProperties.getFormIdComplaint();
//        String callAppointment = appProperties.getCallAppointment();
        FormBuilder formBuilder = new FormBuilder(formId);
        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("Form Complaint");
        button.setSubTitle("Form Complaint");
        List<EasyMap> actions = new ArrayList<>();

        EasyMap bookAction = new EasyMap();

        bookAction.setName("Isi Form");
        bookAction.setValue(formBuilder.build());
        actions.add(bookAction);

        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        output.put(OUTPUT, buttonBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGetFormEventCCW(ExtensionRequest extensionRequest) {

        Map<String, String> output = new HashMap<>();
//        String formId = appProperties.getFormIdCuti();
//        FormBuilder formBuilder = new FormBuilder(formId);
        ButtonTemplate button = new ButtonTemplate();
        button.setTitle("Form Event");
        button.setSubTitle("Form Event");
        button.setPictureLink(Image_cuti);
        button.setPicturePath(Image_cuti);
        List<EasyMap> actions = new ArrayList<>();
        EasyMap bookAction = new EasyMap();
        bookAction.setName("Isi Form");
//        bookAction.setValue(formBuilder.build());
        bookAction.setValue(appProperties.getFormEventCCW());
        actions.add(bookAction);
        button.setButtonValues(actions);
        ButtonBuilder buttonBuilder = new ButtonBuilder(button);

        output.put(OUTPUT, buttonBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult dogetajuincuti(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder respBuilder = new StringBuilder();
        StringBuilder respBuilder2 = new StringBuilder();

        String ticketNumber = extensionRequest.getIntent().getTicket().getTicketNumber();

        String Bearer = "";
        String nama = "";
        String nik = "";
        String lembaga = "";
        String ijinuntuk = "";
        String tanggal = "";
        String waktuijin = "";
        String keperluan = "";

        // 1.get data dari form
        // ambil token
        Bearer = getToken();

        // request API dolphin
        System.out.println("bearer = " + Bearer);

        Datum data = new Datum();
        data = getForm(Bearer, ticketNumber);
        // 2. parsing data

        nama = data.getNama();
        nik = data.getNik();
        lembaga = data.getLembaga();
        ijinuntuk = data.getMengajukanPermohonanIjinUntuk();
        tanggal = data.getTanggal();
        waktuijin = data.getWaktuIjin();
        keperluan = data.getKeperluanKeterangan();
        String namaatasan = appProperties.getNamerecipient1();
        String namaHrd = appProperties.getNamerecipient2();

        respBuilder.append("Kepada Yth.\n");
        respBuilder.append(namaatasan);
        respBuilder.append("\nDengan Hormat,");
        respBuilder.append("\n\nNama : " + nama);
        respBuilder.append("\nNIK : " + nik);
        respBuilder.append("\nLembaga : " + lembaga);
        respBuilder.append("\nPermohonan ijin untuk  :" + ijinuntuk);
        respBuilder.append("\ntanggal : " + tanggal);
        respBuilder.append("\nwaktu ijin : " + waktuijin);
        respBuilder.append("\nkeperluan : " + keperluan);
        respBuilder.append("\n\nDemikian Surat cuti ini dibuat.");
        respBuilder.append("\nterima kasih");

        respBuilder2.append("Kepada Yth.\n");
        respBuilder2.append(namaHrd);
        respBuilder2.append("\nDengan Hormat,");
        respBuilder2.append("\n\nNama : " + nama);
        respBuilder2.append("\nNIK : " + nik);
        respBuilder2.append("\nLembaga : " + lembaga);
        respBuilder2.append("\nPermohonan ijin untuk  :" + ijinuntuk);
        respBuilder2.append("\ntanggal : " + tanggal);
        respBuilder2.append("\nwaktu ijin : " + waktuijin);
        respBuilder2.append("\nkeperluan : " + keperluan);
        respBuilder2.append("\n\nDemikian Surat cuti ini dibuat.");
        respBuilder2.append("\nterima kasih");

        String bodyAtasan = respBuilder.toString();
        String bodyHrd = respBuilder2.toString();

        // 3. kirim email
        String recipient1 = appProperties.getEmailrecipient1();
        String recipient2 = appProperties.getEmailrecipient2();
        System.out.println("recipient 1 : " + recipient1);
        System.out.println("recipient 2 : " + recipient2);

        //String recipient1 = appProperties.getEmailrecipient1();
        // String recipient2 = appProperties.getEmailrecipient2();
        MailModel mailModel = new MailModel(recipient1, "Surat Ijin Cuti", bodyAtasan);
        MailModel mailModel2 = new MailModel(recipient2, "Surat Ijin Cuti", bodyHrd);
        String sendMailResult = svcMailService.sendMail(mailModel);
        String sendMailResult2 = svcMailService.sendMail(mailModel2);
        System.out.println("hasil kirim email" + sendMailResult);
        System.out.println("hasil kirim email" + sendMailResult2);

        String result = "";
        if (sendMailResult.toLowerCase().contains("success") && sendMailResult2.toLowerCase().contains("success")) {
            result = "Baik kak silahkan tunggu konfirmasi ya kak";
        } else if (sendMailResult.toLowerCase().contains("fail") && sendMailResult2.toLowerCase().contains("fail")) {
            result = "Maaf kak pengiriman email gagal. Boleh diulangi kak";
        } else {
            result = "Maaf kak {bot_name} belum mengerti. Bisa tolong ulangi lagi kak.";
        }

        output.put(OUTPUT, result);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGetCuaca(ExtensionRequest extensionRequest) {
        String kota = getEasyMapValueByName(extensionRequest, "tempat");
        String uri = "https://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=beb536b6a3f98bb2bfde28ac6d99c6fc";

        URL url;
        String inline = "";
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        try {
            url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
            }
            JSONObject obj = new JSONObject(inline);
            JSONObject main = obj.getJSONObject("main");
            int temp = main.getInt("temp");
            inline = temp + "";

//            JSONObject obj = new JSONObject(inline);
            int pageName = obj.getJSONObject("coord").getInt("lon");

            JSONArray arr = obj.getJSONArray("weather");
            for (int i = 0; i < arr.length(); i++) {
                String description = arr.getJSONObject(i).getString("description");
                inline = description + " suhunya " + temp + "";
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(ServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        output.put("cuaca", inline);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult getCarLocation(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sb = new StringBuilder();
        StringBuilder urlBuilder = new StringBuilder();
        /*
		 * 
		 * Get datas from application.properties
		 * user can set on application.properties
		 * URL, path, Collection Name, fieldName, distance, and number of result
		 * 
		 * 
         */
        String url = appProperties.getUrl();
        String path = appProperties.getPath();

        String collName = appProperties.getCollName();
        String fieldName = appProperties.getFieldName();
        int distance = Integer.parseInt(appProperties.getDistance());
        int count = Integer.parseInt(appProperties.getCount());
        String atmImagePath = appProperties.getAtmUrl();
        String googleMapquery = appProperties.getGoogleMapUrl();
        String buttonTitlePayload = appProperties.getButtonTitlePayload();

        String errorAtmNotFoundMessage = appProperties.getAtmNotFoundMessage();

        String location = getEasyMapValueByName(extensionRequest, "location");
        String bearer = getToken();
        /*
		 * 
		 * Split latitude and longitude from google_map
		 * 
         */
        String[] explodedLoc = location.split(";");
        String lati = explodedLoc[0];
        String longi = explodedLoc[1];
        lati = lati.replace(" ", ".");
        longi = longi.replace(" ", ".");
        String loc = lati + ";" + longi;

        /*
		 * Build Url using String builder
		 * 
         */
        urlBuilder.append(url);
        urlBuilder.append(path);
        urlBuilder.append(API_PARAM_NAME_COLLNAME + collName);
        urlBuilder.append(API_PARAM_NAME_FIELDNAME + fieldName);
        urlBuilder.append(API_PARAM_NAME_LATLONG + loc);
        urlBuilder.append(API_PARAM_NAME_DISTANCE + distance);
        urlBuilder.append(API_PARAM_NAME_COUNT + count);

        String URL = urlBuilder.toString();

        /*
		 * request to dolphin API to get Location Data collection Name : location
		 * 
		 * @param collName
		 * 
		 * @param fieldName
		 * 
		 * @param latitude
		 * 
		 * @param longitude
		 * 
		 * @param distance
		 * 
		 * @param start, count
         */
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        okHttpUtil.init(true);

        try {
            Request request = new Request.Builder().url(URL).addHeader("Authorization", "Bearer " + bearer).get()
                    .build();
            Response response = okHttpUtil.getClient().newCall(request).execute();

            /*
			 * parsing JSONObjek parsing JSONArray form JSONObjek dataArr from jsonObjek
             */
            JSONObject jsonObjek = new JSONObject(response.body().string());
            String status = jsonObjek.getString("status");

            if (status.equalsIgnoreCase("success")) {
                JSONArray dataArr = jsonObjek.getJSONArray("data");
                // System.out.println("Kie data array : "+dataArr);

                JsonParser parser = new JsonParser();

                /*
				 * for each element named el in dataArr { create element named jsonEl as json
				 * string create jsonObjek named elObj from json string get string from elObj
				 * named branch_name, branch_address and google_map
				 * 
                 */
                dataArr.forEach(el -> {
                    String jsonEl = el.toString();
                    JsonObject elObj = parser.parse(jsonEl).getAsJsonObject();
                    String branch_name = elObj.get("branch_name").getAsString();
                    String branch_address = elObj.get("branch_address").getAsString();
                    String google_map = elObj.get("google_map").getAsString().replace(" ", "");

                    //Buat Button 
                    ButtonTemplate button = new ButtonTemplate();
                    button.setTitle(branch_name);
                    button.setSubTitle(branch_address);
                    button.setPictureLink(atmImagePath);
                    button.setPicturePath(atmImagePath);
                    List<EasyMap> actions = new ArrayList<>();
                    EasyMap bookAction = new EasyMap();
                    bookAction.setName(buttonTitlePayload);
                    bookAction.setValue(googleMapquery + google_map);
                    actions.add(bookAction);
                    button.setButtonValues(actions);
                    ButtonBuilder buttonBuilder = new ButtonBuilder(button);

                    String btnBuilder = buttonBuilder.build().toString();
                    sb.append(btnBuilder).append(CONSTANT_SPLIT_SYNTAX);

                });
            } else if (status.equalsIgnoreCase("error")) {
                sb.append(errorAtmNotFoundMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        output.put(OUTPUT, sb.toString());
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }
//leo leo

    @Override
    public ExtensionResult doGetTerdekat(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sb = new StringBuilder();
        String ask = getEasyMapValueByName(extensionRequest, "pertanyaan");
        String longlat = getEasyMapValueByName(extensionRequest, "longlat");

        String[] arrAsk = ask.split(" ");
        ask = "";

        for (int i = 0; i < arrAsk.length; i++) {
            ask += arrAsk[i];
            if (i != arrAsk.length - 1) {
                ask += "+";
            }
        }
        String[] arrLoglat = longlat.split(";");
        String lat = arrLoglat[0];
        String longi = arrLoglat[1];
        longlat = lat + "," + longi;

        sb.append("https://www.google.com/maps/search/");
        sb.append(ask).append("/@");
        sb.append(longlat).append(",12z");

        output.put(OUTPUT, sb.toString());
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     * Seva.id sdk Mendapatkan tipe tipe mobil
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult doGetTipeMobil(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        QuickReplyBuilder quickReplyBuilder = new QuickReplyBuilder.Builder("Type")
                .add("Sport", "Sport")
                .add("MPV", "mpv")
                .add("Hatchback", "Hatchback")
                .add("SUV", "suv")
                .add("Commercial", "Commercial")
                .add("Sedan", "Sedan").build();
        output.put(OUTPUT, quickReplyBuilder.string());

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     * Seva.id sdk Membuat carousel berupa merk-merk mobil yang dinamis
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult doGetMerkMobils(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        String url = "https://bububibap.herokuapp.com/getMerkMobil";

        List<String> merks = getMobilDinamis(url, "mobil", "merk");
        List<ButtonBuilder> buttonBuilders = new ArrayList<>();
        for (String merk : merks) {
            logger.debug("Mobil dengan merk : " + merk);
            ButtonTemplate button = new ButtonTemplate();
            button.setPictureLink(appProperties.getToyotaImgUrl());
            button.setPicturePath(appProperties.getToyotaImgUrl());
            button.setTitle(merk);
            button.setSubTitle("Astra " + merk);
            List<EasyMap> actions = new ArrayList<>();
            EasyMap bookAction = new EasyMap();
            bookAction.setName(merk);
            bookAction.setValue(merk);
            actions.add(bookAction);
            button.setButtonValues(actions);

            ButtonBuilder buttonBuilder = new ButtonBuilder(button);
            buttonBuilders.add(buttonBuilder);
        }
        String btnBuilders = "";
        for (ButtonBuilder buttonBuilder : buttonBuilders) {
            btnBuilders += buttonBuilder.build();
            btnBuilders += "&split&";
        }

        CarouselBuilder carouselBuilder = new CarouselBuilder(btnBuilders);
        output.put(OUTPUT, carouselBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     * Seva.id sdk nge get model mobil
     *
     * @param extensionRequest
     * @return
     */
    @Override
    public ExtensionResult doGetModelMobils(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        String tipe = getEasyMapValueByName(extensionRequest, "type");
        String merek = getEasyMapValueByName(extensionRequest, "merk");

        String url = "https://bububibap.herokuapp.com/getToyota/";
        List<List<String>> models = getModelModel(url, "mobil", "model", tipe);
        List<ButtonBuilder> buttonBuilders = new ArrayList<>();
        List<String> model = models.get(type_index);
        for (String mod : model) {
            ButtonTemplate button = new ButtonTemplate();
            //button.setPictureLink(appProperties.getToyotaImgUrl());
            //button.setPicturePath(appProperties.getToyotaImgUrl());
            button.setTitle(mod);
            button.setSubTitle(mod);
            List<EasyMap> actions = new ArrayList<>();
            EasyMap bookAction = new EasyMap();
            bookAction.setName(mod);
            bookAction.setValue("model " + mod);
            actions.add(bookAction);
            button.setButtonValues(actions);
            ButtonBuilder buttonBuilder = new ButtonBuilder(button);
            buttonBuilders.add(buttonBuilder);
        }
        String btnBuilders = "";
        for (ButtonBuilder buttonBuilder : buttonBuilders) {
            btnBuilders += buttonBuilder.build();
            btnBuilders += "&split&";
        }

        CarouselBuilder carouselBuilder = new CarouselBuilder(btnBuilders);
        output.put(OUTPUT, carouselBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    /**
     * Seva.id sdk untuk mendapatkan data mobil baik itu merk, model, atau
     * varian
     *
     * @param url API database yang akan digunakan
     * @param jsonName penamaan nama JSONObject
     * @param key adalah string yang diget dari JSONObject
     * @return
     */
    private List<String> getMobilDinamis(String url, String jsonName, String key) {
        List<String> result = new ArrayList<>();
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            Request request = new Request.Builder().url(url).get().build();
            Response response = okHttpUtil.getClient().newCall(request).execute();

            String res = "{\"" + jsonName + "\":" + response.body().string() + "}";

            JSONObject jsonObject = new JSONObject(res);
            JSONArray jSONArray = jsonObject.getJSONArray(jsonName);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject obj = (JSONObject) jSONArray.get(i);
                result.add(obj.getString(key));
            }
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * Seva.id sdk untuk mendapatkan data model mobil dinamis
     *
     * @param url API db user
     * @param jsonName pernamaan json
     * @param key model
     * @return
     */
    private List<List<String>> getModelModel(String url, String jsonName, String key, String tipe) {
        List<List<String>> result = new ArrayList<>();

        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            Request request = new Request.Builder().url(url).get().build();
            Response response = okHttpUtil.getClient().newCall(request).execute();

            String res = "{\"" + jsonName + "\":" + response.body().string() + "}";

            JSONObject jsonObject = new JSONObject(res);
            JSONArray jSONArray = jsonObject.getJSONArray(jsonName);
            List<String> list = new ArrayList<>();
            JSONObject obj = (JSONObject) jSONArray.get(0);
            String temp = obj.getString("type");
            List<String> types = new ArrayList<>();
            for (int i = 0; i < jSONArray.length(); i++) {
                obj = (JSONObject) jSONArray.get(i);
                if (obj.getString("type").equalsIgnoreCase(temp)) {
                    list.add(obj.getString(key));
                } else {
                    types.add(temp);
                    if (temp.equals(tipe)) {
                        type_index = result.size();
                    }
                    result.add(list);
                    list = new ArrayList<>();
                    list.add(obj.getString(key));
                    temp = obj.getString("type");
                }
            }
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * buat shorten bitly tp gajadi make
     *
     * @param link
     * @return
     */
    private String shortenBitLy(String link) {
        Bitly bitly = Bit.ly(appProperties.getBitlyAccessToken());
        String shortUrl = bitly.shorten(link);
        return shortUrl;
    }
    ///// Booking Service /////

    @Override
    public ExtensionResult doSendComplaint(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sbCX = new StringBuilder();

        String ticketNumber = extensionRequest.getIntent().getTicket().getTicketNumber();

        String Bearer = "";
        String nama = getEasyMapValueByName(extensionRequest, "person");
        String perusahaan = getEasyMapValueByName(extensionRequest, "company");
        String posisi = getEasyMapValueByName(extensionRequest, "position");
        String email = getEasyMapValueByName(extensionRequest, "email");
        String nohp = getEasyMapValueByName(extensionRequest, "phone");
        String keluhan = "";
        String masukan = "";

        // 1.get data dari form
        // ambil token
        Bearer = getToken();

        // request API dolphin
        System.out.println("bearer = " + Bearer);

        DatumComplaint data = new DatumComplaint();
        data = getFormComplaint(Bearer, ticketNumber);
        // 2. parsing data

        keluhan = data.getKeluhan();
        masukan = data.getMasukan();
        sbCX.append("<html>").append("<body>");
        sbCX.append(getHeaderEmail("Complaint", nama, posisi, perusahaan, email, nohp).toString());
        sbCX
                .append("<td>6.</td>").append("<td>Keluhan</td>").append("<td>:</td>").append("<td>" + keluhan + "</td>")
                .append("</tr><tr>")
                .append("<td>7.</td>").append("<td>Masukan</td>").append("<td>:</td>").append("<td>" + masukan + "</td>")
                .append("</tr>");
        sbCX.append("</table>");

        sbCX.append(getFooterEmail().toString())
                .append("</body>").append("</html>");

        int codeCX = sendGridEmail(sbCX, email, "Kakak CX", "SAMI - Complaint (" + keluhan + ")");

        String result = "";
        if (codeCX == 202) {
            result = "Baik kak silahkan tunggu konfirmasi ya kak";
        } else {
            result = "Maaf kak pengiriman email gagal. Boleh diulangi kak";
        }

        output.put(OUTPUT, result);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGetTicketNumber(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        StringBuilder respBuilder = new StringBuilder();
        respBuilder.append("Ticket Number : " + extensionRequest.getIntent().getTicket().getTicketNumber() + "\n");

        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        output.put(OUTPUT, respBuilder.toString());
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGetLastFormData(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();

        String ticketNumber = extensionRequest.getIntent().getTicket().getTicketNumber();

        String Bearer = "";
        String nama = "";
        String nik = "";
        String lembaga = "";
        String ijinuntuk = "";
        String tanggal = "";
        String waktuijin = "";
        String keperluan = "";

        Datum data = new Datum();

        Bearer = getToken();

        data = getForm(Bearer, ticketNumber);

        nama = data.getNama();
        nik = data.getNik();
        lembaga = data.getLembaga();
        ijinuntuk = data.getMengajukanPermohonanIjinUntuk();
        tanggal = data.getTanggal();
        waktuijin = data.getWaktuIjin();
        keperluan = data.getKeperluanKeterangan();

        java.util.Date time = new java.util.Date(new Long(tanggal));

        SimpleDateFormat dtf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        dtf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        try {
            java.util.Date jdate = dtf.parse(time.toString());
            tanggal = jdate.toString();
            //Thu Jan 03 00:00:00 ICT 2019
            String[] dates = tanggal.split(" ");
            tanggal = dates[2] + " " + dates[1] + " " + dates[dates.length - 1];
        } catch (ParseException ex) {
            Logger.getLogger(ServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }

//        java.util.Date time = new java.util.Date(new Long(tanggal));
//        String date = time.toString();
//        
//        //Thu Jan 03 00:00:00 ICT 2019
//        String[] dates = date.split(" ");
//        tanggal = dates[2] + " " + dates[1] + " " + dates[dates.length - 1];
        StringBuilder result = new StringBuilder();
        result.append(nama + "\n")
                .append(nik + "\n")
                .append(lembaga + "\n")
                .append(ijinuntuk + "\n")
                .append(tanggal + "\n")
                .append(waktuijin + "\n")
                .append(keperluan);

        output.put(OUTPUT, result.toString());
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doSendEmailRequest(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sbCX = new StringBuilder();
        StringBuilder respBuilder2 = new StringBuilder();

        String ticketNumber = extensionRequest.getIntent().getTicket().getTicketNumber();

        String Bearer = "";
        String nama = getEasyMapValueByName(extensionRequest, "person");
        String perusahaan = getEasyMapValueByName(extensionRequest, "company");
        String posisi = getEasyMapValueByName(extensionRequest, "position");
        String email = getEasyMapValueByName(extensionRequest, "email");
        String nohp = getEasyMapValueByName(extensionRequest, "phone");
        String request = "";
        String kebutuhan = "";
        String waktu = "";

        // 1.get data dari form
        // ambil token
        Bearer = getToken();

        // request API dolphin
        System.out.println("bearer = " + Bearer);

        DatumRequest data = new DatumRequest();
        data = getDataRequest(Bearer, ticketNumber);
        // 2. parsing data

        request = data.getRequest();
        kebutuhan = data.getKebutuhan();
        waktu = data.getTanggal();
        waktu = new java.util.Date(new Long(waktu)).toString();

        String[] times = waktu.split(" ");

        waktu = times[2] + " " + times[1] + " " + times[times.length - 1];
        sbCX.append("<html>").append("<body>");
        sbCX.append(getHeaderEmail("Request", nama, posisi, perusahaan, email, nohp).toString());
        sbCX
                .append("<td>6.</td>").append("<td>Request</td>").append("<td>:</td>").append("<td>" + request + "</td>")
                .append("</tr><tr>")
                .append("<td>7.</td>").append("<td>Kebutuhan</td>").append("<td>:</td>").append("<td>" + kebutuhan + "</td>")
                .append("</tr><tr>")
                .append("<td>8.</td>").append("<td>Waktu</td>").append("<td>:</td>").append("<td>" + waktu + "</td>")
                .append("</tr>");
        sbCX.append("</table>");

        sbCX.append(getFooterEmail().toString())
                .append("</body>").append("</html>");

        // 3. kirim email
        int codeCX = sendGridEmail(sbCX, email, nama, "SAMI - Request (" + request + ")");

        String result = "";
        if (codeCX == 202) {
            result = "Baik kak silahkan tunggu konfirmasi ya kak";
        } else {
            result = "Maaf kak pengiriman email gagal. Boleh diulangi kak";
        }

        output.put(OUTPUT, result);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doSendEmailSolution(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sbCX = new StringBuilder();
        StringBuilder sbCustomer = new StringBuilder();
        String pertanyaan = getEasyMapValueByName(extensionRequest, "pertanyaan");
        String nama = getEasyMapValueByName(extensionRequest, "person");
        String perusahaan = getEasyMapValueByName(extensionRequest, "company");
        String posisi = getEasyMapValueByName(extensionRequest, "position");
        String email = getEasyMapValueByName(extensionRequest, "email");
        String nohp = getEasyMapValueByName(extensionRequest, "phone");

        sbCX.append("<html>").append("<body>");
        sbCX.append(getHeaderEmail("Solution", nama, posisi, perusahaan, email, nohp).toString());
        sbCX
                .append("<td>6.</td>").append("<td>Pertanyaan</td>").append("<td>:</td>").append("<td>" + pertanyaan + "</td>")
                .append("</tr>");
        sbCX.append("</table>");

        sbCX.append(getFooterEmail().toString())
                .append("</body>").append("</html>");

        int codeCX = sendGridEmail(sbCX, email, nama, "SAMI - Solution (" + pertanyaan + ")");

        String result = "";
        if (codeCX == 202) {
            result = "Baik kak silahkan tunggu konfirmasi ya kak";
        } else {
            result = "Maaf kak pengiriman email gagal. Boleh diulangi kak";
        }

        output.put(OUTPUT, result);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doSendEmailPromo(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        StringBuilder sbCX = new StringBuilder();
        StringBuilder sbCustomer = new StringBuilder();
        String promo = getEasyMapValueByName(extensionRequest, "promo");
        String nama = getEasyMapValueByName(extensionRequest, "person");
        String perusahaan = getEasyMapValueByName(extensionRequest, "company");
        String posisi = getEasyMapValueByName(extensionRequest, "position");
        String email = getEasyMapValueByName(extensionRequest, "email");
        String nohp = getEasyMapValueByName(extensionRequest, "phone");

        sbCX.append("<html>").append("<body>");
        sbCX.append(getHeaderEmail("Promo", nama, posisi, perusahaan, email, nohp).toString());
        sbCX
                .append("<td>6.</td>").append("<td>Promo</td>").append("<td>:</td>").append("<td>" + promo + "</td>")
                .append("</tr>");
        sbCX.append("</table>");

        sbCX.append(getFooterEmail().toString())
                .append("</body>").append("</html>");

        //Kirim email
        int codeCX = sendGridEmail(sbCX, email, nama, "SAMI - Promo (" + promo + ")");
//        int codeCustomer = sendGridEmail(sbCustomer, email, nama, "SAMI - Tiket - Promo");

        String result = "";
        if (codeCX == 202) {
            result = "Baik kak silahkan tunggu konfirmasi ya kak";
        } else {
            result = "Maaf kak pengiriman email gagal. Boleh diulangi kak";
        }

        output.put(OUTPUT, result);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    private String addWordPhone(String phone) {
        String result = phone;
        if (phone.chars().allMatch(Character::isDigit)) {
            result = phone + " nomorku";
        }
        return result;
    }

    @Override
    public ExtensionResult doValidatePhone(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String phone = getEasyMapValueByName(extensionRequest, "notelp");
        phone = phone.replace(" ", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        phone = phone.replace("-", "");
        phone = phone.replace(".", "");

        if (phone.matches("^[+0-9]*$")) {
            String preZero8 = phone.substring(0, 2);
            String prePlus62 = phone.substring(0, 4);

            if (phone.length() > 9 && phone.length() < 16) {
                if (prePlus62.equals("+628")) {
                    phone = phone.replace("+628", "08");
                    clearEntities.put("notelp", phone);
//                    clearEntities.put("notelp", addWordPhone(phone));
                    clearEntities.put("confirm", "confirm dong");
                } else if (!preZero8.equals("08")) {
                    clearEntities.put("notelp", null);
                } else {
                    clearEntities.put("notelp", phone);
//                    clearEntities.put("notelp", addWordPhone(phone));
                    clearEntities.put("confirm", "confirm dong");
                }
            } else {
                clearEntities.put("notelp", null);
            }
        } else {
            clearEntities.put("notelp", null);
        }
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

    @Override
    public ExtensionResult doValidatePhoneSetNextEntity(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String phone = getEasyMapValueByName(extensionRequest, "phone");
        phone = phone.replace(" ", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        phone = phone.replace("-", "");
        phone = phone.replace(".", "");

        if (phone.matches("^[+0-9]*$")) {
            String preZero8 = phone.substring(0, 2);
            String prePlus62 = phone.substring(0, 4);

            if (phone.length() > 9 && phone.length() < 16) {
                if (prePlus62.equals("+628")) {
                    phone = phone.replace("+628", "08");
                    clearEntities.put("phone", phone);
                    clearEntities.put("confirm", "mantul");
                    extensionResult.setEntities(clearEntities);
                } else if (!preZero8.equals("08")) {
                    clearEntities.put("phone", null);
                    extensionResult.setEntities(clearEntities);
                } else {
                    clearEntities.put("phone", phone);
                    clearEntities.put("confirm", "mantul");
                    extensionResult.setEntities(clearEntities);
                }
            } else {
                clearEntities.put("phone", null);
                extensionResult.setEntities(clearEntities);
            }
        } else {
            clearEntities.put("phone", null);
            extensionResult.setEntities(clearEntities);
        }

        return extensionResult;
    }

    @Override
    public ExtensionResult doValidateEmail(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String email = getEasyMapValueByName(extensionRequest, "email");
        Matcher matcher = Pattern.compile("^[A-Z0-9._-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email);

        if (matcher.find() == false) {
            clearEntities.put("email", null);
            extensionResult.setEntities(clearEntities);
        }
        return extensionResult;
    }

    @Override
    public ExtensionResult yesNoConfirm(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String conf = getEasyMapValueByName(extensionRequest, "confirm");

        if (conf.equalsIgnoreCase("salah")) {
            clearEntities.put("phone", null);
            clearEntities.put("email", null);
            clearEntities.put("confirm", null);
        } else {
            clearEntities.put("confirm2", "yes");
        }
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGet17MerkMobil(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        List<ButtonBuilder> buttonBuilders = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            logger.debug("Mobil dengan merk : " + i);
            ButtonTemplate button = new ButtonTemplate();
            button.setPictureLink(appProperties.getToyotaImgUrl());
            button.setPicturePath(appProperties.getToyotaImgUrl());
            button.setTitle(i + "");
            button.setSubTitle("Astra " + i);
            List<EasyMap> actions = new ArrayList<>();
            EasyMap bookAction = new EasyMap();
            bookAction.setName(i + "");
            bookAction.setValue(i + "");
            actions.add(bookAction);
            button.setButtonValues(actions);

            ButtonBuilder buttonBuilder = new ButtonBuilder(button);
            buttonBuilders.add(buttonBuilder);
        }
        String btnBuilders = "";
        for (ButtonBuilder buttonBuilder : buttonBuilders) {
            btnBuilders += buttonBuilder.build();
            btnBuilders += "&split&";
        }

        CarouselBuilder carouselBuilder = new CarouselBuilder(btnBuilders);
        output.put(OUTPUT, carouselBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doPing(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        output.put(OUTPUT, "pong");
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public void ping() {
        String url = "http://sdk-bipbip.herokuapp.com/ping";
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            Request request = new Request.Builder().url(url).get().build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ExtensionResult doCheckVoucher(ExtensionRequest extensionRequest) {
        String chance = getEasyMapValueByName(extensionRequest, "chance");
        String code = getEasyMapValueByName(extensionRequest, "code");
        Map<String, String> clearEntities = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        int chc = Integer.parseInt(chance);
        if (chc > 0) {
            if (!code.equalsIgnoreCase("KD1010")) {
                chc--;
                clearEntities.put("code", null);
                clearEntities.put("chance", chc + "");
            } else {
                clearEntities.put("conf", "yes");
            }
            extensionResult.setEntities(clearEntities);
        } else {
            clearEntities.put("conf", "yes");
            extensionResult.setEntities(clearEntities);
        }

        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        return extensionResult;
    }

    @Override
    public ExtensionResult doGetResult(ExtensionRequest extensionRequest) {
        String chance = getEasyMapValueByName(extensionRequest, "chance");
        Map<String, String> output = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
        int chc = Integer.parseInt(chance);
        if (chc > 0) {
            output.put(OUTPUT, "Sukses ya ka. . .");
        } else {
            output.put(OUTPUT, "Duuuuh maaf ya kak, kesempatannya sudah habis. . .");
        }
        extensionResult.setValue(output);
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        return extensionResult;
    }

    @Override
    public ExtensionResult doSetChance(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        Map<String, String> clearEntities = new HashMap<>();
        clearEntities.put("chance", "2");
        extensionResult.setEntities(clearEntities);

        return extensionResult;
    }

    @Override
    public ExtensionResult mauGakLocation(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        String mauLocation = getEasyMapValueByName(extensionRequest, "maugak");

        QuickReplyBuilder quickReplyBuilder = null;
        Map<String, String> clearEntities = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();

        if (mauLocation.equalsIgnoreCase("mau")) {
            quickReplyBuilder = new QuickReplyBuilder.Builder("Kirim lokasi kakak ya")
                    .add("location", "location").build();
            output.put(OUTPUT, quickReplyBuilder.string());
            extensionResult.setValue(output);
        } else {
            clearEntities.put("city", "Jakarta");
            extensionResult.setEntities(clearEntities);
        }
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        return extensionResult;
    }

    @Override
    public ExtensionResult latLongToCity(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        String city = getEasyMapValueByName(extensionRequest, "city");

        Map<String, String> clearEntities = new HashMap<>();
        ExtensionResult extensionResult = new ExtensionResult();
//        GoogleResponse googleResponse = new GoogleResponse();
        if (!city.equalsIgnoreCase("jakarta")) {
            String[] explodedLoc = city.split(";");
            String lati = explodedLoc[0];
            String longi = explodedLoc[1];
            String loc = lati + "," + longi;
//            try {
//                googleResponse = latlngToCity(loc);
//                for (Result result : googleResponse.getResults()) {
//                    System.out.println("Lattitude of address is :" + result.getGeometry().getLocation().getLat());
//                    System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
//                    System.out.println("Location is " + result.getGeometry().getLocation_type());
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceImp.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        return extensionResult;
    }

    private StringBuilder getHeaderEmail(String jenisEmail, String nama, String posisi, String namaPerusahaan, String email, String hp) {
        StringBuilder respBuilder = new StringBuilder();
        respBuilder.append("Dear Kakak CX,");
        respBuilder.append("<br>SAMI mohon bantuannya untuk memproses tiket ini ya.");

        respBuilder.append("<br><br>Jenis : " + jenisEmail);
        respBuilder.append("<br><br>Data : ");

        respBuilder.append("<table><tr>")
                .append("<td>1.</td>").append("<td>Nama</td>").append("<td>:</td>").append("<td>" + nama + "</td>")
                .append("</tr><tr>")
                .append("<td>2.</td>").append("<td>Posisi</td>").append("<td>:</td>").append("<td>" + posisi + "</td>")
                .append("</tr><tr>")
                .append("<td>3.</td>").append("<td>Nama Perusahaan</td>").append("<td>:</td>").append("<td>" + namaPerusahaan + "</td>")
                .append("</tr><tr>")
                .append("<td>4.</td>").append("<td>Email</td>").append("<td>:</td>").append("<td>" + email + "</td>")
                .append("</tr><tr>")
                .append("<td>5.</td>").append("<td>No Telpon Selular (Hp)</td>").append("<td>:</td>").append("<td>" + hp + "</td>")
                .append("</tr><tr>");

        return respBuilder;
    }

    private StringBuilder getFooterEmail() {
        StringBuilder respBuilder = new StringBuilder();

        respBuilder.append("<br>Terima kasih, Kakak CX.");

        respBuilder.append("<br><br>Salam Sahabat,");
        respBuilder.append("<br>SAMI");

        return respBuilder;
    }

    @Override
    public ExtensionResult doConfirmPromo(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String conf = getEasyMapValueByName(extensionRequest, "confirm");

        if (conf.equalsIgnoreCase("salah")) {
            clearEntities.put("person", null);
            clearEntities.put("company", null);
            clearEntities.put("position", null);
            clearEntities.put("email", null);
            clearEntities.put("phone", null);
            clearEntities.put("promo", null);
            clearEntities.put("confirm", null);
        } else {
            clearEntities.put("confirm2", "Yes");
        }
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

    @Override
    public ExtensionResult doConfirmSolution(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String conf = getEasyMapValueByName(extensionRequest, "confirm");

        if (conf.equalsIgnoreCase("salah")) {
            clearEntities.put("person", null);
            clearEntities.put("company", null);
            clearEntities.put("position", null);
            clearEntities.put("email", null);
            clearEntities.put("phone", null);
            clearEntities.put("pertanyaan", null);
            clearEntities.put("confirm", null);
        } else {
            clearEntities.put("confirm2", "Yes");
        }
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

    @Override
    public ExtensionResult doConfirmReqCom(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        Map<String, String> clearEntities = new HashMap<>();
        String conf = getEasyMapValueByName(extensionRequest, "confirm");

        if (conf.equalsIgnoreCase("salah")) {
            clearEntities.put("person", null);
            clearEntities.put("company", null);
            clearEntities.put("position", null);
            clearEntities.put("email", null);
            clearEntities.put("phone", null);
            clearEntities.put("confirm", null);
        } else {
            clearEntities.put("confirm2", "Yes");
        }
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

    @Override
    public ExtensionResult doGetAboutSami(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();

        String jabatan = getEasyMapValueByName(extensionRequest, "jabatan");
        String perusahaan = getEasyMapValueByName(extensionRequest, "perusahaan");
        StringBuilder stringBuilder = new StringBuilder();
        ButtonBuilder buttonBuilder = null;
        switch (perusahaan) {
            case "mii":
            case "Mii":
            case "MII":
                if (jabatan.contains("Presiden Direktur")) {
                    ButtonTemplate button = new ButtonTemplate();
                    button.setPictureLink(appProperties.getImgSjafril2());
                    button.setPicturePath(appProperties.getImgSjafril2());
                    button.setTitle("Ir. Sjafril Effendi");
                    button.setSubTitle("Presiden direktur Mitra Integrasi Informatika itu adalah Bapak Ir. Sjafril Effendi.");
                    buttonBuilder = new ButtonBuilder(button);
                }
                break;
            case "metrodata":
            case "Metrodata":
            case "METRODATA":
                switch (jabatan) {
                    case "Presiden Direktur":
                    case "Presiden direktur":
                    case "presiden direktur":
                    case "PRESIDEN DIREKTUR":
                        ButtonTemplate button = new ButtonTemplate();
                        button.setPictureLink(appProperties.getImgSusanto());
                        button.setPicturePath(appProperties.getImgSusanto());
                        button.setTitle("Susanto Djaja, SE, MH");
                        button.setSubTitle("Presiden direktur Metrodata itu adalah Bapak Susanto Djaja, SE, MH.");
                        buttonBuilder = new ButtonBuilder(button);
                        break;
                }
                break;
        }
        String btnBuilders = buttonBuilder.build() + "&split&";
        CarouselBuilder carouselBuilder = new CarouselBuilder(btnBuilders);
        output.put(OUTPUT, carouselBuilder.build());
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doSendgridEmailEx(ExtensionRequest extensionRequest) {
        Map<String, String> output = new HashMap<>();
        int code = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder respBuilder = new StringBuilder();

        respBuilder.append("<html>").append("<body>");
        respBuilder.append(getHeaderEmail("Complaint", "nama", "posisi", "perusahaan", "email", "nohp").toString());
        respBuilder
                .append("<td>6.</td>").append("<td>Keluhan</td>").append("<td>:</td>").append("<td>" + "keluhan" + "</td>")
                .append("</tr><tr>")
                .append("<td>7.</td>").append("<td>Masukan</td>").append("<td>:</td>").append("<td>" + "masukan" + "</td>")
                .append("</tr>");
        respBuilder.append("</table>");

        respBuilder.append(getFooterEmail().toString())
                .append("</body>").append("</html>");

        sb.append(""
                + " {\"personalizations\":[{ "
                + " \"to\":[{ "
                + " \"email\":\"7nandemonai@gmail.com\", " //dinamis
                + " \"name\":\"Kurnia Sandy\"}]," //dinamis
                + " \"subject\":\"Santosa Group Recruitment\"}]," //dinamis
                + " \"from\":{\"email\":\"no-reply@santosagroup.com\","
                + " \"name\":\"Santosa Group\"},"
                //                + " \"reply_to\":{\"email\":\"sandykur77@gmail.com\","
                //                + " \"name\":\"Sandykur\"},"
                + " \"content\":[{"
                + " \"type\":\"text/html\","
                + " \"value\":\"" + respBuilder.toString() + "\"}]}"
                + "");
        try {
            String url = "https://api.sendgrid.com/v3/mail/send";
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            RequestBody body = RequestBody.create(JSON, sb.toString());
            Request request = new Request.Builder().url(url).post(body).addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + "api key").build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            code = response.code();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);

        output.put(OUTPUT, code + "");
        extensionResult.setValue(output);
        return extensionResult;
    }

    int sendGridEmail(StringBuilder resBuilder, String sender, String name, String subject) {
        StringBuilder sb = new StringBuilder();
        int code = 0;
        sb.append(""
                + " {\"personalizations\":[{ "
                + " \"to\":[{ "
                + " \"email\":\"sami@mii-zendesk.zendesk.com\", "
                //                + " \"email\":\"support@mii-zendesk.zendesk.com\", "
                //                + " \"email\":\"7nandemonai@gmail.com\", "
                + " \"name\":\"Kakak CX\"}]," //dinamis
                + " \"subject\":\"" + subject + "\"}]," //dinamis
                //                + " \"from\":{\"email\":\"no-reply@mii.co.id\","
                + " \"from\":{\"email\":\"" + sender + "\","
                + " \"name\":\"" + name + "\"},"
                + " \"content\":[{"
                + " \"type\":\"text/html\","
                + " \"value\":\"" + resBuilder.toString() + "\"}]}"
                + "");
        try {
            String url = "https://api.sendgrid.com/v3/mail/send";
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            RequestBody body = RequestBody.create(JSON, sb.toString());
            Request request = new Request.Builder().url(url).post(body).addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + "api key").build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            code = response.code();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return code;
    }

    @Override
    public ExtensionResult doCalculate(ExtensionRequest extensionRequest) {
//        String operasi = "3 +2-16/2* 2*4";
//        String operasi = "3 +2-16";
        String result = "Maaf Kak. ada kesalahan :( Mohon dicek lagi inputannya!";
        String operasi = getEasyMapValueByName(extensionRequest, "hitung");
        operasi = operasi.replaceAll("\\s+", "");
        operasi = operasi.replaceAll("multiply", "*");
        operasi = operasi.replaceAll("Multiply", "*");
        operasi = operasi.replaceAll("bagi", "/");
        operasi = operasi.replaceAll("Bagi", "/");
        String[] arr = operasi.split("(?<=[-+*/])|(?=[-+*/])");
        double res = 0;
        try {

            //ubah - jadi + dan merubah next index jd angka negatif
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("-")) {
                    arr[i] = "+";
                    arr[i + 1] = "-" + arr[i + 1];
                }
            }

            //temporary
            List<String> temp = new ArrayList<>();
            for (String obj : arr) {
                temp.add(obj);
            }

            if (operasi.contains("*")) {
                //perkalian
                for (int i = 1; i < temp.size(); i++) {
                    if (temp.get(i).equals("*")) {
                        double angka1 = new Double(temp.get(i - 1));
                        double angka2 = new Double(temp.get(i + 1));
                        String operator = temp.get(i);
                        double hasil = calculate(angka1, operator, angka2);

                        temp.remove(i);
                        temp.remove(i);
                        temp.set(i - 1, hasil + "");
                        i--;
                    }
                }
            }
            if (operasi.contains("/")) {
                //pembagian
                for (int i = 1; i < temp.size(); i++) {
                    if (temp.get(i).equals("/")) {
                        double angka1 = new Double(temp.get(i - 1));
                        double angka2 = new Double(temp.get(i + 1));
                        String operator = temp.get(i);
                        double hasil = calculate(angka1, operator, angka2);

                        temp.remove(i);
                        temp.remove(i);
                        temp.set(i - 1, hasil + "");
                        i--;
                    }
                }
            }

            //menjumlahkan semua angka
            for (int i = 0; i < temp.size(); i += 2) {
                double d = new Double(temp.get(i));
                res = res + d;
            }
            result = "Hasilnya " + res + " Kak.";
        } catch (Exception e) {

        }
        ExtensionResult extensionResult = new ExtensionResult();
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        Map<String, String> output = new HashMap<>();

        output.put(OUTPUT, result);
        extensionResult.setValue(output);
        return extensionResult;
    }

    private double calculate(double angka1, String operator, double angka2) {
        double result = 0;
        switch (operator) {
            case "*":
                result = angka1 * angka2;
                break;
            case "/":
                result = angka1 / angka2;
                break;
            case "+":
                result = angka1 + angka2;
                break;
            case "-":
                result = angka1 - angka2;
                break;
        }
        return result;
    }

    @Override
    public ExtensionResult doValidateDate(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        String sdate = getEasyMapValueByName(extensionRequest, "tanggallahir");
        Map<String, String> clearEntities = new HashMap<>();
        MonthBuilder monthBuilder = new MonthBuilder();
        String result = "";
        sdate = sdate.replaceAll("/", "-");
        sdate = sdate.replaceAll(" ", "-");
        String[] arrDate = sdate.split("-");
        boolean isNumeric;

        // mengubah nama bulan ke dalam angka
        for (int i = 0; i < arrDate.length; i++) {
            isNumeric = arrDate[i].chars().allMatch(Character::isDigit);
            if (isNumeric == false) {
                arrDate[i] = monthBuilder.toMonthNumber(arrDate[i]);
            }
        }

        String[] temp = new String[3];
        int dex = 1;
        int obj;
        try {
            for (int i = 0; i < arrDate.length; i++) {
                obj = Integer.parseInt(arrDate[i]);

                // memposisikan tahun di index ke 0
                if (arrDate[i].length() == 4) {
                    temp[0] = arrDate[i];
                } // mengubah date 1 angka jadi 2 angka
                else if (arrDate[i].length() < 2) {
                    temp[dex] = "0" + arrDate[i];
                    dex++;
                } // memposisikan tanggal dan bulan di index selain 0
                else {
                    // cek hari
                    if (obj > 12) {
                        temp[2] = arrDate[i];
                    } else {
                        temp[dex] = arrDate[i];
                        dex++;
                    }
                }
            }

            // membuat format tanggal yyyy-mm-dd
            for (int i = 0; i < temp.length; i++) {
                result += temp[i];
                if (i < temp.length - 1) {
                    result += "-";
                }
            }
            clearEntities.put("tanggallahir", result);
            extensionResult.setEntities(clearEntities);
        } catch (Exception e) {
            clearEntities = new HashMap<>();
            clearEntities.put("tanggallahir", null);
            extensionResult.setEntities(clearEntities);
        }

        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        Map<String, String> output = new HashMap<>();

        output.put(OUTPUT, result);
        extensionResult.setValue(output);
        return extensionResult;
    }

    @Override
    public ExtensionResult doClearDate(ExtensionRequest extensionRequest) {
        ExtensionResult extensionResult = new ExtensionResult();
        Map<String, String> clearEntities = new HashMap<>();

        String confirm = getEasyMapValueByName(extensionRequest, "confirm");
        if (confirm.equalsIgnoreCase("tidak")) {
            clearEntities.put("tanggal", null);
            clearEntities.put("confirm", null);
        } else {
            clearEntities.put("confirm2", "confirm2");
        }
        extensionResult.setAgent(false);
        extensionResult.setRepeat(false);
        extensionResult.setSuccess(true);
        extensionResult.setNext(true);
        extensionResult.setEntities(clearEntities);
        return extensionResult;
    }

}
