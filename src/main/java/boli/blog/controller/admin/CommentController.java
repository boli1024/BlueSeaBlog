package boli.blog.controller.admin;

import boli.blog.entity.Comment;
import boli.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller("adminCommentController")
@RequestMapping("/admin/comment")
public class CommentController {

    @Resource(name = "commentService")
    private CommentService commentService;

    @RequestMapping("/")
    public String begin(){
        return "redirect:/admin/comment/all";
    }

    @RequestMapping("/all")
    public String all(Model model){
        List<Comment> comments = commentService.selectAll();
        model.addAttribute("comments",comments);
        return "admin/comment/all";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        int result = commentService.deleteById(id);
        if(result == 1){
            System.out.println("删除评论成功");
        }
        else {
            System.out.println("删除评论失败");
        }
        return "redirect:/admin/comment/all";
    }


}
