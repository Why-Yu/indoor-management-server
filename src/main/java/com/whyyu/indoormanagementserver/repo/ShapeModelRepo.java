package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.ShapeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/3 17:15
 */
@Repository
public interface ShapeModelRepo extends JpaRepository<ShapeModel, Integer> {
}
