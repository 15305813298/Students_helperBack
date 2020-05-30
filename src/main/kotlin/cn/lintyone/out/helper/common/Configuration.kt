package cn.lintyone.out.helper.common

import cn.lintyone.out.helper.App
import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*

@Configuration
class Configuration : WebMvcConfigurer {

    @Bean
    fun getAdminInterceptor() = AdminInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(getAdminInterceptor())
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/kaptcha")
                .excludePathPatterns("/graphql/**")
                .excludePathPatterns("/uploads/**")
                .excludePathPatterns("/upload/**")
                .addPathPatterns("/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + App.runPath + "uploads/")
        registry.addResourceHandler("/vendor/playground/**")
                .addResourceLocations("classpath:/playground/")
    }

    @Bean
    fun getDefaultKaptcha(): DefaultKaptcha {
        val defaultKaptcha = DefaultKaptcha()
        val properties = Properties()
        properties.setProperty("kaptcha.border", "yes")
        properties.setProperty("kaptcha.border.color", "105,179,90")
        properties.setProperty("kaptcha.textproducer.font.color", "red")
        properties.setProperty("kaptcha.image.width", "120")
        properties.setProperty("kaptcha.image.height", "38")
        properties.setProperty("kaptcha.textproducer.font.size", "30")
        properties.setProperty("kaptcha.session.key", "code")
        properties.setProperty("kaptcha.textproducer.char.length", "4")
        properties.setProperty("kaptcha.textproducer.font.names", "宋体，楷体，微软雅黑")
        val config = Config(properties)
        defaultKaptcha.config = config
        return defaultKaptcha
    }

}