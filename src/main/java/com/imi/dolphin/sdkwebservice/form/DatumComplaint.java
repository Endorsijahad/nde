/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imi.dolphin.sdkwebservice.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Nande
 */
public class DatumComplaint {
    
    @SerializedName("kakak_ada_masalah_apa__")
    @Expose
    private String keluhan;
    
    @SerializedName("silahkan_masukannya_kak___kakak_ingin_sami_bagaimana___sami_mohon_maaf_sebelumnya_ya__kak_")
    @Expose
    private String masukan;
    
    @SerializedName("ticket_number")
    @Expose
    private String ticketNumber;
    @SerializedName("form_id")
    @Expose
    private String formId;
    @SerializedName("channel_type")
    @Expose
    private String channelType;
    @SerializedName("channel_key")
    @Expose
    private String channelKey;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("account_name")
    @Expose
    private String accountName;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("id")
    @Expose
    private String id;

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getMasukan() {
        return masukan;
    }

    public void setMasukan(String masukan) {
        this.masukan = masukan;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
