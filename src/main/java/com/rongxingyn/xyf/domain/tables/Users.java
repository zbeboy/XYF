/*
 * This file is generated by jOOQ.
 */
package com.rongxingyn.xyf.domain.tables;


import com.rongxingyn.xyf.domain.Indexes;
import com.rongxingyn.xyf.domain.Keys;
import com.rongxingyn.xyf.domain.Xyf;
import com.rongxingyn.xyf.domain.tables.records.UsersRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Users extends TableImpl<UsersRecord> {

    /**
     * The reference instance of <code>xyf.users</code>
     */
    public static final Users USERS = new Users();
    private static final long serialVersionUID = -943726578;
    /**
     * The column <code>xyf.users.username</code>.
     */
    public final TableField<UsersRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");
    /**
     * The column <code>xyf.users.password</code>.
     */
    public final TableField<UsersRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(300).nullable(false), this, "");
    /**
     * The column <code>xyf.users.disabled</code>.
     */
    public final TableField<UsersRecord, Byte> DISABLED = createField("disabled", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");
    /**
     * The column <code>xyf.users.account_expired</code>.
     */
    public final TableField<UsersRecord, Byte> ACCOUNT_EXPIRED = createField("account_expired", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");
    /**
     * The column <code>xyf.users.account_locked</code>.
     */
    public final TableField<UsersRecord, Byte> ACCOUNT_LOCKED = createField("account_locked", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");
    /**
     * The column <code>xyf.users.credentials_expired</code>.
     */
    public final TableField<UsersRecord, Byte> CREDENTIALS_EXPIRED = createField("credentials_expired", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>xyf.users</code> table reference
     */
    public Users() {
        this(DSL.name("users"), null);
    }

    /**
     * Create an aliased <code>xyf.users</code> table reference
     */
    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    /**
     * Create an aliased <code>xyf.users</code> table reference
     */
    public Users(Name alias) {
        this(alias, USERS);
    }

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
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
        return Arrays.<Index>asList(Indexes.USERS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UsersRecord> getPrimaryKey() {
        return Keys.KEY_USERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UsersRecord>> getKeys() {
        return Arrays.<UniqueKey<UsersRecord>>asList(Keys.KEY_USERS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Name name) {
        return new Users(name, null);
    }
}
