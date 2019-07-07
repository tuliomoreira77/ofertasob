package com.mercado.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatJarInputStream;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import conexoes.ProdutoDao;
import objetos.Produto;
 
@Controller
public class MercadoControler {
	
	@RequestMapping("/search")
	public ModelAndView retornaLista(String query, Model model)
	{
		if(query != null)
		{
			List<Produto> produtos = new ProdutoDao().buscaProdutos(query);
			//List<Produto> produtosExibir = Utilidades.buscaProdutos(query, produtos);
			model.addAttribute("produtos", produtos);
		}
		return new ModelAndView("search");
	}
	

	@RequestMapping("/like")
	public void votaLike(int id, HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = Utilidades.getCookie(request, Integer.toString(id));
		if(cookie == null || !cookie.getValue().equals("like")) {
			new ProdutoDao().vota(id, true);
			cookie = new Cookie(Integer.toString(id), "like");
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			response.setStatus(200);
			return;
		}
		response.setStatus(202);
	}
	

	@RequestMapping("/dislike")
	public void votaDislike(int id, HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = Utilidades.getCookie(request, Integer.toString(id));
		if(cookie == null || !cookie.getValue().equals("dislike")) {
			new ProdutoDao().vota(id, false);
			cookie = new Cookie(Integer.toString(id), "dislike");
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			response.setStatus(200);
			return;
		}
		response.setStatus(202);
	}
	
	@ResponseBody
	@RequestMapping("/sendoferta")
	public void sendOferta(Produto produto)
	{
		String from = "contato@automaticasoftware.com.br";
		String fromName = "Tulio";
		String to = "tuliomoreira@automaticasoftware.com.br";
		String userName = "contato@automaticasoftware.com.br";
		String password = "130493tmmt";
		String host = "smtp.automaticasoftware.com.br";
		int port = 587;
		
		String content = produto.getNome() + "<br>"
						+produto.getPreco() +"<br>"
						+produto.getQuantidade() + "<br>"
						+produto.getMercado();
		
		
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", port); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	Session session = Session.getDefaultInstance(props);
    	MimeMessage msg = new MimeMessage(session);
    	
        try {
			msg.setFrom(new InternetAddress(from,fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(produto.getNome());
			msg.setContent(content,"text/html");
			Transport transport = session.getTransport();
			transport.connect(host, userName, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
