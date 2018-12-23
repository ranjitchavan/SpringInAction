package com.einfochips.webportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the key_rotation_policy database table.
 * 
 */
@Entity
@Table(name = "key_rotation_policy")
@NamedQuery(name = "KeyRotationPolicy.findAll", query = "SELECT k FROM KeyRotationPolicy k")
public class KeyRotationPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key_rotation_id")
	private int keyRotationId;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
	private Date creationTime;

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

	public KeyRotationPolicy() {
		// default constructor
	}

	public int getKeyRotationId() {
		return this.keyRotationId;
	}

	public void setKeyRotationId(int keyRotationId) {
		this.keyRotationId = keyRotationId;
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

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
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

	public void setUpdationTime(Date updationTime) {
		this.updationTime = updationTime;
	}

}