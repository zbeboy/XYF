package com.rongxingyn.xyf.service.backstage.goods.classify

import com.rongxingyn.xyf.domain.tables.daos.ClassifyDao
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.web.bean.backstage.goods.classify.ClassifyBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service("goodsClassifyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsClassifyServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<ClassifyBean>(), GoodsClassifyService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var classifyDao: ClassifyDao

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<ClassifyBean>): Result<Record> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countAll(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<ClassifyBean>): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}