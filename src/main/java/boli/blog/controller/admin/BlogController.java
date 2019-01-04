package boli.blog.controller.admin;

import boli.blog.entity.Blog;
import boli.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller("adminBlogController")
@RequestMapping("/admin/blog")
public class BlogController {

    @Resource(name = "blogService")
    private BlogService blogService;

    @RequestMapping("/")
    public String blog(){
        return "redirect:/admin/blog/all";
    }

    @RequestMapping("/all")
    public String all(Model model){
        List<Blog> blogs = blogService.selectAll();
        model.addAttribute("blogs",blogs);
        return "admin/blog/all";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = blogService.deleteById(id);
        if(result == 1){
            System.out.println("删除博客成功");
        }
        else {
            System.out.println("删除博客失败");
        }
        return "redirect:/admin/blog/all";
    }

    @RequestMapping("/insertPre")
    public String insertPre(){
        return "admin/blog/add";
    }

    @RequestMapping("/insert")
    public String insert(Blog blog){
        int result = blogService.insert(blog);
        if(result == 1){
            System.out.println("博客添加成功");
        }
        else {
            System.out.println("博客添加失败");
        }
        return "redirect:/admin/blog/all";
    }
}
