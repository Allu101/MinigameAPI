package com.allu.minigameapi.ranking;

public class RankedPlayer implements Comparable<RankedPlayer> {

	private final String uuid;
	private String name;
	private int value;
	
	public RankedPlayer(String uuid, String name, int value) {
		this.uuid = uuid;
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public int getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(RankedPlayer rp) {
		return getValue() - rp.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RankedPlayer that = (RankedPlayer) o;
		return uuid.equals(that.uuid);
	}
}
