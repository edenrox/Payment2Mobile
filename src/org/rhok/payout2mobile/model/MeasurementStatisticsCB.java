package org.rhok.payout2mobile.model;

public class MeasurementStatisticsCB
	implements MeasurementCallback {

	private double average;
	private int count;
	private double min;
	private double max;
	private double sum;
	
	public double getSum() { return sum; }
	public double getAverage() { return sum / count; } 
	public int getCount() { return count; }
	public double getMinimum() { return min; }
	public double getMaximum() { return max; }
	
	public MeasurementStatisticsCB() {
		average = 0;
		count = 0;
		sum = 0;
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
	}
	
	public void callback(Measurement item) {
		double cur = item.getMagnitude();
		min = Math.min(cur, min);
		max = Math.max(cur, max);
		sum += cur;
		count++;
	}
}
