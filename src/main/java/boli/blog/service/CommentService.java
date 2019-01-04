package boli.blog.service;

import boli.blog.dao.CommentDao;
import boli.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    // 查询所有评论
    public List<Comment> selectAll(){
        return commentDao.selectAll();
    }

    // 根据博客 id 查询该博客的所有评论
    public List<Comment> selectByBlogId(int blogId){
        return commentDao.selectByBlogId(blogId);
    }

    // 根据 id 删除评论
    public int deleteById(int id){
        return commentDao.deleteById(id);
    }

    // 添加评论
    public int insert(Comment comment){
        return commentDao.insert(comment);
    }
}
