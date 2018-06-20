/*
 * Copyright (c) 2014-2016 CODING.
 */

package cn.iselab.mooctest.monitor.mysql.webIDE.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vangie on 14/12/10.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "T_PROJECT")
public class ProjectEntity extends BaseEntity {

    @Column(name = "F_NAME")
    private String name;

    @Column(name = "F_FULL_NAME")
    private String fullName;

    @Column(name = "F_ICON_URL")
    private String iconUrl;

    @Column(name = "F_URL")
    private String url;

    @Column(name = "F_OWNER_NAME")
    private String ownerName;

    @Column(name = "F_CAN_SUBMIT")
    private boolean canSubmit;

    @Column(name = "F_MONITOR")
    private boolean monitor;
}
