package com.hardraada.minecraft.plugin.telnet;

import java.util.List;
import com.hardraada.minecraft.commons.SimplePlayer;
import net.canarymod.Canary;
import net.canarymod.commandsys.CommandManager;
import net.canarymod.logger.Logman;

/**
  * This class brokers CanaryMod commands to the server.
  * 
  * @author hardraada1
  */
public class CommandBroker {
	private static Logman logger = Logman.getLogman( "Telnet" );
	
	/**
	  * Executes the given command.
	  * 
	  * @param args The command and parameters, if any.
	  * @return
	  */
	public static String execute( String[ ] args ) {
		if( args == null || args.length == 0 ) return "No command was issued.";
		
		String rv = "";
		
		try {
			//  Create a message receiver.
			SimplePlayer receiver = new SimplePlayer( );
			
			//  CommandManager doesn't like the opening "/".
			if( args[ 0 ].indexOf( "/" ) == 0 ) args[ 0 ] = args[ 0 ].substring( 1 );
			
			//  Issue the command.
			CommandManager manager = Canary.commands( );
			boolean resp = manager.parseCommand( receiver, args[ 0 ], args );
			logger.debug( "Response from parseCommand is " + resp );
			
			//  Build the response.
			List<String> msgs = receiver.getMessages( );
			for( String msg : msgs ) rv += msg + "\n";
		} catch( Exception e ) {
			rv = e.getMessage( );
		}
		
		return rv;
	}
}
