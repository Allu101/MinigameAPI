package com.allu.minigameapi.ranking;

public class RankedPlayer {

	private String uuid;
	private String name;
	private int value;
	
	public RankedPlayer(String uuid, String name, int value) {
		this.uuid = uuid;
		this.name = name;
		this.value = value;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
