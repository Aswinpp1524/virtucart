package com.server.virtucart.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProductResponse {

	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long productId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Long> productsId;

	public ProductResponse() {

	}

	public ProductResponse(String message, Long productId, List<Long> productsId) {
		super();
		this.message = message;
		this.productId = productId;
		this.productsId = productsId;
	}

	public ProductResponse(String message, Long productId) {
		super();
		this.message = message;
		this.productId = productId;
	}

	public ProductResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<Long> getProductsId() {
		return productsId;
	}

	public void setProductsId(List<Long> productsId) {
		this.productsId = productsId;
	}

}
