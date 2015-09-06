package com.canigenus.appointmentcore.model;

import java.util.Date;



public class BaseAppointmentSearch  extends BaseAppointment{

	    
	Date startTimeFrom;
	
	Date startTimeTo;
	
	public Date getStartTimeFrom() {
		return startTimeFrom;
	}

	public void setStartTimeFrom(Date startTimeFrom) {
		this.startTimeFrom = startTimeFrom;
	}

	public Date getStartTimeTo() {
		return startTimeTo;
	}

	public void setStartTimeTo(Date startTimeTo) {
		this.startTimeTo = startTimeTo;
	}
	
	
	

}
