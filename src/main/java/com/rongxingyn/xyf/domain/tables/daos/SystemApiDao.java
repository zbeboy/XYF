/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.daos;


import com.rongxingyn.xyf.domain.tables.SystemApi;
import com.rongxingyn.xyf.domain.tables.records.SystemApiRecord;

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
public class SystemApiDao extends DAOImpl<SystemApiRecord, com.rongxingyn.xyf.domain.tables.pojos.SystemApi, Integer> {

    /**
     * Create a new SystemApiDao without any configuration
     */
    public SystemApiDao() {
        super(SystemApi.SYSTEM_API, com.rongxingyn.xyf.domain.tables.pojos.SystemApi.class);
    }

    /**
     * Create a new SystemApiDao with an attached configuration
     */
    @Autowired
    public SystemApiDao(Configuration configuration) {
        super(SystemApi.SYSTEM_API, com.rongxingyn.xyf.domain.tables.pojos.SystemApi.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.rongxingyn.xyf.domain.tables.pojos.SystemApi object) {
        return object.getApiId();
    }

    /**
     * Fetch records that have <code>api_id IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.SystemApi> fetchByApiId(Integer... values) {
        return fetch(SystemApi.SYSTEM_API.API_ID, values);
    }

    /**
     * Fetch a unique record that has <code>api_id = value</code>
     */
    public com.rongxingyn.xyf.domain.tables.pojos.SystemApi fetchOneByApiId(Integer value) {
        return fetchOne(SystemApi.SYSTEM_API.API_ID, value);
    }

    /**
     * Fetch records that have <code>param IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.SystemApi> fetchByParam(String... values) {
        return fetch(SystemApi.SYSTEM_API.PARAM, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.rongxingyn.xyf.domain.tables.pojos.SystemApi> fetchByRemark(String... values) {
        return fetch(SystemApi.SYSTEM_API.REMARK, values);
    }
}
