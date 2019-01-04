package boli.blog.service;

import boli.blog.dao.ImageDao;
import boli.blog.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("imageService")
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    // 查询所有图片信息
    public List<Image> selectAll(){
        return imageDao.selectAll();
    }

    // 根据图片 id 查询图片信息
    public Image selectById(int id){
        return imageDao.selectById(id);
    }

    // 根据用户 id 查询该用户所有图片
    public List<Image> selectByUserId(int userId){
        return imageDao.selectByUserId(userId);
    }

    // 添加图片
    public int insert(Image image){
        return imageDao.insert(image);
    }

    // 根据图片 id 删除图片
    public int delete(int id){
        return imageDao.delete(id);
    }
}
