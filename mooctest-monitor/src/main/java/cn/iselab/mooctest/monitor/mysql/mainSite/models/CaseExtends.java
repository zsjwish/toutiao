package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(exclude = "createTime")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "case_entity")
@Data
public class CaseExtends {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private Integer domain;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    //case_id:子站中case可能存的是字符串
    @Column(name = "case_id")
    private String caseId;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "is_public")
    private Boolean visible = true;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "platform")
    private Short platform;

    @Column(name = "subsite_id")
    private Long answerWay;

    @Column(name = "properties")
    private String properties;
}

