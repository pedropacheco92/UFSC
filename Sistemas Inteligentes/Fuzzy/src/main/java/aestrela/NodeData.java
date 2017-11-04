package aestrela;

import java.util.Map;

final class NodeData<T> {

	private final T nodeId;
	private final Map<T, Double> heuristic;

	private double g; // g is distance from the source
	private double h; // h is the heuristic of destination.
	private double f; // f = g + h

	public NodeData(T nodeId, Map<T, Double> heuristic) {
		this.nodeId = nodeId;
		this.g = Double.MAX_VALUE;
		this.heuristic = heuristic;
	}

	public T getNodeId() {
		return this.nodeId;
	}

	public double getG() {
		return this.g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public void calcF(T destination) {
		this.h = this.heuristic.get(destination);
		this.f = this.g + this.h;
	}

	public double getH() {
		return this.h;
	}

	public double getF() {
		return this.f;
	}
}
