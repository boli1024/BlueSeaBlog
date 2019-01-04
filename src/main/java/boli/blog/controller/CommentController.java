package boli.blog.controller;

import boli.blog.entity.Comment;
import boli.blog.entity.User;
import boli.blog.service.CommentService;
import boli.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource(name = "commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/add")
    public String add(Comment comment, HttpSession session){
        if("".equals(String.valueOf(comment.getBlogId())) || "null".equals(String.valueOf(comment.getBlogId()))){
            return "redirect:/";
        }
        if(!(Boolean)session.getAttribute("isLogin")){
            return "redirect:/loginPre";
        }
        if("".equals(comment.getContent())){
            return "redirect:/blog/detail?id="+comment.getBlogId();
        }

        String name = (String)session.getAttribute("currentUser");
        User user = userService.selectByName(name);
        comment.setUserId(user.getId());
        comment.setReplyTime(new Timestamp(System.currentTimeMillis()));
        int result = commentService.insert(comment);
        System.out.println("添加评论结果："+result);

        return "redirect:/blog/detail?id="+comment.getBlogId();
    }
}
