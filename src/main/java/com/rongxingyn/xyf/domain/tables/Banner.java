/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables;


import com.rongxingyn.xyf.domain.Indexes;
import com.rongxingyn.xyf.domain.Keys;
import com.rongxingyn.xyf.domain.Xyf;
import com.rongxingyn.xyf.domain.tables.records.BannerRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Banner extends TableImpl<BannerRecord> {

    private static final long serialVersionUID = -216982679;

    /**
     * The reference instance of <code>xyf.banner</code>
     */
    public static final Banner BANNER = new Banner();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BannerRecord> getRecordType() {
        return BannerRecord.class;
    }

    /**
     * The column <code>xyf.banner.banner_id</code>.
     */
    public final TableField<BannerRecord, String> BANNER_ID = createField("banner_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>xyf.banner.banner_serial</code>.
     */
    public final TableField<BannerRecord, Integer> BANNER_SERIAL = createField("banner_serial", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>xyf.banner.banner_url</code>.
     */
    public final TableField<BannerRecord, String> BANNER_URL = createField("banner_url", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "");

    /**
     * The column <code>xyf.banner.is_hide</code>.
     */
    public final TableField<BannerRecord, Byte> IS_HIDE = createField("is_hide", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>xyf.banner.classify_id</code>.
     */
    public final TableField<BannerRecord, Integer> CLASSIFY_ID = createField("classify_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>xyf.banner</code> table reference
     */
    public Banner() {
        this(DSL.name("banner"), null);
    }

    /**
     * Create an aliased <code>xyf.banner</code> table reference
     */
    public Banner(String alias) {
        this(DSL.name(alias), BANNER);
    }

    /**
     * Create an aliased <code>xyf.banner</code> table reference
     */
    public Banner(Name alias) {
        this(alias, BANNER);
    }

    private Banner(Name alias, Table<BannerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Banner(Name alias, Table<BannerRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BANNER_CLASSIFY_ID, Indexes.BANNER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BannerRecord> getPrimaryKey() {
        return Keys.KEY_BANNER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BannerRecord>> getKeys() {
        return Arrays.<UniqueKey<BannerRecord>>asList(Keys.KEY_BANNER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<BannerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BannerRecord, ?>>asList(Keys.BANNER_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banner as(String alias) {
        return new Banner(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banner as(Name alias) {
        return new Banner(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Banner rename(String name) {
        return new Banner(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Banner rename(Name name) {
        return new Banner(name, null);
    }
}
