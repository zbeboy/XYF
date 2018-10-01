package com.rongxingyn.xyf.service.common

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.web.bean.file.FileBean
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ZeroCopyHttpOutputMessage
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.annotation.Resource

/**
 * Created by zbeboy 2017-12-20 .
 **/
@Service("fileSystemService")
open class FileSystemServiceImpl : FileSystemService {

    private val log = LoggerFactory.getLogger(FileSystemServiceImpl::class.java)

    @Resource
    open lateinit var xyfProperties: XYFProperties

    override fun upload(filePart: FilePart, path: String, fileUniqueId: String): Optional<FileBean> {
        var fileData = Optional.empty<FileBean>()
        val filePath = xyfProperties.getConstants().documentRoot + Workbook.DIRECTORY_SPLIT + path
        // step 1. 文件目录是否存在
        if (!Files.exists(Paths.get(filePath))) {
            Files.createDirectories(Paths.get(filePath))
        }

        // step 2. 空间是否足够 500MB 524288000
        val tempFile = Files.createTempFile(Paths.get(filePath), fileUniqueId, filePart.filename())
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

    override fun download(fileName: String, filePath: String, response: ServerHttpResponse): Mono<Void> {
        val zeroCopyResponse = response as ZeroCopyHttpOutputMessage
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${String((fileName).toByteArray(charset("gb2312")), charset("ISO8859-1"))}")
        response.getHeaders().contentType = MediaType.APPLICATION_OCTET_STREAM

        val path = xyfProperties.getConstants().documentRoot + Workbook.DIRECTORY_SPLIT + filePath

        return zeroCopyResponse.writeWith(Paths.get(path).toFile(), 0, Paths.get(path).toFile().length())
    }

    override fun delete(filePath: String) {
        val path = xyfProperties.getConstants().documentRoot + Workbook.DIRECTORY_SPLIT + filePath
        Files.deleteIfExists(Paths.get(path))
    }

    private fun ext(fileName: String): String {
        var ext = ""
        if (fileName.contains('.')) {
            ext = fileName.substring(fileName.lastIndexOf('.') + 1)
        }
        return ext
    }
}