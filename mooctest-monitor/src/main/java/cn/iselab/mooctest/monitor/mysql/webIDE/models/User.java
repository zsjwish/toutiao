package cn.iselab.mooctest.monitor.mysql.webIDE.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * created by zsj in 20:16 2018/6/4
 * description:
 **/
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;
}
