package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "group_entity")
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "allow_join")
    private Boolean allowJoin = true;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "exam_2_group", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private Set<Exam> examSet;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getAllowJoin() {
        return allowJoin;
    }

    public void setAllowJoin(Boolean allowJoin) {
        this.allowJoin = allowJoin;
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

    public Set<Exam> getExamSet() {
        return examSet;
    }

    public void setExamSet(Set<Exam> examSet) {
        this.examSet = examSet;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }

    public Boolean getIsActive(){
        return this.isActive;
    }
}
