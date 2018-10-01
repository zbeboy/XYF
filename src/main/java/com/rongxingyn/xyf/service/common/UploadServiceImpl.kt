package com.rongxingyn.xyf.service.common

import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.web.bean.file.FileBean
import org.slf4j.LoggerFactory
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zbeboy 2017-12-20 .
 **/
@Service("uploadService")
open class UploadServiceImpl : UploadService {

    private val log = LoggerFactory.getLogger(UploadServiceImpl::class.java)

    @Resource
    open lateinit var xyfProperties: XYFProperties

    override fun upload(filePart: FilePart, path: String, fileUniqueId: String): Optional<FileBean> {
        var fileData = Optional.empty<FileBean>()

        // step 1. 文件目录是否存在
        if (!Files.exists(Paths.get(path))) {
            Files.createDirectories(Paths.get(path))
        }

        // step 2. 空间是否足够 500MB 524288000
        val tempFile = Files.createTempFile(Paths.get(path), fileUniqueId, filePart.filename())
        if (tempFile.toFile().totalSpace - tempFile.toFile().usableSpace > xyfProperties.getConstants().fileFreeSpace!!) {
            filePart.transferTo(tempFile.toFile()).doOnSuccess {
                val fileBean = FileBean()
                fileBean.contentType = filePart.headers().contentType!!.toString()
                fileBean.originalFileName = filePart.filename()
                fileBean.ext = ext(filePart.filename())
                fileBean.size = Files.size(tempFile.toRealPath())
                fileBean.newName = tempFile.fileName.toString()
                fileBean.lastPath = tempFile.toRealPath().toString()
                fileData = Optional.of(fileBean)
                log.info("File is upload success : {}", tempFile.toRealPath())
            }.doOnError {
                log.error("File upload error : {}", filePart.filename())
            }.block()
        }

        return fileData
    }

    override fun download(fileName: String, filePath: String, response: HttpServletResponse, request: HttpServletRequest) {

    }

    override fun download(fileName: String, file: File, response: HttpServletResponse, request: HttpServletRequest) {

    }

    private fun ext(fileName: String): String {
        var ext = ""
        if (fileName.contains('.')) {
            ext = fileName.substring(fileName.lastIndexOf('.') + 1)
        }
        return ext
    }
}