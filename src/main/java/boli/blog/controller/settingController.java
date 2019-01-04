package boli.blog.controller;

import boli.blog.entity.Blog;
import boli.blog.entity.Category;
import boli.blog.entity.Image;
import boli.blog.entity.User;
import boli.blog.service.BlogService;
import boli.blog.service.ImageService;
import boli.blog.service.SettingService;
import boli.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/set")
public class settingController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "blogService")
    private BlogService blogService;

    @Resource(name = "imageService")
    private ImageService imageService;

    @Resource(name = "settingService")
    private SettingService settingService;

    @RequestMapping("/setting")
    public String setting(Model model, HttpSession session){
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

        return "setting";
    }

    @RequestMapping("/setImage")
    public String setImage(int userId,int imageId,HttpSession session){
        Image image = imageService.selectById(imageId);
        int result = settingService.setImage(userId,image);

        if(result == 1){
            System.out.println("用户图片设置成功");
            session.setAttribute("backImage",settingService.selectByUserId(userId).getImage());
        }
        else {
            System.out.println("用户图片设置失败");
        }

        return "redirect:/set/setting";
    }
}
