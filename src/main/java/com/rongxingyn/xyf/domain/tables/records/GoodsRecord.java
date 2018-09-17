/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.records;


import com.rongxingyn.xyf.domain.tables.Goods;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class GoodsRecord extends UpdatableRecordImpl<GoodsRecord> implements Record8<String, String, Double, String, Integer, Timestamp, Byte, Integer> {

    private static final long serialVersionUID = 1784521074;

    /**
     * Setter for <code>xyf.goods.goods_id</code>.
     */
    public void setGoodsId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>xyf.goods.goods_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getGoodsId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>xyf.goods.goods_name</code>.
     */
    public void setGoodsName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>xyf.goods.goods_name</code>.
     */
    @NotNull
    @Size(max = 100)
    public String getGoodsName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>xyf.goods.goods_price</code>.
     */
    public void setGoodsPrice(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>xyf.goods.goods_price</code>.
     */
    public Double getGoodsPrice() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>xyf.goods.goods_brief</code>.
     */
    public void setGoodsBrief(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>xyf.goods.goods_brief</code>.
     */
    @Size(max = 200)
    public String getGoodsBrief() {
        return (String) get(3);
    }

    /**
     * Setter for <code>xyf.goods.goods_recommend</code>.
     */
    public void setGoodsRecommend(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>xyf.goods.goods_recommend</code>.
     */
    public Integer getGoodsRecommend() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>xyf.goods.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>xyf.goods.update_time</code>.
     */
    @NotNull
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>xyf.goods.is_del_goods</code>.
     */
    public void setIsDelGoods(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>xyf.goods.is_del_goods</code>.
     */
    public Byte getIsDelGoods() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>xyf.goods.classify_id</code>.
     */
    public void setClassifyId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>xyf.goods.classify_id</code>.
     */
    @NotNull
    public Integer getClassifyId() {
        return (Integer) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, Double, String, Integer, Timestamp, Byte, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<String, String, Double, String, Integer, Timestamp, Byte, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Goods.GOODS.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Goods.GOODS.GOODS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field3() {
        return Goods.GOODS.GOODS_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Goods.GOODS.GOODS_BRIEF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Goods.GOODS.GOODS_RECOMMEND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return Goods.GOODS.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return Goods.GOODS.IS_DEL_GOODS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Goods.GOODS.CLASSIFY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getGoodsName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double component3() {
        return getGoodsPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getGoodsBrief();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getGoodsRecommend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getIsDelGoods();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getClassifyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getGoodsName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value3() {
        return getGoodsPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getGoodsBrief();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getGoodsRecommend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getIsDelGoods();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getClassifyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value1(String value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value2(String value) {
        setGoodsName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value3(Double value) {
        setGoodsPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value4(String value) {
        setGoodsBrief(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value5(Integer value) {
        setGoodsRecommend(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value7(Byte value) {
        setIsDelGoods(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord value8(Integer value) {
        setClassifyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsRecord values(String value1, String value2, Double value3, String value4, Integer value5, Timestamp value6, Byte value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsRecord
     */
    public GoodsRecord() {
        super(Goods.GOODS);
    }

    /**
     * Create a detached, initialised GoodsRecord
     */
    public GoodsRecord(String goodsId, String goodsName, Double goodsPrice, String goodsBrief, Integer goodsRecommend, Timestamp updateTime, Byte isDelGoods, Integer classifyId) {
        super(Goods.GOODS);

        set(0, goodsId);
        set(1, goodsName);
        set(2, goodsPrice);
        set(3, goodsBrief);
        set(4, goodsRecommend);
        set(5, updateTime);
        set(6, isDelGoods);
        set(7, classifyId);
    }
}
