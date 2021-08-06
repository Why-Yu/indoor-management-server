package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 17:26
 */
@Repository
public interface PlanRepo extends JpaRepository<Plan, Integer> {
    /**
     * 根据Id删除对应记录
     * @param planId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from plan where plan.index = ?1", nativeQuery = true)
    public int deleteByPlanId(Integer planId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name 瓦片集名
     * @param path 存储路径
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update plan set name = ?2, path = ?3 where index = ?1", nativeQuery = true)
    public int updatePlan(Integer index, String name, String path);

    public Plan findByIndex(Integer planIndex);
}
