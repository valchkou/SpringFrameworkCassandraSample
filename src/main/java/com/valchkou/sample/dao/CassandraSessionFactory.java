package com.valchkou.sample.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * This class is responsible for opening Datastax Session to Cassandra Cluster.
 * And creating keyspace if it does not exists yet.
 */
@Repository
public class CassandraSessionFactory implements InitializingBean {

    @Value("${cassandra.node}")
    protected String node;
    
    @Value("${cassandra.keyspace}")
    protected String keyspace;
    
    protected Cluster cluster;
	protected Session session;
	
    /** only 1 thread is permitted to open connection */
    private void connect() {
        if (session == null) {
            cluster = Cluster.builder().addContactPoint(node).build();
            session = cluster.connect();
            session.execute("CREATE KEYSPACE IF NOT EXISTS "+ getKeyspace() +
                " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 }");
        }   
    }
	
    public Session getSession() {
        return session;
    }

	public void setSession(Session session) {
		this.session = session;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }
}
