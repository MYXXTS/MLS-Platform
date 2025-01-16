package com.myxxts.mls.server.service;

import java.util.HashMap;

import com.myxxts.mls.server.model.entity.Setting;

public interface SettingService {

  /**
   * 系统配置文件初始化
   */
  void init ();

  /**
   * 获取系统配置信息
   * 
   * @return
   */
  Setting getSetting (String name);

  /**
   * 获取前端配置信息
   * 
   * @return
   */
  HashMap<String, Object> getProperties (String name);

}
