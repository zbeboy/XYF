package com.rongxingyn.xyf.service.backstage.data

import com.rongxingyn.xyf.domain.tables.pojos.DataInfo
import com.rongxingyn.xyf.domain.tables.records.DataInfoRecord
import org.jooq.Result

interface DataInfoService {
    /**
     * 通过前缀查询
     *
     * @param prefix 前缀
     * @return 数据
     */
    fun findByPrefix(prefix: String): Result<DataInfoRecord>

    /**
     * 保存
     *
     * @param dataInfo 数据
     */
    fun save(dataInfo: List<DataInfo>)
}