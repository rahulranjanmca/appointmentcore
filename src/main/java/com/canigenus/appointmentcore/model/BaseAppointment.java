package com.canigenus.appointmentcore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseAppointment  {
	
	@Column(name="START_TIME")
	Date startTime;
	
	@Column(name="END_TIME")
	Date endTime;
	
	@Column(name="CREATED_AT")
	Date createdAt;
	
	@Column(name="CREATED_BY")
	String createBy;
	
	@Column(name="TYPE")
	Type type;
	
	@Column(name="APPOINTMENT_FOR")
	String appointmentFor;
	
	@Column(name="APPOINTMENT_WITH")
	String appointmentWith;
	
	@Column(name="REFERENCE_NO")
	String referenceNo;
	
	@Column(name="CLIENT_ID")
	String clientId;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getAppointmentFor() {
		return appointmentFor;
	}

	public void setAppointmentFor(String appointmentFor) {
		this.appointmentFor = appointmentFor;
	}

	public String getAppointmentWith() {
		return appointmentWith;
	}

	public void setAppointmentWith(String appointmentWith) {
		this.appointmentWith = appointmentWith;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	

}
