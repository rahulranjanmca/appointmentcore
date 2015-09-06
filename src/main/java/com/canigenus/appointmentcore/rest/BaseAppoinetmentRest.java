package com.canigenus.appointmentcore.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.canigenus.appointmentcore.model.BaseAppointment;
import com.canigenus.appointmentcore.model.BaseAppointmentSearch;
import com.canigenus.appointmentcore.model.Type;
import com.canigenus.appointmentcore.service.IBaseAppointmentService;

public abstract class BaseAppoinetmentRest<T extends BaseAppointment,  U extends BaseAppointmentSearch> {
	
	public abstract IBaseAppointmentService<T,U> getAppointmentService();
	
	public abstract T instantiateAppointment();
	
	public abstract U instantiateAppointmentSearch();

	public abstract Integer getInterval();
	
	public T getAppointment(String appointmentWith, Date startDate)
	{
		return getAppointmentService().getAppointment(appointmentWith,startDate);
	}
	public List<T> getAppointmentList(Set<String> appointmentWith, Date startDate, Date endDate)
	{
		return getAppointmentService().getAppointmentList(appointmentWith,startDate, endDate);
	}
	
	public void bookAppointment(T baseAppointment)
	{
		baseAppointment.setType(Type.BOOKED);
		getAppointmentService().update(baseAppointment);
	}
	

	public void cancelAppointment(T baseAppointment)
	{
		baseAppointment.setAppointmentFor(null);
		baseAppointment.setType(Type.FREE);
		getAppointmentService().update(baseAppointment);
	}
	
	public void updateParentCalendar(String parent, T baseAppointment)
	{
		
	}
	
	
	public Map<Integer, List<T>> getAppointmentList(String appointmentWith, Calendar startTimeOrginal, Calendar  endTime) {
		/*Calendar calendar= Calendar.getInstance();
	    calendar.set(Calendar.HOUR, 8);
	    calendar.set(Calendar.MINUTE, 30);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    calendar.set(Calendar.AM_PM, Calendar.AM);
	    Calendar toCalendar= Calendar.getInstance();
	    toCalendar.set(Calendar.SECOND, 0);
	    toCalendar.set(Calendar.MILLISECOND, 0);
	    toCalendar.add(Calendar.DATE, 6);
	    toCalendar.set(Calendar.HOUR, 8);
	    toCalendar.set(Calendar.MINUTE, 30);
	    toCalendar.set(Calendar.AM_PM, Calendar.PM);*/
		Calendar startTime=(Calendar)startTimeOrginal.clone();
		Map<Long,T> map=getAppointmentService().getAppointmentMap(appointmentWith, startTime, endTime, getInterval());
	    int i=0;
	    Map<Integer,List<T>> appMap= new HashMap<Integer, List<T>>();
	  
	    while(startTime.before(endTime))
	    {
	    	if(appMap.get(i)==null)
	    		appMap.put(i, new ArrayList<T>());
	    	T  appointmentSlot= map.get(startTime.getTimeInMillis());
	    	if(appointmentSlot==null)
	    	{
	    		appointmentSlot= instantiateAppointment();
	    		appointmentSlot.setType(Type.BREAK);
	    		appMap.get(i).add(appointmentSlot);
	    	}
	    	else{
	    		appMap.get(i).add(appointmentSlot);
	    	}
	    	
	    	if(startTime.get(Calendar.HOUR)==endTime.get(Calendar.HOUR) && startTime.get(Calendar.MINUTE)==endTime.get(Calendar.MINUTE) && startTime.get(Calendar.AM_PM)==endTime.get(Calendar.AM_PM))
	    	{
	    		  startTime.set(Calendar.HOUR, startTimeOrginal.get(Calendar.HOUR));
	    		  startTime.set(Calendar.MINUTE, startTimeOrginal.get(Calendar.MINUTE));
	    		  startTime.set(Calendar.SECOND, 0);
	    		  startTime.add(Calendar.DATE, 1);
	    		  startTime.set(Calendar.AM_PM, startTimeOrginal.get(Calendar.AM_PM));
	    		  i=0;
	    		  continue;
	    	}
	    	startTime.add(Calendar.MINUTE, getInterval());
	    	i++;
	    }
	    return appMap;
		
		
	}
	
	

}
