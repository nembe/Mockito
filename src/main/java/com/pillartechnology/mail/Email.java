package com.pillartechnology.mail;

import java.util.Date;

public class Email {

    private String afZender;
    private String adres;
    private String domain;
    private String user;
    private String body;
    private Date timestamp;
    private String tijdStip;
    private String status;

    private Timestamper timestamper;

    public Email() {
	timestamper = new Timestamper();
	tijdStip = timestamper.getStamp();
    }

    public String getTijdStip() {
	return tijdStip;
    }

    public void setTijdStip(String tijdStip) {
	this.tijdStip = tijdStip;
    }

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
    }

    public String getAdres() {
	return adres;
    }

    public void setAdres(String adres) {
	this.adres = adres;
    }

    public Date getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    public String getAfZender() {
	return afZender;
    }

    public void setAfZender(String afZender) {
	this.afZender = afZender;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Timestamper getTimestamper() {
	return timestamper;
    }

    public void setTimestamper(Timestamper timestamper) {
	this.timestamper = timestamper;
    }

}
