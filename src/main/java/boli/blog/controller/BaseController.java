package boli.blog.controller;

import boli.blog.entity.Blog;
import boli.blog.entity.Category;
import boli.blog.entity.Setting;
import boli.blog.entity.User;
import boli.blog.service.BlogService;
import boli.blog.service.SettingService;
import boli.blog.service.UserService;
import boli.blog.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseController {

    @Value(value = "F:/resources/")
    private String uploadPath;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "blogService")
    private BlogService blogService;

    @Resource(name = "settingService")
    private SettingService settingService;

    @RequestMapping("/")
    public String begin(Model model,HttpSession session){
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if(isLogin == null || !isLogin){
            session.setAttribute("isLogin",false);
        }
        else {
            session.setAttribute("isLogin",true);
        }

        String backImage = (String) session.getAttribute("backImage");
        if("".equals(backImage) || null == backImage){
            session.setAttribute("backImage","/static/images/back.jpg");
        }

        List<Blog> blogs = blogService.selectAll();

        model.addAttribute("blogs",blogs);
        return "index0";
    }

    @RequestMapping("/index")
    public String index(Model model,HttpSession session){
        String name = (String) session.getAttribute("currentUser");
        if("".equals(name) || null == name){
            return "redirect:/loginPre";
        }

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

        Setting setting = settingService.selectByUserId(userId);
        if(setting != null){
            session.setAttribute("backImage",setting.getImage());
        }

        return "index";

    }

    @RequestMapping("/loginPre")
    public String loginPre(){
        return "login";
    }

    @RequestMapping("/registerPre")
    public String registerPre(){
        return "register";
    }

    @RequestMapping("/login")
    public String login(Model model,String name, String password,HttpSession session){
        ArrayList<String> results = new ArrayList<String>();

        if("".equals(name)){
            results.add("用户名不能为空");
        }
        if("".equals(password)){
            results.add("密码不能为空");
        }

        if(results.size() == 0){
            String result = userService.login(name,password);
            if("true".equals(result)){
                session.setAttribute("currentUser",name);
                session.setAttribute("isLogin",true);

                User user = userService.selectByName(name);
                FileUtil.mkdir(uploadPath + user.getId() + "/");

                return "redirect:/index";
            }
            else {
                results.add(result);
            }
        }

        model.addAttribute("results",results);
        model.addAttribute("name",name);
        model.addAttribute("password",password);
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model,User user){
        ArrayList<String> results = new ArrayList<String>();
        String success = "注册成功，请前往登录页面登录";

        if("".equals(user.getName()) || null == user.getName()){
            results.add("用户名不能为空");
        }
        if("".equals(user.getPassword()) || null == user.getPassword()){
            results.add("密码不能为空");
        }
        if(userService.selectByName(user.getName()) != null){
            results.add("该用户名已被注册");
        }
        if(results.size() != 0){
            model.addAttribute("results",results);
            model.addAttribute("name",user.getName());
            model.addAttribute("password",user.getPassword());
            return "register";
        }

        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        int result = userService.insert(user);
        if(result == 1){
            model.addAttribute("success",success);
        }
        else {
            results.add("注册失败，请联系管理员");
            model.addAttribute("results",results);
        }
        return "register";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("isLogin",false);
        session.removeAttribute("currentUser");
        session.removeAttribute("backImage");
        return "redirect:/";
    }
}
