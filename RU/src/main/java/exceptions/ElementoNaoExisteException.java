package exceptions;

public class ElementoNaoExisteException extends Exception {

	private static final long serialVersionUID = -7096022758377417172L;
	
	private Object elemento;
    
    public ElementoNaoExisteException(Object obj) {
        super(obj.getClass().getSimpleName()+" n�o existe no reposit�rio");
        this.elemento = obj;        
    }
    
    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }
    
}
