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

    @Value("${app.form.id.test_form}")
    String formIdTest;

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

    @Value("${dolphin.error.atm.not.found}")
    String atmNotFoundMessage;

    @Value("${dolphin.button.title}")
    String buttonTitlePayload;

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

    public String getAtmNotFoundMessage() {
        return atmNotFoundMessage;
    }

    public void setAtmNotFoundMessage(String atmNotFoundMessage) {
        this.atmNotFoundMessage = atmNotFoundMessage;
    }

    public String getButtonTitlePayload() {
        return buttonTitlePayload;
    }

    public void setButtonTitlePayload(String buttonTitlePayload) {
        this.buttonTitlePayload = buttonTitlePayload;
    }

}
