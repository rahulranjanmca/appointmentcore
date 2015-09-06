package com.canigenus.appointmentcore.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.canigenus.appointmentcore.model.BaseAppointment;
import com.canigenus.appointmentcore.model.BaseAppointmentSearch;
import com.canigenus.appointmentcore.model.Day;
import com.canigenus.appointmentcore.model.Type;
import com.canigenus.appointmentcore.model.WorkingPlan;
import com.canigenus.appointmentcore.model.Day.WeekDay;


public abstract class BaseScheduleFillerCron<T extends BaseAppointment, U extends BaseAppointmentSearch> {
	
	protected SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/YYYY");

	public abstract IBaseAppointmentService<T,U> getAppointmentService();
	
	public abstract T instantiateAppointment();
	
	public  List<String> getChildId(){return new ArrayList<String>();}
	
	public abstract Integer getInterval();
	
	public abstract Integer noOfDaysToFill();
	
	public void insertWorkingPlan(Map<String,WorkingPlan> workingPlans, String clientId){
		Set<T> oneDayList= new HashSet<T>();
		for(String workPlanKey:workingPlans.keySet())
		{
		WorkingPlan workingPlan=workingPlans.get(workPlanKey);
		if(!workingPlan.getDays().isEmpty())
		{
		T baseAppointmentSearch= instantiateAppointment();
		baseAppointmentSearch.setClientId(clientId);
		baseAppointmentSearch.setAppointmentWith(workPlanKey);
		Date date=getAppointmentService().getLastInsertDate(baseAppointmentSearch);
		Calendar calendar=Calendar.getInstance();
	
		calendar.setTime(date);
		for(int i=0;i<noOfDaysToFill();i++)
		{
			Date startTime=calendar.getTime();
			calendar.add(Calendar.DATE,1);
			Date endTime=calendar.getTime();
			calendar.add(Calendar.MINUTE,getInterval());
			while(endTime.before(workingPlan.getDays().get(WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK)]).getOpeningAndClosedHours().getEndTime()))
			{
				T baseAppointment= instantiateAppointment();
				baseAppointment.setClientId(clientId);
				baseAppointment.setAppointmentWith(workPlanKey);
				baseAppointment.setStartTime(startTime);
				baseAppointment.setEndTime(endTime);
				baseAppointment.setType(Type.FREE);
				oneDayList.add(baseAppointment);
				startTime=endTime;
				endTime=calendar.getTime();
				calendar.add(Calendar.MINUTE,getInterval());
				startTime=calendar.getTime();
			}
		}
		
		}
		else{
			T baseAppointmentSearch= instantiateAppointment();
			baseAppointmentSearch.setClientId(clientId);
			baseAppointmentSearch.setAppointmentWith(workPlanKey);
			for(String workingPlanDate:workingPlan.getDates().keySet())
			{
				Calendar calendar=Calendar.getInstance();
				
			
		    	Day day=workingPlan.getDates().get(workingPlanDate);
				calendar.setTime(day.getOpeningAndClosedHours().getStartTime());
				Date startTime=calendar.getTime();
				calendar.add(Calendar.MINUTE,getInterval());
				Date endTime=calendar.getTime();
				
				while(endTime.before(day.getOpeningAndClosedHours().getEndTime()))
				{
					T baseAppointment= instantiateAppointment();
					baseAppointment.setClientId(clientId);
					baseAppointment.setAppointmentWith(workPlanKey);
					baseAppointment.setStartTime(startTime);
					baseAppointment.setEndTime(endTime);
					baseAppointment.setType(Type.FREE);
					oneDayList.add(baseAppointment);
					startTime=endTime;
					calendar.add(Calendar.MINUTE,getInterval());
					endTime=calendar.getTime();
				}
			}
			
			
			
		}
		
		}
		getAppointmentService().insert(oneDayList);
		
		
		
		//for)
	}
	
	public void insertWorkingPlanForParent(Map<String,WorkingPlan> workingPlans, List<String> userIds, String clientId){
		for(String workPlanKey:workingPlans.keySet())
		{
		WorkingPlan workingPlan=workingPlans.get(workPlanKey);
	
		T baseAppointmentSearch= instantiateAppointment(); 
		baseAppointmentSearch.setClientId(clientId);
		baseAppointmentSearch.setAppointmentWith(workPlanKey);
		Date date=getAppointmentService().getLastInsertDate(baseAppointmentSearch);
		Calendar calendar=Calendar.getInstance();
		Set<T> oneDayList= new HashSet<T>();
		calendar.setTime(date);
		for(int i=0;i<noOfDaysToFill();i++)
		{
			Date startTime=calendar.getTime();
			calendar.add(Calendar.DATE,1);
			Date endTime=calendar.getTime();
			calendar.add(Calendar.MINUTE,getInterval());
			while(endTime.before(workingPlan.getDays().get(WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK)]).getOpeningAndClosedHours().getEndTime()))
			{
				T baseAppointment= instantiateAppointment();
				baseAppointment.setClientId(clientId);
				baseAppointment.setAppointmentWith(workPlanKey);
				baseAppointment.setStartTime(startTime);
				baseAppointment.setEndTime(endTime);
				baseAppointment.setType(Type.FREE);
				oneDayList.add(baseAppointment);
				startTime=endTime;
				endTime=calendar.getTime();
				calendar.add(Calendar.MINUTE,getInterval());
				startTime=calendar.getTime();
			}
		}
		getAppointmentService().insert(oneDayList);
		}
		
	}
	
	
	
}
