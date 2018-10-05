package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.Tables.GOODS_PICS
import com.rongxingyn.xyf.domain.tables.daos.GoodsPicsDao
import com.rongxingyn.xyf.domain.tables.pojos.GoodsPics
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service("goodsPicsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsPicsServiceImpl @Autowired constructor(dslContext: DSLContext) : GoodsPicsService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var goodsPicsDao: GoodsPicsDao

    override fun findByGoodsId(goodsId: String): List<GoodsPics> {
        return goodsPicsDao.fetchByGoodsId(goodsId)
    }

    override fun deleteByGoodsId(goodsId: String) {
        create.deleteFrom(GOODS_PICS).where(GOODS_PICS.GOODS_ID.eq(goodsId)).execute()
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(goodsPics: GoodsPics) {
        goodsPicsDao.insert(goodsPics)
    }

}