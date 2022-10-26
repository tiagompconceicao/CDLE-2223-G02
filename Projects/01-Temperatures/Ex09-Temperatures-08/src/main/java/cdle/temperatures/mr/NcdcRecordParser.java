package cdle.temperatures.mr;

import org.apache.hadoop.io.Text;

public class NcdcRecordParser {

	private static final int MISSING_TEMPERATURE = 9999;

	private String year;
	private int airTemperature;
	private String quality;

	public void parse(String record) {
		this.year = record.substring(15, 19);
		
		String airTemperatureString;
		
		// Remove leading plus sign as parseInt doesn't like them (pre-Java 7)
		if ( record.charAt(87)=='+' ) {
			airTemperatureString = record.substring(88, 92);
		}
		else {
			airTemperatureString = record.substring(87, 92);
		}
		this.airTemperature = Integer.parseInt(airTemperatureString);
		this.quality = record.substring(92, 93);
	}

	public void parse(Text record) {
		parse(record.toString());
	}

	public boolean isValidTemperature() {
		return this.airTemperature != MISSING_TEMPERATURE && this.quality.matches("[01459]");
	}

	public String getYear() {
		return this.year;
	}

	public int getAirTemperature() {
		return this.airTemperature;
	}
}