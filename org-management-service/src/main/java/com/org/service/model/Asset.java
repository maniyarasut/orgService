package com.org.service.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Asset")
@XmlRootElement
public class Asset implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4435098397792757897L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @OneToOne
    @JoinColumn(name="orgid",nullable=false)
    private Org org;
    
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="empid")
    private Emp emp;
    
    

	public Asset(Long id, String name, Org org, Emp emp) {
		super();
		this.id = id;
		this.name = name;
		this.org = org;
		this.emp = emp;
	}

	public Asset() {
		
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}
    
    

}
