package br.com.elogroup.squadfullstack.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtils <T> {

	public List<T> getListFromIterable(Iterable<T> iterable) {
		
		List<T> list = new ArrayList<>();
		if(iterable != null) {
			iterable.forEach(list::add);
		}
		return list;
	}
}
