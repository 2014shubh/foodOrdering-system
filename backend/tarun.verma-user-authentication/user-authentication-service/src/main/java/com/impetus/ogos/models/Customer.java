package com.impetus.ogos.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	@Max(value = 11)
	@Column(columnDefinition = "integer default 0")
	private int negativePoints;

	@OneToMany(
	        mappedBy = "customer",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	    private List<Address> addresses = new ArrayList<>();

	public Customer() {
		super();
	}

	public Customer(int cid, User user, @Max(11) int negativePoints) {
		super();
		this.cid = cid;
		this.user = user;
		this.negativePoints = negativePoints;
	}

	public int getNegativePoints() {
		return negativePoints;
	}

	public void setNegativePoints(int negativePoints) {
		this.negativePoints = negativePoints;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}



}
