package com.rongxingyn.xyf.service.reception

import com.rongxingyn.xyf.domain.Tables.BANNER
import com.rongxingyn.xyf.domain.Tables.CLASSIFY
import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.pojos.Classify
import com.rongxingyn.xyf.service.backstage.data.DataInfoService
import com.rongxingyn.xyf.service.backstage.data.DataKey
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import javax.annotation.Resource

@Service("receptionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class ReceptionServiceImpl @Autowired constructor(dslContext: DSLContext) : ReceptionService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var dataInfoService: DataInfoService

    override fun getBanners(): List<Banner> {
        val data = create.selectFrom(BANNER).where(BANNER.BANNER_IS_HIDE.eq(0).and(BANNER.BANNER_ITEM.eq(0))).orderBy(BANNER.BANNER_SERIAL).fetch()
        return if (data.isNotEmpty) {
            data.into(Banner::class.java)
        } else {
            ArrayList()
        }
    }

    override fun getClassifies(): List<Classify> {
        val data = create.selectFrom(CLASSIFY).where(CLASSIFY.CLASSIFY_IS_DEL.ne(1)).fetch()
        return if (data.isNotEmpty) {
            data.into(Classify::class.java)
        } else {
            ArrayList()
        }
    }

    override fun getWebsiteInfo(model: Model) {
        val data = dataInfoService.findByPrefix(DataKey.WEBSITE.name)
        if (data.isNotEmpty) {
            data.forEach { i ->
                model.addAttribute(i.dataKey, i.dataValue)
            }
        }
    }

    override fun getShopInfo(model: Model) {
        val data = dataInfoService.findByPrefix(DataKey.SHOP.name)
        if (data.isNotEmpty) {
            data.forEach { i ->
                model.addAttribute(i.dataKey, i.dataValue)
            }
        }
    }
}