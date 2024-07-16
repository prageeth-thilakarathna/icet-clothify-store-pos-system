package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.JobRoleBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.JobRoleDao;
import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.model.employee.JobRole;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class JobRoleBoImpl implements JobRoleBo {
    private final JobRoleDao jobRoleDao = DaoFactory.getDao(DaoType.JOB_ROLE);

    @Override
    public void jobRoleRegister(JobRole jobRole) {
        assert jobRoleDao != null;
        jobRoleDao.save(new ModelMapper().map(jobRole, JobRoleEntity.class));
    }

    @Override
    public JobRole getJobRoleByName(String name) {
        assert jobRoleDao != null;
        return new ModelMapper().map(jobRoleDao.getByName(name), JobRole.class);
    }

    @Override
    public List<JobRole> getAllJobRole() {
        assert jobRoleDao != null;
        return new ModelMapper().map(jobRoleDao.getAll(), new TypeToken<List<JobRole>>() {
        }.getType());
    }

    @Override
    public JobRole getJobRole(Integer id) {
        assert jobRoleDao != null;
        return new ModelMapper().map(jobRoleDao.get(id), JobRole.class);
    }
}
