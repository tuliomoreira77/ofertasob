package conexoes;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mercado.controller.Utilidades;

import objetos.*;
import sun.security.jca.GetInstance;

public class ProdutoDao {
	private Connection connection;

	public ProdutoDao() {
		this.connection	= new ConnectionFactory().getConnection();
	}
	
	public List<Produto> buscaProdutos(String query)
	{
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			
			List<String> palavrasChave = Utilidades.contaPalavras(query);
			String statement = "select * from produto where (";
			/*for(int i=0; i < palavrasChave.size(); i++)
			{
				statement = statement + "instr(nome,'" + palavrasChave.get(i) + "') ";
				if(i != palavrasChave.size() -1)
				{
					statement = statement + "and ";
				}
			}
			statement = statement + ") or (";*/
			for(int i=0; i< palavrasChave.size(); i++)
			{
				statement = statement + "instr(chaves,'" + palavrasChave.get(i) + "') ";
				if(i != palavrasChave.size() -1)
				{
					statement = statement + "and ";
				}
			}
			statement = statement + ") order by preco asc";
			
			PreparedStatement stmt = connection.prepareStatement(statement);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setPreco(rs.getDouble("preco"));
				p.setQuantidade(rs.getString("quantidade"));
				p.setMercado(rs.getString("mercado"));
				p.setVotos(rs.getInt("votos"));
				
				Calendar dataC = Calendar.getInstance();
				dataC.setTime(rs.getDate("dataCriacao"));
				p.setDataCriacao(dataC);
				
				Calendar dataE = Calendar.getInstance();
				dataE.setTime(rs.getDate("dataExpiracao"));
				p.setDataExpiracao(dataE);
				
				long daysBetween = ChronoUnit.DAYS.between(dataC.toInstant(), Calendar.getInstance().toInstant());
				p.setDias(daysBetween);
				
				
				long expiration = ChronoUnit.DAYS.between(Calendar.getInstance().toInstant(), dataE.toInstant());
				
				if(expiration >= 0)
					produtos.add(p);
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}
	
	public void vota(int id, boolean voto)
	{
		if(voto)
		{
			String statement = "UPDATE mercadobanco.produtos SET votos = votos+1 WHERE (id = ?)";
			try {
				
				PreparedStatement stmt = connection.prepareStatement(statement);
				stmt.setInt(1, id);
				stmt.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			String statement = "UPDATE mercadobanco.produtos SET votos = votos-1 WHERE (id = ?)";
			try {
				
				PreparedStatement stmt = connection.prepareStatement(statement);
				stmt.setInt(1, id);
				stmt.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
