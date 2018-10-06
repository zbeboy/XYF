package com.rongxingyn.xyf.web.backstage.goods.banner

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.service.backstage.goods.banner.GoodsBannerService
import com.rongxingyn.xyf.service.common.FileSystemService
import com.rongxingyn.xyf.service.utils.UUIDUtils
import com.rongxingyn.xyf.web.utils.AjaxUtils
import com.rongxingyn.xyf.web.vo.backstage.goods.banner.BannerHideVo
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@RequestMapping("/web/backstage/goods")
open class GoodsBannerRestController {

    @Resource
    open lateinit var goodsBannerService: GoodsBannerService

    @Resource
    open lateinit var fileSystemService: FileSystemService

    @Resource
    open lateinit var xyfProperties: XYFProperties

    /**
     * 商品banner列表数据
     *
     * @return list data
     */
    @GetMapping("/banner/data")
    fun data(): Mono<ResponseEntity<Map<String, Any>>> {
        val list = goodsBannerService.findAll()
        var banners: List<Banner> = ArrayList()
        if (list.isNotEmpty) {
            banners = list.into(Banner::class.java)
        }
        val ajaxUtils = AjaxUtils.of().success().msg("获取数据成功").listData(banners)
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 上传图片
     *
     * @param filePart 数据
     * @return 文件信息
     */
    @PostMapping("/banner/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") filePart: FilePart): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        val path = xyfProperties.getConstants().staticImages + Workbook.DIRECTORY_SPLIT
        val fileData = fileSystemService.upload(filePart, path, Workbook.GOODS_BANNER_FILE)
        if (fileData.isPresent) {
            val banner = Banner()
            banner.bannerId = UUIDUtils.getUUID()
            banner.bannerIsHide = 0
            banner.bannerSerial = 0
            banner.bannerUrl = Workbook.DIRECTORY_SPLIT + path + fileData.get().newName
            goodsBannerService.save(banner)
            ajaxUtils.success().msg("上传文件成功")
        } else {
            ajaxUtils.fail().msg("上传文件失败")
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 是否隐藏
     *
     * @param bannerHideVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @PutMapping("/banner/hide")
    fun hide(@Valid bannerHideVo: BannerHideVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            goodsBannerService.updateHide(bannerHideVo.bannerId!!, bannerHideVo.bannerIsHide!!)
            ajaxUtils.success().msg("更新状态成功")
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}