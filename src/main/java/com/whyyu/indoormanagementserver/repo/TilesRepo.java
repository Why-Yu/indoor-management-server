package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.Tiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/5 10:22
 */
@Repository
public interface TilesRepo extends JpaRepository<Tiles, Integer> {
    /**
     * 根据Id删除对应记录
     * @param tilesId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from tiles where tiles.index = ?1", nativeQuery = true)
    public int deleteByTilesId(Integer tilesId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name 瓦片集名
     * @param zoom 缩放层级
     * @param row 行
     * @param col 列
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update tiles set name = ?2, zoom = ?3, row = ?4, col = ?5 where index = ?1", nativeQuery = true)
    public int updateTiles(Integer index, String name, String zoom, String row, String col);
}
