package com.rongxingyn.xyf.service.common

import com.rongxingyn.xyf.web.bean.file.FileBean
import org.springframework.http.codec.multipart.FilePart
import java.io.File
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by zbeboy 2017-12-20 .
 **/
interface UploadService {
    /**
     * 上传文件
     *
     * @param filePart 请求对象
     * @param path    根路径
     * @param fileUniqueId 文件识别名
     * @return file data info.
     */
    fun upload(filePart: FilePart, path: String, fileUniqueId: String): Optional<FileBean>

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param response 响应对象
     * @param request  请求对象
     */
    fun download(fileName: String, filePath: String, response: HttpServletResponse, request: HttpServletRequest)

    /**
     * 文件下载
     *
     * @param fileName     文件名
     * @param file          文件对象
     * @param response      响应对象
     * @param request       请求对象
     */
    fun download(fileName: String, file: File, response: HttpServletResponse, request: HttpServletRequest)
}