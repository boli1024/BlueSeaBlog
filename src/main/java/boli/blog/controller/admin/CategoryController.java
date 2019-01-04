package boli.blog.controller.admin;

import boli.blog.entity.Category;
import boli.blog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller("adminCategoryController")
@RequestMapping("/admin/category")
public class CategoryController {

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @RequestMapping("/")
    public String category(){
        return "redirect:/admin/category/all";
    }

    @RequestMapping("/all")
    public String all(Model model){
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categorys",categories);
        return "admin/category/all";
    }

    @RequestMapping("/addPre")
    public String addPre(){
        return "admin/category/add";
    }

    @RequestMapping("/add")
    public String add(Category category){
        int result = categoryService.insert(category);
        if(result == 1){
            System.out.println("插入类别数据成功");
        }
        else {
            System.out.println("插入类别数据失败");
        }
        return "redirect:/admin/category/all";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = categoryService.deleteById(id);
        if(result == 1){
            System.out.println("删除类别数据成功");
        }
        else {
            System.out.println("删除类别数据失败");
        }
        return "redirect:/admin/category/all";
    }
}
