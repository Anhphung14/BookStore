package bookstore.Entity.Address;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "administrative_units")
public class AdministrativeUnitsEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "full_name", length = 255)
    private String fullName; 

    @Column(name = "full_name_en", length = 255)
    private String fullNameEn;  
    
    @Column(name = "short_name", length = 255)
    private String shortName; 

    @Column(name = "short_name_en", length = 255)
    private String shortNameEn;  
    
    @Column(name = "code_name", length = 255)
    private String codeName;  
    
    @Column(name = "code_name_en", length = 255)
    private String codeNameEn;  
    
    @OneToMany(mappedBy = "administrativeUnitsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProvincesEntity> provincesEntity;
    
    @OneToMany(mappedBy = "administrativeUnitsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistrictsEntity> districtsEntity;  // Liên kết với bảng districts
    
    @OneToMany(mappedBy = "administrativeUnitsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WardsEntity> wardsEntity;  // Liên kết với bảng districts

   public AdministrativeUnitsEntity() {
	   super();
   }
    
	public AdministrativeUnitsEntity(Integer id, String fullName, String fullNameEn, String shortName,
			String shortNameEn, String codeName, String codeNameEn, List<ProvincesEntity> provincesEntity,
			List<DistrictsEntity> districtsEntity, List<WardsEntity> wardsEntity) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.fullNameEn = fullNameEn;
		this.shortName = shortName;
		this.shortNameEn = shortNameEn;
		this.codeName = codeName;
		this.codeNameEn = codeNameEn;
		this.provincesEntity = provincesEntity;
		this.districtsEntity = districtsEntity;
		this.wardsEntity = wardsEntity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortNameEn() {
		return shortNameEn;
	}

	public void setShortNameEn(String shortNameEn) {
		this.shortNameEn = shortNameEn;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeNameEn() {
		return codeNameEn;
	}

	public void setCodeNameEn(String codeNameEn) {
		this.codeNameEn = codeNameEn;
	}

	public List<ProvincesEntity> getProvincesEntity() {
		return provincesEntity;
	}

	public void setProvincesEntity(List<ProvincesEntity> provincesEntity) {
		this.provincesEntity = provincesEntity;
	}

	public List<DistrictsEntity> getDistrictsEntity() {
		return districtsEntity;
	}

	public void setDistrictsEntity(List<DistrictsEntity> districtsEntity) {
		this.districtsEntity = districtsEntity;
	}

	public List<WardsEntity> getWardsEntity() {
		return wardsEntity;
	}

	public void setWardsEntity(List<WardsEntity> wardsEntity) {
		this.wardsEntity = wardsEntity;
	}
    
    
}
