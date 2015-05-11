package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	
	
	
	@Test
	public void criarSessaoDiariaQuandoComecaETerminaHoje() {
		Espetaculo esp = new Espetaculo();
		LocalDate hoje = new LocalDate();
		LocalTime agora = new LocalTime();
		
		List<Sessao> sessoes=esp.criaSessoes(hoje, hoje, agora, Periodicidade.DIARIA);
	
		Assert.assertEquals(1, sessoes.size());
		Assert.assertEquals(hoje.toDateTime(agora), sessoes.get(0).getInicio());
	}

	@Test
	public void criar3SessoesDiariasQuandoComecaHojeETerminaDepoisDeAmanha() {
		Espetaculo esp = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio.plusDays(2);
		LocalTime agora = new LocalTime();
		LocalDate proximo = inicio;
		
		List<Sessao> sessoes=esp.criaSessoes(inicio, fim, agora, Periodicidade.DIARIA);
	
		Assert.assertEquals(3, sessoes.size());
		
		for(Sessao sessao : sessoes)
		{
			Assert.assertEquals(proximo.toDateTime(agora), sessao.getInicio());
			proximo = proximo.plusDays(1);
		}
	}
	
	@Test
	public void criarUmaSessaoSemanalIniciaHojeETerminaAmanha() {
		Espetaculo esp = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate amanha = inicio.plusDays(1);
		LocalTime agora = new LocalTime();
		
		List<Sessao> sessoes=esp.criaSessoes(inicio, amanha, agora, Periodicidade.SEMANAL);
	
		Assert.assertEquals(1, sessoes.size());
		Assert.assertEquals(inicio.toDateTime(agora), sessoes.get(0).getInicio());
	}
}
