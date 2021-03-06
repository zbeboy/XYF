/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.SystemApi;

import javax.annotation.Generated;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class SystemApiRecord extends UpdatableRecordImpl<SystemApiRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 688788936;

    /**
     * Setter for <code>xyf.system_api.api_id</code>.
     */
    public void setApiId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.system_api.api_id</code>.
     */
    public Integer getApiId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>xyf.system_api.param</code>.
     */
    public void setParam(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.system_api.param</code>.
     */
    @Size(max = 200)
    public String getParam() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.system_api.remark</code>.
     */
    public void setRemark(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.system_api.remark</code>.
     */
    @Size(max = 500)
    public String getRemark() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return SystemApi.SYSTEM_API.API_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return SystemApi.SYSTEM_API.PARAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return SystemApi.SYSTEM_API.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getApiId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getApiId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApiRecord value1(Integer value) {
        setApiId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApiRecord value2(String value) {
        setParam(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApiRecord value3(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApiRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SystemApiRecord
     */
    public SystemApiRecord() {
        super(SystemApi.SYSTEM_API);
    }

    /**
     * Create a detached, initialised SystemApiRecord
     */
    public SystemApiRecord(Integer apiId, String param, String remark) {
        super(SystemApi.SYSTEM_API);

        set(0, apiId);
        set(1, param);
        set(2, remark);
    }
}
