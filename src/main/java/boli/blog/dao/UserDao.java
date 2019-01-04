package boli.blog.dao;

import boli.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    // 通过用户 id 查找用户
    public User selectById(int id);

    // 通过用户名查找用户
    public User selectByName(String name);

    // 获取所有用户信息
    public List<User> selectAll();

    // 新增用户信息
    public int insert(User user);

    // 删除用户信息
    public int delete(int id);

    // 修改用户信息
    public int update(User user);

    // 通过用户 id 查找该用户信息以及该用户的博客
    public User selectBlogById(int id);
}
