package com.whyyu.indoormanagementserver.repo;

import com.whyyu.indoormanagementserver.entity.RemoteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/8/6 22:27
 */
@Repository
public interface RemoteImageRepo extends JpaRepository<RemoteImage, Integer> {
    /**
     * 根据Id删除对应记录
     * @param remoteImageId 数据库主键
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from remoteimage where remoteimage.index = ?1", nativeQuery = true)
    public int deleteByRemoteImageId(Integer remoteImageId);

    /**
     * 修改对应记录
     * @param index 主键
     * @param name 遥感图像数据标识
     * @param band 条带号
     * @param rowIndex 行编号
     * @param date 拍摄日期
     * @param cloud 云量
     * @param longitude 经度
     * @param latitude 纬度
     * @return int
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update remoteimage set name = ?2, band = ?3, row_index = ?4, date = ?5, cloud = ?6, " +
            "longitude = ?7, latitude = ?8 where index = ?1", nativeQuery = true)
    public int updateRemoteImage(Integer index, String name, Integer band, Integer rowIndex, String date, Float cloud,
                                 Double longitude, Double latitude);

    public RemoteImage findByIndex(Integer remoteImageIndex);
}
