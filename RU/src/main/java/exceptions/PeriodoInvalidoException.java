package exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PeriodoInvalidoException extends Exception {
    public PeriodoInvalidoException (LocalDate inicio, LocalDate fim){
        super("O período de "+inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" a "+fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" é inválido!");
    }
}