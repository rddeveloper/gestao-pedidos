package br.com.foursales.ordermanagement.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableDTO {

	private Integer limit;
	private Long offset;
	private Integer pageNumber;
	private Integer pageElements;
	private Integer totalPages;
	private Long totalElements;

}
