package dados;

import exceptions.ElementoJaExisteException;
import exceptions.ElementoNaoExisteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioGenerico<T> implements IRepositorioGenerico<T> {
    
    protected List<T> elementos;
 
    //CONSTRUTOR
    public RepositorioGenerico() {
     
        this.elementos = new ArrayList<>();
          
    }
    
    //RECOVER
    @Override
    public List<T> listar() {
        return Collections.unmodifiableList(this.elementos);
    }

    //CREATE
    /**
     * método somente deve permitir a inserção de um novo elementos se o mesmo
     * não já não foi inserido anteriorment. Para isso é imprescindível a
     * correta implementação do método equals do objeto envolvido.
     * 
     * A implementação deste método deve OBRIGATORIAMENTE usar o método contains
     * da interface java.util.List para verificar se o elemento a ser adicionado
     * já existe na lista.
     * 
     * @throws ElementoJaExisteException
     *             Exce��o dever� ser levantada se, ao tentar inserir um novo
     *             elemento e o mesmo j� exista no reposit�rio
     */
    @Override
    public void inserir(T novoObj) throws ElementoJaExisteException {
        if (!this.elementos.contains(novoObj)) {
            this.elementos.add(novoObj);
        } else {
            throw new ElementoJaExisteException(novoObj);
        }        
    }
    
    //DELETE
    /**
     * método deve remover um elemento previamente cadastrado no reposit�rio.
     * 
     * A implementação deste método deve OBRIGATORIAMENTE usar o método indexOf
     * da classe java.util.List para encontrar o �ndice de um determinado objeto
     * do reposit�rio e remov�-lo.
     * 
     * @throws ElementoNaoExisteException
     *             Exce��o dever� ser levantada se, ao tentar remover um
     *             elemento da lista, o mesmo n�o exista no reposit�rio
     */   
    @Override
    public void remover(T obj) throws ElementoNaoExisteException {
        if (this.elementos.contains(obj)) {
            this.elementos.remove(this.elementos.indexOf(obj));
        } else {
            throw new ElementoNaoExisteException(obj);
        }
    }
    
    
    //UPDATE
    /**
     * método deve atualizar um elemento previamente cadastrado no reposit�rio.
     * 
     * A implementação deste método deve OBRIGATORIAMENTE usar o método indexOf
     * da classe java.util.List para encontrar o �ndice de um determinado voo e
     * atualiz�-lo.
     * 
     * @throws ElementoNaoExisteException
     *             Exce��o dever� ser levantada se, ao tentar procurar um
     *             elemento para ser atualizado, o mesmo n�o exista no
     *             reposit�rio
     */
    @Override
    public void atualizar(T newObj) throws ElementoNaoExisteException {
        if (this.elementos.contains(newObj)) {
            int indice = this.elementos.indexOf(newObj);
            this.elementos.set(indice, newObj);
        } else {
            throw new ElementoNaoExisteException(newObj);
        }
    }
    
}
