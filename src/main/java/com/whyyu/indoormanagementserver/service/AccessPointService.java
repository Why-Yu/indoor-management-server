package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.AccessPoint;
import com.whyyu.indoormanagementserver.repo.AccessPointRepo;
import com.whyyu.indoormanagementserver.util.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WhyYu
 * @Description
 * @Date 2021/7/28 11:44
 */
@Service
public class AccessPointService {
    @Autowired
    AccessPointRepo accessPointRepo;

    public List<AccessPoint> saveAll(Iterable<AccessPoint> entities) {
        return accessPointRepo.saveAll(entities);
    }

    public Page<AccessPoint> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return accessPointRepo.findAll(pageable);
    }

    public int deleteByAccessPointId(Integer accessPointId) {
        return accessPointRepo.deleteByAccessPointId(accessPointId);
    }

    public int addAccessPoint(AccessPoint accessPoint) {
        accessPointRepo.save(accessPoint);
        return 1;
    }

    public int updateAccessPoint(AccessPoint accessPoint) {
        return accessPointRepo.updateAccessPoint(accessPoint.getIndex(), accessPoint.getName(), accessPoint.getX(), accessPoint.getY(), accessPoint.getH());
    }

    public List<AccessPoint> findAll() {
        return accessPointRepo.findAll();
    }

    public long count() {
        return accessPointRepo.count();
    }
}
