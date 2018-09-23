package com.rongxingyn.xyf.service.backstage

import com.rongxingyn.xyf.domain.Tables.TABLE_TIME
import com.rongxingyn.xyf.domain.tables.pojos.TableTime
import com.rongxingyn.xyf.domain.tables.records.TableTimeRecord
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("tableTimeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class TableTimeServiceImpl @Autowired constructor(dslContext: DSLContext) : TableTimeService {

    private val create: DSLContext = dslContext

    override fun findById(id: String): Optional<TableTimeRecord> {
        return create.selectFrom(TABLE_TIME).where(TABLE_TIME.TABLE_NAME.eq(id)).fetchOptional()
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(tableTime: TableTime) {
        create.insertInto(TABLE_TIME, TABLE_TIME.TABLE_NAME, TABLE_TIME.DEAL_TIME).values(tableTime.tableName, tableTime.dealTime)
                .onDuplicateKeyUpdate().set(TABLE_TIME.DEAL_TIME, tableTime.dealTime)
                .execute()
    }

}