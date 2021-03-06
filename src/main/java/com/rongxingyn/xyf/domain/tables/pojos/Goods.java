/*
 * This file is generated by jOOQ.
*/
package com.rongxingyn.xyf.domain.tables.pojos;


import java.io.Serializable;

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

    private static final long serialVersionUID = -1010289721;

    private String  goodsId;
    private String  goodsName;
    private Double  goodsPrice;
    private String  goodsBrief;
    private Integer goodsRecommend;
    private Integer goodsSerial;
    private Byte    goodsIsDel;
    private Integer goodsItem;
    private Byte    goodsIsStick;
    private Integer classifyId;

    public Goods() {}

    public Goods(Goods value) {
        this.goodsId = value.goodsId;
        this.goodsName = value.goodsName;
        this.goodsPrice = value.goodsPrice;
        this.goodsBrief = value.goodsBrief;
        this.goodsRecommend = value.goodsRecommend;
        this.goodsSerial = value.goodsSerial;
        this.goodsIsDel = value.goodsIsDel;
        this.goodsItem = value.goodsItem;
        this.goodsIsStick = value.goodsIsStick;
        this.classifyId = value.classifyId;
    }

    public Goods(
        String  goodsId,
        String  goodsName,
        Double  goodsPrice,
        String  goodsBrief,
        Integer goodsRecommend,
        Integer goodsSerial,
        Byte    goodsIsDel,
        Integer goodsItem,
        Byte    goodsIsStick,
        Integer classifyId
    ) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsBrief = goodsBrief;
        this.goodsRecommend = goodsRecommend;
        this.goodsSerial = goodsSerial;
        this.goodsIsDel = goodsIsDel;
        this.goodsItem = goodsItem;
        this.goodsIsStick = goodsIsStick;
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

    public Integer getGoodsSerial() {
        return this.goodsSerial;
    }

    public void setGoodsSerial(Integer goodsSerial) {
        this.goodsSerial = goodsSerial;
    }

    public Byte getGoodsIsDel() {
        return this.goodsIsDel;
    }

    public void setGoodsIsDel(Byte goodsIsDel) {
        this.goodsIsDel = goodsIsDel;
    }

    public Integer getGoodsItem() {
        return this.goodsItem;
    }

    public void setGoodsItem(Integer goodsItem) {
        this.goodsItem = goodsItem;
    }

    public Byte getGoodsIsStick() {
        return this.goodsIsStick;
    }

    public void setGoodsIsStick(Byte goodsIsStick) {
        this.goodsIsStick = goodsIsStick;
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
        sb.append(", ").append(goodsSerial);
        sb.append(", ").append(goodsIsDel);
        sb.append(", ").append(goodsItem);
        sb.append(", ").append(goodsIsStick);
        sb.append(", ").append(classifyId);

        sb.append(")");
        return sb.toString();
    }
}
