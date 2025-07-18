package exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataInvalidaException extends Exception{

    public DataInvalidaException(LocalDate data){
        super("A data "+data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" é inválida!");
    }




}
