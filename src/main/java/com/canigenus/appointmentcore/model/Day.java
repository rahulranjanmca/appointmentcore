package com.canigenus.appointmentcore.model;

import java.util.ArrayList;
import java.util.List;

public class Day {
	
	
	StartTimeWithEndTime openingAndClosedHours;
	
	List<StartTimeWithEndTime> breaks= new ArrayList<StartTimeWithEndTime>();
	
	WeekDay weekDay;
	
	List<BaseAppointment> baseAppointments=new ArrayList<BaseAppointment>();
	
	public enum WeekDay{SUNDAY, MONDAY, TUESDAY, THRUSDAY, FRIDAY, SATURDAY}

	public StartTimeWithEndTime getOpeningAndClosedHours() {
		return openingAndClosedHours;
	}

	public void setOpeningAndClosedHours(StartTimeWithEndTime openingAndClosedHours) {
		this.openingAndClosedHours = openingAndClosedHours;
	}

	public List<StartTimeWithEndTime> getBreaks() {
		return breaks;
	}

	public void setBreaks(List<StartTimeWithEndTime> breaks) {
		this.breaks = breaks;
	}

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weekDay == null) ? 0 : weekDay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (weekDay != other.weekDay)
			return false;
		return true;
	}
	
	

}
