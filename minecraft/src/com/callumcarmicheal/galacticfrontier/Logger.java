package com.callumcarmicheal.galacticfrontier;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;

public class Logger {

	private java.util.logging.Logger log =
		java.util.logging.Logger.getLogger(
			"GalacticFrontier"
	);
	
	public Logger() {}

	/**
	 * Add information to Log
	 * 
	 * @param 	LoggingLevel	The Level of message to be Logged
	 * @param  	Message			The message to be logged into console and/or file
	 */
	public void Log(String LoggingLevel, String Message) { log.log(Level.parse(LoggingLevel), Message); }
	
	/**
	 * Add information to Log
	 * 
	 * @param 	LoggingLevel	The Level of message to be Logged
	 * @param  	Message			The message to be logged into console and/or file
	 */
	public void Log(java.util.logging.Level level, String message) { log.log(level, message); }
	
	/**
	 * Add information to Log
	 * 
	 * @param 	level			The level to be logged at
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	message			The message to be logged
	 */
	public void Log(java.util.logging.Level level, String sourceClass, String sourceMethod, String message) { log.logp(level, sourceClass, sourceMethod, message); }
	
	/**
	 * Add information to Log
	 * 
	 * @param 	level			The level to be logged at
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	sourceMethod	The source method parameters
	 * @param  	message			The message to be logged
	 */
	public void Log(java.util.logging.Level level, String sourceClass, String sourceMethod, Object[] sourceMethodParams, String message) { log.logp(level, sourceClass, sourceMethod, message, sourceMethodParams); }
	
	/**
	 * Add information to Log under INFO Logging Level
	 * 		(Low, Just info used for developer or user)
	 * 
	 * @param  	Message			The message to be logged
	 */
	public void INFO(String Message) { log.log(Level.INFO, Message); }
	
	/**
	 * Add information to Log under WARNING Logging Level
	 * 		(HIGH, most likely to contain a error)
	 * 
	 * @param  	Message			The message to be logged
	 */
	public void WARNING(String Message) { log.log(Level.WARNING, Message); }
	
	/**
	 * Add information to Log under SEVERE Logging Level
	 * 		(Medium, can contain a error)
	 * 
	 * @param  	Message			The message to be logged
	 */
	public void SEVERE(String Message) { log.log(Level.SEVERE, Message); }
	
	/**
	 * Print a exception to the log under SERVE
	 * 
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	ex				The exception to be logged
	 * @param  	Extra			Any extra information needed to be displayed
	 * @param  	Params			The source method parameters
	 */
	public void EXCEPTION(String sourceClass, String sourceMethod, java.lang.Exception ex, String Extra, Object[] Params) { log.logp(Level.SEVERE, sourceClass, sourceMethod, Extra + "  | ERR " + ex.getStackTrace(), Params); }
	
	/**
	 * Print a exception to the log under SERVE
	 * 
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	ex				The exception to be logged
	 * @param  	Extra			Any extra information needed to be displayed
	 */
	public void EXCEPTION(String sourceClass, String sourceMethod, java.lang.Exception ex, String Extra) { log.logp(Level.SEVERE, sourceClass, sourceMethod, Extra + "  | ERR " + ex.getStackTrace()); }
	
	/**
	 * Print a exception to the log under SERVE
	 * 
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	ex				The exception to be logged
	 * @param  	Params			The source method parameters
	 */
	public void EXCEPTION(String sourceClass, String sourceMethod, java.lang.Exception ex, Object[] Params) { log.logp(Level.SEVERE, sourceClass, sourceMethod, " ERR " + ex.getStackTrace(), Params); }
	
	/**
	 * Print a exception to the log under SERVE
	 * 
	 * @param  	sourceClass		The source class the message is from
	 * @param  	sourceMethod	The source method inside the class
	 * @param  	ex				The exception to be logged
	 */
	public void EXCEPTION(String sourceClass, String sourceMethod, java.lang.Exception ex) { log.logp(Level.SEVERE, sourceClass, sourceMethod, " ERR " + ex.getStackTrace()); }
}
