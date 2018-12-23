package com.einfochips.webportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the customer_key_rotation_policy database table.
 * 
 * @author asra.shaikh
 *
 */
@Entity
@Table(name = "customer_key_rotation_policy")
@NamedQuery(name = "CustomerKeyRotationPolicy.findAll", query = "SELECT c FROM CustomerKeyRotationPolicy c")
public class CustomerKeyRotationPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_key_rotation_policy_id")
	private int customerKeyRotationPolicyId;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "customer_master_id")
	private int customerMasterId;

	@Column(name = "key_rotation_policy_level0")
	private int keyRotationPolicyLevel0;

	@Column(name = "key_rotation_policy_level1")
	private int keyRotationPolicyLevel1;

	@Column(name = "key_rotation_reminder_level0")
	private int keyRotationReminderLevel0;

	@Column(name = "key_rotation_reminder_level1")
	private int keyRotationReminderLevel1;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updation_time")
	private Date updationTime;

	public CustomerKeyRotationPolicy() {
		// default constructor
	}

	public int getCustomerKeyRotationPolicyId() {
		return this.customerKeyRotationPolicyId;
	}

	public void setCustomerKeyRotationPolicyId(int customerKeyRotationPolicyId) {
		this.customerKeyRotationPolicyId = customerKeyRotationPolicyId;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public int getCustomerMasterId() {
		return this.customerMasterId;
	}

	public void setCustomerMasterId(int customerMasterId) {
		this.customerMasterId = customerMasterId;
	}

	public int getKeyRotationPolicyLevel0() {
		return this.keyRotationPolicyLevel0;
	}

	public void setKeyRotationPolicyLevel0(int keyRotationPolicyLevel0) {
		this.keyRotationPolicyLevel0 = keyRotationPolicyLevel0;
	}

	public int getKeyRotationPolicyLevel1() {
		return this.keyRotationPolicyLevel1;
	}

	public void setKeyRotationPolicyLevel1(int keyRotationPolicyLevel1) {
		this.keyRotationPolicyLevel1 = keyRotationPolicyLevel1;
	}

	public int getKeyRotationReminderLevel0() {
		return this.keyRotationReminderLevel0;
	}

	public void setKeyRotationReminderLevel0(int keyRotationReminderLevel0) {
		this.keyRotationReminderLevel0 = keyRotationReminderLevel0;
	}

	public int getKeyRotationReminderLevel1() {
		return this.keyRotationReminderLevel1;
	}

	public void setKeyRotationReminderLevel1(int keyRotationReminderLevel1) {
		this.keyRotationReminderLevel1 = keyRotationReminderLevel1;
	}

	public Date getUpdationTime() {
		return this.updationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerKeyRotationPolicy [customerKeyRotationPolicyId=")
				.append(customerKeyRotationPolicyId).append(", createdBy=")
				.append(createdBy).append(", creationTime=").append(creationTime)
				.append(", customerMasterId=").append(customerMasterId)
				.append(", keyRotationPolicyLevel0=").append(keyRotationPolicyLevel0)
				.append(", keyRotationPolicyLevel1=").append(keyRotationPolicyLevel1)
				.append(", keyRotationReminderLevel0=").append(keyRotationReminderLevel0)
				.append(", keyRotationReminderLevel1=").append(keyRotationReminderLevel1)
				.append(", updationTime=").append(updationTime).append("]");
		return builder.toString();
	}

}