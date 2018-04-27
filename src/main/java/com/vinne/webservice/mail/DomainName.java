package com.vinne.webservice.mail;

public enum DomainName {
    DOMAIN("doenda.nl"), WANTED("robber.com");

    private String domain;

    private DomainName(String c) {
	domain = c;
    }

    public String getDomain() {

	return domain;
    }

}
