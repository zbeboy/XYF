/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.GoodsPics;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
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
public class GoodsPicsRecord extends UpdatableRecordImpl<GoodsPicsRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 244378583;

    /**
     * Setter for <code>xyf.goods_pics.pic_id</code>.
     */
    public void setPicId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.goods_pics.pic_id</code>.
     */
    public Integer getPicId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>xyf.goods_pics.goods_id</code>.
     */
    public void setGoodsId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.goods_pics.goods_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getGoodsId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.goods_pics.pic_url</code>.
     */
    public void setPicUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.goods_pics.pic_url</code>.
     */
    @NotNull
    @Size(max = 500)
    public String getPicUrl() {
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
        return GoodsPics.GOODS_PICS.PIC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GoodsPics.GOODS_PICS.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GoodsPics.GOODS_PICS.PIC_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getPicId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPicUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPicId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPicUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPicsRecord value1(Integer value) {
        setPicId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPicsRecord value2(String value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPicsRecord value3(String value) {
        setPicUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsPicsRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsPicsRecord
     */
    public GoodsPicsRecord() {
        super(GoodsPics.GOODS_PICS);
    }

    /**
     * Create a detached, initialised GoodsPicsRecord
     */
    public GoodsPicsRecord(Integer picId, String goodsId, String picUrl) {
        super(GoodsPics.GOODS_PICS);

        set(0, picId);
        set(1, goodsId);
        set(2, picUrl);
    }
}
