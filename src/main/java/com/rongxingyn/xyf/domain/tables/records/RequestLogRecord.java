/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.RequestLog;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class RequestLogRecord extends UpdatableRecordImpl<RequestLogRecord> implements Record6<String, String, String, String, String, Timestamp> {

    private static final long serialVersionUID = -387065866;

    /**
     * Setter for <code>xyf.request_log.log_id</code>.
     */
    public void setLogId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.request_log.log_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getLogId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>xyf.request_log.request_url</code>.
     */
    public void setRequestUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.request_log.request_url</code>.
     */
    @Size(max = 100)
    public String getRequestUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.request_log.request_param</code>.
     */
    public void setRequestParam(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.request_log.request_param</code>.
     */
    @Size(max = 500)
    public String getRequestParam() {
        return (String) get(2);
    }

    /**
     * Setter for <code>xyf.request_log.result</code>.
     */
    public void setResult(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>xyf.request_log.result</code>.
     */
    @Size(max = 500)
    public String getResult() {
        return (String) get(3);
    }

    /**
     * Setter for <code>xyf.request_log.channel_name</code>.
     */
    public void setChannelName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>xyf.request_log.channel_name</code>.
     */
    @Size(max = 20)
    public String getChannelName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>xyf.request_log.request_date</code>.
     */
    public void setRequestDate(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>xyf.request_log.request_date</code>.
     */
    @NotNull
    public Timestamp getRequestDate() {
        return (Timestamp) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return RequestLog.REQUEST_LOG.LOG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return RequestLog.REQUEST_LOG.REQUEST_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return RequestLog.REQUEST_LOG.REQUEST_PARAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return RequestLog.REQUEST_LOG.RESULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return RequestLog.REQUEST_LOG.CHANNEL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return RequestLog.REQUEST_LOG.REQUEST_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getRequestUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getRequestParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getChannelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getRequestDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getRequestUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getRequestParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getChannelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getRequestDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value1(String value) {
        setLogId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value2(String value) {
        setRequestUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value3(String value) {
        setRequestParam(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value4(String value) {
        setResult(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value5(String value) {
        setChannelName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord value6(Timestamp value) {
        setRequestDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestLogRecord values(String value1, String value2, String value3, String value4, String value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RequestLogRecord
     */
    public RequestLogRecord() {
        super(RequestLog.REQUEST_LOG);
    }

    /**
     * Create a detached, initialised RequestLogRecord
     */
    public RequestLogRecord(String logId, String requestUrl, String requestParam, String result, String channelName, Timestamp requestDate) {
        super(RequestLog.REQUEST_LOG);

        set(0, logId);
        set(1, requestUrl);
        set(2, requestParam);
        set(3, result);
        set(4, channelName);
        set(5, requestDate);
    }
}
