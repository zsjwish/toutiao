package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by NJUta on 2017/5/26.
 */
@Entity
@Table(name = "bug")
public class Bug {
    @Id
    private long id;
    @Column(name = "case_take_id")
    private long caseTakeId;
    @Column(name = "report_id")
    private long reportId;
    @Column(name = "create_time_millis")
    private long createTimeMillis;
    @Column(name = "bug_category")
    private String bugCategory;
    @Column(name = "description")
    private String description;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "severity")
    private short severity;
    @Column(name = "recurrent")
    private short recurrent;
    @Column(name = "from_cloud")
    private int fromCloud;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCaseTakeId() {
        return caseTakeId;
    }

    public void setCaseTakeId(long caseTakeId) {
        this.caseTakeId = caseTakeId;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public void setCreateTimeMillis(long createTimeMillis) {
        this.createTimeMillis = createTimeMillis;
    }

    public String getBugCategory() {
        return bugCategory;
    }

    public void setBugCategory(String bugCategory) {
        this.bugCategory = bugCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public short getSeverity() {
        return severity;
    }

    public void setSeverity(short severity) {
        this.severity = severity;
    }

    public short getRecurrent() {
        return recurrent;
    }

    public void setRecurrent(short recurrent) {
        this.recurrent = recurrent;
    }

    public int getFromCloud() {
        return fromCloud;
    }

    public void setFromCloud(int fromCloud) {
        this.fromCloud = fromCloud;
    }
}
