package com.canigenus.appointmentcore.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.canigenus.appointmentcore.model.BaseAppointment;
import com.canigenus.appointmentcore.model.BaseAppointmentSearch;
import com.canigenus.appointmentcore.model.Type;


public abstract class BaseAppointmentService<T extends BaseAppointment, U extends BaseAppointmentSearch> implements IBaseAppointmentService<T, U>{
	
	public void insert(Set<T> a)
	{
	for(T t:a){	
		getEntityManager().persist(t);
		}
	}
	
	public void update(T a)
	{
		getEntityManager().merge(a);
	}
	
	@Override
	public Date getLastInsertDate(T baseAppointment)
	{
		Calendar calendar=Calendar.getInstance();
		Query query= getEntityManager().createQuery("select endTime from "+getEntityClass().getSimpleName()+" where appointmentWith in :userIds  order by endTime desc");
		query.setParameter("userIds", baseAppointment.getAppointmentWith());
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<?> list=query.getResultList();
		if(list.isEmpty())
		{
			calendar.setTime( new Date());
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		else{
			calendar.setTime(((Timestamp)list.get(0)));
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}
		
	}
	@Override
	public String getLeastBusyUser(Set<String> userIds, Date fromDate, Date toDate)
	{
		Query query= getEntityManager().createQuery("select appointmentWith, count(*) as freeSlot from "+getEntityClass().getSimpleName()+" where appointmentWith in (select appointmentWith from "+getEntityClass().getSimpleName()+" where  type=:type and appointmentWith in :userIds and startTime = :startTime )   group by appointmentWith order by freeSlot");
		query.setParameter("userIds", userIds);
		query.setParameter("type", Type.FREE);
		query.setParameter("startTime", fromDate);
		query.setParameter("type", Type.FREE);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return ((Object[])query.getResultList().get(0))[0].toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getAppointment(String appointmentWith, Date fromDate)
	{
		Query query= getEntityManager().createQuery("select apptbooking from "+getEntityClass().getSimpleName()+" apptbooking where startTime = :startDate and appointmentWith=:appointmentWith order by startTime", getEntityClass());
		query.setParameter("startDate", fromDate);
		query.setParameter("appointmentWith", appointmentWith);
		List<T> list= query.getResultList();
		if(list.isEmpty())
		return null;
		else
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getAppointment(Long clientId, String appointmentWith, Date fromDate)
	{
		Query query= getEntityManager().createQuery("select apptbooking from "+getEntityClass().getSimpleName()+" apptbooking where clientId = :clientId and  startTime = :startDate and appointmentWith=:appointmentWith order by startTime", getEntityClass());
		query.setParameter("startDate", fromDate);
		query.setParameter("appointmentWith", appointmentWith);
		query.setParameter("clientId", clientId);
		List<T> list= query.getResultList();
		if(list.isEmpty())
		return null;
		else
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long,T> getAppointmentMap(String appointmentWith, Calendar fromDate, Calendar toDate, Integer interval)
	{
		Query query= getEntityManager().createQuery("select apptbooking from "+getEntityClass().getSimpleName()+" apptbooking where startTime between  :startDate and :endTime and appointmentWith=:appointmentWith order by startTime", getEntityClass());
		query.setParameter("startDate", fromDate.getTime());
		query.setParameter("endDate", toDate.getTime());
		query.setParameter("appointmentWith", appointmentWith);
		List<T> list= query.getResultList();
		Calendar fromDateClone=(Calendar)fromDate.clone();
		Map<Long,T> map= new HashMap<Long, T>();
		while(fromDateClone.before(toDate))
		{
			for(T t:list)
			{
				if(t.getStartTime().compareTo(fromDateClone.getTime())==0)
				{
					map.put(t.getStartTime().getTime(), t);
				}
			}
			fromDateClone.add(Calendar.MINUTE, interval);
		}
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long,T> getAppointmentMap(String clientId,  String appointmentWith, Calendar fromDate, Calendar toDate, Integer interval)
	{
		Query query= getEntityManager().createQuery("select apptbooking from "+getEntityClass().getSimpleName()+" apptbooking where clientId= :clientId and startTime between  :startDate and :endTime and appointmentWith=:appointmentWith order by startTime", getEntityClass());
		query.setParameter("startDate", fromDate.getTime());
		query.setParameter("endDate", toDate.getTime());
		query.setParameter("appointmentWith", appointmentWith);
		query.setParameter("clientId", clientId);
		List<T> list= query.getResultList();
		Calendar fromDateClone=(Calendar)fromDate.clone();
		Map<Long,T> map= new HashMap<Long, T>();
		while(fromDateClone.before(toDate))
		{
			for(T t:list)
			{
				if(t.getStartTime().compareTo(fromDateClone.getTime())==0)
				{
					map.put(t.getStartTime().getTime(), t);
				}
			}
			fromDateClone.add(Calendar.MINUTE, interval);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAppointmentList(Set<String> appointmentWith, Date fromDate, Date toDate)
	{
		Query query= getEntityManager().createQuery("select apptbooking from "+getEntityClass().getSimpleName()+" apptbooking where startTime = :startDate  and appointmentWith in :appointmentWith order by startTime", getEntityClass());
		query.setParameter("startDate", fromDate);
		query.setParameter("appointmentWith", appointmentWith);
		return query.getResultList();
	}
	
	@Override
	public List<T> getListByCriteria(U baseAppointment) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<T> q = cb.createQuery(getEntityClass());
		Root<T> c = q.from(getEntityClass());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (baseAppointment.getReferenceNo() != null && !baseAppointment.getReferenceNo().isEmpty()) {
			Expression<String> expression = c.get("referenceNo");
			Predicate rolePredicate = cb.equal(expression,baseAppointment.getReferenceNo());
			predicates.add(rolePredicate);
		}
		q.select(c);
		if (predicates.size() != 0) {
			Predicate[] pre = new Predicate[predicates.size()];
			Predicate predicate = cb.and(predicates.toArray(pre));
			q.where(predicate);
		}
		q.orderBy(cb.desc(c.get("id")));
			return getEntityManager().createQuery(q).getResultList();
		
	}

	
	public  abstract Class<T> getEntityClass();
	
	public abstract EntityManager getEntityManager();
	
	@Override
	public void bookAppointment(Date bookTime, String referenceNo, String appointmentFor, String createdBy, 
			Double noOfHours, String parentId,
			Set<String> userIds) {
		int noOfSlotsForAppointmentment=(int) (noOfHours*60)%30==0?(int)(noOfHours.doubleValue())/30:(int)(noOfHours/30)+1;
		if(noOfSlotsForAppointmentment==0)
		{
			noOfSlotsForAppointmentment=1;
		}
		for(int i=0;i<noOfSlotsForAppointmentment;i++)
		{
			
			String userId=getLeastBusyUser(userIds, new Date(bookTime.getTime()+i*30*60*1000),  new Date(bookTime.getTime()+(i+1)*30*60*1000));
			T  appointmentSlot2= getAppointment(userId, bookTime);
			if(appointmentSlot2!=null)
			{
				appointmentSlot2.setReferenceNo(referenceNo.toString());
				appointmentSlot2.setAppointmentFor(appointmentFor);
				appointmentSlot2.setCreateBy(createdBy);
				appointmentSlot2.setCreatedAt(new Date());
				appointmentSlot2.setType(Type.BOOKED);
				update(appointmentSlot2);
			}
			
			List<T>  list2= getAppointmentList(userIds, new Date(bookTime.getTime()+i*30*60*1000),  new Date(bookTime.getTime()+(i+1)*30*60*1000));
			boolean markWarehouseAsBooked=true;
			for(T appointmentSlot3:list2)
			{
				if(appointmentSlot3.getType()==Type.FREE)
				{
					markWarehouseAsBooked=false;
				}
			}
			if(markWarehouseAsBooked)
			{
				T  appointmentSlot4= getAppointment(parentId, bookTime);
				if(appointmentSlot4!=null)
				{
					appointmentSlot4.setCreateBy(createdBy);
					appointmentSlot4.setCreatedAt(new Date());
					appointmentSlot4.setType(Type.BOOKED);
					update(appointmentSlot2);
				}
			}
		}
	}

}
