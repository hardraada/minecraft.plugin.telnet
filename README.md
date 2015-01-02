minecraft.plugin.telnet
=======================

Overview:
Allows telnet access to a CanaryMod server so that commands may be relayed remotely.

Dependencies:
You may either download the project and build your own JAR or 
you may download the JAR directly from 
https://github.com/hardraada/minecraft.plugin.telnet/blob/master/target/minecraft.plugin.telnet-1.0.jar.  
This project relies on minecraft.commons which can be found 
at https://github.com/hardraada/minecraft.commons.  You may 
download that JAR directly from 
https://github.com/hardraada/minecraft.commons/blob/master/target/minecraft.commons-1.0.jar

Permissions:
None.

Configuration:
You must specify a username and password used to log in to the telnet session.  You may also 
specify a port (the default is 25523) and a welcome message, if desired.

username=telnet_user
password=Passw0rd
port=25523
welcome.message=Welcome to my Minecraft server, ${username}!

Usage:
Once authenticated, you may issue commands just as you would in the server 
console.  They may include the initial "/" but it is not required.  Commands 
that have output will have it redirected to the telnet client.