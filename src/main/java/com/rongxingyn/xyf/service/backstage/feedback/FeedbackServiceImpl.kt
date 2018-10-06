package com.rongxingyn.xyf.service.backstage.feedback

import com.rongxingyn.xyf.domain.Tables.FEEDBACK
import com.rongxingyn.xyf.domain.tables.daos.FeedbackDao
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import com.rongxingyn.xyf.web.bean.backstage.feedback.FeedbackBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import javax.annotation.Resource

@Service("feedbackService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class FeedbackServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<FeedbackBean>(), FeedbackService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var feedbackDao: FeedbackDao

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<FeedbackBean>): Result<Record> {
        return dataPagingQueryAll(dataTablesUtils, create, FEEDBACK)
    }

    override fun countAll(): Int {
        return statisticsAll(create, FEEDBACK)
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<FeedbackBean>): Int {
        return statisticsWithCondition(dataTablesUtils, create, FEEDBACK)
    }

    override fun updateState(feedbackId: Int, hasDeal: Byte) {
        create.update(FEEDBACK).set(FEEDBACK.HAS_DEAL, hasDeal).where(FEEDBACK.FEEDBACK_ID.eq(feedbackId)).execute()
    }

    override fun updateRemark(feedbackId: Int, remake: String) {
        create.update(FEEDBACK).set(FEEDBACK.REMARK, remake).where(FEEDBACK.FEEDBACK_ID.eq(feedbackId)).execute()
    }

    override fun deleteById(feedbackId: Int) {
        feedbackDao.deleteById(feedbackId)
    }

    /**
     * 数据全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    override fun searchCondition(dataTablesUtils: DataTablesUtils<FeedbackBean>): Condition? {
        var a: Condition? = null

        val search = dataTablesUtils.search
        if (!ObjectUtils.isEmpty(search)) {
            val customerName = StringUtils.trimWhitespace(search!!.getString("customerName"))
            val customerContact = StringUtils.trimWhitespace(search.getString("customerContact"))
            val hasDeal = StringUtils.trimWhitespace(search.getString("hasDeal"))
            if (StringUtils.hasLength(customerName)) {
                a = FEEDBACK.CUSTOMER_NAME.like(SQLQueryUtils.likeAllParam(customerName))
            }

            if (StringUtils.hasLength(customerContact)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    FEEDBACK.CUSTOMER_CONTACT.like(SQLQueryUtils.likeAllParam(customerContact))
                } else {
                    a!!.and(FEEDBACK.CUSTOMER_CONTACT.like(SQLQueryUtils.likeAllParam(customerContact)))
                }
            }

            if (StringUtils.hasLength(hasDeal)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    FEEDBACK.HAS_DEAL.eq(hasDeal.toByte())
                } else {
                    a!!.and(FEEDBACK.HAS_DEAL.eq(hasDeal.toByte()))
                }
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
    override fun sortCondition(dataTablesUtils: DataTablesUtils<FeedbackBean>, selectConditionStep: SelectConditionStep<Record>?, selectJoinStep: SelectJoinStep<Record>?, type: Int) {
        val orderColumnName = dataTablesUtils.orderColumnName
        val orderDir = dataTablesUtils.orderDir
        val isAsc = "asc".equals(orderDir, ignoreCase = true)
        var sortField: Array<SortField<*>?>? = null
        if (StringUtils.hasLength(orderColumnName)) {

            if ("customer_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.CUSTOMER_NAME.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.CUSTOMER_NAME.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

            if ("customer_contact".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.CUSTOMER_CONTACT.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.CUSTOMER_CONTACT.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

            if ("content".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.CONTENT.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.CONTENT.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

            if ("create_date".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.CREATE_DATE.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.CREATE_DATE.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

            if ("has_deal".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.HAS_DEAL.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.HAS_DEAL.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

            if ("remark".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = FEEDBACK.REMARK.asc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.asc()
                } else {
                    sortField[0] = FEEDBACK.REMARK.desc()
                    sortField[1] = FEEDBACK.FEEDBACK_ID.desc()
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, *sortField!!)
    }

}