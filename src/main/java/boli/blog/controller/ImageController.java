package boli.blog.controller;

import boli.blog.entity.Blog;
import boli.blog.entity.Category;
import boli.blog.entity.Image;
import boli.blog.entity.User;
import boli.blog.service.BlogService;
import boli.blog.service.ImageService;
import boli.blog.service.UserService;
import boli.blog.utils.FileUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Value(value = "F:/resources/")
    private String uploadPath;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "blogService")
    private BlogService blogService;

    @Resource(name = "imageService")
    private ImageService imageService;

    @RequestMapping("/all")
    public String all(Model model, HttpSession session){
        String name = (String) session.getAttribute("currentUser");
        User user = userService.selectByName(name);

        int userId = user.getId();

        ArrayList<Category> categories = new ArrayList<Category>();

        user = userService.selectUserAndBlogById(userId);
        int blogNumber = user.getBlogs().size();
        int totalReadTimes = 0;
        for(Blog blog:user.getBlogs()){
            totalReadTimes += blog.getReadTimes();

            if(categories.size() != 0){
                int find=0;
                for(Category category:categories){
                    if(category.getId() == blog.getCategory().getId()){
                        find = 1;
                        break;
                    }
                }
                if(find == 0){
                    categories.add(blog.getCategory());
                }
            }
            else {
                categories.add(blog.getCategory());
            }
        }

        for(Category category:categories){
            category.setBlogNumber(blogService.selectByUserIdAndCategoryId(userId,category.getId()).size());
        }

        model.addAttribute("userAll",user);
        model.addAttribute("blogNumber",blogNumber);
        model.addAttribute("totalReadTimes",totalReadTimes);
        model.addAttribute("categories",categories);

        List<Image> images = imageService.selectByUserId(userId);
        model.addAttribute("images",images);

        return "picture";
    }

    @RequestMapping("/uploadPre")
    public String uploadPre(){
        return "uploadImage";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file,HttpSession session) throws IOException {
        String name = (String) session.getAttribute("currentUser");
        User user = userService.selectByName(name);

        int userId = user.getId();

        String path = uploadPath + userId + "/";
        boolean result = FileUtil.mkdir(path);
        if(!result){
            return "{}";
        }
        String filename = file.getOriginalFilename();

        Image image = new Image();
        image.setName(filename);
        image.setUploadTime(new Timestamp(System.currentTimeMillis()));
        image.setUserId(userId);

        imageService.insert(image);

        File targetFile = new File(path,filename);
        file.transferTo(targetFile);

        return "{}";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = imageService.delete(id);
        if(result == 1){
            System.out.println("图片删除成功");
        }
        else {
            System.out.println("图片删除失败");
        }
        return "redirect:/image/all";
    }
}
