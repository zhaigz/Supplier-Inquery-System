package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.DataSnapshot;
import com.ruoyi.srm.mapper.DataSnapshotMapper;
import com.ruoyi.srm.service.IDataSnapshotService;

@Service
public class DataSnapshotServiceImpl implements IDataSnapshotService
{
    @Autowired
    private DataSnapshotMapper DataSnapshotMapper;

    public DataSnapshot selectDataSnapshotById(Long id) { return DataSnapshotMapper.selectDataSnapshotById(id); }
    public List<DataSnapshot> selectDataSnapshotList(DataSnapshot entity) { return DataSnapshotMapper.selectDataSnapshotList(entity); }
    public int insertDataSnapshot(DataSnapshot entity) { return DataSnapshotMapper.insertDataSnapshot(entity); }
    public int updateDataSnapshot(DataSnapshot entity) { return DataSnapshotMapper.updateDataSnapshot(entity); }
    public int deleteDataSnapshotById(Long id) { return DataSnapshotMapper.deleteDataSnapshotById(id); }
    public int deleteDataSnapshotByIds(Long[] ids) { return DataSnapshotMapper.deleteDataSnapshotByIds(ids); }
}
