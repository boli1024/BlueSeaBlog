package boli.blog.dao;

import boli.blog.entity.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageDao {

    // 查询所有图片信息
    public List<Image> selectAll();

    // 根据图片 id 查询图片信息
    public Image selectById(int id);

    // 根据用户 id 查询该用户所有图片
    public List<Image> selectByUserId(int userId);

    // 添加图片信息
    public int insert(Image image);

    // 删除图片信息
    public int delete(int id);
}
