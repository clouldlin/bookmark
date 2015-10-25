package cf.bookmark.web.member.dao.vo;

import javax.persistence.*; //**
import java.util.Date;

@Entity
@Table(name = "MEMBER", 
	   // uniqueConstraints : DDL 생성시에 유니크 제약조건을 만든다. name과 age를 묶어서 unique
	   // catalog, schema : 데이터베이스 catalog, schema 이름
       uniqueConstraints = { @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = { "NAME", "AGE" }) })
public class Member {

	@Id
	@Column(name = "ID")
	private String id;
	
	/*
	  	기본키 할당 방식
	  	1) 직접 할당 : Id 
	  			member.setId("Id1"); // em.persist()로 엔티티를 저장하기 전에 애플리케이션에 직접 기본키를 할당
	  			
	  	2) IDENTITY 전략 : DB에서 직접 증감시켜주는 옵션 사용 (MySQL, PostgreSQL, SQL Server, DB2)
	  			@Id
				@GeneratedValue(strategy = GeneratinType.IDENTITY)
				private String id;
				
				Member member = new Member();
				em.persist(member); // IDENTITY 전략은 엔티티가 영속 상태가되려면 식별자가 반드기 필요하기 때문에 em.persist()를 호출하는 즉시 insert 쿼리가 데이터베이스에 전달된다. 
									// 인서트 후에 기본키 값을 조회할 수 있다. (JDBC3의 Statement.getGeneratedKeys()를 사용하면 동시에 기본키 값을 얻을 수 있다.)

	  	3) SEQUENCE 전략  : (Oracle, PostgreSQL, DB2, H2)
	  	
		   @Entity
		   @SequenceGenerator(name = "MEMBER_Generator", 
		   					  sequenceName = "MEMBER_SEQ",  // 시퀀스 이름	
		       				   initialValue = 1, allocationSize = 1)
		   public class Member {
		   		@Id
				@GeneratedValue(strategy = GeneratinType.SEQUENCE,
							    generator = "MEMBER_Generator")
				private String id;
		   }
			
			Member member = new Member();
			em.persist(member); // IDENTITY 전략과는 다르게 시퀀스 조회 후 영속성 컨텍스트에 저장 후 플러시(db 저장)
			
		4) TABLE 전략
			i) 먼저 키 생성 용도로 사용할 테이블을 만들어야 한다.
		
			create table TABLE_SEQUENCE {
				sequence_name	varchar(255) not null, // MEMBER_SEQ
				next_val bigint, // 초기화 하지 않아도 자동 초기화
				primary key (sequence_name)
			}
			
		   @Entity
		   @TableGenerator(name = "MEMBER_SEQ_GENERATOR", 
		   					  table = "TABLE_SEQUENCE",  // 시퀀스 이름	
		       				  pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
		       				  // valueColumnName 시퀀스 값 컬럼명 (next_val)
		       				  // pkColumnName 시퀀스 컬러명 (sequence_name)
		       				  // uniqueConstraints(DDL) : 유니크 제약 조건 설정
		       				  // TABLE 전략은 값을 조회하면서 SELECT 쿼리를 사용하고 다음 값으로 증가시키기 위해 UPDATE 쿼리를  사용한다.
		       				  // 이 전략은 SEQUENCE 전략과 비교해서 데이터베이스와 한번 더 통신한다는 단점이 있다.
		       				  // @TableGenerator.allocationSize를 사용하면 된다.
		   public class Member {
		   
		   		@Id
				@GeneratedValue(strategy = GeneratinType.TABLE,
							    generator = "MEMBER_SEQ_GENERATOR")
				private Long id;
		   }

		5) AUTO 전략 : 선택한 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 중 하나를 자동으로 선택
		        	   데이터베이스를 변경해도 코드를 수정할 필요가 없다는 장점이 있다. (개발 단게나 프로토타입시 사용)
		        	 AUTO를 사용할 때 SEQUENCE나 TABLE 전략이 선택되면 시퀀스나 키 생성용 테이블을 미리 만들어두어야 한다.  
		
			public class Member {
		   
		   		@Id
				@GeneratedValue(strategy = GeneratinType.AUTO)
				private Long id;
		   }
	 */
	

	@Column(name = "NAME", nullable = false, length = 10) // nullable = false : not null 제약조건 추가
	
	// unique = true : 한 컬럼에 간단히 유니크 제약조건을 사용할때
	
	// columnDefinition = "varhcar(100) default 'EMPTY'" : 컬럼정보를 직접 줄 수 있다.
	
	// @Column(precision = 10,  scale = 2) : BigDecimal 타입에서 사용, precision : 전체 자리수 / scale : 소수의 자리숫, double/float에는 적용되지 않는다.
    // private BigDecimal cal; 
	
	// @Column(name = "NAME") 
	private String username;

	private Integer age;

	
	// EnumType.ORDINAL : 0, 1 순서대로 (이건 주의, 새로 추가시 순번이 변경되기 때문이다.)
	// EnumType.STRING  : 'ADMIN', 'USER' 문자대로 테이터 베이스에 저장
	@Enumerated(EnumType.STRING) // ENUM 타입 매핑
	private RoleType roleType;

	@Temporal(TemporalType.TIMESTAMP) // 자바의 날짜 타입
	private Date createdDate;

	// @Temporal(TemporalType.DATE) :: 2013-10-11 (date)
	// @Temporal(TemporalType.TIME) :: 11:11:11 (time) 
	@Temporal(TemporalType.TIMESTAMP) // 2013-10-11 11:11:11
	private Date lastModifiedDate;
	
	@Lob // 회원을 설명하는 필드는 길이 제한이 없다. CLOB 타입으로 저장해야 한다. (BLOB, CLOB 매핑), 매핑값이 문자면 CLOB로, 나머지는 BLOB로 매핑 
	private String description;

	@Transient // 특정필드를 매핑하지 않는다.
	private String temp;

	
	// @Access : 테이블, 컬럼에 적용할 수 있다.
	// @Access(AccessType.FIELD)    : 필드에 직접 접근, private도 가능 
	// @Access(AccessType.PROPERTY) : 접근자를 통한 접근
	
	// Getter, Setter

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}
