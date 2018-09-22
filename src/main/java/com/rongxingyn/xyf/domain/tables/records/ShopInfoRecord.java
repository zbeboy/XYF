/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.ShopInfo;

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
public class ShopInfoRecord extends UpdatableRecordImpl<ShopInfoRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = -1723974881;

    /**
     * Setter for <code>xyf.shop_info.shop_id</code>.
     */
    public void setShopId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.shop_info.shop_id</code>.
     */
    public Integer getShopId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>xyf.shop_info.shop_name</code>.
     */
    public void setShopName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.shop_info.shop_name</code>.
     */
    @NotNull
    @Size(max = 200)
    public String getShopName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.shop_info.shop_brief</code>.
     */
    public void setShopBrief(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.shop_info.shop_brief</code>.
     */
    @Size(max = 65535)
    public String getShopBrief() {
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
        return ShopInfo.SHOP_INFO.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ShopInfo.SHOP_INFO.SHOP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ShopInfo.SHOP_INFO.SHOP_BRIEF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getShopBrief();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getShopBrief();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value1(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value2(String value) {
        setShopName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord value3(String value) {
        setShopBrief(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopInfoRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShopInfoRecord
     */
    public ShopInfoRecord() {
        super(ShopInfo.SHOP_INFO);
    }

    /**
     * Create a detached, initialised ShopInfoRecord
     */
    public ShopInfoRecord(Integer shopId, String shopName, String shopBrief) {
        super(ShopInfo.SHOP_INFO);

        set(0, shopId);
        set(1, shopName);
        set(2, shopBrief);
    }
}