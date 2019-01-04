package boli.blog.dao;

import boli.blog.entity.Setting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingDao {

    // 通过用户 id 查找设置
    public Setting selectByUserId(int userId);

    // 插入设置
    public int insert(Setting setting);

    // 修改设置
    public int updateImage(Setting setting);
}
