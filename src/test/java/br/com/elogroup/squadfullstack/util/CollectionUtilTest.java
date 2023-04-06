package br.com.elogroup.squadfullstack.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.util.ArrayIterator;

class CollectionUtilTest {
	CollectionUtil<String> collectionUtil = new CollectionUtil<String>();
	private Iterable<String> iterableList;

	@BeforeEach
	void init() {
		
		String item1 = "Item 1";
		String item2 = "Item 2";
		iterableList = new ArrayIterator<>(new String[] {item1, item2});
		
	}
	
	@Test
	void deveRetornarListaComQuantidadeTest() {

		List<String> listFromIterable = collectionUtil.getListFromIterable(iterableList);
	
		assertEquals(2, listFromIterable.size(), "Erro ao converter os itens para lista");
	}

	@Test
	void deveRetornarListaValiziaSeParametroForNuloQuantidadeTest() {

		List<String> listFromIterable = collectionUtil.getListFromIterable(null);
	
		assertEquals(0, listFromIterable.size(), "Erro ao converter os itens para lista");
	}
	
	
	@Test
	void deveRetornarUmaInstanciaDeListTest() {
		 
		CollectionUtil<String> collectionUtil = new CollectionUtil<String>();
		
		List<String> listFromIterable = collectionUtil.getListFromIterable(iterableList);
		
		assertEquals(true, listFromIterable instanceof List, "A lista retornada não é do tipo esperado");
	}
	
	@Test
	void deveRetornarListaNaMesmaOrdemTest() {
		 
		CollectionUtil<String> collectionUtil = new CollectionUtil<String>();
		
		String item1 = "Item 1";
		String item2 = "Item 2";
		String item3 = "Item 3";
		Iterable<String> iterableList = new ArrayIterator<>(new String[] {item1, item2, item3});

		List<String> listFromIterable = collectionUtil.getListFromIterable(iterableList);
		
		assertEquals(listFromIterable.get(1), item2 , "A lista retornada Não foi criada na ordem correta");				
	}
	
}
