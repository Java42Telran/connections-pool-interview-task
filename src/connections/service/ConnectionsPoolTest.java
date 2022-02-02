package connections.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connections.dto.Connection;

class ConnectionsPoolTest {
	int limit = 5;
ConnectionsPool pool;
	@BeforeEach
	void setUp() throws Exception {
		pool = new ConnectionsPoolImpl(limit);
		for (int i = 1; i <= limit; i++) {
			pool.addConnection(new Connection(i, "address", 8080));
		}
	}

	@Test
	void testAddConnection() {
		int id = limit + 1;
		Connection connection = new Connection(id, "address", 8080);
		assertTrue(pool.addConnection(connection));
		assertFalse(pool.addConnection(connection));
		assertNull(pool.getConnection(1));
		assertNotNull(pool.getConnection(id));
		
	}

	@Test
	void testGetConnection() {
		assertNotNull(pool.getConnection(1));
		for(int i = limit + 1; i < limit * 2; i++) {
			pool.addConnection(new Connection(i, "bbbbb", 10));
		}
		assertNull(pool.getConnection(limit));
		assertNotNull(pool.getConnection(1));
	}
	

}
