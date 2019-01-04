package boli.blog.controller.admin;

import boli.blog.entity.User;
import boli.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Controller("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/")
    public String userAdmin(){
        return "redirect:/admin/user/all";
    }

    @RequestMapping("/all")
    public String all(Model model){
        List<User> users = userService.selectAll();
        model.addAttribute("users",users);
        return "admin/user/all";
    }

    @RequestMapping("/addPre")
    public String addPre(){
        return "admin/user/add";
    }

    @RequestMapping("/add")
    public String insert(User user){

        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));

        int result = userService.insert(user);

        if(result == 1){
            System.out.println("插入数据成功");
        }
        else {
            System.out.println("插入数据失败");
        }

        return "redirect:/admin/user/all";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = userService.delete(id);
        if(result == 0){
            System.out.println("用户信息删除成功");
        }
        else {
            System.out.println("用户信息删除失败");
        }
        return "redirect:/admin/user/all";
    }

    @RequestMapping("/updatePre")
    public String updatePre(Model model,int id){
        User user = userService.selectById(id);
        model.addAttribute("user",user);
        return "admin/user/update";
    }

    @RequestMapping("/update")
    public String update(User user){
        int result = userService.update(user);

        if(result == 1){
            System.out.println("修改数据成功");
        }
        else {
            System.out.println("修改数据失败");
        }

        return "redirect:/admin/user/all";
    }

    @RequestMapping("/searchPre")
    public String updatePre(){
        return "admin/user/search";
    }

    @RequestMapping("/search")
    public String search(Model model,int id){
        User user = userService.selectById(id);
        model.addAttribute("user",user);
        return "admin/user/search";
    }
}
