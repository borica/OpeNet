package com.br.opet.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.digest.Crypt;

import com.br.opet.controller.base.BaseController;
import com.br.opet.dao.CursoDAO;
import com.br.opet.domain.dto.GoogleTokenDTO;
import com.br.opet.domain.entity.Curso;
import com.br.opet.domain.entity.Usuario;
import com.br.opet.domain.enumerator.LoginMessages;
import com.br.opet.service.GoogleOauthService;
import com.br.opet.service.LoginService;
import com.google.common.base.Strings;

@SessionScoped
@ManagedBean
public class LoginController extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String TAG = LoginController.class.getName() + ": ";
	
	@EJB
	private LoginService loginBusiness;
	
	@EJB
	private GoogleOauthService googleOauthBusiness;
	
	@EJB
	private CursoDAO cursoDAO;
	
	private String username;
	private String password;
	private String googleLoginUrl;
	
	@PostConstruct
	public void init() {
		try {
			this.googleLoginUrl = googleOauthBusiness.getGoogleOauthLink();
			cleanFields();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void checkForGCode() {
		try {
			logger.info(TAG + "Realizando verifica��o para codigo de retorno da API Google.");
			String gCode = (String) getSessionParameter("code");
			GoogleTokenDTO googleTokenDTO = (GoogleTokenDTO) getSessionAttribute("googleTokenDTO");
			
			if(!Strings.isNullOrEmpty(gCode)){
				googleTokenDTO = googleOauthBusiness.getGoogleTokenByAuthCode(gCode);
			}
			if(googleTokenDTO != null) {
				Usuario gUser = googleOauthBusiness.getGoogleUserAsUser(googleTokenDTO);
				gUser.setGoogleTokenDTO(googleTokenDTO);
				gUser.setLastLogin(new Date());
				gUser.setGoogleUser(true);
				
				setSessionAttribute("loggedUser", gUser);
				
				contextRedirect(PAGE_DASHBOARD);
				logger.info(TAG + "Tentativa de login com Google foi bem sucedida.");
			}
			if(!Strings.isNullOrEmpty(gCode) && googleTokenDTO == null){
				addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_GOOGLE_ERROR.getMessage());
				logger.warn(TAG + "Tentativa de login com Google falhou.");
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_GOOGLE_ERROR.getMessage());
			logger.error(e.getMessage());
		}
	}
	
	public void login() {
		try {
			
			List<String> cursosDesc = new ArrayList<String>();
			List<Curso> listCursos = new ArrayList<Curso>();
			
			cursosDesc.add("Administra��o");
			cursosDesc.add("Administra��o P�blica");
			cursosDesc.add("Agroecologia");
			cursosDesc.add("Agroneg�cio");
			cursosDesc.add("Agronomia");
			cursosDesc.add("An�lise de Sistemas");
			cursosDesc.add("Antropologia");
			cursosDesc.add("Arquitetura e Urbanismo");
			cursosDesc.add("Arquivologia");
			cursosDesc.add("Artes");
			cursosDesc.add("Artes C�nicas");
			cursosDesc.add("Astronomia");
			cursosDesc.add("Biblioteconomia");
			cursosDesc.add("Biologia");
			cursosDesc.add("Biomedicina");
			cursosDesc.add("Bioqu�mica");
			cursosDesc.add("Canto");
			cursosDesc.add("Cenografia");
			cursosDesc.add("Ci�ncia da Computa��o");
			cursosDesc.add("Ci�ncias Biol�gicas");
			cursosDesc.add("Ci�ncias Cont�beis");
			cursosDesc.add("Ci�ncias Econ�micas");
			cursosDesc.add("Ci�ncias Sociais");
			cursosDesc.add("Cinema e Audiovisual");
			cursosDesc.add("Composi��o e Reg�ncia");
			cursosDesc.add("Computa��o");
			cursosDesc.add("Comunica��o e Marketing");
			cursosDesc.add("Comunica��o Social");
			cursosDesc.add("Desenho Industrial");
			cursosDesc.add("Design");
			cursosDesc.add("Design de Ambientes");
			cursosDesc.add("Design de Games");
			cursosDesc.add("Design de Interiores");
			cursosDesc.add("Design de Moda");
			cursosDesc.add("Design de Produto");
			cursosDesc.add("Design Digital");
			cursosDesc.add("Design Gr�fico");
			cursosDesc.add("Dire��o");
			cursosDesc.add("Direito");
			cursosDesc.add("Educa��o F�sica");
			cursosDesc.add("Enfermagem");
			cursosDesc.add("Engenharia Ac�stica");
			cursosDesc.add("Engenharia Aeroespacial");
			cursosDesc.add("Engenharia Aeron�utica");
			cursosDesc.add("Engenharia Agr�cola");
			cursosDesc.add("Engenharia Agroindustrial");
			cursosDesc.add("Engenharia Agron�mica");
			cursosDesc.add("Engenharia Ambiental");
			cursosDesc.add("Engenharia Automotiva");
			cursosDesc.add("Engenharia Bioenerg�tica");
			cursosDesc.add("Engenharia Biom�dica");
			cursosDesc.add("Engenharia Bioqu�mica");
			cursosDesc.add("Engenharia Biotecnol�gica");
			cursosDesc.add("Engenharia Cartogr�fica");
			cursosDesc.add("Engenharia Civil");
			cursosDesc.add("Engenharia da Computa��o");
			cursosDesc.add("Engenharia da Mobilidade");
			cursosDesc.add("Engenharia de Agrimensura");
			cursosDesc.add("Engenharia de Agroneg�cios");
			cursosDesc.add("Engenharia de Alimentos");
			cursosDesc.add("Engenharia de Aquicultura");
			cursosDesc.add("Engenharia de Automa��o");
			cursosDesc.add("Engenharia de Bioprocessos");
			cursosDesc.add("Engenharia de Biossistemas");
			cursosDesc.add("Engenharia de Biotecnologia");
			cursosDesc.add("Engenharia de Energia");
			cursosDesc.add("Engenharia de Gest�o");
			cursosDesc.add("Engenharia de Informa��o");
			cursosDesc.add("Engenharia de Instrumenta��o, Automa��o e Rob�tica");
			cursosDesc.add("Engenharia de Manufatura");
			cursosDesc.add("Engenharia de Materiais");
			cursosDesc.add("Engenharia de Minas");
			cursosDesc.add("Engenharia de Pesca");
			cursosDesc.add("Engenharia de Petr�leo");
			cursosDesc.add("Engenharia de Produ��o");
			cursosDesc.add("Engenharia de Recursos H�dricos");
			cursosDesc.add("Engenharia de Sa�de e Seguran�a");
			cursosDesc.add("Engenharia de Sistemas");
			cursosDesc.add("Engenharia de Software");
			cursosDesc.add("Engenharia de Telecomunica��es");
			cursosDesc.add("Engenharia de Transporte e Log�stica");
			cursosDesc.add("Engenharia El�trica");
			cursosDesc.add("Engenharia Eletr�nica");
			cursosDesc.add("Engenharia em Sistemas Digitais");
			cursosDesc.add("Engenharia Ferrovi�ria e Metrovi�ria");
			cursosDesc.add("Engenharia F�sica");
			cursosDesc.add("Engenharia Florestal");
			cursosDesc.add("Engenharia Geol�gica");
			cursosDesc.add("Engenharia H�drica");
			cursosDesc.add("Engenharia Industrial");
			cursosDesc.add("Engenharia Mec�nica");
			cursosDesc.add("Engenharia Mecatr�nica");
			cursosDesc.add("Engenharia Metal�rgica");
			cursosDesc.add("Engenharia Naval");
			cursosDesc.add("Engenharia Qu�mica");
			cursosDesc.add("Engenharia T�xtil");
			cursosDesc.add("Estat�stica");
			cursosDesc.add("Farm�cia");
			cursosDesc.add("Filosofia");
			cursosDesc.add("F�sica");
			cursosDesc.add("Fisioterapia");
			cursosDesc.add("Fonoaudiologia");
			cursosDesc.add("Geografia");
			cursosDesc.add("Gest�o Ambiental");
			cursosDesc.add("Gest�o da Informa��o");
			cursosDesc.add("Gest�o de Pol�ticas P�blicas");
			cursosDesc.add("Gest�o de Servi�os de Sa�de");
			cursosDesc.add("Gest�o do Agroneg�cio");
			cursosDesc.add("Gest�o P�blica");
			cursosDesc.add("Hist�ria");
			cursosDesc.add("Hotelaria");
			cursosDesc.add("Jornalismo");
			cursosDesc.add("Letras");
			cursosDesc.add("Marketing");
			cursosDesc.add("Matem�tica");
			cursosDesc.add("Mec�nica Industrial");
			cursosDesc.add("Medicina");
			cursosDesc.add("Medicina Veterin�ria");
			cursosDesc.add("Moda");
			cursosDesc.add("M�sica");
			cursosDesc.add("Nutri��o");
			cursosDesc.add("Odontologia");
			cursosDesc.add("Pedagogia");
			cursosDesc.add("Pol�ticas P�blicas");
			cursosDesc.add("Propaganda e Marketing");
			cursosDesc.add("Psicologia");
			cursosDesc.add("Publicidade e Propaganda");
			cursosDesc.add("Qu�mica");
			cursosDesc.add("R�dio, TV e Internet");
			cursosDesc.add("Rela��es Internacionais");
			cursosDesc.add("Rela��es P�blicas");
			cursosDesc.add("Secretariado Executivo");
			cursosDesc.add("Servi�o Social");
			cursosDesc.add("Sistemas de Informa��o");
			cursosDesc.add("Tecnologias Digitais");
			cursosDesc.add("Teologia");
			cursosDesc.add("Terapia Ocupacional");
			cursosDesc.add("Tradutor e Int�rprete");
			cursosDesc.add("Turismo");
			cursosDesc.add("Zootecnia");
			cursosDesc.add("Artes");
			cursosDesc.add("Artes C�nicas");
			cursosDesc.add("Artes Pl�sticas");
			cursosDesc.add("Artes Visuais");
			cursosDesc.add("Biologia");
			cursosDesc.add("Ci�ncia da Computa��o");
			cursosDesc.add("Ci�ncias Agr�colas");
			cursosDesc.add("Ci�ncias da Natureza");
			cursosDesc.add("Ci�ncias Exatas");
			cursosDesc.add("Ci�ncias Sociais");
			cursosDesc.add("Computa��o");
			cursosDesc.add("Desenho e Pl�stica");
			cursosDesc.add("Educa��o do Campo");
			cursosDesc.add("Educa��o Especial");
			cursosDesc.add("Educa��o F�sica");
			cursosDesc.add("Enfermagem");
			cursosDesc.add("Filosofia");
			cursosDesc.add("F�sica");
			cursosDesc.add("Geografia");
			cursosDesc.add("Hist�ria");
			cursosDesc.add("Inform�tica");
			cursosDesc.add("Letras");
			cursosDesc.add("Matem�tica");
			cursosDesc.add("M�sica");
			cursosDesc.add("Pedagogia");
			cursosDesc.add("Programa Especial de Forma��o Pedag�gica");
			cursosDesc.add("Psicologia");
			cursosDesc.add("Qu�mica");
			cursosDesc.add("Segunda licenciatura");
			cursosDesc.add("Teatro");
			cursosDesc.add("Acupuntura");
			cursosDesc.add("Agrimensura");
			cursosDesc.add("Agrocomputa��o");
			cursosDesc.add("Agroecologia");
			cursosDesc.add("Agroind�stria");
			cursosDesc.add("Agroneg�cio");
			cursosDesc.add("Agropecu�ria");
			cursosDesc.add("Alimentos");
			cursosDesc.add("An�lise de Dados");
			cursosDesc.add("An�lise e Desenvolvimento de Sistemas");
			cursosDesc.add("Apicultura e Meliponicultura");
			cursosDesc.add("Aquicultura");
			cursosDesc.add("Arqueologia");
			cursosDesc.add("Arquitetura de Dados");
			cursosDesc.add("Artes do Espet�culo");
			cursosDesc.add("Artes e M�dias Digitais");
			cursosDesc.add("Assessoria Executiva Digital");
			cursosDesc.add("Atividades de Intelig�ncia e Gest�o de Sigilos");
			cursosDesc.add("Auditoria em Sa�de");
			cursosDesc.add("Automa��o de Escrit�rios e Secretariado");
			cursosDesc.add("Automa��o e Manufatura Digital");
			cursosDesc.add("Automa��o Industrial");
			cursosDesc.add("Banco de Dados");
			cursosDesc.add("Big Data e Intelig�ncia Anal�tica");
			cursosDesc.add("Big Data no Agroneg�cio");
			cursosDesc.add("Biocombust�veis");
			cursosDesc.add("Bioenergia");
			cursosDesc.add("Bioinform�tica");
			cursosDesc.add("Biotecnologia");
			cursosDesc.add("Blockchain e Criptografia Digital");
			cursosDesc.add("Cafeicultura");
			cursosDesc.add("Ciberseguran�a");
			cursosDesc.add("Ci�ncia de Dados");
			cursosDesc.add("Cinema e Audiovisual");
			cursosDesc.add("Coach Digital");
			cursosDesc.add("Coaching e Mentoring");
			cursosDesc.add("Coding");
			cursosDesc.add("Com�rcio Exterior");
			cursosDesc.add("Computa��o em Nuvem");
			cursosDesc.add("Comunica��o Assistiva");
			cursosDesc.add("Comunica��o Digital");
			cursosDesc.add("Comunica��o em Computa��o Gr�fica");
			cursosDesc.add("Comunica��o em M�dias Digitais");
			cursosDesc.add("Comunica��o Institucional");
			cursosDesc.add("Conserva��o e Restauro");
			cursosDesc.add("Constru��o Civil");
			cursosDesc.add("Constru��o de Edif�cios");
			cursosDesc.add("Constru��o Naval");
			cursosDesc.add("Controle Ambiental ");
			cursosDesc.add("Controle de Obras");
			cursosDesc.add("Cosmetologia e Est�tica");
			cursosDesc.add("Cozinha Contempor�nea");
			cursosDesc.add("Data Science");
			cursosDesc.add("Defesa Cibern�tica");
			cursosDesc.add("Defesa M�dica Hospitalar");
			cursosDesc.add("Desenho de Anima��o");
			cursosDesc.add("Desenvolvimento Back-End");
			cursosDesc.add("Desenvolvimento de Aplicativos para Dispositivos M�veis");
			cursosDesc.add("Desenvolvimento de Produtos Pl�sticos");
			cursosDesc.add("Desenvolvimento de Sistemas");
			cursosDesc.add("Desenvolvimento e Gest�o de Startups");
			cursosDesc.add("Desenvolvimento Mobile");
			cursosDesc.add("Desenvolvimento para Internet");
			cursosDesc.add("Desenvolvimento para Web");
			cursosDesc.add("Design");
			cursosDesc.add("Design Comercial");
			cursosDesc.add("Design de Anima��o");
			cursosDesc.add("Design de Aplica��es e Interfaces Digitais");
			cursosDesc.add("Design de Experi�ncia e de Servi�os");
			cursosDesc.add("Design de Games");
			cursosDesc.add("Design de Interiores");
			cursosDesc.add("Design de Moda");
			cursosDesc.add("Design de Produto");
			cursosDesc.add("Design Editorial");
			cursosDesc.add("Design Educacional");
			cursosDesc.add("Design Gr�fico");
			cursosDesc.add("Devops");
			cursosDesc.add("Digital Influencer");
			cursosDesc.add("Digital Security");
			cursosDesc.add("E-Commerce");
			cursosDesc.add("Educa��o e Processos de Trabalho: Alimenta��o Escolar");
			cursosDesc.add("Educador Social");
			cursosDesc.add("Eletr�nica Automotiva");
			cursosDesc.add("Eletr�nica Industrial");
			cursosDesc.add("Eletrot�cnica Industrial");
			cursosDesc.add("Embelezamento e Imagem Pessoal");
			cursosDesc.add("Empreendedorismo");
			cursosDesc.add("Energias Renov�veis");
			cursosDesc.add("Escrita Criativa");
			cursosDesc.add("Est�tica e Cosm�tica");
			cursosDesc.add("Estilismo");
			cursosDesc.add("Estradas");
			cursosDesc.add("Eventos");
			cursosDesc.add("Fabrica��o Mec�nica");
			cursosDesc.add("Filmmaker");
			cursosDesc.add("Finan�as, Blockchain e Criptomoedas");
			cursosDesc.add("Fitoterapia");
			cursosDesc.add("Fotografia");
			cursosDesc.add("Fruticultura");
			cursosDesc.add("Futebol");
			cursosDesc.add("Game Design");
			cursosDesc.add("Gastronomia");
			cursosDesc.add("Geoprocessamento");
			cursosDesc.add("Gerenciamento de Redes de Computadores");
			cursosDesc.add("Gerontologia");
			cursosDesc.add("Gest�o Ambiental");
			cursosDesc.add("Gest�o Comercial");
			cursosDesc.add("Gest�o Cultural");
			cursosDesc.add("Gest�o da Avalia��o");
			cursosDesc.add("Gest�o da Inova��o e Empreendedorismo Digital");
			cursosDesc.add("Gest�o da Produ��o Industrial");
			cursosDesc.add("Gest�o da Qualidade");
			cursosDesc.add("Gest�o da Seguran�a P�blica e Patrimonial");
			cursosDesc.add("Gest�o das Organiza��es do Terceiro Setor");
			cursosDesc.add("Gest�o das Rela��es Eletr�nicas");
			cursosDesc.add("Gest�o da Tecnologia da Informa��o");
			cursosDesc.add("Gest�o de Agroneg�cios");
			cursosDesc.add("Gest�o de Cidades Inteligentes");
			cursosDesc.add("Gest�o de Cloud Computing");
			cursosDesc.add("Gest�o de Cooperativas");
			cursosDesc.add("Gest�o de Energia e Efici�ncia Energ�tica");
			cursosDesc.add("Gest�o de Equinocultura");
			cursosDesc.add("Gest�o de Invent�rio Extrajudicial");
			cursosDesc.add("Gest�o de Investimentos");
			cursosDesc.add("Gest�o de Lojas e Pontos de Vendas");
			cursosDesc.add("Gest�o de Mercado de Capitais");
			cursosDesc.add("Gest�o de Micro e Pequenas Empresas");
			cursosDesc.add("Gest�o de Neg�cios");
			cursosDesc.add("Gest�o de Pessoas");
			cursosDesc.add("Gest�o de Produ��o Industrial");
			cursosDesc.add("Gest�o de Qualidade na Sa�de");
			cursosDesc.add("Gest�o de Recursos H�dricos");
			cursosDesc.add("Gest�o de Recursos Humanos");
			cursosDesc.add("Gest�o de Representa��o Comercial");
			cursosDesc.add("Gest�o de Res�duos de Servi�os de Sa�de");
			cursosDesc.add("Gest�o de Sa�de P�blica");
			cursosDesc.add("Gest�o de Seguran�a Privada");
			cursosDesc.add("Gest�o de Seguros");
			cursosDesc.add("Gest�o de Servi�os Judici�rios e Notariais");
			cursosDesc.add("Gest�o Desportiva e de Lazer");
			cursosDesc.add("Gest�o de Telecomunica��es");
			cursosDesc.add("Gest�o de Tr�nsito");
			cursosDesc.add("Gest�o de Turismo");
			cursosDesc.add("Gest�o Empresarial");
			cursosDesc.add("Gest�o em Servi�os");
			cursosDesc.add("Gest�o Financeira");
			cursosDesc.add("Gest�o Hospitalar");
			cursosDesc.add("Gest�o Portu�ria");
			cursosDesc.add("Gest�o P�blica");
			cursosDesc.add("Gest�o Tribut�ria");
			cursosDesc.add("Horticultura");
			cursosDesc.add("Hotelaria");
			cursosDesc.add("Inform�tica");
			cursosDesc.add("Inform�tica para Neg�cios");
			cursosDesc.add("Instala��es El�tricas");
			cursosDesc.add("Instrumenta��o Cir�rgica");
			cursosDesc.add("Intelig�ncia Artificial");
			cursosDesc.add("Interiores e Decora��es");
			cursosDesc.add("Internet das Coisas");
			cursosDesc.add("Investiga��o e Per�cia Criminal");
			cursosDesc.add("Irriga��o e Drenagem");
			cursosDesc.add("Jogos Digitais");
			cursosDesc.add("Latic�nios");
			cursosDesc.add("Log�stica");
			cursosDesc.add("Luteria");
			cursosDesc.add("Manufatura Avan�ada");
			cursosDesc.add("Manuten��o de Aeronaves");
			cursosDesc.add("Manuten��o Industrial");
			cursosDesc.add("Marketing");
			cursosDesc.add("Massoterapia");
			cursosDesc.add("Mec�nica Automobil�stica");
			cursosDesc.add("Mec�nica de Precis�o");
			cursosDesc.add("Mec�nica");
			cursosDesc.add("Mecatr�nica Automotiva");
			cursosDesc.add("Mecatr�nica Industrial");
			cursosDesc.add("Media��o");
			cursosDesc.add("Microeletr�nica");
			cursosDesc.add("M�dias Sociais");
			cursosDesc.add("Minera��o");
			cursosDesc.add("Minist�rio Pastoral");
			cursosDesc.add("Moda");
			cursosDesc.add("Multidisciplinar em Depend�ncia Qu�mica");
			cursosDesc.add("Neg�cios Digitais");
			cursosDesc.add("Neg�cios Imobili�rios");
			cursosDesc.add("Oft�lmica");
			cursosDesc.add("�ptica e Optometria");
			cursosDesc.add("Paisagismo");
			cursosDesc.add("Papel e Celulose");
			cursosDesc.add("Paramedicina");
			cursosDesc.add("Petr�leo e G�s");
			cursosDesc.add("Pilotagem Profissional de Aeronaves");
			cursosDesc.add("Planejamento Log�stico de Cargas");
			cursosDesc.add("Podologia");
			cursosDesc.add("Pol�meros");
			cursosDesc.add("Pol�tica e Gest�o Cultural");
			cursosDesc.add("Pol�ticas e Estrat�gicas P�blicas");
			cursosDesc.add("Pr�ticas Integrativas e Complementares");
			cursosDesc.add("Processamento de Dados");
			cursosDesc.add("Processos Ambientais");
			cursosDesc.add("Processos Escolares");
			cursosDesc.add("Processos Gerenciais");
			cursosDesc.add("Processos Metal�rgicos");
			cursosDesc.add("Processos Qu�micos");
			cursosDesc.add("Produ��o Agr�cola");
			cursosDesc.add("Produ��o Agropecu�ria");
			cursosDesc.add("Produ��o Audiovisual");
			cursosDesc.add("Produ��o Cervejeira");
			cursosDesc.add("Produ��o Cultural");
			cursosDesc.add("Produ��o de Cacau e Chocolate");
			cursosDesc.add("Produ��o de Cacha�a");
			cursosDesc.add("Produ��o de F�rmacos");
			cursosDesc.add("Produ��o de Gr�os");
			cursosDesc.add("Produ��o de Pl�stico");
			cursosDesc.add("Produ��o Fonogr�fica");
			cursosDesc.add("Produ��o Gr�fica");
			cursosDesc.add("Produ��o Industrial");
			cursosDesc.add("Produ��o Leiteira");
			cursosDesc.add("Produ��o Multim�dia");
			cursosDesc.add("Produ��o Musical");
			cursosDesc.add("Produ��o Pesqueira");
			cursosDesc.add("Produ��o Publicit�ria");
			cursosDesc.add("Produ��o Sucroalcooleira");
			cursosDesc.add("Produ��o T�xtil");
			cursosDesc.add("Projeto de Estruturas Aeron�uticas");
			cursosDesc.add("Projetos Mec�nicos");
			cursosDesc.add("Qualidade de Vida na Contemporaneidade");
			cursosDesc.add("Quiropraxia");
			cursosDesc.add("Radiologia");
			cursosDesc.add("Redes de Computadores");
			cursosDesc.add("Refrigera��o e Climatiza��o");
			cursosDesc.add("Rochas Ornamentais");
			cursosDesc.add("Saneamento Ambiental");
			cursosDesc.add("Sa�de Coletiva");
			cursosDesc.add("Secretariado");
			cursosDesc.add("Seguran�a Alimentar");
			cursosDesc.add("Seguran�a no Trabalho");
			cursosDesc.add("Service Design");
			cursosDesc.add("Silvicultura");
			cursosDesc.add("Sistema de Informa��o");
			cursosDesc.add("Sistemas Automotivos");
			cursosDesc.add("Sistemas Biom�dicos");
			cursosDesc.add("Sistemas para Internet");
			cursosDesc.add("Soldagem");
			cursosDesc.add("Streaming Profissional");
			cursosDesc.add("Tecnologia da Informa��o");
			cursosDesc.add("Tecnologia Eletr�nica");
			cursosDesc.add("Tecnologia em Controle Ambiental");
			cursosDesc.add("Tecnologia Mec�nica");
			cursosDesc.add("Tecnologias Educacionais");
			cursosDesc.add("Telem�tica");
			cursosDesc.add("Terapias Integrativas e Complementares");
			cursosDesc.add("Toxicologia Ambiental");
			cursosDesc.add("Tr�nsito");
			cursosDesc.add("Transporte Terrestre");
			cursosDesc.add("Turismo");
			cursosDesc.add("Tutoria de Educa��o a Dist�ncia");
			cursosDesc.add("Varejo Digital");
			cursosDesc.add("Visagismo e Terapias Capilares");
			cursosDesc.add("Viticultura e Enologia");
			
			for (String string : cursosDesc) {
				Curso curso = new Curso();
				curso.setDescricao(string);
				curso.setActive("Y");
				listCursos.add(curso);
			}
			
			cursoDAO.salvarCursosBulk(listCursos);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loginDisabled() {
		logger.info(TAG + "Realizando tentativa de login.");
		try {
			if(validateLogin()) {
				Usuario loggedUser = new Usuario();
				loggedUser.setUsername(username);
				loggedUser.setLastLogin(new Date());
				setSessionAttribute("loggedUser", loggedUser);
				cleanFields();
				contextRedirect(PAGE_DASHBOARD);
				logger.info(TAG + "Tentativa bem sucedida.");
			} else {
				cleanFields();
				addMessage(FacesMessage.SEVERITY_ERROR, "Erro!", LoginMessages.LOGIN_VAZIO.getMessage());
				logger.warn(TAG + "Tentativa de login falhou.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void loginWithGoogle() {
		try {
			logger.info(TAG + "Redirecionando para pagina de login com Google.");
			externalRedirect(googleLoginUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void signUp() {
		try {
			logger.info(TAG + "Redirecionando para pagina de cadastro.");
			contextRedirect(PAGE_SIGNUP);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private boolean validateLogin() throws Exception {
		boolean isValid = true;

		if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
			isValid = false;
		}
		
		//Adds encryption layer
		password = Crypt.crypt(password);
		
		if (!loginBusiness.validCredentials(new Usuario(username, password, null))) {
			isValid = false;
		}

		return isValid;
	}

	private void cleanFields() {
		this.username = "";
		this.password = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
