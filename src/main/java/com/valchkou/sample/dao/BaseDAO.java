package com.valchkou.sample.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.mapping.MappingSession;

/**
 * Spring Bean wrapper for MappingSession.
 * This class inherits the MappingSession api 
 * And initializes with Datastax Session and Keyspace.
 */
@Repository
public class BaseDAO extends MappingSession implements InitializingBean {

    @Autowired 
    protected CassandraSessionFactory sf;
	
	public CassandraSessionFactory getSessionFactory() {
		return sf;
	}

	public void setSessionFactory(CassandraSessionFactory sf) {
		this.sf = sf;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        keyspace = sf.getKeyspace();
        session = sf.getSession();
    }
	
}
