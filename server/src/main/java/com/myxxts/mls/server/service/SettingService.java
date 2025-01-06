package com.myxxts.mls.server.service;

import java.util.HashMap;

import com.myxxts.mls.server.module.entity.Setting;

public interface SettingService {

  /**
   * 系统配置文件初始化
   */
  public void init ();

  /**
   * 获取系统配置信息
   * 
   * @return
   */
  public Setting getSetting (String name);

  /**
   * 获取前端配置信息
   * 
   * @return
   */
  public HashMap<String, String> getProperties (String name);

}
