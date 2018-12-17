package com.ricardo.models;

public class Customer {
	private int cid;
	private String nombre;
	private String dni;
	private String email;
	
	
	public Customer() {}

	public Customer(int cid, String nombre, String dni, String email) {
		super();
		this.cid = cid;
		this.nombre = nombre;
		this.dni = dni;
		this.email = email;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
