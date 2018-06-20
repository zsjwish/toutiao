package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by NJUta on 2017/5/26.
 */
@Entity
@NoArgsConstructor
@Table(name = "case_take")
public class CaseTake {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "application_id")
    private long applicationId;
    @Column(name = "exam_id")
    private long examId;
    @Column(name = "case_id")
    private long caseId;
    @Column(name = "total")
    private float total;
    @Column(name = "manual")
    private float manual;
    @Column(name = "script")
    private int script;
    @Column(name = "participant_id")
    private long participantId;

//    @OneToOne
//    @JoinColumn(name = "case_take_id")
//    private Report report;

    public CaseTake(long applicationId, long examId, long caseId, long participantId) {
        this.applicationId = applicationId;
        this.examId = examId;
        this.caseId = caseId;
        this.participantId = participantId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getCaseId() {
        return caseId;
    }

    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getManual() {
        return manual;
    }

    public void setManual(float manual) {
        this.manual = manual;
    }

    public int getScript() {
        return script;
    }

    public void setScript(int script) {
        this.script = script;
    }

    public void setParticipantId(long participantId){
        this.participantId = participantId;
    }

    public long getParticipantId(){
        return this.participantId;
    }
}
