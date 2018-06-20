package cn.iselab.mooctest.monitor.mysql.mainSite.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_2_role")
public class User2Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name  = "role_id")
    private Long roleId;

    @Column(name  = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
