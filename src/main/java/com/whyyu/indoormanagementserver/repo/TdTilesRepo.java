package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.TdTiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 15:06
 */
@Repository
public interface TdTilesRepo extends JpaRepository<TdTiles, Integer> {
    /**
     * 根据Id删除对应记录
     * @param tdTilesId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from tdtiles where tdtiles.index = ?1", nativeQuery = true)
    public int deleteByTdTilesId(Integer tdTilesId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name
     * @param path
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update tdtiles set name = ?2, path = ?3, longitude = ?4, latitude = ?5 where index = ?1", nativeQuery = true)
    public int updateTdTiles(Integer index, String name, String path, Double longitude, Double latitude);
}
