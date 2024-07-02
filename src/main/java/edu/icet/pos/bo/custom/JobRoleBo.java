package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.employee.JobRole;

import java.util.List;

public interface JobRoleBo extends SuperBo {
    void jobRoleRegister(JobRole jobRole);

    JobRole getJobRoleByName(String name);

    List<JobRole> getAllJobRole();
}
