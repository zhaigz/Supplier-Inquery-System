package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.DataSnapshot;

public interface IDataSnapshotService
{
    DataSnapshot selectDataSnapshotById(Long id);
    List<DataSnapshot> selectDataSnapshotList(DataSnapshot entity);
    int insertDataSnapshot(DataSnapshot entity);
    int updateDataSnapshot(DataSnapshot entity);
    int deleteDataSnapshotById(Long id);
    int deleteDataSnapshotByIds(Long[] ids);
}
