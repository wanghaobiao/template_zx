package com.acrabsoft.web.util;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@ApiModel(description= "")
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "主键id", name = "id", required = false)
    private String id;

    @Basic
    @Column(name = "deleted")
    @ApiModelProperty(value = "逻辑删除", name = "deleted", required = false)
    private String deleted;

    @Basic
    @Column(name = "create_user")
    @CreatedBy
    @ApiModelProperty(value="创建人",name="create",required=false)
    private String createUser;

    /*@OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH)
    //@JsonView(JsonViewMark.DetailView.class)
    @JoinColumn(name="create_user",referencedColumnName="obj_id")
    @ApiModelProperty(value="创建人",name="createUser",required=false)
    private UserInfoEntity createUser;*/

    @Basic
    @Column(name = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "创建时间", name = "createTime", required = false)
    private Date createTime;


    /*@OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH)
    //@JsonView(JsonViewMark.DetailView.class)
    @JoinColumn(name="update_user",referencedColumnName="obj_id")
    @ApiModelProperty(value="创建人",name="createUser",required=false)
    private UserInfoEntity updateUser;*/

    @Basic
    @Column(name = "update_user")
    @LastModifiedBy
    @ApiModelProperty(value = "更新人", name = "updateUser", required = false)
    private String updateUser;


    @Basic
    @Column(name = "update_time")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "最后修改时间", name = "updateTime", required = false)
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}



