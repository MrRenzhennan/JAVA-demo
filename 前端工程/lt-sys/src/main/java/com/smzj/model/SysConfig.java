package com.smzj.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SYS_CONFIG")
public class SysConfig {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator  = "UUID")
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONFIGVAL")
    private String configval;

    @Column(name = "KEY")
    private String key;

    /**
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return CONFIGVAL
     */
    public String getConfigval() {
        return configval;
    }

    /**
     * @param configval
     */
    public void setConfigval(String configval) {
        this.configval = configval;
    }

    /**
     * @return KEY
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }
}