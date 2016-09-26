package com.wangguofeng1923.ignite.examples.domain;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class PersonKey {
    // Person ID used to identify a person.
    private long personId;
 
    // Company ID which will be used for affinity.
    @AffinityKeyMapped
    private long companyId;
	public PersonKey() {
		
	}
    
	public PersonKey(long personId, long companyId) {
		super();
		this.personId = personId;
		this.companyId = companyId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + (int) (personId ^ (personId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonKey other = (PersonKey) obj;
		if (companyId != other.companyId)
			return false;
		if (personId != other.personId)
			return false;
		return true;
	}
  
}