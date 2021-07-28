package com.whyyu.indoormanagementserver.service;

import com.whyyu.indoormanagementserver.entity.BlueTooth;
import com.whyyu.indoormanagementserver.repo.BlueToothRepo;
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
public class BlueToothService {
    @Autowired
    BlueToothRepo blueToothRepo;

    public List<BlueTooth> saveAll(Iterable<BlueTooth> entities) {
        return blueToothRepo.saveAll(entities);
    }

    public Page<BlueTooth> findAllByPage(PageParam pageParam) {
        Pageable pageable = PageRequest.of(pageParam.getPage(), pageParam.getSize());
        return blueToothRepo.findAll(pageable);
    }

    public int deleteByBlueToothId(Integer blueToothId) {
        return blueToothRepo.deleteByBlueToothId(blueToothId);
    }

    public int addBlueTooth(BlueTooth blueTooth) {
        blueToothRepo.save(blueTooth);
        return 1;
    }

    public int updateBlueTooth(BlueTooth blueTooth) {
        return blueToothRepo.updateBlueTooth(blueTooth.getIndex(), blueTooth.getName(), blueTooth.getX(), blueTooth.getY(), blueTooth.getH());
    }

    public List<BlueTooth> findAll() {
        return blueToothRepo.findAll();
    }
}
