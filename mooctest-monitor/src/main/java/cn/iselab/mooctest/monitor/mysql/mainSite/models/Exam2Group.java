package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import javax.persistence.*;

@Entity
@Table(name = "exam_2_group")
public class Exam2Group {

    @Id
    @GeneratedValue
    private long id;

    private static final long serialVersionUID = 3732224373262156909L;

    @Column(name = "exam_id")
    private long examId;

    @Column(name = "group_id")
    private long groupId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
