package br.com.foursales.ordermanagement.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageableUtil {
	
	private PageableUtil() {
	}
	
	public static String[] verifySortNew(String[] sort, String defaultField) {
		if(sort == null) {
			sort = new String[]{};
		}
		
		Object[] objectArray = Arrays.stream(sort).map(element -> {
			if(element.contains("_")) {
				return element.replace("_", "");
			}
			return element;
		}).toArray();
		sort = Arrays.copyOf(objectArray, objectArray.length, String[].class);
		return sort.length != 0 ? Arrays.stream(sort).toArray(String[]::new) : new String[] {defaultField};
	}

	public Sort customSortBuilder(String[] params) {
		List<Sort.Order> orders = new ArrayList<>();

		if(params == null) {
			params = new String[]{};
		}

		for (String string : params) {
			orders.add(
					string.charAt(0) == '-' ? new Sort.Order(Sort.Direction.DESC, string.substring(1))
							: new Sort.Order(Sort.Direction.ASC, string));
		}
		return Sort.by(orders);
	}

	public static Sort.Direction customSortBuilderDirection(String[] params){
		if(params == null) {
			params = new String[]{};
		}

		Sort.Direction direction = null;
		for(String string : params) {
			direction = string.charAt(0) == '-' ? Sort.Direction.DESC : Sort.Direction.ASC;
		}
		return direction != null ? direction : Sort.Direction.ASC;
	}

}
