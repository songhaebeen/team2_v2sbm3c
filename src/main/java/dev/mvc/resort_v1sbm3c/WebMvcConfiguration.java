package dev.mvc.resort_v1sbm3c;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.contents.Contents;
import dev.mvc.cosme.Cosme;
import dev.mvc.fboard.Fboard;
import dev.mvc.notice.Notice;
import dev.mvc.tool.Tool;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
      
        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
      
        // JSP 인식되는 경로: http://localhost:9091/contents/storage";
        registry.addResourceHandler("/fboard/storage/**").addResourceLocations("file:///" +  Fboard.getUploadDir());
        registry.addResourceHandler("/notice/storage/**").addResourceLocations("file:///" +  Notice.getUploadDir());
<<<<<<< HEAD
        registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Fboard.getUploadDir());
        registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Cosme.getUploadDir());
=======
        registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Cosme.getUploadDir());


        registry.addResourceHandler("/notice/storage/**").addResourceLocations("file:///" +  Notice.getUploadDir());
        registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Cosme.getUploadDir());

<<<<<<< HEAD
=======
>>>>>>> b34d95a1429ac5938560c66ba542fa1ae792670b
>>>>>>> 2d8a5182bc80e89fab32b8f16438493e2055675b
>>>>>>> 9db52cda889ac9decdd6d193495f3add6d51a9ae
        
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
 
}
