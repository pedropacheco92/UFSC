package main;

import java.util.AbstractMap.SimpleEntry;

public class MiniMaxEntry extends SimpleEntry<Integer, Integer> implements Comparable<MiniMaxEntry> {

	private static final long serialVersionUID = 1L;

	public MiniMaxEntry(Integer key, Integer value) {
		super(key, value);
	}

	@Override
	public int compareTo(MiniMaxEntry o) {
		return Integer.compare(this.getValue(), o.getValue());
	}

}
