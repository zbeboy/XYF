package com.rongxingyn.xyf.service.backstage.feedback

import com.rongxingyn.xyf.domain.tables.pojos.Feedback
import com.rongxingyn.xyf.web.bean.backstage.feedback.FeedbackBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.Record
import org.jooq.Result

interface FeedbackService {

    /**
     * 分页查询
     *
     * @param dataTablesUtils datatables工具类
     * @return 分页数据
     */
    fun findAllByPage(dataTablesUtils: DataTablesUtils<FeedbackBean>): Result<Record>

    /**
     * 总数
     *
     * @return 总数
     */
    fun countAll(): Int

    /**
     * 根据条件查询总数
     *
     * @return 条件查询总数
     */
    fun countByCondition(dataTablesUtils: DataTablesUtils<FeedbackBean>): Int

    /**
     * 更新状态
     *
     * @param feedbackId id
     * @param hasDeal 状态
     */
    fun updateState(feedbackId: Int, hasDeal: Byte)

    /**
     * 更新备注
     *
     * @param feedbackId id
     * @param remake 状态
     */
    fun updateRemark(feedbackId: Int, remake: String)

    /**
     * 根据ID删除
     *
     * @param feedbackId id
     */
    fun deleteById(feedbackId: Int)

    /**
     * 保存
     *
     * @param feedback 数据
     */
    fun save(feedback: Feedback)
}