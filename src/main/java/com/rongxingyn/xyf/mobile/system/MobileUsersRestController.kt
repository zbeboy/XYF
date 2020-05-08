package com.rongxingyn.xyf.mobile.system

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.config.XYFProperties
import com.rongxingyn.xyf.domain.tables.pojos.Authorities
import com.rongxingyn.xyf.domain.tables.pojos.Banner
import com.rongxingyn.xyf.domain.tables.pojos.Users
import com.rongxingyn.xyf.mobile.vo.users.LoginVo
import com.rongxingyn.xyf.mobile.vo.users.RegisterVo
import com.rongxingyn.xyf.mobile.vo.users.UsersDataVo
import com.rongxingyn.xyf.security.AuthBook
import com.rongxingyn.xyf.service.common.FileSystemService
import com.rongxingyn.xyf.service.system.AuthoritiesService
import com.rongxingyn.xyf.service.system.UsersService
import com.rongxingyn.xyf.service.utils.BCryptUtils
import com.rongxingyn.xyf.service.utils.UUIDUtils
import com.rongxingyn.xyf.web.utils.AjaxUtils
import org.apache.commons.lang3.BooleanUtils
import org.apache.commons.lang3.StringUtils
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
@RequestMapping("/mobile")
open class MobileUsersRestController {

    @Resource
    open lateinit var usersService: UsersService

    @Resource
    open lateinit var authoritiesService: AuthoritiesService

    @Resource
    open lateinit var fileSystemService: FileSystemService

    @Resource
    open lateinit var xyfProperties: XYFProperties

    /**
     * 登录
     *
     * @param loginVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @GetMapping("/login")
    fun login(@Valid loginVo: LoginVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val users = usersService.findByUsername(loginVo.username!!)
            if (Objects.nonNull(users)) {
                if (BooleanUtils.isFalse(users!!.accountExpired == 1.toByte())) {
                    if (BooleanUtils.isFalse(users.accountLocked == 1.toByte())) {
                        if (BooleanUtils.isFalse(users.credentialsExpired == 1.toByte())) {
                            if (BCryptUtils.bCryptPasswordMatches(loginVo.password!!, users.password)) {
                                users.accessToken = UUIDUtils.getUUID()
                                usersService.update(users)
                                ajaxUtils.success().put("access_token", users.accessToken).msg("登录成功")
                            } else {
                                ajaxUtils.fail().msg("密码错误")
                            }
                        } else {
                            ajaxUtils.fail().msg("账号凭证过期")
                        }
                    } else {
                        ajaxUtils.fail().msg("账号已被锁")
                    }
                } else {
                    ajaxUtils.fail().msg("账号已过期")
                }
            } else {
                ajaxUtils.fail().msg("账号不存在")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 注册
     *
     * @param registerVo 数据
     * @param bindingResult 检验
     * @return true or false
     */
    @GetMapping("/register")
    fun register(@Valid registerVo: RegisterVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(registerVo.username!!)
            val checkUsers = usersService.findByUsername(name)
            if (Objects.isNull(checkUsers)) {
                val users = Users()
                users.username = registerVo.username
                users.password = BCryptUtils.bCryptPassword(registerVo.password!!)
                users.disabled = 0
                users.accountExpired = 0
                users.accountLocked = 0
                users.credentialsExpired = 0
                users.address = registerVo.address
                users.realName = registerVo.realName
                users.sex = registerVo.sex
                users.contact = registerVo.contact
                users.photo = Workbook.DEFAULT_PHOTO
                usersService.save(users)
                val authorities = Authorities()
                authorities.username = registerVo.username
                authorities.authority = AuthBook.ROLE_USERS.name
                authoritiesService.save(authorities)
                ajaxUtils.success().msg("注册成功")
            } else {
                ajaxUtils.fail().msg("该账号已被注册")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 获取用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user")
    fun user(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    val users = Users()
                    users.username = data.username
                    users.address = data.address
                    users.realName = data.realName
                    users.sex = data.sex
                    users.contact = data.contact
                    users.accessToken = data.accessToken
                    users.photo = data.photo
                    ajaxUtils.success().put("user", users).msg("获取数据成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 上传图片
     *
     * @param filePart 数据
     * @return 文件信息
     */
    @PostMapping("/user/edit/photo", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") filePart: FilePart, @Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    val path = xyfProperties.getConstants().staticImages + Workbook.DIRECTORY_SPLIT
                    val fileData = fileSystemService.upload(filePart, path, Workbook.USER_PHOTO_FILE)
                    if (fileData.isPresent) {fileSystemService
                        data.photo = Workbook.DIRECTORY_SPLIT + path + fileData.get().newName
                        usersService.update(data)
                        ajaxUtils.success().put("user", data).msg("更新成功")
                    } else {
                        ajaxUtils.fail().msg("上传文件失败")
                    }
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        }else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }

        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user/edit/real_name")
    fun editRealName(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    data.realName = usersDataVo.realName
                    usersService.update(data)
                    ajaxUtils.success().put("user", data).msg("更新成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user/edit/password")
    fun editPassword(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    data.password = BCryptUtils.bCryptPassword(usersDataVo.password!!)
                    usersService.update(data)
                    ajaxUtils.success().put("user", data).msg("更新成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user/edit/sex")
    fun editSex(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    data.sex = usersDataVo.sex
                    usersService.update(data)
                    ajaxUtils.success().put("user", data).msg("更新成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user/edit/contact")
    fun editContact(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    data.contact = usersDataVo.contact
                    usersService.update(data)
                    ajaxUtils.success().put("user", data).msg("更新成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }

    /**
     * 更新用户数据
     *
     * @param usersDataVo 数据
     * @return true or false
     */
    @GetMapping("/user/edit/address")
    fun editAddress(@Valid usersDataVo: UsersDataVo, bindingResult: BindingResult): Mono<ResponseEntity<Map<String, Any>>> {
        val ajaxUtils = AjaxUtils.of()
        if (!bindingResult.hasErrors()) {
            val name = StringUtils.trim(usersDataVo.username!!)
            val data = usersService.findByUsername(name)
            if (Objects.nonNull(data)) {
                if (StringUtils.equals(data!!.accessToken, usersDataVo.accessToken)) {
                    data.address = usersDataVo.address
                    usersService.update(data)
                    ajaxUtils.success().put("user", data).msg("更新成功")
                } else {
                    ajaxUtils.fail().msg("登录失效")
                }
            } else {
                ajaxUtils.fail().msg("获取数据失败")
            }
        } else {
            ajaxUtils.fail().msg(bindingResult.fieldError!!.defaultMessage!!)
        }
        return Mono.just(ResponseEntity(ajaxUtils.send(), HttpStatus.OK))
    }
}