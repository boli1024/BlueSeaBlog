package boli.blog.service;

import boli.blog.dao.SettingDao;
import boli.blog.entity.Image;
import boli.blog.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("settingService")
public class SettingService {

    @Autowired
    private SettingDao settingDao;

    // 根据用户 id 查询设置
    public Setting selectByUserId(int userId){
        return settingDao.selectByUserId(userId);
    }

    // 设置背景图
    public int setImage(int userId, Image image){

        Setting setting = settingDao.selectByUserId(userId);

        String imagePath = "/" + userId + "/" + image.getName();

        if(setting == null){
            Setting userSetting = new Setting();
            userSetting.setUserId(userId);

            userSetting.setImage(imagePath);
            return settingDao.insert(userSetting);
        }
        else {
            setting.setImage(imagePath);
            return settingDao.updateImage(setting);
        }
    }
}
