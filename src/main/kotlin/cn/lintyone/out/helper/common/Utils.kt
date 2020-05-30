package cn.lintyone.out.helper.common

import cn.lintyone.out.helper.App
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*


object Utils {

    enum class Upload(val folder: String) {
        AVATAR("avatar/"),
        IMAGE("image/")
    }

    /**
     * 文件上传
     */
    private fun uploadFile(
            file: ByteArray,
            filePath: String,
            fileName: String
    ) {
        val targetFile = File(filePath)
        if (!targetFile.exists()) {
            targetFile.mkdirs()
        }
        val out = FileOutputStream(filePath + File.separator.toString() + fileName)
        out.write(file)
        out.flush()
        out.close()
    }

    /**
     * 提供图片类型[Upload] [type]，指定图片地址，默认为[Upload.IMAGE]
     * 上传图片，返回图片路径
     */
    fun uploadImage(image: MultipartFile, type: Upload = Upload.IMAGE): String {
        var fileName = image.originalFilename
        val suffixName: String
        suffixName = fileName?.substring(fileName.lastIndexOf(".")) ?: throw MyException("未知错误")
        val filePath: String = App.runPath + "/uploads/" + type.folder
        fileName = UUID.randomUUID().toString() + suffixName
        uploadFile(image.bytes, filePath, fileName)
        return "/uploads/" + type.folder + fileName
    }
}