package bookstore.Entity.Address;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "districts")
public class DistrictsEntity {
	@Id
    @Column(name = "code", nullable = false, length = 20)
    private String code;
	
	@Column(name = "name", nullable = false, length = 255)
    private String name;  

    @Column(name = "name_en", length = 255)
    private String nameEn;  

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName; 

    @Column(name = "full_name_en", length = 255)
    private String fullNameEn;  

    @Column(name = "code_name", length = 255)
    private String codeName;  
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_code")
    private ProvincesEntity provincesEntity;  // Liên kết với bảng administrative_units
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administrative_unit_id")
    private AdministrativeUnitsEntity administrativeUnitsEntity;  // Liên kết với bảng administrative_units
    
    @OneToMany(mappedBy = "districtsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WardsEntity> wardsEntity;  // Liên kết với bảng districts

    public DistrictsEntity() {
    	super();
    }

	public DistrictsEntity(String code, String name, String nameEn, String fullName, String fullNameEn, String codeName,
			ProvincesEntity provincesEntity, AdministrativeUnitsEntity administrativeUnitsEntity,
			List<WardsEntity> wardsEntity) {
		super();
		this.code = code;
		this.name = name;
		this.nameEn = nameEn;
		this.fullName = fullName;
		this.fullNameEn = fullNameEn;
		this.codeName = codeName;
		this.provincesEntity = provincesEntity;
		this.administrativeUnitsEntity = administrativeUnitsEntity;
		this.wardsEntity = wardsEntity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
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

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public ProvincesEntity getProvincesEntity() {
		return provincesEntity;
	}

	public void setProvincesEntity(ProvincesEntity provincesEntity) {
		this.provincesEntity = provincesEntity;
	}

	public AdministrativeUnitsEntity getAdministrativeUnitsEntity() {
		return administrativeUnitsEntity;
	}

	public void setAdministrativeUnitsEntity(AdministrativeUnitsEntity administrativeUnitsEntity) {
		this.administrativeUnitsEntity = administrativeUnitsEntity;
	}

	public List<WardsEntity> getWardsEntity() {
		return wardsEntity;
	}

	public void setWardsEntity(List<WardsEntity> wardsEntity) {
		this.wardsEntity = wardsEntity;
	}
    
	
	
    
}
