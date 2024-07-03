package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.JobRoleEntity;

import java.util.List;

public interface JobRoleDao extends SuperDao {
    void save(JobRoleEntity jobRoleEntity);

    JobRoleEntity getByName(String name);

    List<JobRoleEntity> getAll();

    JobRoleEntity get(Integer id);
}
