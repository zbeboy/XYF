/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.daos;


import com.rongxingyn.xyf.domain.tables.Goods;
import com.rongxingyn.xyf.domain.tables.records.GoodsRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
@Repository
public class GoodsDao extends DAOImpl<GoodsRecord, com.rongxingyn.xyf.domain.tables.pojos.Goods, String> {

    /**
     * Create a new GoodsDao without any configuration
     */
    public GoodsDao() {
        super(Goods.GOODS, com.rongxingyn.xyf.domain.tables.pojos.Goods.class);
    }

    /**
     * Create a new GoodsDao with an attached configuration
     */
    @Autowired
    public GoodsDao(Configuration configuration) {
        super(Goods.GOODS, com.rongxingyn.xyf.domain.tables.pojos.Goods.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(com.rongxingyn.xyf.domain.tables.pojos.Goods object) {
        return object.getGoodsId();
    }

    /**
     * Fetch records that have <code>goods_id IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsId(String... values) {
        return fetch(Goods.GOODS.GOODS_ID, values);
    }

    /**
     * Fetch a unique record that has <code>goods_id = value</code>
     */
    public com.rongxingyn.xyf.domain.tables.pojos.Goods fetchOneByGoodsId(String value) {
        return fetchOne(Goods.GOODS.GOODS_ID, value);
    }

    /**
     * Fetch records that have <code>goods_name IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsName(String... values) {
        return fetch(Goods.GOODS.GOODS_NAME, values);
    }

    /**
     * Fetch a unique record that has <code>goods_name = value</code>
     */
    public com.rongxingyn.xyf.domain.tables.pojos.Goods fetchOneByGoodsName(String value) {
        return fetchOne(Goods.GOODS.GOODS_NAME, value);
    }

    /**
     * Fetch records that have <code>goods_price IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsPrice(Double... values) {
        return fetch(Goods.GOODS.GOODS_PRICE, values);
    }

    /**
     * Fetch records that have <code>goods_brief IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsBrief(String... values) {
        return fetch(Goods.GOODS.GOODS_BRIEF, values);
    }

    /**
     * Fetch records that have <code>goods_recommend IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsRecommend(Integer... values) {
        return fetch(Goods.GOODS.GOODS_RECOMMEND, values);
    }

    /**
     * Fetch records that have <code>goods_serial IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsSerial(Integer... values) {
        return fetch(Goods.GOODS.GOODS_SERIAL, values);
    }

    /**
     * Fetch records that have <code>goods_is_del IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsIsDel(Byte... values) {
        return fetch(Goods.GOODS.GOODS_IS_DEL, values);
    }

    /**
     * Fetch records that have <code>goods_item IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsItem(Integer... values) {
        return fetch(Goods.GOODS.GOODS_ITEM, values);
    }

    /**
     * Fetch records that have <code>goods_is_stick IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByGoodsIsStick(Byte... values) {
        return fetch(Goods.GOODS.GOODS_IS_STICK, values);
    }

    /**
     * Fetch records that have <code>classify_id IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.Goods> fetchByClassifyId(Integer... values) {
        return fetch(Goods.GOODS.CLASSIFY_ID, values);
    }
}
