package com.callumcarmicheal.solar.exceptions;

import com.callumcarmicheal.solar.objects.IPlanet;

public class PlanetException extends Exception {

	public IPlanet planet;
	public ExCause cause;
	public String reasonString;
	
	
	public PlanetException(IPlanet planet, ExCause Cause, String Reason) {
		this.planet = planet;
		this.cause = Cause;
		this.reasonString = Reason;
	}
	
	public PlanetException(ExCause Cause, String Reason) {
		this(null, Cause, Reason);
	}
}
