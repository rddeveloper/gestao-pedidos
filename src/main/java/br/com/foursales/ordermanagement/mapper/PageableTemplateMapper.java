package br.com.foursales.ordermanagement.mapper;


import br.com.foursales.ordermanagement.model.dto.PageableDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PageableTemplateMapper {

	default <T> PageableDTO pageToPageable(Page<T> page) {
		PageableDTO pageableDTO = new PageableDTO();
		BeanUtils.copyProperties(page.getPageable(), pageableDTO);
		pageableDTO.setLimit(page.getPageable().getPageSize());
		pageableDTO.setPageElements(page.getNumberOfElements());
		pageableDTO.setTotalElements(page.getTotalElements());
		pageableDTO.setTotalPages(page.getTotalPages());
		return pageableDTO;
	}
}
