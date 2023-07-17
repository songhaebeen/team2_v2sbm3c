package dev.mvc.resort_v1sbm3c;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.contentsco.Contentsco;
import dev.mvc.cosme.Cosme;
import dev.mvc.costip.Costip;
import dev.mvc.fboard.Fboard;
import dev.mvc.tool.Tool;
import dev.mvc.notice.Notice;


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
        registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Cosme.getUploadDir());    
        registry.addResourceHandler("/notice/storage/**").addResourceLocations("file:///" +  Notice.getUploadDir());
        registry.addResourceHandler("/contentsco/storage/**").addResourceLocations("file:///" +  Contentsco.getUploadDir());
        registry.addResourceHandler("/costip/storage/**").addResourceLocations("file:///" +  Costip.getUploadDir());

        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
 
}
