/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.PersistentLogins;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class PersistentLoginsRecord extends UpdatableRecordImpl<PersistentLoginsRecord> implements Record4<String, String, String, Timestamp> {

    private static final long serialVersionUID = -1305003809;

    /**
     * Setter for <code>xyf.persistent_logins.username</code>.
     */
    public void setUsername(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.persistent_logins.username</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getUsername() {
        return (String) get(0);
    }

    /**
     * Setter for <code>xyf.persistent_logins.series</code>.
     */
    public void setSeries(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.persistent_logins.series</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getSeries() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.persistent_logins.token</code>.
     */
    public void setToken(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.persistent_logins.token</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getToken() {
        return (String) get(2);
    }

    /**
     * Setter for <code>xyf.persistent_logins.last_used</code>.
     */
    public void setLastUsed(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>xyf.persistent_logins.last_used</code>.
     */
    @NotNull
    public Timestamp getLastUsed() {
        return (Timestamp) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return PersistentLogins.PERSISTENT_LOGINS.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PersistentLogins.PERSISTENT_LOGINS.SERIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return PersistentLogins.PERSISTENT_LOGINS.TOKEN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return PersistentLogins.PERSISTENT_LOGINS.LAST_USED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getSeries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getLastUsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSeries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getLastUsed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentLoginsRecord value1(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentLoginsRecord value2(String value) {
        setSeries(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentLoginsRecord value3(String value) {
        setToken(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentLoginsRecord value4(Timestamp value) {
        setLastUsed(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentLoginsRecord values(String value1, String value2, String value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PersistentLoginsRecord
     */
    public PersistentLoginsRecord() {
        super(PersistentLogins.PERSISTENT_LOGINS);
    }

    /**
     * Create a detached, initialised PersistentLoginsRecord
     */
    public PersistentLoginsRecord(String username, String series, String token, Timestamp lastUsed) {
        super(PersistentLogins.PERSISTENT_LOGINS);

        set(0, username);
        set(1, series);
        set(2, token);
        set(3, lastUsed);
    }
}
