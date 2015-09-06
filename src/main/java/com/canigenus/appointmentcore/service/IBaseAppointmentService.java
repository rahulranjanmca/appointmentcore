package com.canigenus.appointmentcore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import com.canigenus.appointmentcore.model.BaseAppointment;
import com.canigenus.appointmentcore.model.BaseAppointmentSearch;


public interface IBaseAppointmentService<T extends BaseAppointment, U extends BaseAppointmentSearch> {
	
	public void insert(Set<T> a);
	
	public void update(T a);
	
	public Date getLastInsertDate(T baseAppointment);
	
	public String getLeastBusyUser(Set<String> userIds, Date fromTime, Date toTime);
	
	public T getAppointment(String appointmentWith, Date fromDate);
	
	public List<T> getAppointmentList(Set<String> appointmentWith, Date fromDate, Date toDate);
	
	public  abstract Class<T> getEntityClass();
	
	public abstract EntityManager getEntityManager();

	List<T> getListByCriteria(U baseAppointment);

	T getAppointment(Long clientId, String appointmentWith, Date fromDate);

	Map<Long, T> getAppointmentMap(String appointmentWith, Calendar fromDate,
			Calendar toDate, Integer interval);

	Map<Long, T> getAppointmentMap(String clientId, String appointmentWith,
			Calendar fromDate, Calendar toDate, Integer interval);

	void bookAppointment(Date bookTime, String referenceNo,
			String appointmentFor, String createdBy, Double noOfHours,
			String parentId, Set<String> userIds);

}
