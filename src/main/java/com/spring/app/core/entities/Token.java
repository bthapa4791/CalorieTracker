package com.spring.app.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "token")
@NamedQueries({
    @NamedQuery(
            name = Token.FIND_BY_TOKENSERIES,
            query = "select t from Token t where series = :series"
    )
})
public class Token{
	
	public static final String FIND_BY_TOKENSERIES = "user.findBySeries";

	@Id
	private String series;

	private String value;

	private Date date;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "username")
	private String username;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserLogin() {
		return username;
	}

	public void setUserLogin(String username) {
		this.username = username;
	}

}
