package boli.blog.service;

import boli.blog.dao.CategoryDao;
import boli.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    // 查询所有类别
    public List<Category> selectAll(){
        return categoryDao.selectAll();
    }

    // 新增类别
    public int insert(Category category){
        return categoryDao.insert(category);
    }

    // 通过 Id 删除类别
    public int deleteById(int id){
        return categoryDao.deleteById(id);
    }
}
