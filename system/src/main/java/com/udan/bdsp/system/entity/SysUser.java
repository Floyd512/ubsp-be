package com.udan.bdsp.system.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 用户表
* @TableName sys_user
*/
public class SysUser implements Serializable {

    /**
    * 用户ID
    */
    @NotNull(message="[用户ID]不能为空")
    @ApiModelProperty("用户ID")
    private Long id;
    /**
    * 用户名
    */
    @NotBlank(message="[用户名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 密码(加密)
    */
    @NotBlank(message="[密码(加密)]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("密码(加密)")
    @Length(max= 255,message="编码长度不能超过255")
    private String password;
    /**
    * 邮箱
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("邮箱")
    @Length(max= 100,message="编码长度不能超过100")
    private String email;
    /**
    * 手机号
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("手机号")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 头像URL
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("头像URL")
    @Length(max= 500,message="编码长度不能超过500")
    private String avatar;
    /**
    * 状态(0:禁用,1:启用)
    */
    @ApiModelProperty("状态(0:禁用,1:启用)")
    private Integer status;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updatedTime;
    /**
    * 最后登录时间
    */
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    /**
    * 用户ID
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 用户名
    */
    private void setUsername(String username){
    this.username = username;
    }

    /**
    * 密码(加密)
    */
    private void setPassword(String password){
    this.password = password;
    }

    /**
    * 邮箱
    */
    private void setEmail(String email){
    this.email = email;
    }

    /**
    * 手机号
    */
    private void setPhone(String phone){
    this.phone = phone;
    }

    /**
    * 头像URL
    */
    private void setAvatar(String avatar){
    this.avatar = avatar;
    }

    /**
    * 状态(0:禁用,1:启用)
    */
    private void setStatus(Integer status){
    this.status = status;
    }

    /**
    * 创建时间
    */
    private void setCreatedTime(Date createdTime){
    this.createdTime = createdTime;
    }

    /**
    * 更新时间
    */
    private void setUpdatedTime(Date updatedTime){
    this.updatedTime = updatedTime;
    }

    /**
    * 最后登录时间
    */
    private void setLastLoginTime(Date lastLoginTime){
    this.lastLoginTime = lastLoginTime;
    }


    /**
    * 用户ID
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 用户名
    */
    private String getUsername(){
    return this.username;
    }

    /**
    * 密码(加密)
    */
    private String getPassword(){
    return this.password;
    }

    /**
    * 邮箱
    */
    private String getEmail(){
    return this.email;
    }

    /**
    * 手机号
    */
    private String getPhone(){
    return this.phone;
    }

    /**
    * 头像URL
    */
    private String getAvatar(){
    return this.avatar;
    }

    /**
    * 状态(0:禁用,1:启用)
    */
    private Integer getStatus(){
    return this.status;
    }

    /**
    * 创建时间
    */
    private Date getCreatedTime(){
    return this.createdTime;
    }

    /**
    * 更新时间
    */
    private Date getUpdatedTime(){
    return this.updatedTime;
    }

    /**
    * 最后登录时间
    */
    private Date getLastLoginTime(){
    return this.lastLoginTime;
    }

}
