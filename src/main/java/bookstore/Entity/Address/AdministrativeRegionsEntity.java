package bookstore.Entity.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "administrative_regions")
public class AdministrativeRegionsEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;  // Khóa chính

    @Column(name = "name", nullable = false, length = 255)
    private String name;  // Tên khu vực hành chính

    @Column(name = "name_en", nullable = false, length = 255)
    private String nameEn;  // Tên khu vực hành chính (tiếng Anh)

    @Column(name = "code_name", length = 255)
    private String codeName;  // Mã khu vực hành chính

    @Column(name = "code_name_en", length = 255)
    private String codeNameEn;  // Mã khu vực hành chính (tiếng Anh)

    
    @OneToMany(mappedBy = "administrativeRegionsEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProvincesEntity> provincesEntity; // Một khu vực hành chính có thể có nhiều tỉnh thành

    
    public AdministrativeRegionsEntity() {
    	super();
    }
    
    
	public AdministrativeRegionsEntity(Integer id, String name, String nameEn, String codeName, String codeNameEn,
			List<ProvincesEntity> provincesEntities) {
		super();
		this.id = id;
		this.name = name;
		this.nameEn = nameEn;
		this.codeName = codeName;
		this.codeNameEn = codeNameEn;
		this.provincesEntity = provincesEntities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<ProvincesEntity> getProvinces() {
		return provincesEntity;
	}

	public void setProvinces(List<ProvincesEntity> provincesEntities) {
		this.provincesEntity = provincesEntities;
	}
    
    
}
