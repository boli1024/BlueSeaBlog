package boli.blog.dao;

import boli.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    // 查询所有评论
    public List<Comment> selectAll();

    // 根据博客 id 查询该博客的所有评论
    public List<Comment> selectByBlogId(int blogId);

    // 根据 id 删除评论
    public int deleteById(int id);

    // 添加评论
    public int insert(Comment comment);
}
