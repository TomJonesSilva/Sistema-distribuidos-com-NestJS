package exceptions;

public class ParametroVazioException extends Exception{

		public ParametroVazioException(String campoVazio) {
			super(campoVazio + " não foi preenchido!!");
		}
		
}
