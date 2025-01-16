package com.myxxts.mls.server.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.myxxts.mls.server.model.entity.Setting;
import com.myxxts.mls.server.repository.SettingRepository;
import com.myxxts.mls.server.service.SettingService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettingServiceImpl implements SettingService {

  private final SettingRepository settingRepository;

  public SettingServiceImpl (SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  @Override
  @PostConstruct
  public void init () {

    if (settingRepository.findByName(Setting.SYSTEM_STATUS).isEmpty()) {
      Setting setting = new Setting();
      setting.setName(Setting.SYSTEM_STATUS);
      HashMap<String, Object> properties = new HashMap<>();
      // 初始化状态
      properties.put("Initialized", false);
      setting.setProperties(properties);
      settingRepository.save(setting);
    }

  }

  @Override
  public HashMap<String, Object> getProperties (String name) {
    return getSetting(name).getProperties();
  }

  @Override
  public Setting getSetting (String name) {
    return settingRepository.findByName(name).orElseThrow();
  }

}
