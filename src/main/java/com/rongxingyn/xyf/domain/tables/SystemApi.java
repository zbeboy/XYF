/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables;


import com.rongxingyn.xyf.domain.Indexes;
import com.rongxingyn.xyf.domain.Keys;
import com.rongxingyn.xyf.domain.Xyf;
import com.rongxingyn.xyf.domain.tables.records.SystemApiRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class SystemApi extends TableImpl<SystemApiRecord> {

    private static final long serialVersionUID = -1341851696;

    /**
     * The reference instance of <code>xyf.system_api</code>
     */
    public static final SystemApi SYSTEM_API = new SystemApi();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SystemApiRecord> getRecordType() {
        return SystemApiRecord.class;
    }

    /**
     * The column <code>xyf.system_api.api_id</code>.
     */
    public final TableField<SystemApiRecord, Integer> API_ID = createField("api_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>xyf.system_api.param</code>.
     */
    public final TableField<SystemApiRecord, String> PARAM = createField("param", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>xyf.system_api.remark</code>.
     */
    public final TableField<SystemApiRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR(500), this, "");

    /**
     * Create a <code>xyf.system_api</code> table reference
     */
    public SystemApi() {
        this(DSL.name("system_api"), null);
    }

    /**
     * Create an aliased <code>xyf.system_api</code> table reference
     */
    public SystemApi(String alias) {
        this(DSL.name(alias), SYSTEM_API);
    }

    /**
     * Create an aliased <code>xyf.system_api</code> table reference
     */
    public SystemApi(Name alias) {
        this(alias, SYSTEM_API);
    }

    private SystemApi(Name alias, Table<SystemApiRecord> aliased) {
        this(alias, aliased, null);
    }

    private SystemApi(Name alias, Table<SystemApiRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Xyf.XYF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SYSTEM_API_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SystemApiRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SYSTEM_API;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SystemApiRecord> getPrimaryKey() {
        return Keys.KEY_SYSTEM_API_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SystemApiRecord>> getKeys() {
        return Arrays.<UniqueKey<SystemApiRecord>>asList(Keys.KEY_SYSTEM_API_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApi as(String alias) {
        return new SystemApi(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemApi as(Name alias) {
        return new SystemApi(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SystemApi rename(String name) {
        return new SystemApi(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SystemApi rename(Name name) {
        return new SystemApi(name, null);
    }
}
