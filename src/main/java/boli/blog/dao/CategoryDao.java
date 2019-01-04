package boli.blog.dao;

import boli.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao {

    // 查询所有类别
    public List<Category> selectAll();

    // 根据 id 查找类别
    public Category selectById(int id);

    // 新增类别
    public int insert(Category category);

    // 删除类别
    public int deleteById(int id);
}
