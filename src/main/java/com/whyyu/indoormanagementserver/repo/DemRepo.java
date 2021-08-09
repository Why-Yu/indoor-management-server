package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.Dem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/9 16:18
 */
@Repository
public interface DemRepo extends JpaRepository<Dem, Integer> {
    /**
     * 根据Id删除对应记录
     * @param demId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from dem where dem.index = ?1", nativeQuery = true)
    public int deleteByDemId(Integer demId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name dem名称
     * @param band 条带号
     * @param rowIndex 行编号
     * @param longitude 经度
     * @param latitude 纬度
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update dem set name = ?2, band = ?3, row_index = ?4, longitude = ?5, latitude = ?6 where index = ?1", nativeQuery = true)
    public int updateDem(Integer index, String name, Integer band, Integer rowIndex, Double longitude, Double latitude);
}
