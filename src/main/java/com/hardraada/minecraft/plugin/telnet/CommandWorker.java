package com.hardraada.minecraft.plugin.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;
import net.canarymod.logger.Logman;

/**
  * This class interacts with a Telnet client via a socket that is given at creation time.  
  * The worker uses very simple authentication with a username and password being stored in 
  * plain text in the plugin configuration file.  It then brokers commands to the CanaryMod 
  * server.  To disconnect a telnet client, enter the command "/exit".
  * 
  * @author hardraada1
  */
public class CommandWorker implements Runnable {
	private static final String EXIT_COMMAND = "exit";
	private static final String USER_TOKEN = "${username}";
	
	private static Logman logger = Logman.getLogman( "Telnet" );
	
	private Credential credential;
	private String message;
	private Socket socket;
	
	/**
	  * Constructs a new CommandWorker with the given socket and user credential.
	  * 
	  * @param socket
	  * @param credential
	  */
	public CommandWorker( Socket socket, Credential credential, String message ) {
		this.credential = credential;
		this.message = message;
		this.socket = socket;
	}
	
	/**
	  * Interacts with the Telnet client.
	  */
	public void run( ) {
		OutputStream os = null;
		PrintWriter pw = null;
		
		try {
			os = this.socket.getOutputStream( );
			pw = new PrintWriter( os, true );
			
			//  Request the username.
			pw.println( "Username:" );
			
			//  Read the username.
			BufferedReader br = new BufferedReader( new InputStreamReader( this.socket.getInputStream( ) ) );
			String username = br.readLine( );
			
			//  Request the password.
			pw.println( "Password:" );
			
			//  Read the password.
			String password = br.readLine( );
			
			//  Authenticate
			boolean authenticated = ( username.equals( this.credential.getUsername( ) ) && password.equals( this.credential.getPassword( ) ) );
			
			if( authenticated ) {
				//  User is authenticated.
				String msg = this.message.replaceAll( Pattern.quote( CommandWorker.USER_TOKEN ), this.credential.getUsername( ) );
				pw.println( msg );
				String cmd = "";
				
				while( !CommandWorker.EXIT_COMMAND.equals( cmd ) ) {
					//  Request a command.
					pw.println( "Command:" );
					cmd = br.readLine( );
					logger.debug( "Executing command " + cmd );
					
					//  Execute the command.
					String args[ ] = cmd.split( " " );
					String resp = CommandBroker.execute( args );
					
					//  Write the response to the client.
					pw.println( resp );
					logger.debug( resp );
				}
			} else {
				//  User is not authenticated.
				pw.println( "Authentication failed." );
			}
		} catch( IOException e ) {
			logger.error( "An error occurred interacting with the client:  " + e.getMessage( ) );
		} finally {
			try {
				//  Close up our network and output resources.
				if( pw != null ) pw.close( );
				if( os != null ) os.close( );
				if( this.socket != null ) this.socket.close( );
			} catch( IOException e ) {
				logger.error( "An error occurred closing the socket:  " + e.getMessage( ) );
			}
		}
	}	
}
