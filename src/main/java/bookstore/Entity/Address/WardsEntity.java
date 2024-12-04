package bookstore.Entity.Address;

import javax.persistence.*;

@Entity
@Table(name = "wards")
public class WardsEntity {

	@Id
    @Column(name = "code", nullable = false, length = 20)
    private String code; 

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "name_en", length = 255)
    private String nameEn;
    
    @Column(name = "full_name", length = 255)
    private String fullName;
    
    @Column(name = "full_name_en", length = 255)
    private String fullNameEn;
    
    @Column(name = "code_name", length = 255)
    private String codeName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_code")
    private DistrictsEntity districtsEntity;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administrative_unit_id")
    private AdministrativeUnitsEntity administrativeUnitsEntity;

    public WardsEntity() {
    	super();
    }
    
	public WardsEntity(String code, String name, String nameEn, String fullName, String fullNameEn, String codeName,
			DistrictsEntity districtsEntity, AdministrativeUnitsEntity administrativeUnitsEntity) {
		super();
		this.code = code;
		this.name = name;
		this.nameEn = nameEn;
		this.fullName = fullName;
		this.fullNameEn = fullNameEn;
		this.codeName = codeName;
		this.districtsEntity = districtsEntity;
		this.administrativeUnitsEntity = administrativeUnitsEntity;
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

	public DistrictsEntity getDistrictsEntity() {
		return districtsEntity;
	}

	public void setDistrictsEntity(DistrictsEntity districtsEntity) {
		this.districtsEntity = districtsEntity;
	}

	public AdministrativeUnitsEntity getAdministrativeUnitsEntity() {
		return administrativeUnitsEntity;
	}

	public void setAdministrativeUnitsEntity(AdministrativeUnitsEntity administrativeUnitsEntity) {
		this.administrativeUnitsEntity = administrativeUnitsEntity;
	}
    
    
}
