package boli.blog.controller;

import boli.blog.entity.Blog;
import boli.blog.entity.Category;
import boli.blog.entity.User;
import boli.blog.service.BlogService;
import boli.blog.service.CategoryService;
import boli.blog.service.UserService;
import boli.blog.utils.FileUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Value(value = "F:/resources/")
    private String uploadPath;

    @Resource(name = "blogService")
    private BlogService blogService;

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/")
    public String begin(){
        return "redirect:/";
    }

    @RequestMapping("/detail")
    public String detail(Model model,int id){
        blogService.updateReadTimes(id);
        Blog blog = blogService.selectById(id);
        model.addAttribute("blog",blog);
        return "detail";
    }

    @RequestMapping("/createPre")
    public String createPre(Model model){
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories",categories);
        model.addAttribute("blog",new Blog());
        return "create";
    }

    @RequestMapping("/create")
    public String create(Model model, Blog blog, HttpSession session){
        ArrayList<String> results = new ArrayList<>();
        if("".equals(String.valueOf(blog.getCategoryId()))){
            results.add("请选择博客类别");
        }
        if("".equals(String.valueOf(blog.getTitle()))){
            results.add("请输入博客标题");
        }
        if("".equals(String.valueOf(blog.getContent()))){
            results.add("请输入博客内容");
        }
        if(results.size() != 0){
            List<Category> categories = categoryService.selectAll();
            model.addAttribute("categories",categories);
            model.addAttribute("results",results);
            model.addAttribute("blog",blog);
            return "create";
        }

        User user = userService.selectByName((String)session.getAttribute("currentUser"));
        blog.setUserId(user.getId());

        int result = blogService.insert(blog);
        System.out.println("博客添加结果:"+result);

        return "redirect:/index";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "imgFile",required = false) MultipartFile file,HttpSession session) throws IOException {
        JSONObject json = new JSONObject();

        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if(isLogin == null || !isLogin){
            json.put("error",1);
            json.put("message","请登录");
            return json.toJSONString();
        }

        String name = (String) session.getAttribute("currentUser");
        User user = userService.selectByName(name);

        String currentTime = String.valueOf(System.currentTimeMillis());
        String path = user.getId() + "/" + currentTime + "/";
        String absolutePath = uploadPath + path;

//        System.out.println(currentTime);
//        System.out.println(path);
//        System.out.println(absolutePath);

        boolean mkdirResult = FileUtil.mkdir(absolutePath);
        if(!mkdirResult){
            json.put("error",1);
            json.put("message","文件夹创建失败");
            return json.toJSONString();
        }

        String filename = file.getOriginalFilename();
        File image = new File(absolutePath,filename);
        file.transferTo(image);

        json.put("error",0);
        json.put("url","/" + path + filename);
        return json.toJSONString();
    }
}
