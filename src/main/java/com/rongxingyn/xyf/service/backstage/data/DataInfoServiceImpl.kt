package com.rongxingyn.xyf.service.backstage.data

import com.rongxingyn.xyf.domain.Tables.DATA_INFO
import com.rongxingyn.xyf.domain.tables.pojos.DataInfo
import com.rongxingyn.xyf.domain.tables.records.DataInfoRecord
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service("dataInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class DataInfoServiceImpl @Autowired constructor(dslContext: DSLContext) : DataInfoService {

    private val create: DSLContext = dslContext

    override fun findByPrefix(prefix: String): Result<DataInfoRecord> {
        return create.selectFrom(DATA_INFO)
                .where(DATA_INFO.DATA_KEY.like(SQLQueryUtils.rightLikeParam(prefix)))
                .fetch()
    }

    override fun save(dataInfo: List<DataInfo>) {
        val bind = create.batch(create.insertInto(DATA_INFO, DATA_INFO.DATA_KEY, DATA_INFO.DATA_VALUE).values("", null)
                .onDuplicateKeyUpdate().set(DATA_INFO.DATA_VALUE, ""))
        dataInfo.forEach { data ->
            bind.bind(data.dataKey, data.dataValue, data.dataValue)
        }
        bind.execute()
    }
}