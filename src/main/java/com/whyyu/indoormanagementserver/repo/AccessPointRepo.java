package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.AccessPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/28 11:41
 */
@Repository
public interface AccessPointRepo extends JpaRepository<AccessPoint, Integer> {
    /**
     * 根据Id删除对应记录
     * @param accessPointId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from accesspoint where accesspoint.index = ?1", nativeQuery = true)
    public int deleteByAccessPointId(Integer accessPointId);

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
    @Query(value = "update accesspoint set name = ?2, x = ?3, y = ?4, h = ?5 where index = ?1", nativeQuery = true)
    public int updateAccessPoint(Integer index, String name, Double x, Double y, Double h);
}
