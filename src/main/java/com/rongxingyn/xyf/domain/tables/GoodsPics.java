/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables;


import com.rongxingyn.xyf.domain.Indexes;
import com.rongxingyn.xyf.domain.Keys;
import com.rongxingyn.xyf.domain.Xyf;
import com.rongxingyn.xyf.domain.tables.records.GoodsPicsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class GoodsPics extends TableImpl<GoodsPicsRecord> {

    private static final long serialVersionUID = 1420157976;

    /**
     * The reference instance of <code>xyf.goods_pics</code>
     */
    public static final GoodsPics GOODS_PICS = new GoodsPics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsPicsRecord> getRecordType() {
        return GoodsPicsRecord.class;
    }

    /**
     * The column <code>xyf.goods_pics.pic_id</code>.
     */
    public final TableField<GoodsPicsRecord, Integer> PIC_ID = createField("pic_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>xyf.goods_pics.goods_id</code>.
     */
    public final TableField<GoodsPicsRecord, String> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>xyf.goods_pics.pic_url</code>.
     */
    public final TableField<GoodsPicsRecord, String> PIC_URL = createField("pic_url", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "");

    /**
     * Create a <code>xyf.goods_pics</code> table reference
     */
    public GoodsPics() {
        this(DSL.name("goods_pics"), null);
    }

    /**
     * Create an aliased <code>xyf.goods_pics</code> table reference
     */
    public GoodsPics(String alias) {
        this(DSL.name(alias), GOODS_PICS);
    }

    /**
     * Create an aliased <code>xyf.goods_pics</code> table reference
     */
    public GoodsPics(Name alias) {
        this(alias, GOODS_PICS);
    }

    private GoodsPics(Name alias, Table<GoodsPicsRecord> aliased) {
        this(alias, aliased, null);
    }

    private GoodsPics(Name alias, Table<GoodsPicsRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.GOODS_PICS_GOODS_ID, Indexes.GOODS_PICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<GoodsPicsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_GOODS_PICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GoodsPicsRecord> getPrimaryKey() {
        return Keys.KEY_GOODS_PICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GoodsPicsRecord>> getKeys() {
        return Arrays.<UniqueKey<GoodsPicsRecord>>asList(Keys.KEY_GOODS_PICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<GoodsPicsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GoodsPicsRecord, ?>>asList(Keys.GOODS_PICS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPics as(String alias) {
        return new GoodsPics(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPics as(Name alias) {
        return new GoodsPics(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsPics rename(String name) {
        return new GoodsPics(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsPics rename(Name name) {
        return new GoodsPics(name, null);
    }
}
