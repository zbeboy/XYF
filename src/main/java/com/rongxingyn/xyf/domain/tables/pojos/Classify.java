/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Classify implements Serializable {

    private static final long serialVersionUID = 1845964120;

    private Integer classifyId;
    private String  classifyName;
    private Byte    classifyIsDel;

    public Classify() {}

    public Classify(Classify value) {
        this.classifyId = value.classifyId;
        this.classifyName = value.classifyName;
        this.classifyIsDel = value.classifyIsDel;
    }

    public Classify(
        Integer classifyId,
        String  classifyName,
        Byte    classifyIsDel
    ) {
        this.classifyId = classifyId;
        this.classifyName = classifyName;
        this.classifyIsDel = classifyIsDel;
    }

    public Integer getClassifyId() {
        return this.classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    @NotNull
    @Size(max = 30)
    public String getClassifyName() {
        return this.classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Byte getClassifyIsDel() {
        return this.classifyIsDel;
    }

    public void setClassifyIsDel(Byte classifyIsDel) {
        this.classifyIsDel = classifyIsDel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Classify (");

        sb.append(classifyId);
        sb.append(", ").append(classifyName);
        sb.append(", ").append(classifyIsDel);

        sb.append(")");
        return sb.toString();
    }
}
