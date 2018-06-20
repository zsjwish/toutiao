package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@EqualsAndHashCode(of={"id"})
@Table(name = "exam")
public class Exam {

    public static final Integer STATUS_UPCOMING = 0;
    public static final Integer STATUS_ONGOING = 1;
    public static final Integer STATUS_SCORING = 2;
    public static final Integer STATUS_FINISHED = 3;

    public static final Byte TYPE_EXAM = 0;
    public static final Byte TYPE_EXERCISE = 1;
    public static final Byte TYPE_CONTEST = 2;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "begin_time")
    private Timestamp beginTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "information")
    private String information;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "type")
    private Byte type;// exam: 0, exercise: 1, activity: 2

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_third_party")
    private Integer owningParty;

    @ManyToMany
    @JoinTable(name = "exam_2_group", joinColumns = @JoinColumn(name = "exam_id"),
    inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groupSet;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Byte getType() { return type; }

    public void setType(Byte type) { this.type = type; }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getOwningParty() {
        return owningParty;
    }

    public void setOwningParty(Integer owningParty) {
        this.owningParty = owningParty;
    }

    public Set<Group> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<Group> groupSet) {
        this.groupSet = groupSet;
    }
}
