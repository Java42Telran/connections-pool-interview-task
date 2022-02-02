package connections.service;

import java.util.HashMap;

import connections.dto.Connection;

public class ConnectionsPoolImpl implements ConnectionsPool {
	private static class Node {
		 Connection connection;
		 Node prev;
		 Node next;
		 Node(Connection connection) {
			this.connection = connection;
		}
	}
	private static class ConnectionsList {
		Node head;
		Node tail;
		
	}
	ConnectionsList list = new ConnectionsList();
	HashMap<Integer, Node> mapConnections = new HashMap<>();
	int connectionsPoolLimit;
	public ConnectionsPoolImpl(int limit) {
		this.connectionsPoolLimit = limit;
	}

	@Override
	public boolean addConnection(Connection connection) {
		if(mapConnections.containsKey(connection.getId())) {
			return false;
		}
		Node node = new Node(connection);
		addHead(node);
		if (connectionsPoolLimit == mapConnections.size()) {
			mapConnections.remove(list.tail.connection.getId());
			removeTail();
			
		}
		mapConnections.put(connection.getId(), node);
		
		return true;
		
	}

	private void removeTail() {
		Node newTail = list.tail.prev;
		newTail.next = null;
		list.tail = newTail;
		
		
	}

	private void addHead(Node node) {
		
		
		if(list.tail == null) {
			list.head = list.tail = node;
		} else {
			moveToHead(node);
		}
		
		
		
		
	}

	private void moveToHead(Node node) {
		node.next = list.head;
		node.prev = null;
		list.head.prev = node;
		list.head = node;
	}

	@Override
	public Connection getConnection(int id) {
		Node node = mapConnections.get(id);
		if (node == null) {
			return null;
		}
		Connection connection = node.connection;
		if (list.head != node) {
			removeNode(node);
			moveToHead(node);
		}
		return connection;
	}

	private void removeNode(Node node) {
		if (list.tail == node) {
			removeTail();
		} else {
			Node prev = node.prev;
			Node next = node.next;
			prev.next = next;
			next.prev = prev;
			
		}
		
	}

}
