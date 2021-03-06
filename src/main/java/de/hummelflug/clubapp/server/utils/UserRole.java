package de.hummelflug.clubapp.server.utils;

public enum UserRole {
	
	ADMIN(Constants.ADMIN_VALUE),
	BOARD(Constants.BOARD_VALUE),
	COACH(Constants.COACH_VALUE),
	DEP_HEAD(Constants.DEP_HEAD_VALUE),
	NO_MEMBER(Constants.NO_MEMBER_VALUE),
	ORGANIZER(Constants.ORGANIZER_VALUE),
	PLAYER(Constants.PLAYER_VALUE);
	
	private final String role;
	
	private UserRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return role;
	}
	
	public static class Constants {
		public static final String ADMIN_VALUE = "ADMIN";
		public static final String BOARD_VALUE = "BOARD";
		public static final String COACH_VALUE = "COACH";
		public static final String DEP_HEAD_VALUE = "DEP_HEAD";
		public static final String NO_MEMBER_VALUE = "NO_MEMBER";
		public static final String ORGANIZER_VALUE = "ORGANIZER";
		public static final String PLAYER_VALUE = "PLAYER";
	}
	
}
