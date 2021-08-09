package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 12:19
 */
@Repository
public interface PoiRepo extends JpaRepository<Poi, Integer> {
    /**
     * 根据Id删除对应记录
     * @param poiId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from poi where poi.index = ?1", nativeQuery = true)
    public int deleteByPoiId(Integer poiId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param province 省
     * @param city 城市
     * @param area 区
     * @param type poi类别
     * @param name poi名称
     * @param longitude 经度
     * @param latitude 纬度
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update poi set province = ?2, city = ?3, area = ?4, type = ?5, name = ?6, " +
            "longitude = ?7, latitude = ?8 where index = ?1", nativeQuery = true)
    public int updatePoi(Integer index, String province, String city, String area, String type, String name,
                         Double longitude, Double latitude);
}
