package boli.blog.service;

import boli.blog.dao.BlogDao;
import boli.blog.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("blogService")
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    // 查询所有博客
    public List<Blog> selectAll(){
        return blogDao.selectAll();
    }

    // 根据 id 查找博客
    public Blog selectById(int id){
        return blogDao.selectById(id);
    }

    // 根据用户 id，类别 id 查找博客
    public List<Blog> selectByUserIdAndCategoryId(int userId,int categoryId){
        return blogDao.selectByUserIdAndCategoryId(userId,categoryId);
    }

    // 根据 Id 删除博客
    public int deleteById(int id){
        return blogDao.deleteById(id);
    }

    // 添加博客
    public int insert(Blog blog){
        if(blog.getCreateTime() == null){
            blog.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        blog.setReadTimes(0);
        return blogDao.insert(blog);
    }

    // 根据用户 id 获取该用户的博客数
    public int getBlogNumberById(int id){
        return blogDao.selectByUserId(id).size();
    }

    // 访问次数 +1
    public int updateReadTimes(int id){
        return blogDao.updateReadTimes(id);
    }
}
