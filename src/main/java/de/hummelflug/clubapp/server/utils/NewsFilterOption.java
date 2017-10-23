package de.hummelflug.clubapp.server.utils;

public enum NewsFilterOption {
	All("ALL"), RECENT("RECENT");
	
	private final String option;
	
	private NewsFilterOption(String option) {
		this.option = option;
	}
	
	@Override
	public String toString() {
		return option;
	}
	
	public static NewsFilterOption fromString(String text) {
		for (NewsFilterOption newsFilterOption : NewsFilterOption.values()) {
			if (newsFilterOption.option.equalsIgnoreCase(text)) {
				return newsFilterOption;
			}
		}
		return null;
	}
}
