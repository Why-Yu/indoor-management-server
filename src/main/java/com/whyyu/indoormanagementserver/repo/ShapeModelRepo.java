package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.ShapeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/3 17:15
 */
@Repository
public interface ShapeModelRepo extends JpaRepository<ShapeModel, Integer> {
    /**
     * 根据Id删除对应记录
     * @param shapeModelId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from shape_model where shape_model.index = ?1", nativeQuery = true)
    public int deleteByShapeModelId(Integer shapeModelId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param buildId 建筑物标号
     * @param beginId 起始点标号
     * @param beginX x
     * @param beginY y
     * @param endId 终止点标号
     * @param endX x
     * @param endY y
     * @param floor 楼层
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update shape_model set build_id = ?2, begin_id = ?3, beginx = ?4, beginy = ?5, " +
            "end_id = ?6, endx = ?7, endy = ?8, floor = ?9 where index = ?1", nativeQuery = true)
    public int updateShapeModel(Integer index, String buildId, String beginId, Double beginX, Double beginY,
                          String endId, Double endX, Double endY, String floor);
}
