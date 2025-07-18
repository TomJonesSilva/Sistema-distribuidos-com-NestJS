package negocio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import exceptions.*;
import models.*;
import dados.IRepositorioGenerico;
import dados.RepositorioGenerico;

public class Controlador {

    private IRepositorioGenerico<Estudante> repositorioEstudante;
    private IRepositorioGenerico<Funcionario> repositorioFuncionario;
    private IRepositorioGenerico<TicketRefeicao> repositorioTicketRefeicao;
    private IRepositorioGenerico<CardapioSemanal> repositorioCardapioSemanal;
    private IRepositorioGenerico<TipoRefeicao> repositorioTipoRefeicao;
    private IRepositorioGenerico<OpcaoRefeicao> repositorioOpcaoRefeicao;
    
    private static Controlador instance;

    private Usuario usuario;

    private Controlador (){
        this.repositorioEstudante = new RepositorioGenerico<>();
        this.repositorioFuncionario = new RepositorioGenerico<>();
        this.repositorioTicketRefeicao = new RepositorioGenerico<>();
        this.repositorioCardapioSemanal = new RepositorioGenerico<>();
        this.repositorioTipoRefeicao = new RepositorioGenerico<>();
        this.repositorioOpcaoRefeicao = new RepositorioGenerico<>();
    }

    public static Controlador getInstance() {
        if (instance == null) {
            instance = new Controlador();
        }
        return instance;
    }


    public IRepositorioGenerico<Estudante> getRepositorioEstudante() {
        return repositorioEstudante;
    }

    public void setRepositorioEstudante(IRepositorioGenerico<Estudante> repositorioEstudante) {
        this.repositorioEstudante = repositorioEstudante;
    }

    public IRepositorioGenerico<Funcionario> getRepositorioFuncionario() {
        return repositorioFuncionario;
    }

    public void setRepositorioFuncionario(IRepositorioGenerico<Funcionario> repositorioFuncionario) {
        this.repositorioFuncionario = repositorioFuncionario;
    }

    public IRepositorioGenerico<TicketRefeicao> getRepositorioTicketRefeicao() {
        return repositorioTicketRefeicao;
    }

    public void setRepositorioTicketRefeicao(IRepositorioGenerico<TicketRefeicao> repositorioTicketRefeicao) {
        this.repositorioTicketRefeicao = repositorioTicketRefeicao;
    }
    
    public IRepositorioGenerico<CardapioSemanal> getRepositorioCardapioSemanal() {
        return repositorioCardapioSemanal;
    }
    
    public void setRepositorioCardapioSemanal(IRepositorioGenerico<CardapioSemanal> repositorioCardapioSemanal) {
        this.repositorioCardapioSemanal = repositorioCardapioSemanal;
    }

    public IRepositorioGenerico<TipoRefeicao> getRepositorioTipotRefeicao() {
        return repositorioTipoRefeicao;
    }
    
    public void setRepositorioTipoRefeicao(IRepositorioGenerico<TipoRefeicao> repositorioTipoRefeicao) {
        this.repositorioTipoRefeicao = repositorioTipoRefeicao;
    }
    
    public IRepositorioGenerico<OpcaoRefeicao> getRepositorioOpcaotRefeicao() {
        return repositorioOpcaoRefeicao;
    }
    
    public void setRepositorioOpcaoRefeicao(IRepositorioGenerico<OpcaoRefeicao> repositorioOpcaoRefeicao) {
        this.repositorioOpcaoRefeicao = repositorioOpcaoRefeicao;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Estudante> listarEstudantes(){
        return repositorioEstudante.listar();
    }

    public void removerEstudante(Estudante obj) throws ElementoNaoExisteException{
        repositorioEstudante.remover(obj);
    }

    public void atualizarEstudante(Estudante obj) throws ElementoNaoExisteException{
        repositorioEstudante.atualizar(obj);
    }

    public void inserirEstudante(Estudante obj) throws ElementoJaExisteException{
        repositorioEstudante.inserir(obj);
    }

    public List<Funcionario> listarFuncionarios(){
        return repositorioFuncionario.listar();
    }

    public void removerFuncionario(Funcionario obj) throws ElementoNaoExisteException{
        repositorioFuncionario.remover(obj);
    }

    public void atualizarFuncionario(String codigo,String nome,String cpf,  LocalDate dataNasc,String email, String senha,double salario, LocalDate dataAdmin) throws ElementoNaoExisteException,DataInvalidaException, ParametroVazioException{
        if(!nome.isEmpty() && !cpf.isEmpty() && !codigo.isEmpty()
                && !email.isEmpty() && !senha.isEmpty()&& !dataNasc.isAfter(LocalDate.now().minusYears(16))&& !dataAdmin.isAfter(LocalDate.now())) {
            Funcionario f = new Funcionario(codigo,nome,cpf,dataNasc,email,senha,salario,dataAdmin);
            repositorioFuncionario.atualizar(f);
        }
        else{
            if(nome.isEmpty()) {
                throw new ParametroVazioException("O nome");
            }
            if(cpf.isEmpty() ){
                throw new ParametroVazioException("O CPF");
            }
            if(codigo.isEmpty()){
                throw new ParametroVazioException("O Código");
            }
            if(email.isEmpty() ){
                throw new ParametroVazioException("O Email");
            }
            if(senha.isEmpty()){
                throw new ParametroVazioException("A senha");
            }
            if(dataNasc.isAfter(LocalDate.now().minusYears(16))){
                throw new DataInvalidaException(dataNasc);
            }
            if(dataAdmin.isAfter(LocalDate.now())){
                new DataInvalidaException(dataAdmin);
            }
        }
    }

    public void inserirFuncionario(Funcionario obj) throws ElementoJaExisteException {
        repositorioFuncionario.inserir(obj);
    }

    public List<TicketRefeicao> listarTicketRefeicao() {
        return repositorioTicketRefeicao.listar();
    }
    
    public void inserirTicket(TicketRefeicao obj) throws ElementoJaExisteException {
    	repositorioTicketRefeicao.inserir(obj);
    }
    
    public List<CardapioSemanal> listarCardapioSemanal(){
        return repositorioCardapioSemanal.listar();
    }
    
    public void inserirCardapioSemanal(LocalDate dataInicial,Map<DiasDaSemana,OpcaoRefeicao> cardapio ) throws ElementoJaExisteException, DataInvalidaException {
        if(dataInicial.getDayOfWeek().equals(DayOfWeek.MONDAY) && !dataInicial.isBefore(LocalDate.now())) {
            CardapioSemanal novo = new CardapioSemanal(dataInicial, cardapio);
            repositorioCardapioSemanal.inserir(novo);
        }
        else {
            throw new DataInvalidaException(dataInicial);
        }
    }
    
    public void removerCardapioSemanal(CardapioSemanal obj) throws ElementoNaoExisteException{
        repositorioCardapioSemanal.remover(obj);
    }
    
    public void atualizarCardapioSemanal(CardapioSemanal obj) throws ElementoNaoExisteException{
        repositorioCardapioSemanal.atualizar(obj);
    }
    
    public List<TipoRefeicao> listarTipoRefeicao(){
        return repositorioTipoRefeicao.listar();
    }
    
 
    
    
    public List<OpcaoRefeicao> listarOpcaoRefeicao(){
        return repositorioOpcaoRefeicao.listar();
    }
    
    public void inserirOpcaoRefeicao(OpcaoRefeicao obj) throws ElementoJaExisteException{
        repositorioOpcaoRefeicao.inserir(obj);
    }
    
    public void removerOpcaoRefeicao(OpcaoRefeicao obj) throws ElementoNaoExisteException{
        repositorioOpcaoRefeicao.remover(obj);
    }
    
    public void atualizarOpcaoRefeicao(OpcaoRefeicao obj) throws ElementoNaoExisteException{
        repositorioOpcaoRefeicao.atualizar(obj);
    }

    public Usuario validLogin(String cpf, String senha) throws LoginInvalidoException{
        Usuario pessoa = null;
        for (Usuario user: this.listarEstudantes()) {
            if(user.getCpf().equals(cpf)){
                pessoa = user;
            }
        }
        for (Usuario user: this.listarFuncionarios()) {
            if(user.getCpf().equals(cpf)){
                pessoa = user;
            }
        }
        if (pessoa == null){
            throw new LoginInvalidoException(1);
        }
        if (pessoa.getSenha().equals(senha)){
            return pessoa;
        } else {
            throw new LoginInvalidoException(2);
        }
    }

    
    // Recebe uma instancia de usuario e 
    //retorna uma lista com os tickes de almço que ele possui.
    public List<TicketRefeicao> listarTicketAlmocoUsuario(Usuario usuario){
    	List<TicketRefeicao> ticketsDoUsuario = new ArrayList<>(); 
		
    	for(int i=0;i<repositorioTicketRefeicao.listar().size();i++) {
    		TicketRefeicao u = repositorioTicketRefeicao.listar().get(i);
    		
			if(usuario.equals(u.getComprador())) {
				TipoRefeicao tipo=TipoRefeicao.valueOf("ALMOCO");
				
				if(u.getTipo().equals(tipo))
				    ticketsDoUsuario.add(u);
			}
    	}
    	
		return ticketsDoUsuario;
    	
    }

    public List<TicketRefeicao> listarTicketAlmocoNaoConsumido(Usuario usuario){
        List<TicketRefeicao> ticketsAlmoco=this.listarTicketAlmocoUsuario(usuario);
        List<TicketRefeicao> ticketsDisponiveis=new ArrayList<>();

        for(TicketRefeicao ticket:ticketsAlmoco){
            if(ticket.getDataConsumo().equals(LocalDate.of(1,1,1))){
                ticketsDisponiveis.add(ticket);
            }
        }

        return ticketsDisponiveis;

    }

    public List<TicketRefeicao> listarTicketJantarUsuario(Usuario usuario){
    	List<TicketRefeicao> ticketsDoUsuario = new ArrayList<>();
		
    	for(int i=0;i<repositorioTicketRefeicao.listar().size();i++) {
    		TicketRefeicao u = repositorioTicketRefeicao.listar().get(i);
    		
			if(usuario.equals(u.getComprador())) {
				TipoRefeicao tipo=TipoRefeicao.valueOf("JANTAR");
				
				if(u.getTipo().equals(tipo))
				    ticketsDoUsuario.add(u);
			}
    	}
    	
		return ticketsDoUsuario;
    	
    }

    public List<TicketRefeicao> listarTicketJantarNaoConsumido(Usuario usuario){
        List<TicketRefeicao> ticketsJantar=this.listarTicketJantarUsuario(usuario);
        List<TicketRefeicao> ticketsDisponiveis=new ArrayList<>();

        for(TicketRefeicao ticket:ticketsJantar){
            if(ticket.getDataConsumo().equals(LocalDate.of(1,1,1))){
                ticketsDisponiveis.add(ticket);
            }
        }

        return ticketsDisponiveis;

    }

    public String consumirRefeicao(Usuario user, TipoRefeicao tipo) throws NaoPossuiTicketDisponivelException {
        List<TicketRefeicao> tickets;
        if(tipo.equals(TipoRefeicao.ALMOCO)){
            tickets=listarTicketAlmocoNaoConsumido(user);
        } else{
            tickets=listarTicketJantarNaoConsumido(user);
        }
        String codConsumo;
        if(tickets.size()!=0){
            codConsumo=tickets.get(0).getCodigo();
            tickets.get(0).setDataConsumo(LocalDate.now());
        }
        else{
            if(tipo.equals(TipoRefeicao.ALMOCO))
                throw new NaoPossuiTicketDisponivelException (TipoRefeicao.ALMOCO);
            else
                throw new NaoPossuiTicketDisponivelException (TipoRefeicao.JANTAR);
        }
        return codConsumo;

    }

    public void comprarRefeicao (Usuario user, TipoRefeicao tipo) throws ElementoJaExisteException {
        TicketRefeicao novoTicket=new TicketRefeicao(LocalDate.now(),String.valueOf(new Random().nextInt(100000)),3.5,user,tipo);
        this.repositorioTicketRefeicao.inserir(novoTicket);

    }



    public OpcaoRefeicao inserirOpcoes(String opcao1,
                                       String opcao2, String vegetariano, String fastGrill, String suco, String sobremesa, TipoRefeicao tipo, DiasDaSemana dia) throws ParametroVazioException {
    	OpcaoRefeicao refeicoes=null;
    	if(!(opcao1.isEmpty()) && !opcao2.isEmpty() && !(vegetariano.isEmpty()) && !suco.isEmpty() && !sobremesa.isEmpty() && !fastGrill.isEmpty() ) {
    		 refeicoes=new OpcaoRefeicao(opcao1, opcao2, vegetariano, fastGrill, suco, sobremesa,tipo);
    	}else {
    		if(opcao1.isEmpty()) {
    			throw new ParametroVazioException("Prato principal 1 da "+dia.name().toLowerCase());
    		}
    		if(opcao2.isEmpty()) {
    			throw new ParametroVazioException("Prato principal 2 da "+dia.name().toLowerCase());
    		}
    		if(vegetariano.isEmpty()) {
    			throw new ParametroVazioException("Prato vegetariano da "+dia.name().toLowerCase());
    		}
    		if(suco.isEmpty()) {
    			throw new ParametroVazioException("O suco da "+dia.name().toLowerCase());
    		}
    		if(fastGrill.isEmpty()) {
    			throw new ParametroVazioException("O fast grill da "+dia.name().toLowerCase());
    		}
    		if(sobremesa.isEmpty()) {
    			throw new ParametroVazioException("A sobremesa da "+dia.name().toLowerCase());
    		}
    			
    	}
    
    	return refeicoes;
    }
    
    


    public Map<LocalDate,Integer> relatorioVendasAlmoco(LocalDate inicio, LocalDate fim) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            for (TicketRefeicao venda:this.repositorioTicketRefeicao.listar()) {
                if(!venda.getDataVenda().isBefore(inicio) && !venda.getDataVenda().isAfter(fim)){
                    if(count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("ALMOCO")) {
                        count.replace(venda.getDataVenda(), count.get(venda.getDataVenda()) + 1);
                    }
                    else if(!count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("ALMOCO")){
                        count.put(venda.getDataVenda(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }

    public Map<LocalDate,Integer> relatorioVendasJantar(LocalDate inicio, LocalDate fim) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            for (TicketRefeicao venda:this.repositorioTicketRefeicao.listar()) {
                if(!venda.getDataVenda().isBefore(inicio) && !venda.getDataVenda().isAfter(fim)){
                    if(count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("JANTAR")) {
                        count.replace(venda.getDataVenda(), count.get(venda.getDataVenda()) + 1);
                    }
                    else if(!count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("JANTAR")){
                        count.put(venda.getDataVenda(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    public Map<LocalDate,Integer> relatorioConsumoAlmoco(LocalDate inicio, LocalDate fim) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            List<TicketRefeicao> listaConsumos=this.repositorioTicketRefeicao.listar().stream().filter(t->t.getDataConsumo()!=null).toList();
            for (TicketRefeicao consumo:listaConsumos) {
                if(!consumo.getDataConsumo().isBefore(inicio) && !consumo.getDataConsumo().isAfter(fim)){
                    if(count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("ALMOCO")) {
                        count.replace(consumo.getDataConsumo(), count.get(consumo.getDataConsumo()) + 1);
                    }
                    else if(!count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("ALMOCO")){
                        count.put(consumo.getDataConsumo(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    public Map<LocalDate,Integer> relatorioConsumoJantar(LocalDate inicio, LocalDate fim) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            List<TicketRefeicao> listaConsumos=this.repositorioTicketRefeicao.listar().stream().filter(t->t.getDataConsumo()!=null).toList();
            for (TicketRefeicao consumo:listaConsumos) {
                if(!consumo.getDataConsumo().isBefore(inicio) && !consumo.getDataConsumo().isAfter(fim)){
                    if(count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("JANTAR")) {
                        count.replace(consumo.getDataConsumo(), count.get(consumo.getDataConsumo()) + 1);
                    }
                    else if(!count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("JANTAR")){
                        count.put(consumo.getDataConsumo(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    public Map<LocalDate,Integer> historicoVendasAlmoco(LocalDate inicio, LocalDate fim, Usuario user) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            for (TicketRefeicao venda:this.repositorioTicketRefeicao.listar()) {
                if(venda.getComprador().equals(user) && !venda.getDataVenda().isBefore(inicio) && !venda.getDataVenda().isAfter(fim)){
                    if(count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("ALMOCO")) {
                        count.replace(venda.getDataVenda(), count.get(venda.getDataVenda()) + 1);
                    }
                    else if(!count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("ALMOCO")){
                        count.put(venda.getDataVenda(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }

    public Map<LocalDate,Integer> historicoVendasJantar(LocalDate inicio, LocalDate fim,Usuario user) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            for (TicketRefeicao venda:this.repositorioTicketRefeicao.listar()) {
                if(venda.getComprador().equals(user) && !venda.getDataVenda().isBefore(inicio) && !venda.getDataVenda().isAfter(fim)){
                    if(count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("JANTAR")) {
                        count.replace(venda.getDataVenda(), count.get(venda.getDataVenda()) + 1);
                    }
                    else if(!count.containsKey(venda.getDataVenda()) && venda.getTipo().name().equals("JANTAR")){
                        count.put(venda.getDataVenda(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    public Map<LocalDate,Integer> historicoConsumoAlmoco(LocalDate inicio, LocalDate fim, Usuario user) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            List<TicketRefeicao> listaConsumos=this.repositorioTicketRefeicao.listar().stream().filter(t->t.getDataConsumo()!=null).toList();
            for (TicketRefeicao consumo:listaConsumos) {
                if(consumo.getComprador().equals(user) && !consumo.getDataConsumo().isBefore(inicio) && !consumo.getDataConsumo().isAfter(fim)){
                    if(count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("ALMOCO")) {
                        count.replace(consumo.getDataConsumo(), count.get(consumo.getDataConsumo()) + 1);
                    }
                    else if(!count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("ALMOCO")){
                        count.put(consumo.getDataConsumo(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    public Map<LocalDate,Integer> historicoConsumoJantar(LocalDate inicio, LocalDate fim, Usuario user) throws DataInvalidaException, PeriodoInvalidoException{
        Map<LocalDate,Integer> count=new HashMap<>();
        if(!inicio.isAfter(LocalDate.now()) && !fim.isAfter(LocalDate.now()) && !inicio.isAfter(fim)){
            List<TicketRefeicao> listaConsumos=this.repositorioTicketRefeicao.listar().stream().filter(t->t.getDataConsumo()!=null).toList();
            for (TicketRefeicao consumo:listaConsumos) {
                if(consumo.getComprador().equals(user) && !consumo.getDataConsumo().isBefore(inicio) && !consumo.getDataConsumo().isAfter(fim)){
                    if(count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("JANTAR")) {
                        count.replace(consumo.getDataConsumo(), count.get(consumo.getDataConsumo()) + 1);
                    }
                    else if(!count.containsKey(consumo.getDataConsumo()) && consumo.getTipo().name().equals("JANTAR")){
                        count.put(consumo.getDataConsumo(),1);
                    }
                }
            }
        }
        else {
            if (inicio.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(inicio);
            }
            if (fim.isAfter(LocalDate.now())) {
                throw new DataInvalidaException(fim);
            }
            if (inicio.isAfter(fim)) {
                throw new PeriodoInvalidoException(inicio,fim);
            }
        }
        return count;
    }
    
    public int indexSemanaCardapio(LocalDate data, TipoRefeicao tipo) {
    	
    	
    	if(data.minusDays(1).getDayOfWeek().equals(DayOfWeek.MONDAY))
    		data = data.minusDays(1);
    	
   		if(data.minusDays(2).getDayOfWeek().equals(DayOfWeek.MONDAY))
            data = data.minusDays(2);
    		
   		if(data.minusDays(3).getDayOfWeek().equals(DayOfWeek.MONDAY))
            data = data.minusDays(3);
    			 				
    	if(data.minusDays(4).getDayOfWeek().equals(DayOfWeek.MONDAY))
            data = data.minusDays(4);
    	
    	if(data.plusDays(2).getDayOfWeek().equals(DayOfWeek.MONDAY))
            data = data.plusDays(2);
    	
    	if(data.plusDays(1).getDayOfWeek().equals(DayOfWeek.MONDAY))
            data = data.plusDays(1);
    	
    	
    	for(int i=0;i<listarCardapioSemanal().size();i++) {
    		if(listarCardapioSemanal().get(i).getDataInicial().equals(data) && listarCardapioSemanal().get(i).getCardapio().get(DiasDaSemana.SEGUNDA).getTipo().equals(tipo)) {
    			return i;
    		}
    		
    	}
    	
    	
    	return -1;
    }



}