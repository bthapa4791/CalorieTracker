package com.spring.app.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;

@Entity
@NamedQueries({
    @NamedQuery(
            name = Authority.FIND_BY_AUTHORITY_ID,
            query = "select a from Authority a where id = :id"
    )
})
public class Authority {

	public static final String FIND_BY_AUTHORITY_ID = "authority.findById";
	
	@Id @GeneratedValue
	private Long id;

	@Column(name = "name", unique=true, nullable = false)
	private String name = UserAuthorityType.USER.getUserProfileType();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
