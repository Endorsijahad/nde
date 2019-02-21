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
package com.imi.dolphin.sdkwebservice.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author reja
 *
 */
@Component
public class AppProperties {

    @Value("${server.port}")
    String servicePort;

    @Value("${app.form.id}")
    String formId;

    @Value("${mail.username}")
    String mailUsername;

    @Value("${mail.password}")
    String mailPassword;

    @Value("${mail.smtp.auth}")
    String mailStmpAuth;

    @Value("${mail.smtp.starttls.enable}")
    String mailSmtpTls;

    @Value("${mail.smtp.host}")
    String mailSmtpHost;

    @Value("${mail.smtp.port}")
    String mailSmtpPort;

    @Value("${email.recipient1}")
    String emailrecipient1;

    @Value("${email.recipient2}")
    String emailrecipient2;

    @Value("${email.recipient1.name}")
    String namerecipient1;

    @Value("${email.recipient2.name}")
    String namerecipient2;

    @Value("${dolphin.url.base}")
    String baseUrl;

    @Value("${dolphin.api.token}")
    String apiToken;

    @Value("${dolphin.api.form}")
    String apiForm;

    @Value("${fieldName.ticketNumber}")
    String ticketNumber;

    @Value("${dolphin.form.id.cuti}")
    String formIdCuti;

    @Value("${dolphin.url.api}")
    String url;

    @Value("${dolphin.url.path}")
    String path;

    @Value("${dolphin.url.param.collName}")
    String collName;

    @Value("${dolphin.url.param.fieldName}")
    String fieldName;

    @Value("${dolphin.url.param.distance}")
    String distance;

    @Value("${dolphin.url.param.start}")
    String start;

    @Value("${dolphin.url.param.count}")
    String count;

    @Value("${dolphin.google.map.url.query}")
    String googleMapUrl;

    @Value("${dolphin.atm.image.url}")
    String atmUrl;

    @Value("${dolphin.toyota.image.url}")
    String toyotaImgUrl;

    @Value("${dolphin.daihatsu.image.url}")
    String daihatsuImgUrl;

    @Value("${dolphin.bmw.image.url}")
    String bmwImgUrl;

    @Value("${dolphin.peugeot.image.url}")
    String peugeotImgUrl;

    @Value("${dolphin.isuzu.image.url}")
    String isuzuImgUrl;

    @Value("${dolphin.error.atm.not.found}")
    String atmNotFoundMessage;

    @Value("${dolphin.button.title}")
    String buttonTitlePayload;

    @Value("${app.form.id.test_form}")
    String formIdTest;

    @Value("${bitly.access.token}")
    String bitlyAccessToken;

    @Value("${app.form.id.request_form}")
    String formIdRequest;

    @Value("${dolphin.form.id.complaint}")
    String formIdComplaint;

    @Value("${shorten.form.event_ccw}")
    String formEventCCW;

    @Value("${dolphin.call.appointment}")
    String callAppointment;
    
    @Value("${img.sami}")
    String imgSami;
    
    @Value("${img.metrodata}")
    String imgMetrodata;
    
    @Value("${img.miibigger}")
    String imgMiiBigger;
    
    @Value("${img.synnex}")
    String imgSynnex;
    
    @Value("${img.xerindo}")
    String imgXerindo;
    
    @Value("${img.packetsystems}")
    String imgPacketSystems;
    
    @Value("${img.soltius}")
    String imgSoltius;
    
    @Value("${img.mii}")
    String imgMii;
    
    @Value("${img.candra}")
    String imgCandra;
    
    @Value("${img.ben}")
    String imgBen;
    
    @Value("${img.agus}")
    String imgAgus;
    
    @Value("${img.lulu}")
    String imgLulu;
    
    @Value("${img.randy}")
    String imgRandy;
    
    @Value("${img.susanto}")
    String imgSusanto;
    
    @Value("${img.sjafril}")
    String imgSjafril;
    
    @Value("${img.susanto2}")
    String imgSusanto2;
    
    @Value("${img.sjafril2}")
    String imgSjafril2;
    
    @Value("${img.ira}")
    String imgIra;
    
    @Value("${img.titi}")
    String imgTiti;
    
    @Value("${img.alex}")
    String imgAlex;
    
    @Value("${mail.apikey}")
    String mailApiKey;

    public String getButtonTitlePayload() {
        return buttonTitlePayload;
    }

    public void setButtonTitlePayload(String buttonTitlePayload) {
        this.buttonTitlePayload = buttonTitlePayload;
    }

    public String getAtmNotFoundMessage() {
        return atmNotFoundMessage;
    }

    public void setAtmNotFoundMessage(String atmNotFoundMessage) {
        this.atmNotFoundMessage = atmNotFoundMessage;
    }

    public String getGoogleMapUrl() {
        return googleMapUrl;
    }

    public void setGoogleMapUrl(String googleMapUrl) {
        this.googleMapUrl = googleMapUrl;
    }

    public String getAtmUrl() {
        return atmUrl;
    }

    public void setAtmUrl(String atmUrl) {
        this.atmUrl = atmUrl;
    }

    public String getToyotaImgUrl() {
        return toyotaImgUrl;
    }

    public void setToyotaImgUrl(String toyotaImgUrl) {
        this.toyotaImgUrl = toyotaImgUrl;
    }

    public String getDaihatsuImgUrl() {
        return daihatsuImgUrl;
    }

    public void setDaihatsuImgUrl(String daihatsuImgUrl) {
        this.daihatsuImgUrl = daihatsuImgUrl;
    }

    public String getBmwImgUrl() {
        return bmwImgUrl;
    }

    public void setBmwImgUrl(String bmwImgUrl) {
        this.bmwImgUrl = bmwImgUrl;
    }

    public String getPeugeotImgUrl() {
        return peugeotImgUrl;
    }

    public void setPeugeotImgUrl(String peugeotImgUrl) {
        this.peugeotImgUrl = peugeotImgUrl;
    }

    public String getIsuzuImgUrl() {
        return isuzuImgUrl;
    }

    public void setIsuzuImgUrl(String isuzuImgUrl) {
        this.isuzuImgUrl = isuzuImgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormIdCuti() {
        return formIdCuti;
    }

    public void setFormIdCuti(String formIdCuti) {
        this.formIdCuti = formIdCuti;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getApiForm() {
        return apiForm;
    }

    public void setApiForm(String apiForm) {
        this.apiForm = apiForm;
    }

    public String getNamerecipient1() {
        return namerecipient1;
    }

    public void setNamerecipient1(String namerecipient1) {
        this.namerecipient1 = namerecipient1;
    }

    public String getEmailrecipient1() {
        return emailrecipient1;
    }

    public void setEmailrecipient1(String emailrecipient1) {
        this.emailrecipient1 = emailrecipient1;
    }

    public String getEmailrecipient2() {
        return emailrecipient2;
    }

    public void setEmailrecipient2(String emailrecipient2) {
        this.emailrecipient2 = emailrecipient2;
    }

    public String getNamerecipient2() {
        return namerecipient2;
    }

    public void setNamerecipient2(String namerecipient2) {
        this.namerecipient2 = namerecipient2;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public void setMailUsername(String mailUsername) {
        this.mailUsername = mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getMailStmpAuth() {
        return mailStmpAuth;
    }

    public void setMailStmpAuth(String mailStmpAuth) {
        this.mailStmpAuth = mailStmpAuth;
    }

    public String getMailSmtpTls() {
        return mailSmtpTls;
    }

    public void setMailSmtpTls(String mailSmtpTls) {
        this.mailSmtpTls = mailSmtpTls;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(String mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

    public String getFormIdTest() {
        return formIdTest;
    }

    public void setFormIdTest(String formIdTest) {
        this.formIdTest = formIdTest;
    }

    public String getBitlyAccessToken() {
        return bitlyAccessToken;
    }

    public void setBitlyAccessToken(String bitlyAccessToken) {
        this.bitlyAccessToken = bitlyAccessToken;
    }

    public String getFormIdRequest() {
        return formIdRequest;
    }

    public void setFormIdRequest(String formIdRequest) {
        this.formIdRequest = formIdRequest;
    }

    public String getFormIdComplaint() {
        return formIdComplaint;
    }

    public void setFormIdComplaint(String formIdComplaint) {
        this.formIdComplaint = formIdComplaint;
    }

    public String getFormEventCCW() {
        return formEventCCW;
    }

    public void setFormEventCCW(String formEventCCW) {
        this.formEventCCW = formEventCCW;
    }

    public String getCallAppointment() {
        return callAppointment;
    }

    public void setCallAppointment(String callAppointment) {
        this.callAppointment = callAppointment;
    }

    public String getImgSami() {
        return imgSami;
    }

    public void setImgSami(String imgSami) {
        this.imgSami = imgSami;
    }

    public String getImgMetrodata() {
        return imgMetrodata;
    }

    public void setImgMetrodata(String imgMetrodata) {
        this.imgMetrodata = imgMetrodata;
    }

    public String getImgMiiBigger() {
        return imgMiiBigger;
    }

    public void setImgMiiBigger(String imgMiiBigger) {
        this.imgMiiBigger = imgMiiBigger;
    }

    public String getImgSynnex() {
        return imgSynnex;
    }

    public void setImgSynnex(String imgSynnex) {
        this.imgSynnex = imgSynnex;
    }

    public String getImgXerindo() {
        return imgXerindo;
    }

    public void setImgXerindo(String imgXerindo) {
        this.imgXerindo = imgXerindo;
    }

    public String getImgPacketSystems() {
        return imgPacketSystems;
    }

    public void setImgPacketSystems(String imgPacketSystems) {
        this.imgPacketSystems = imgPacketSystems;
    }

    public String getImgSoltius() {
        return imgSoltius;
    }

    public void setImgSoltius(String imgSoltius) {
        this.imgSoltius = imgSoltius;
    }

    public String getImgMii() {
        return imgMii;
    }

    public void setImgMii(String imgMii) {
        this.imgMii = imgMii;
    }

    public String getImgCandra() {
        return imgCandra;
    }

    public void setImgCandra(String imgCandra) {
        this.imgCandra = imgCandra;
    }

    public String getImgBen() {
        return imgBen;
    }

    public void setImgBen(String imgBen) {
        this.imgBen = imgBen;
    }

    public String getImgAgus() {
        return imgAgus;
    }

    public void setImgAgus(String imgAgus) {
        this.imgAgus = imgAgus;
    }

    public String getImgLulu() {
        return imgLulu;
    }

    public void setImgLulu(String imgLulu) {
        this.imgLulu = imgLulu;
    }

    public String getImgRandy() {
        return imgRandy;
    }

    public void setImgRandy(String imgRandy) {
        this.imgRandy = imgRandy;
    }

    public String getImgSusanto() {
        return imgSusanto;
    }

    public void setImgSusanto(String imgSusanto) {
        this.imgSusanto = imgSusanto;
    }

    public String getImgSjafril() {
        return imgSjafril;
    }

    public void setImgSjafril(String imgSjafril) {
        this.imgSjafril = imgSjafril;
    }

    public String getImgSusanto2() {
        return imgSusanto2;
    }

    public void setImgSusanto2(String imgSusanto2) {
        this.imgSusanto2 = imgSusanto2;
    }

    public String getImgSjafril2() {
        return imgSjafril2;
    }

    public void setImgSjafril2(String imgSjafril2) {
        this.imgSjafril2 = imgSjafril2;
    }

    public String getImgIra() {
        return imgIra;
    }

    public void setImgIra(String imgIra) {
        this.imgIra = imgIra;
    }

    public String getImgTiti() {
        return imgTiti;
    }

    public void setImgTiti(String imgTiti) {
        this.imgTiti = imgTiti;
    }

    public String getImgAlex() {
        return imgAlex;
    }

    public void setImgAlex(String imgAlex) {
        this.imgAlex = imgAlex;
    }

    public String getMailApiKey() {
        return mailApiKey;
    }

    public void setMailApiKey(String mailApiKey) {
        this.mailApiKey = mailApiKey;
    }
    
    

}
