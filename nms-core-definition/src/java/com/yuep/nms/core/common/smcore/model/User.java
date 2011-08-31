/*
 * $Id: User.java, 2011-3-24 上午11:20:20 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.base.def.DisplayNameGetter;
import com.yuep.nms.core.common.base.def.ValueCompareObject;
import com.yuep.nms.core.common.base.def.ValueCompareObjectMapGetter;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: User
 * </p>
 * <p>
 * Description: 用户
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:20:20
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class User implements Serializable, ValueCompareObjectMapGetter<User>, DisplayNameGetter {

	private static final long serialVersionUID = 5294669378248188851L;

	/**
	 * 用户名称
	 */
	@Id
	private String userName;
	
    /**
     * 用户全名
     */
	private String fullName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 备注信息
	 */
	private String description;

   /**
    * 用户IP地址管理范围
    */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<IpRange> ipRanges;

	/**
	 * 用户所属部门
	 */
	private String department;

	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 工作电话
	 */
	private String workPhone;

	/**
	 * 邮件地址
	 */
	private String email;

	/**
	 * 用户有效期
	 */
	private Long expiredTime;

	/**
	 * 密码有效期
	 */
	private Long passwordExpiredTime;

	public static final int STATE_NORMAL=0;
	public static final int STATE_DISABLE=1;
	public static final int STATE_LOCK=2;
	
	/**
     * 用户状态:normal,disable,lock
     */
	private int state=STATE_NORMAL;

	/**
	 * 用户的管理范围:user可管理的nm,domain,ne名
	 */
	@Column(name = "mgmtScope", columnDefinition = "text")
    @Type(type = "com.yuep.nms.core.common.mocore.usertype.CommonListUserType", parameters = {
            @Parameter(name = "userType", value = "com.yuep.nms.core.common.mocore.usertype.MoNamingListUserType"),
            @Parameter(name = "elementClass", value = "com.yuep.nms.core.common.mocore.naming.MoNaming") })
    private List<MoNaming> mgmtScope= new ArrayList<MoNaming>();
	
	/**
	 * 用户所属角色
	 */
	@Type(type = "com.yuep.core.db.usertype.ListUserType")
	private List<String> roles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Long getPasswordExpiredTime() {
		return passwordExpiredTime;
	}

	public void setPasswordExpiredTime(Long passwordExpiredTime) {
		this.passwordExpiredTime = passwordExpiredTime;
	}

	/**
	 * 用户的管理范围
	 * @return
	 */
	public List<MoNaming> getMgmtScope() {
		return mgmtScope;
	}

	/**
	 * 用户的管理范围
	 * @param mgmtScope
	 */
	public void setMgmtScope(List<MoNaming> mgmtScope) {
		this.mgmtScope = mgmtScope;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public Map<String, ValueCompareObject> getValueCompareObjectMap(User otherObject) {
		Map<String, ValueCompareObject> map = new HashMap<String, ValueCompareObject>();
		if (otherObject == null)
			return map;
		if (!StringUtils.equals(userName, otherObject.userName))
			map.put("userName", new ValueCompareObject("userName", userName, otherObject.userName));
		if (!StringUtils.equals(password, otherObject.password))
			map.put("password", new ValueCompareObject("password", password, otherObject.password));
		if (!ObjectUtils.equals(expiredTime, otherObject.expiredTime))
			map.put("expiredTime", new ValueCompareObject("expiredTime", expiredTime, otherObject.expiredTime));
		if (!ObjectUtils.equals(passwordExpiredTime, otherObject.passwordExpiredTime))
			map.put("passwordExpiredTime", new ValueCompareObject("passwordExpiredTime", passwordExpiredTime, otherObject.passwordExpiredTime));
		return map;
	}

	@Override
	public String getDisplayName() {
		return userName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public List<IpRange> getIpRanges() {
		return ipRanges;
	}

	public void setIpRanges(List<IpRange> ipRanges) {
		this.ipRanges = ipRanges;
	}


    /**
     * @param workPhone the workPhone to set
     */
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    /**
     * @return the workPhone
     */
    public String getWorkPhone() {
        return workPhone;
    }

 

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

 

	
	
}
