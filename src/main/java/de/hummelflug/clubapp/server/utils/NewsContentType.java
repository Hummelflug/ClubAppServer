package de.hummelflug.clubapp.server.utils;

public enum NewsContentType {
	NEWS("NEWS"), VOTE("VOTE"), LOAN_NEWS("LOAN_NEWS");
	
	private final String type;
	
	private NewsContentType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public static NewsContentType fromString(String text) {
		for (NewsContentType newsContentType : NewsContentType.values()) {
			if (newsContentType.type.equalsIgnoreCase(text)) {
				return newsContentType;
			}
		}
		return null;
	}
}
