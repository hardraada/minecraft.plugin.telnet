package com.hardraada.minecraft.plugin.telnet;

/**
  * This class represents a simple user credential, in this case a username 
  * and a password.
  * 
  * @author hardraada1
  */
public class Credential {
	private String password;
	private String username;
	
	/**
	  * Constructs a new Credential.
	  */
	public Credential( ) {
		
	}
	
	/**
	  * Constructs a new Credential with the given username and password.
	  * 
	  * @param username
	  * @param password
	  */
	public Credential( String username, String password ) {
		this.setUsername( username );
		this.setPassword( password );
	}
	
	/**
	  * Returns the password.
	  * 
	  * @return
	  */
	public String getPassword( ) {
		return this.password;
	}
	
	/**
	  * Returns the username.
	  * 
	  * @return
	  */
	public String getUsername( ) {
		return this.username;
	}
	
	/**
	  * Sets the password.
	  * 
	  * @param value
	  */
	public void setPassword( String value ) {
		this.password = value;
	}
	
	/**
	  * Sets the username.
	  * 
	  * @param value
	  */
	public void setUsername( String value ) {
		this.username = value;
	}
}