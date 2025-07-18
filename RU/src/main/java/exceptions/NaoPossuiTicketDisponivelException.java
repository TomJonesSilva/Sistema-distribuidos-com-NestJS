package exceptions;

import models.TipoRefeicao;

public class NaoPossuiTicketDisponivelException extends Exception{

    public NaoPossuiTicketDisponivelException(TipoRefeicao tipo){
        super("Não há tickets de "+tipo.name().toLowerCase()+" disponível. ");
    }




}
