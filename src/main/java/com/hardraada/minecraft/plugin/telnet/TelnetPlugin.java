package com.hardraada.minecraft.plugin.telnet;

import net.canarymod.plugin.PluginListener;
import net.visualillusionsent.utils.PropertiesFile;
import com.hardraada.minecraft.commons.PluginBase;

/**
  * This CanaryMod plugin class allows Telnet clients to connect and manage the server.  It allows 
  * for a username and password to be set in the configuration file.  The port may also be set.  The 
  * default port is 25523.  Finally, a welcome message may be specified.  The default is "Hello ${username}, 
  * Welcome to Minecraft".  You may specify the username within the message by using the above variable 
  * notation.
  * 
  * @author hardraada1
  */
public class TelnetPlugin extends PluginBase implements PluginListener {
	private TelnetServer server;
	
	@Override
	public void disable( ) {
		//  Tell the Telnet server to stop listening and shut down.
		this.server.kill( );
	}
	
	@Override
	public boolean enable( ) {
		super.enable( );
		
		//  Read configuration data and convert it to a usable format.
		logger.info( "Reading configuration data. . ." );
		PropertiesFile cfg = this.getConfig( );
		Credential cred = new Credential( cfg.getString( "username" ), cfg.getString( "password" ) );
		int port = cfg.getInt( "port" );
		String message = cfg.getString( "welcome.message" );
		
		//  Start the Telnet server.
		logger.info( "Starting new telnet thread. . ." );
		this.server = new TelnetServer( cred, port, message );
		Thread thread = new Thread( this.server );
		thread.start( );
		
		logger.info( "Telnet started on port " + port + "." );
		
		return true;		
	}
}
