package com.canigenus.appointmentcore.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.canigenus.appointmentcore.model.Day.WeekDay;

public class WorkingPlan {
	Map<WeekDay,Day> days= new  LinkedHashMap<WeekDay,Day>();
	
	Map<String,Day> dates= new  LinkedHashMap<String,Day>();

	public Map<WeekDay, Day> getDays() {
		return days;
	}

	public void setDays(Map<WeekDay, Day> days) {
		this.days = days;
	}

	public Map<String, Day> getDates() {
		return dates;
	}

	public void setDates(Map<String, Day> dates) {
		this.dates = dates;
	}



}
