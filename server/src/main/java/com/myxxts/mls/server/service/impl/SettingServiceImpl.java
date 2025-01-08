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

    if (settingRepository.findByName("System Config").isEmpty()) {
      Setting setting = new Setting();
      setting.setName("System Config");
      HashMap<String, String> properties = new HashMap<>();
      properties.put("isInitialized", "false");
      setting.setProperties(properties);
      settingRepository.save(setting);
    }

  }

  @Override
  public HashMap<String, String> getProperties (String name) {

    return getSetting(name).getProperties();

  }

  @Override
  public Setting getSetting (String name) {

    return settingRepository.findByName(name).orElseThrow();

  }

}
