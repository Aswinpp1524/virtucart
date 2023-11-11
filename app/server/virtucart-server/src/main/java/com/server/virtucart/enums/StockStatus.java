package com.server.virtucart.enums;

public enum StockStatus {
	IN_STOCK("in_stock"), OUT_OF_STOCK("out_of_stock");

	private final String value;

	StockStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
