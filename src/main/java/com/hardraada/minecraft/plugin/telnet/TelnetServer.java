package com.hardraada.minecraft.plugin.telnet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import net.canarymod.logger.Logman;

/**
  * This class operates as a simple Telnet server.  The port it listens on can be modified through 
  * the plugin configuration file.  The default port is 25523.
  * 
  * @author hardraada1
  */
public class TelnetServer implements Runnable {
	private static final String DEFAULT_MESSAGE = "Hello ${username}, welcome to Minecraft!";
	private static final int DEFAULT_PORT = 25523;

	private static Logman logger = Logman.getLogman( "Telnet" );
	
	private Credential credential;
	private String message;
	private int port;
	private boolean running;
	
	/**
	  * Constructs a new TelnetServer with the given credential.  It will start 
	  * on the default port 25523.
	  * 
	  * @param credential
	  */
	public TelnetServer( Credential credential ) {
		this( credential, TelnetServer.DEFAULT_PORT );
	}
	
	/**
	  * Constructs a new TelnetServer with the given credential.  It will start 
	  * on the given port and display the default welcome message.
	  * 
	  * @param credential
	  * @param port
	  */
	public TelnetServer( Credential credential, int port ) {
		this( credential, port, TelnetServer.DEFAULT_MESSAGE );
	}
	
	/**
	  * Constructs a new TelnetServer with the given credential.  It will start 
	  * on the given port and display the given welcome message.
	  * 
	  * @param credential
	  * @param port
	  * @param message
	  */
	public TelnetServer( Credential credential, int port, String message ) {
		this.setCredential( credential );
		this.setPort( port );
		this.setWelcomeMessage( message );
		
		this.running = true;
	}
	
	/**
	  * Returns the credential used to authenticate against the server.
	  * 
	  * @return
	  */
	public Credential getCredential( ) {
		return this.credential;
	}
	
	/**
	  * Returns the port number the server is listening on.
	  * 
	  * @return
	  */
	public int getPort( ) {
		return this.port;
	}
	
	/**
	  * Returns the welcome message the server displays on login.
	  * 
	  * @return
	  */
	public String getWelcomeMessage( ) {
		return this.message;
	}
	
	/**
	  * Shuts the server down.
	  */
	public void kill( ) {
		this.running = false;
	}
	
	/**
	  * Starts the server up.
	  */
	public void run( ) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket( this.port );
			
			while( this.running ) {
				//  Set up the socket and output stream.  This will block waiting 
				//  for a request.
				Socket socket = serverSocket.accept( );
				
				//  Start up a new thread to process the request.
				Thread thread = new Thread( new CommandWorker( socket, this.getCredential( ), this.getWelcomeMessage( ) ) );
				thread.start( );
			}
		} catch( IOException e ) {
			logger.error( "An error occurred opening the server socket:  " + e.getMessage( ) );
		} finally {
			if( serverSocket != null ) {
				try { 
					serverSocket.close( );
				} catch( IOException e ) {
					logger.error( "An error occurred closing the server socket:  " + e.getMessage( ) );
				}
			}
		}
	}
	
	/**
	  * Sets the credential to be used to authenticate to the server.
	  * 
	  * @param credential
	  */
	public void setCredential( Credential credential ) {
		this.credential = credential;
	}
	
	/**
	  * Sets the port the server will listen on.
	  * 
	  * @param port
	  */
	public void setPort( int port ) {
		this.port = port;
	}
	
	/**
	  * Sets the welcome message to be displayed on login.
	  * 
	  * @param message
	  */
	public void setWelcomeMessage( String message ) {
		this.message = message;
	}
}
