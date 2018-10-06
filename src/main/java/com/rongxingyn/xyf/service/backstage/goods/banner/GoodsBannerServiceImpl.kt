package com.rongxingyn.xyf.service.backstage.goods.banner

import com.rongxingyn.xyf.domain.Tables.BANNER
import com.rongxingyn.xyf.domain.tables.daos.BannerDao
import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.records.BannerRecord
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service("goodsBannerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsBannerServiceImpl @Autowired constructor(dslContext: DSLContext) : GoodsBannerService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var bannerDao: BannerDao

    override fun findAll(): Result<BannerRecord> {
        return create.selectFrom(BANNER).orderBy(BANNER.BANNER_SERIAL).fetch()
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(banner: Banner) {
        bannerDao.insert(banner)
    }

    override fun updateHide(bannerId: String, bannerIsHide: Byte) {
        create.update(BANNER).set(BANNER.BANNER_IS_HIDE, bannerIsHide).where(BANNER.BANNER_ID.eq(bannerId)).execute()
    }

    override fun updateSerial(bannerId: String, bannerSerial: Int) {
        create.update(BANNER).set(BANNER.BANNER_SERIAL, bannerSerial).where(BANNER.BANNER_ID.eq(bannerId)).execute()
    }
}