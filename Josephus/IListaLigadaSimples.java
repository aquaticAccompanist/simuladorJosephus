 


/**
 * Metodos a serem implementados numa Lista Ligada Simples.
 * 
 * @author Julio Arakaki
 * @version 14/05/2026
 */

public interface IListaLigadaSimples <T> {
    boolean estaVazia();
    void inserirInicio(T elem);
    void inserirFim(T elem);
    T removerInicio();
    T removerFim();
}
