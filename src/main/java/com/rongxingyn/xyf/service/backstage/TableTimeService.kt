package com.rongxingyn.xyf.service.backstage

import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.domain.tables.records.TableTimeRecord
import java.util.*

interface TableTimeService {

    /**
     * 通过主键查询表
     *
     * @param id 主键
     * @return 数据
     */
    fun findById(id: String): Optional<TableTimeRecord>

    /**
     * 保存
     *
     * @param tableTime 数据
     */
    fun save(tableTime: TableTime)
}