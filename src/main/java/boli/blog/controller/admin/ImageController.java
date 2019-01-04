package boli.blog.controller.admin;

import boli.blog.entity.Image;
import boli.blog.service.ImageService;
import boli.blog.utils.FileUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller("adminImageController")
@RequestMapping("/admin/image")
public class ImageController {

    @Value(value = "F:/resources/")
    private String uploadPath;

    @Resource(name = "imageService")
    private ImageService imageService;

    @RequestMapping("/")
    public String image(){
        return "redirect:/admin/image/all";
    }

    @RequestMapping("/all")
    public String all(Model model){
        List<Image> images = imageService.selectAll();
        model.addAttribute("images",images);
        return "admin/image/all";
    }

    @RequestMapping("/uploadPre")
    public String uploadPre(){
        return "admin/image/add";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        int userId = 1;
        JSONObject json = new JSONObject();
        String path = uploadPath + userId + "/";
        boolean result = FileUtil.mkdir(path);
        if(!result){
            json.put("error",1);
            json.put("message","上传失败");
            return json.toJSONString();
        }
        String name = file.getOriginalFilename();

        Image image = new Image();
        image.setUploadTime(new Timestamp(System.currentTimeMillis()));
        image.setUserId(userId);
        image.setName(name);

        imageService.insert(image);

        File targetFile = new File(path,name);
        file.transferTo(targetFile);
        // 上传成功返回的 json 数据，必须包括 url
        json.put("error",0);
        json.put("url","/1/"+name);
        return json.toJSONString();
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = imageService.delete(id);
        if(result == 1){
            System.out.println("删除成功");
        }
        else {
            System.out.println("删除失败");
        }
        return "redirect:/admin/image/all";
    }
}
