package bookstore.Entity.Address;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "provinces")
public class ProvincesEntity {
	@Id
    @Column(name = "code", nullable = false, length = 20)
    private String code;  // Mã tỉnh (khóa chính)

    @Column(name = "name", nullable = false, length = 255)
    private String name;  // Tên tỉnh

    @Column(name = "name_en", length = 255)
    private String nameEn;  // Tên tỉnh bằng tiếng Anh

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;  // Tên đầy đủ tỉnh

    @Column(name = "full_name_en", length = 255)
    private String fullNameEn;  // Tên đầy đủ tỉnh bằng tiếng Anh

    @Column(name = "code_name", length = 255)
    private String codeName;  // Mã tỉnh

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administrative_unit_id")
    private AdministrativeUnitsEntity administrativeUnitsEntity;  // Liên kết với bảng administrative_units

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administrative_region_id")
    private AdministrativeRegionsEntity administrativeRegionsEntity;  // Liên kết với bảng administrative_regions

    @OneToMany(mappedBy = "provincesEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistrictsEntity> districtsEntity;  // Liên kết với bảng districts

    public ProvincesEntity() {
    	super();
    }

	public ProvincesEntity(String code, String name, String nameEn, String fullName, String fullNameEn, String codeName,
			AdministrativeUnitsEntity administrativeUnitsEntity,
			AdministrativeRegionsEntity administrativeRegionsEntity, List<DistrictsEntity> districtsEntity) {
		super();
		this.code = code;
		this.name = name;
		this.nameEn = nameEn;
		this.fullName = fullName;
		this.fullNameEn = fullNameEn;
		this.codeName = codeName;
		this.administrativeUnitsEntity = administrativeUnitsEntity;
		this.administrativeRegionsEntity = administrativeRegionsEntity;
		this.districtsEntity = districtsEntity;
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

	public AdministrativeUnitsEntity getAdministrativeUnitsEntity() {
		return administrativeUnitsEntity;
	}

	public void setAdministrativeUnitsEntity(AdministrativeUnitsEntity administrativeUnitsEntity) {
		this.administrativeUnitsEntity = administrativeUnitsEntity;
	}

	public AdministrativeRegionsEntity getAdministrativeRegionsEntity() {
		return administrativeRegionsEntity;
	}

	public void setAdministrativeRegionsEntity(AdministrativeRegionsEntity administrativeRegionsEntity) {
		this.administrativeRegionsEntity = administrativeRegionsEntity;
	}

	public List<DistrictsEntity> getDistrictsEntity() {
		return districtsEntity;
	}

	public void setDistrictsEntity(List<DistrictsEntity> districtsEntity) {
		this.districtsEntity = districtsEntity;
	}
    
    
    
	
	
   
    
	
    
}
