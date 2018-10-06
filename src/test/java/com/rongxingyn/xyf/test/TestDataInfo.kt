package com.rongxingyn.xyf.test

import com.rongxingyn.xyf.service.backstage.data.DataKey
import org.junit.Test

open class TestDataInfo {

    @Test
    fun testKey(){
       println(DataKey.SHOP_BRIEF.name)
    }
}