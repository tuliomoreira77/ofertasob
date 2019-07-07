package com.mercado.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import objetos.*;

public class Utilidades {
	
	public static List<Produto> buscaProdutos(String query, List<Produto> produtos)
	{
		List<Produto> produtosSelect;
		List<String> palavrasChave;
		HashMap<Produto, Double> hMap = new HashMap<Produto, Double>();
		query = query.toLowerCase();
		palavrasChave = contaPalavras(query);
		
		if(palavrasChave == null)
			return null;
		
		for(int i=0; i<produtos.size(); i++)
		{
			Produto p = produtos.get(i);
			String nomeP = p.getNome().toLowerCase();
			int n = 0;
			for(int j=0; j< palavrasChave.size(); j++) {
				String palavra = palavrasChave.get(j);
				if(nomeP.contains(palavra))
				{
					n++;
				}
			}
			if(n != 0)
				//hMap.put(p, n);	//ordenar por quantidade de palavras chave
				hMap.put(p, p.getPreco()); //ordenar por preço
		}
		
		if(hMap.isEmpty())
			return null;
		
		Map<Produto, Double> sortedMap = hMap.entrySet().stream()
			    .sorted(Entry.comparingByValue())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e1, LinkedHashMap::new));
		
		produtosSelect = new ArrayList<Produto>(sortedMap.keySet());
		
		return produtosSelect;
		
		//return produtos;
	}
	
	public static List<String> contaPalavras(String query)
	{
		int n = 0;
		String palavras = query;
		List<String> palavrasChave = new ArrayList<String>();
		
		if(query.charAt(0) == ' ')
			query = query.substring(1);
		if(query.charAt(query.length()-1) == ' ')
			query = query.substring(0, query.length()-2);
		if(query.isEmpty())
			return null;
		
		n++;
		while(query.indexOf(" ") != -1)
		{
			query = query.substring(query.indexOf(" ") + 1);
			n++;
		}
		
		//System.out.println(n);
		
		for(int i=0; i<n; i++)
		{
			String palavra;
			if(n >1) {
				int index = palavras.indexOf(" ");
				if(index != -1)
					palavra = palavras.substring(0, index);
				else
					palavra = palavras;
			}
			else {
				palavra = palavras;
			}
			palavrasChave.add(palavra);
			palavras = palavras.substring(palavras.indexOf(" ") + 1);
		}
		
		return palavrasChave;
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name)
	{
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals(name))
	            	return cookie;
	        }
	    }
	    return null;
	}
	
	private class ProdutoSort extends Produto
	{
		int nWords;
		
		public int getNWords()
		{
			return nWords;
		}
		
		public void setNWords(int nWords)
		{
			this.nWords = nWords;
		}
	}
}
