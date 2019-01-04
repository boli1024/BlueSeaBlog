package boli.blog.dao;

import boli.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogDao {

    // 查询所有博客
    public List<Blog> selectAll();

    // 根据 id 查找博客
    public Blog selectById(int id);

    // 根据 ID 删除博客
    public int deleteById(int id);

    // 添加博客
    public int insert(Blog blog);

    // 根据用户 id 查询该用户的所有博客
    public List<Blog> selectByUserId(int userId);

    // 根据用户 id，类别 id 查询博客
    public List<Blog> selectByUserIdAndCategoryId(int userId,int categoryId);

    // 访问次数 +1
    public int updateReadTimes(int id);
}
