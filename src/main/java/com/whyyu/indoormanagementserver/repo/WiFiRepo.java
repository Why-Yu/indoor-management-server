package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.WiFi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/26 17:24
 */
@Repository
public interface WiFiRepo extends JpaRepository<WiFi, Integer> {
    /**
     * 根据Id删除对应记录
     * @param wifiId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from wifi where wifi.index = ?1", nativeQuery = true)
    public int deleteByWiFiId(Integer wifiId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name
     * @param x
     * @param y
     * @param h
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update wifi set name = ?2, x = ?3, y = ?4, h = ?5 where index = ?1", nativeQuery = true)
    public int updateWiFi(Integer index, String name, Double x, Double y, Double h);

}
