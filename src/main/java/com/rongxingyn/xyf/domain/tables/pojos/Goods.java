/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Goods implements Serializable {

    private static final long serialVersionUID = 746108952;

    private String    goodsId;
    private String    goodsName;
    private Double    goodsPrice;
    private String    goodsBrief;
    private Integer   goodsRecommend;
    private Timestamp updateTime;
    private Byte      isDelGoods;
    private Integer   classifyId;

    public Goods() {}

    public Goods(Goods value) {
        this.goodsId = value.goodsId;
        this.goodsName = value.goodsName;
        this.goodsPrice = value.goodsPrice;
        this.goodsBrief = value.goodsBrief;
        this.goodsRecommend = value.goodsRecommend;
        this.updateTime = value.updateTime;
        this.isDelGoods = value.isDelGoods;
        this.classifyId = value.classifyId;
    }

    public Goods(
        String    goodsId,
        String    goodsName,
        Double    goodsPrice,
        String    goodsBrief,
        Integer   goodsRecommend,
        Timestamp updateTime,
        Byte      isDelGoods,
        Integer   classifyId
    ) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsBrief = goodsBrief;
        this.goodsRecommend = goodsRecommend;
        this.updateTime = updateTime;
        this.isDelGoods = isDelGoods;
        this.classifyId = classifyId;
    }

    @NotNull
    @Size(max = 64)
    public String getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @NotNull
    @Size(max = 100)
    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice() {
        return this.goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Size(max = 200)
    public String getGoodsBrief() {
        return this.goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public Integer getGoodsRecommend() {
        return this.goodsRecommend;
    }

    public void setGoodsRecommend(Integer goodsRecommend) {
        this.goodsRecommend = goodsRecommend;
    }

    @NotNull
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelGoods() {
        return this.isDelGoods;
    }

    public void setIsDelGoods(Byte isDelGoods) {
        this.isDelGoods = isDelGoods;
    }

    @NotNull
    public Integer getClassifyId() {
        return this.classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Goods (");

        sb.append(goodsId);
        sb.append(", ").append(goodsName);
        sb.append(", ").append(goodsPrice);
        sb.append(", ").append(goodsBrief);
        sb.append(", ").append(goodsRecommend);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(isDelGoods);
        sb.append(", ").append(classifyId);

        sb.append(")");
        return sb.toString();
    }
}
