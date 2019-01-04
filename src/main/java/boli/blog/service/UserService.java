package boli.blog.service;

import boli.blog.dao.UserDao;
import boli.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    // 查询所有用户
    public List<User> selectAll(){
        return userDao.selectAll();
    }

    // 通过用户 Id 查找用户
    public User selectById(int id){
        return userDao.selectById(id);
    }

    // 通过用户名 Name 查找用户
    public User selectByName(String name){
        return userDao.selectByName(name);
    }

    // 新增用户，返回新增结果
    public int insert(User user){
        return userDao.insert(user);
    }

    // 删除用户
    public int delete(int id){
        return userDao.delete(id);
    }

    // 修改用户信息
    public int update(User user){
        return userDao.update(user);
    }

    // 登录
    public String login(String name,String password){
        User user = userDao.selectByName(name);
        if(user != null){
            if(!user.getPassword().equals(password)){
                return "用户密码错误，请重新输入";
            }
            else {
                return "true";
            }
        }
        else {
            return "用户不存在，请注册";
        }
    }

    // 通过用户 id 查找用户信息，包括博客
    public User selectUserAndBlogById(int id){
        return userDao.selectBlogById(id);
    }
}
