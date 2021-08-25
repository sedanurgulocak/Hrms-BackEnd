package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.JobAdvertisementService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlamaio.hrms.entities.concretes.JobAdvertisement;

@Service
public class JobAdvertisementManager implements JobAdvertisementService{

	private JobAdvertisementDao jobAdvertisementDao;
	
	@Autowired
	public JobAdvertisementManager(JobAdvertisementDao jobAdvertisementDao) {
		super();
		this.jobAdvertisementDao = jobAdvertisementDao;
	}

	@Override
	public Result add(JobAdvertisement jobAdvertisement) {
		this.jobAdvertisementDao.save(jobAdvertisement);
		return new SuccessResult("İlan kaydedildi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getAll() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findAll(),"Data listelendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getAllActives() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.queryJobAdvertisementByStatusTrue(), "Aktif iş ilanları listelendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getAllSortedDate() {
		//Sort sort = Sort.by(Sort.Direction.ASC, "createDate");
		//return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findAll(sort));
	
		List<JobAdvertisement> jobAdvertisements = jobAdvertisementDao.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
		return new SuccessDataResult<List<JobAdvertisement>>(jobAdvertisements, "İş ilanları tarihe göre sıralandı");

	}

}
