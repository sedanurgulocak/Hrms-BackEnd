package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.core.verifications.abstracts.EmailVerificationService;
import kodlamaio.hrms.core.verifications.abstracts.MernisVerificationService;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.dtos.CandidateDto;
import kodlamaio.hrms.entities.concretes.users.Candidate;

@Service
public class CandidateManager implements CandidateService {

	private CandidateDao candidateDao;
	
	@Autowired
	public CandidateManager(CandidateDao candidateDao) {
		super();
		this.candidateDao = candidateDao;
	}
	
	@Autowired(required=false)
	private EmailVerificationService emailVerificationService;
	
	@Autowired(required=false)
	private MernisVerificationService mernisVerificationService;


	@Override
	public DataResult<List<Candidate>> getAll() {
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Adaylar listelendi");
	}


	@Override
	public Result add(CandidateDto candidateDto) {
		Candidate candidate = new Candidate();
		candidate.setFirstName(candidateDto.firstName);
		candidate.setLastName(candidateDto.lastName);
		candidate.setNationalIdentity(candidateDto.nationalIdentity);
		candidate.setDateOfBirth(candidateDto.dateOfBirth);
		candidate.setEmail(candidateDto.email);
		candidate.setPassword(candidateDto.password);
		candidate.setPasswordAgain(candidateDto.passwordAgain);
		candidate.setType(candidateDto.type);
		
		if(candidateDto.email == null || candidateDto.password == null || candidateDto.passwordAgain == null || 
				candidateDto.type == null || candidateDto.firstName == null || candidateDto.lastName == null ||
				candidateDto.nationalIdentity == null || candidateDto.dateOfBirth == null ) {
			return new ErrorResult("Tüm alanlar zorunlu");
		}else if(!candidateDto.password.equals(candidateDto.passwordAgain)) {
			return new ErrorResult("Şifre ve şifre tekrarı eşit değil");
		}else if(!this.emailVerificationService.isVerifed(candidateDto.email)) {
			return new ErrorResult("Email onaylanmadı");
		}//else if(!this.mernisVerificationService.checkIfRealPerson(candidateDto)){
			//return new ErrorResult("Kullanıcı mernisten onaylanmadı");
		//}
		else if(this.candidateDao.existsCandidateByEmail(candidateDto.email)) {
			return new ErrorResult("Email zaten kayıtlı");
		}else if(this.candidateDao.existsCandidateByNationalIdentity(candidateDto.nationalIdentity)){
			return new ErrorResult("Tc kimlik no zaten kayıtlı");
		}
		
		this.candidateDao.save(candidate);
		return new SuccessResult("Aday eklendi");
	}


	@Override
	public Candidate getById(int id) {
		Candidate candidate = this.candidateDao.getById(id);
		return candidate;
	}

}
