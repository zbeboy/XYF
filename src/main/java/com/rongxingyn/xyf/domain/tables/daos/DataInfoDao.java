/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.daos;


import com.rongxingyn.xyf.domain.tables.DataInfo;
import com.rongxingyn.xyf.domain.tables.records.DataInfoRecord;

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
public class DataInfoDao extends DAOImpl<DataInfoRecord, com.rongxingyn.xyf.domain.tables.pojos.DataInfo, String> {

    /**
     * Create a new DataInfoDao without any configuration
     */
    public DataInfoDao() {
        super(DataInfo.DATA_INFO, com.rongxingyn.xyf.domain.tables.pojos.DataInfo.class);
    }

    /**
     * Create a new DataInfoDao with an attached configuration
     */
    @Autowired
    public DataInfoDao(Configuration configuration) {
        super(DataInfo.DATA_INFO, com.rongxingyn.xyf.domain.tables.pojos.DataInfo.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(com.rongxingyn.xyf.domain.tables.pojos.DataInfo object) {
        return object.getDataKey();
    }

    /**
     * Fetch records that have <code>data_key IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.DataInfo> fetchByDataKey(String... values) {
        return fetch(DataInfo.DATA_INFO.DATA_KEY, values);
    }

    /**
     * Fetch a unique record that has <code>data_key = value</code>
     */
    public com.rongxingyn.xyf.domain.tables.pojos.DataInfo fetchOneByDataKey(String value) {
        return fetchOne(DataInfo.DATA_INFO.DATA_KEY, value);
    }

    /**
     * Fetch records that have <code>data_value IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.DataInfo> fetchByDataValue(String... values) {
        return fetch(DataInfo.DATA_INFO.DATA_VALUE, values);
    }
}
