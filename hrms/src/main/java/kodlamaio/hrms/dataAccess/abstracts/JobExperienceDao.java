package kodlamaio.hrms.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.hrms.entities.concretes.cvs.JobExperience;

public interface JobExperienceDao extends JpaRepository<JobExperience, Integer>{

}
