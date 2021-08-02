package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.WiFi;
import com.whyyu.indoormanagementserver.repo.WiFiRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/26 17:26
 */
@Service
public class WiFiService {
    @Autowired
    WiFiRepo wifiRepo;

    public List<WiFi> saveAll(Iterable<WiFi> entities) {
        return wifiRepo.saveAll(entities);
    }

    public Page<WiFi> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return wifiRepo.findAll(pageable);
    }

    public int deleteByWiFiId(Integer wiFiId) {
        return wifiRepo.deleteByWiFiId(wiFiId);
    }

    public int addWiFi(WiFi wifi) {
        wifiRepo.save(wifi);
        return 1;
    }

    public int updateWiFi(WiFi wifi) {
        return wifiRepo.updateWiFi(wifi.getIndex(), wifi.getName(), wifi.getX(), wifi.getY(), wifi.getH());
    }

    public List<WiFi> findAll() {
        return wifiRepo.findAll();
    }

    public long count() {
        return wifiRepo.count();
    }
}
