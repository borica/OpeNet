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
			logger.info(TAG + "Realizando verificação para codigo de retorno da API Google.");
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
			
			cursosDesc.add("Administração");
			cursosDesc.add("Administração Pública");
			cursosDesc.add("Agroecologia");
			cursosDesc.add("Agronegócio");
			cursosDesc.add("Agronomia");
			cursosDesc.add("Análise de Sistemas");
			cursosDesc.add("Antropologia");
			cursosDesc.add("Arquitetura e Urbanismo");
			cursosDesc.add("Arquivologia");
			cursosDesc.add("Artes");
			cursosDesc.add("Artes Cênicas");
			cursosDesc.add("Astronomia");
			cursosDesc.add("Biblioteconomia");
			cursosDesc.add("Biologia");
			cursosDesc.add("Biomedicina");
			cursosDesc.add("Bioquímica");
			cursosDesc.add("Canto");
			cursosDesc.add("Cenografia");
			cursosDesc.add("Ciência da Computação");
			cursosDesc.add("Ciências Biológicas");
			cursosDesc.add("Ciências Contábeis");
			cursosDesc.add("Ciências Econômicas");
			cursosDesc.add("Ciências Sociais");
			cursosDesc.add("Cinema e Audiovisual");
			cursosDesc.add("Composição e Regência");
			cursosDesc.add("Computação");
			cursosDesc.add("Comunicação e Marketing");
			cursosDesc.add("Comunicação Social");
			cursosDesc.add("Desenho Industrial");
			cursosDesc.add("Design");
			cursosDesc.add("Design de Ambientes");
			cursosDesc.add("Design de Games");
			cursosDesc.add("Design de Interiores");
			cursosDesc.add("Design de Moda");
			cursosDesc.add("Design de Produto");
			cursosDesc.add("Design Digital");
			cursosDesc.add("Design Gráfico");
			cursosDesc.add("Direção");
			cursosDesc.add("Direito");
			cursosDesc.add("Educação Física");
			cursosDesc.add("Enfermagem");
			cursosDesc.add("Engenharia Acústica");
			cursosDesc.add("Engenharia Aeroespacial");
			cursosDesc.add("Engenharia Aeronáutica");
			cursosDesc.add("Engenharia Agrícola");
			cursosDesc.add("Engenharia Agroindustrial");
			cursosDesc.add("Engenharia Agronômica");
			cursosDesc.add("Engenharia Ambiental");
			cursosDesc.add("Engenharia Automotiva");
			cursosDesc.add("Engenharia Bioenergética");
			cursosDesc.add("Engenharia Biomédica");
			cursosDesc.add("Engenharia Bioquímica");
			cursosDesc.add("Engenharia Biotecnológica");
			cursosDesc.add("Engenharia Cartográfica");
			cursosDesc.add("Engenharia Civil");
			cursosDesc.add("Engenharia da Computação");
			cursosDesc.add("Engenharia da Mobilidade");
			cursosDesc.add("Engenharia de Agrimensura");
			cursosDesc.add("Engenharia de Agronegócios");
			cursosDesc.add("Engenharia de Alimentos");
			cursosDesc.add("Engenharia de Aquicultura");
			cursosDesc.add("Engenharia de Automação");
			cursosDesc.add("Engenharia de Bioprocessos");
			cursosDesc.add("Engenharia de Biossistemas");
			cursosDesc.add("Engenharia de Biotecnologia");
			cursosDesc.add("Engenharia de Energia");
			cursosDesc.add("Engenharia de Gestão");
			cursosDesc.add("Engenharia de Informação");
			cursosDesc.add("Engenharia de Instrumentação, Automação e Robótica");
			cursosDesc.add("Engenharia de Manufatura");
			cursosDesc.add("Engenharia de Materiais");
			cursosDesc.add("Engenharia de Minas");
			cursosDesc.add("Engenharia de Pesca");
			cursosDesc.add("Engenharia de Petróleo");
			cursosDesc.add("Engenharia de Produção");
			cursosDesc.add("Engenharia de Recursos Hídricos");
			cursosDesc.add("Engenharia de Saúde e Segurança");
			cursosDesc.add("Engenharia de Sistemas");
			cursosDesc.add("Engenharia de Software");
			cursosDesc.add("Engenharia de Telecomunicações");
			cursosDesc.add("Engenharia de Transporte e Logística");
			cursosDesc.add("Engenharia Elétrica");
			cursosDesc.add("Engenharia Eletrônica");
			cursosDesc.add("Engenharia em Sistemas Digitais");
			cursosDesc.add("Engenharia Ferroviária e Metroviária");
			cursosDesc.add("Engenharia Física");
			cursosDesc.add("Engenharia Florestal");
			cursosDesc.add("Engenharia Geológica");
			cursosDesc.add("Engenharia Hídrica");
			cursosDesc.add("Engenharia Industrial");
			cursosDesc.add("Engenharia Mecânica");
			cursosDesc.add("Engenharia Mecatrônica");
			cursosDesc.add("Engenharia Metalúrgica");
			cursosDesc.add("Engenharia Naval");
			cursosDesc.add("Engenharia Química");
			cursosDesc.add("Engenharia Têxtil");
			cursosDesc.add("Estatística");
			cursosDesc.add("Farmácia");
			cursosDesc.add("Filosofia");
			cursosDesc.add("Física");
			cursosDesc.add("Fisioterapia");
			cursosDesc.add("Fonoaudiologia");
			cursosDesc.add("Geografia");
			cursosDesc.add("Gestão Ambiental");
			cursosDesc.add("Gestão da Informação");
			cursosDesc.add("Gestão de Políticas Públicas");
			cursosDesc.add("Gestão de Serviços de Saúde");
			cursosDesc.add("Gestão do Agronegócio");
			cursosDesc.add("Gestão Pública");
			cursosDesc.add("História");
			cursosDesc.add("Hotelaria");
			cursosDesc.add("Jornalismo");
			cursosDesc.add("Letras");
			cursosDesc.add("Marketing");
			cursosDesc.add("Matemática");
			cursosDesc.add("Mecânica Industrial");
			cursosDesc.add("Medicina");
			cursosDesc.add("Medicina Veterinária");
			cursosDesc.add("Moda");
			cursosDesc.add("Música");
			cursosDesc.add("Nutrição");
			cursosDesc.add("Odontologia");
			cursosDesc.add("Pedagogia");
			cursosDesc.add("Políticas Públicas");
			cursosDesc.add("Propaganda e Marketing");
			cursosDesc.add("Psicologia");
			cursosDesc.add("Publicidade e Propaganda");
			cursosDesc.add("Química");
			cursosDesc.add("Rádio, TV e Internet");
			cursosDesc.add("Relações Internacionais");
			cursosDesc.add("Relações Públicas");
			cursosDesc.add("Secretariado Executivo");
			cursosDesc.add("Serviço Social");
			cursosDesc.add("Sistemas de Informação");
			cursosDesc.add("Tecnologias Digitais");
			cursosDesc.add("Teologia");
			cursosDesc.add("Terapia Ocupacional");
			cursosDesc.add("Tradutor e Intérprete");
			cursosDesc.add("Turismo");
			cursosDesc.add("Zootecnia");
			cursosDesc.add("Artes");
			cursosDesc.add("Artes Cênicas");
			cursosDesc.add("Artes Plásticas");
			cursosDesc.add("Artes Visuais");
			cursosDesc.add("Biologia");
			cursosDesc.add("Ciência da Computação");
			cursosDesc.add("Ciências Agrícolas");
			cursosDesc.add("Ciências da Natureza");
			cursosDesc.add("Ciências Exatas");
			cursosDesc.add("Ciências Sociais");
			cursosDesc.add("Computação");
			cursosDesc.add("Desenho e Plástica");
			cursosDesc.add("Educação do Campo");
			cursosDesc.add("Educação Especial");
			cursosDesc.add("Educação Física");
			cursosDesc.add("Enfermagem");
			cursosDesc.add("Filosofia");
			cursosDesc.add("Física");
			cursosDesc.add("Geografia");
			cursosDesc.add("História");
			cursosDesc.add("Informática");
			cursosDesc.add("Letras");
			cursosDesc.add("Matemática");
			cursosDesc.add("Música");
			cursosDesc.add("Pedagogia");
			cursosDesc.add("Programa Especial de Formação Pedagógica");
			cursosDesc.add("Psicologia");
			cursosDesc.add("Química");
			cursosDesc.add("Segunda licenciatura");
			cursosDesc.add("Teatro");
			cursosDesc.add("Acupuntura");
			cursosDesc.add("Agrimensura");
			cursosDesc.add("Agrocomputação");
			cursosDesc.add("Agroecologia");
			cursosDesc.add("Agroindústria");
			cursosDesc.add("Agronegócio");
			cursosDesc.add("Agropecuária");
			cursosDesc.add("Alimentos");
			cursosDesc.add("Análise de Dados");
			cursosDesc.add("Análise e Desenvolvimento de Sistemas");
			cursosDesc.add("Apicultura e Meliponicultura");
			cursosDesc.add("Aquicultura");
			cursosDesc.add("Arqueologia");
			cursosDesc.add("Arquitetura de Dados");
			cursosDesc.add("Artes do Espetáculo");
			cursosDesc.add("Artes e Mídias Digitais");
			cursosDesc.add("Assessoria Executiva Digital");
			cursosDesc.add("Atividades de Inteligência e Gestão de Sigilos");
			cursosDesc.add("Auditoria em Saúde");
			cursosDesc.add("Automação de Escritórios e Secretariado");
			cursosDesc.add("Automação e Manufatura Digital");
			cursosDesc.add("Automação Industrial");
			cursosDesc.add("Banco de Dados");
			cursosDesc.add("Big Data e Inteligência Analítica");
			cursosDesc.add("Big Data no Agronegócio");
			cursosDesc.add("Biocombustíveis");
			cursosDesc.add("Bioenergia");
			cursosDesc.add("Bioinformática");
			cursosDesc.add("Biotecnologia");
			cursosDesc.add("Blockchain e Criptografia Digital");
			cursosDesc.add("Cafeicultura");
			cursosDesc.add("Cibersegurança");
			cursosDesc.add("Ciência de Dados");
			cursosDesc.add("Cinema e Audiovisual");
			cursosDesc.add("Coach Digital");
			cursosDesc.add("Coaching e Mentoring");
			cursosDesc.add("Coding");
			cursosDesc.add("Comércio Exterior");
			cursosDesc.add("Computação em Nuvem");
			cursosDesc.add("Comunicação Assistiva");
			cursosDesc.add("Comunicação Digital");
			cursosDesc.add("Comunicação em Computação Gráfica");
			cursosDesc.add("Comunicação em Mídias Digitais");
			cursosDesc.add("Comunicação Institucional");
			cursosDesc.add("Conservação e Restauro");
			cursosDesc.add("Construção Civil");
			cursosDesc.add("Construção de Edifícios");
			cursosDesc.add("Construção Naval");
			cursosDesc.add("Controle Ambiental ");
			cursosDesc.add("Controle de Obras");
			cursosDesc.add("Cosmetologia e Estética");
			cursosDesc.add("Cozinha Contemporânea");
			cursosDesc.add("Data Science");
			cursosDesc.add("Defesa Cibernética");
			cursosDesc.add("Defesa Médica Hospitalar");
			cursosDesc.add("Desenho de Animação");
			cursosDesc.add("Desenvolvimento Back-End");
			cursosDesc.add("Desenvolvimento de Aplicativos para Dispositivos Móveis");
			cursosDesc.add("Desenvolvimento de Produtos Plásticos");
			cursosDesc.add("Desenvolvimento de Sistemas");
			cursosDesc.add("Desenvolvimento e Gestão de Startups");
			cursosDesc.add("Desenvolvimento Mobile");
			cursosDesc.add("Desenvolvimento para Internet");
			cursosDesc.add("Desenvolvimento para Web");
			cursosDesc.add("Design");
			cursosDesc.add("Design Comercial");
			cursosDesc.add("Design de Animação");
			cursosDesc.add("Design de Aplicações e Interfaces Digitais");
			cursosDesc.add("Design de Experiência e de Serviços");
			cursosDesc.add("Design de Games");
			cursosDesc.add("Design de Interiores");
			cursosDesc.add("Design de Moda");
			cursosDesc.add("Design de Produto");
			cursosDesc.add("Design Editorial");
			cursosDesc.add("Design Educacional");
			cursosDesc.add("Design Gráfico");
			cursosDesc.add("Devops");
			cursosDesc.add("Digital Influencer");
			cursosDesc.add("Digital Security");
			cursosDesc.add("E-Commerce");
			cursosDesc.add("Educação e Processos de Trabalho: Alimentação Escolar");
			cursosDesc.add("Educador Social");
			cursosDesc.add("Eletrônica Automotiva");
			cursosDesc.add("Eletrônica Industrial");
			cursosDesc.add("Eletrotécnica Industrial");
			cursosDesc.add("Embelezamento e Imagem Pessoal");
			cursosDesc.add("Empreendedorismo");
			cursosDesc.add("Energias Renováveis");
			cursosDesc.add("Escrita Criativa");
			cursosDesc.add("Estética e Cosmética");
			cursosDesc.add("Estilismo");
			cursosDesc.add("Estradas");
			cursosDesc.add("Eventos");
			cursosDesc.add("Fabricação Mecânica");
			cursosDesc.add("Filmmaker");
			cursosDesc.add("Finanças, Blockchain e Criptomoedas");
			cursosDesc.add("Fitoterapia");
			cursosDesc.add("Fotografia");
			cursosDesc.add("Fruticultura");
			cursosDesc.add("Futebol");
			cursosDesc.add("Game Design");
			cursosDesc.add("Gastronomia");
			cursosDesc.add("Geoprocessamento");
			cursosDesc.add("Gerenciamento de Redes de Computadores");
			cursosDesc.add("Gerontologia");
			cursosDesc.add("Gestão Ambiental");
			cursosDesc.add("Gestão Comercial");
			cursosDesc.add("Gestão Cultural");
			cursosDesc.add("Gestão da Avaliação");
			cursosDesc.add("Gestão da Inovação e Empreendedorismo Digital");
			cursosDesc.add("Gestão da Produção Industrial");
			cursosDesc.add("Gestão da Qualidade");
			cursosDesc.add("Gestão da Segurança Pública e Patrimonial");
			cursosDesc.add("Gestão das Organizações do Terceiro Setor");
			cursosDesc.add("Gestão das Relações Eletrônicas");
			cursosDesc.add("Gestão da Tecnologia da Informação");
			cursosDesc.add("Gestão de Agronegócios");
			cursosDesc.add("Gestão de Cidades Inteligentes");
			cursosDesc.add("Gestão de Cloud Computing");
			cursosDesc.add("Gestão de Cooperativas");
			cursosDesc.add("Gestão de Energia e Eficiência Energética");
			cursosDesc.add("Gestão de Equinocultura");
			cursosDesc.add("Gestão de Inventário Extrajudicial");
			cursosDesc.add("Gestão de Investimentos");
			cursosDesc.add("Gestão de Lojas e Pontos de Vendas");
			cursosDesc.add("Gestão de Mercado de Capitais");
			cursosDesc.add("Gestão de Micro e Pequenas Empresas");
			cursosDesc.add("Gestão de Negócios");
			cursosDesc.add("Gestão de Pessoas");
			cursosDesc.add("Gestão de Produção Industrial");
			cursosDesc.add("Gestão de Qualidade na Saúde");
			cursosDesc.add("Gestão de Recursos Hídricos");
			cursosDesc.add("Gestão de Recursos Humanos");
			cursosDesc.add("Gestão de Representação Comercial");
			cursosDesc.add("Gestão de Resíduos de Serviços de Saúde");
			cursosDesc.add("Gestão de Saúde Pública");
			cursosDesc.add("Gestão de Segurança Privada");
			cursosDesc.add("Gestão de Seguros");
			cursosDesc.add("Gestão de Serviços Judiciários e Notariais");
			cursosDesc.add("Gestão Desportiva e de Lazer");
			cursosDesc.add("Gestão de Telecomunicações");
			cursosDesc.add("Gestão de Trânsito");
			cursosDesc.add("Gestão de Turismo");
			cursosDesc.add("Gestão Empresarial");
			cursosDesc.add("Gestão em Serviços");
			cursosDesc.add("Gestão Financeira");
			cursosDesc.add("Gestão Hospitalar");
			cursosDesc.add("Gestão Portuária");
			cursosDesc.add("Gestão Pública");
			cursosDesc.add("Gestão Tributária");
			cursosDesc.add("Horticultura");
			cursosDesc.add("Hotelaria");
			cursosDesc.add("Informática");
			cursosDesc.add("Informática para Negócios");
			cursosDesc.add("Instalações Elétricas");
			cursosDesc.add("Instrumentação Cirúrgica");
			cursosDesc.add("Inteligência Artificial");
			cursosDesc.add("Interiores e Decorações");
			cursosDesc.add("Internet das Coisas");
			cursosDesc.add("Investigação e Perícia Criminal");
			cursosDesc.add("Irrigação e Drenagem");
			cursosDesc.add("Jogos Digitais");
			cursosDesc.add("Laticínios");
			cursosDesc.add("Logística");
			cursosDesc.add("Luteria");
			cursosDesc.add("Manufatura Avançada");
			cursosDesc.add("Manutenção de Aeronaves");
			cursosDesc.add("Manutenção Industrial");
			cursosDesc.add("Marketing");
			cursosDesc.add("Massoterapia");
			cursosDesc.add("Mecânica Automobilística");
			cursosDesc.add("Mecânica de Precisão");
			cursosDesc.add("Mecânica");
			cursosDesc.add("Mecatrônica Automotiva");
			cursosDesc.add("Mecatrônica Industrial");
			cursosDesc.add("Mediação");
			cursosDesc.add("Microeletrônica");
			cursosDesc.add("Mídias Sociais");
			cursosDesc.add("Mineração");
			cursosDesc.add("Ministério Pastoral");
			cursosDesc.add("Moda");
			cursosDesc.add("Multidisciplinar em Dependência Química");
			cursosDesc.add("Negócios Digitais");
			cursosDesc.add("Negócios Imobiliários");
			cursosDesc.add("Oftálmica");
			cursosDesc.add("Óptica e Optometria");
			cursosDesc.add("Paisagismo");
			cursosDesc.add("Papel e Celulose");
			cursosDesc.add("Paramedicina");
			cursosDesc.add("Petróleo e Gás");
			cursosDesc.add("Pilotagem Profissional de Aeronaves");
			cursosDesc.add("Planejamento Logístico de Cargas");
			cursosDesc.add("Podologia");
			cursosDesc.add("Polímeros");
			cursosDesc.add("Política e Gestão Cultural");
			cursosDesc.add("Políticas e Estratégicas Públicas");
			cursosDesc.add("Práticas Integrativas e Complementares");
			cursosDesc.add("Processamento de Dados");
			cursosDesc.add("Processos Ambientais");
			cursosDesc.add("Processos Escolares");
			cursosDesc.add("Processos Gerenciais");
			cursosDesc.add("Processos Metalúrgicos");
			cursosDesc.add("Processos Químicos");
			cursosDesc.add("Produção Agrícola");
			cursosDesc.add("Produção Agropecuária");
			cursosDesc.add("Produção Audiovisual");
			cursosDesc.add("Produção Cervejeira");
			cursosDesc.add("Produção Cultural");
			cursosDesc.add("Produção de Cacau e Chocolate");
			cursosDesc.add("Produção de Cachaça");
			cursosDesc.add("Produção de Fármacos");
			cursosDesc.add("Produção de Grãos");
			cursosDesc.add("Produção de Plástico");
			cursosDesc.add("Produção Fonográfica");
			cursosDesc.add("Produção Gráfica");
			cursosDesc.add("Produção Industrial");
			cursosDesc.add("Produção Leiteira");
			cursosDesc.add("Produção Multimídia");
			cursosDesc.add("Produção Musical");
			cursosDesc.add("Produção Pesqueira");
			cursosDesc.add("Produção Publicitária");
			cursosDesc.add("Produção Sucroalcooleira");
			cursosDesc.add("Produção Têxtil");
			cursosDesc.add("Projeto de Estruturas Aeronáuticas");
			cursosDesc.add("Projetos Mecânicos");
			cursosDesc.add("Qualidade de Vida na Contemporaneidade");
			cursosDesc.add("Quiropraxia");
			cursosDesc.add("Radiologia");
			cursosDesc.add("Redes de Computadores");
			cursosDesc.add("Refrigeração e Climatização");
			cursosDesc.add("Rochas Ornamentais");
			cursosDesc.add("Saneamento Ambiental");
			cursosDesc.add("Saúde Coletiva");
			cursosDesc.add("Secretariado");
			cursosDesc.add("Segurança Alimentar");
			cursosDesc.add("Segurança no Trabalho");
			cursosDesc.add("Service Design");
			cursosDesc.add("Silvicultura");
			cursosDesc.add("Sistema de Informação");
			cursosDesc.add("Sistemas Automotivos");
			cursosDesc.add("Sistemas Biomédicos");
			cursosDesc.add("Sistemas para Internet");
			cursosDesc.add("Soldagem");
			cursosDesc.add("Streaming Profissional");
			cursosDesc.add("Tecnologia da Informação");
			cursosDesc.add("Tecnologia Eletrônica");
			cursosDesc.add("Tecnologia em Controle Ambiental");
			cursosDesc.add("Tecnologia Mecânica");
			cursosDesc.add("Tecnologias Educacionais");
			cursosDesc.add("Telemática");
			cursosDesc.add("Terapias Integrativas e Complementares");
			cursosDesc.add("Toxicologia Ambiental");
			cursosDesc.add("Trânsito");
			cursosDesc.add("Transporte Terrestre");
			cursosDesc.add("Turismo");
			cursosDesc.add("Tutoria de Educação a Distância");
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
