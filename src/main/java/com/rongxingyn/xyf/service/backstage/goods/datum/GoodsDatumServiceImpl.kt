package com.rongxingyn.xyf.service.backstage.goods.datum

import com.rongxingyn.xyf.domain.Tables.*
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils

@Service("goodsDatumService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class GoodsDatumServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<GoodsBean>(), GoodsDatumService {

    private val create: DSLContext = dslContext

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<GoodsBean>): Result<Record> {
        val a = searchCondition(dataTablesUtils)
        return if (ObjectUtils.isEmpty(a)) {
            val selectJoinStep = create.select()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
            sortCondition(dataTablesUtils, null, selectJoinStep, DataTablesPlugin.JOIN_TYPE)
            pagination(dataTablesUtils, null, selectJoinStep, DataTablesPlugin.JOIN_TYPE)
            selectJoinStep.fetch()
        } else {
            val selectConditionStep = create.select()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                    .where(a)
            sortCondition(dataTablesUtils, selectConditionStep, null, DataTablesPlugin.CONDITION_TYPE)
            pagination(dataTablesUtils, selectConditionStep, null, DataTablesPlugin.CONDITION_TYPE)
            selectConditionStep.fetch()
        }
    }

    override fun countAll(): Int {
        return statisticsAll(create, GOODS)
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<GoodsBean>): Int {
        val count: Record1<Int>
        val a = searchCondition(dataTablesUtils)
        count = if (ObjectUtils.isEmpty(a)) {
            create.selectCount()
                    .from(GOODS).fetchOne()
        } else {
            create.selectCount()
                    .from(GOODS)
                    .join(CLASSIFY)
                    .on(GOODS.CLASSIFY_ID.eq(CLASSIFY.CLASSIFY_ID))
                    .where(a).fetchOne()
        }
        return count.value1()
    }

    /**
     * 数据全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    override fun searchCondition(dataTablesUtils: DataTablesUtils<GoodsBean>): Condition? {
        var a: Condition? = null

        val search = dataTablesUtils.search
        if (!ObjectUtils.isEmpty(search)) {
            val goodsName = StringUtils.trimWhitespace(search!!.getString("goodsName"))
            if (StringUtils.hasLength(goodsName)) {
                a = GOODS.GOODS_NAME.like(SQLQueryUtils.likeAllParam(goodsName))
            }
        }
        return a
    }

    /**
     * 数据排序
     *
     * @param dataTablesUtils     datatables工具类
     * @param selectConditionStep 条件
     */
    override fun sortCondition(dataTablesUtils: DataTablesUtils<GoodsBean>, selectConditionStep: SelectConditionStep<Record>?, selectJoinStep: SelectJoinStep<Record>?, type: Int) {
        val orderColumnName = dataTablesUtils.orderColumnName
        val orderDir = dataTablesUtils.orderDir
        val isAsc = "asc".equals(orderDir, ignoreCase = true)
        var sortField: Array<SortField<*>?>? = null
        if (StringUtils.hasLength(orderColumnName)) {

            if ("goods_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_NAME.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_NAME.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_price".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_PRICE.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_PRICE.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_recommend".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_RECOMMEND.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_RECOMMEND.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("classify_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = CLASSIFY.CLASSIFY_NAME.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_serial".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_SERIAL.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_SERIAL.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

            if ("goods_is_del".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = GOODS.GOODS_IS_DEL.asc()
                    sortField[1] = GOODS.GOODS_ID.asc()
                } else {
                    sortField[0] = GOODS.GOODS_IS_DEL.desc()
                    sortField[1] = GOODS.GOODS_ID.desc()
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, *sortField!!)
    }
}