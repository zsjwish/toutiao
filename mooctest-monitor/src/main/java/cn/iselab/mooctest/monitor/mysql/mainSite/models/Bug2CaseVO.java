package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * created by zsj in 15:32 2018/2/28
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class Bug2CaseVO {

    private Long id;

    private String name;

    private Integer bugNumber;

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

    public Integer getBugNumber() {
        return bugNumber;
    }

    public void setBugNumber(Integer bugNumber) {
        this.bugNumber = bugNumber;
    }
}
