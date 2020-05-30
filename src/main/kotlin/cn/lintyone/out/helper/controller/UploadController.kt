package cn.lintyone.out.helper.controller

import cn.lintyone.out.helper.common.Utils
import cn.lintyone.out.helper.model.response.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/upload")
class UploadController : BaseController() {

    @PutMapping("/avatar")
    fun avatar(@RequestParam avatar: MultipartFile): Response<String> {
        return Response(Utils.uploadImage(avatar, Utils.Upload.AVATAR))
    }

    @PutMapping("/image")
    fun image(@RequestParam avatar: MultipartFile): Response<String> {
        return Response(Utils.uploadImage(avatar, Utils.Upload.IMAGE))
    }

}