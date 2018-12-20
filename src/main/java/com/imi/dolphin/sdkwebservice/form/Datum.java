
package com.imi.dolphin.sdkwebservice.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("silahkan_isi_nama_lengkap_kamu_ya")
    @Expose
    private String nama;
    
    @SerializedName("silahkan_isi_nik_kamu")
    @Expose
    private String nik;
    
    @SerializedName("silahkan_isi_lembaga_kamu")
    @Expose
    private String lembaga;
    
    @SerializedName("tujuan_izin_kamu_untuk_")
    @Expose
    private String mengajukanPermohonanIjinUntuk;
    
    @SerializedName("pilihlah_tanggal_kapan_kamu_ingin_ajukan_cuti")
    @Expose
    private String tanggal;
    
    @SerializedName("berapa_lama_waktu_kamu_izin_cuti_")
    @Expose
    private String waktuIjin;
    
    @SerializedName("apa_keperluan_kamu_untuk_cuti_izin_")
    @Expose
    private String keperluanKeterangan;
    
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



    public String getTanggal() {
		return tanggal;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getMengajukanPermohonanIjinUntuk() {
        return mengajukanPermohonanIjinUntuk;
    }

    public void setMengajukanPermohonanIjinUntuk(String mengajukanPermohonanIjinUntuk) {
        this.mengajukanPermohonanIjinUntuk = mengajukanPermohonanIjinUntuk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLembaga() {
        return lembaga;
    }

    public void setLembaga(String lembaga) {
        this.lembaga = lembaga;
    }
    
    public String getWaktuIjin() {
        return waktuIjin;
    }

    public void setWaktuIjin(String waktuIjin) {
        this.waktuIjin = waktuIjin;
    }

    public String getKeperluanKeterangan() {
        return keperluanKeterangan;
    }

    public void setKeperluanKeterangan(String keperluanKeterangan) {
        this.keperluanKeterangan = keperluanKeterangan;
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
