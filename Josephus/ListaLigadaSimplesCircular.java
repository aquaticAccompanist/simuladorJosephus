 /**
 * Lista Ligada Simples Circular Genérica
 * @author Julio Arakaki
 * @version 1.0 2026/05/14
 */
public class ListaLigadaSimplesCircular<T> implements IListaLigadaSimples<T> {

    private No inicio, fim;
    private int qtdNos;

    /**
     * Classe interna No
     */
    private class No {
        private T conteudo;
        private No proximo;

        public No(T conteudo) {
            this.conteudo = conteudo;
            this.proximo = null;
        }

        public T getConteudo() {
            return conteudo;
        }

        public void setConteudo(T conteudo) {
            this.conteudo = conteudo;
        }

        public No getProximo() {
            return proximo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }
    }

    /**
     * Construtor
     */
    public ListaLigadaSimplesCircular() {
        inicio = null;
        fim = null;
        qtdNos = 0;
    }

    /* ================= GETTERS / SETTERS ================= */

    public No getInicio() {
        return inicio;
    }

    public No getFim() {
        return fim;
    }

    public int getQtdNos() {
        return qtdNos;
    }

    private void setInicio(No inicio) {
        this.inicio = inicio;
    }

    private void setFim(No fim) {
        this.fim = fim;
    }

    private void setQtdNos(int qtdNos) {
        this.qtdNos = qtdNos;
    }

    /* ================= MÉTODOS ================= */

    @Override
    public boolean estaVazia() {
        return qtdNos == 0;
    }

    @Override
    public void inserirInicio(T elem) {
        No novo = new No(elem);

        if (estaVazia()) {
            inicio = novo;
            fim = novo;
            novo.setProximo(novo); // circular
        } else {
            novo.setProximo(inicio);
            inicio = novo;
            fim.setProximo(inicio); // mantém circularidade
        }

        qtdNos++;
    }

    @Override
    public void inserirFim(T elem) {
        No novo = new No(elem);

        if (estaVazia()) {
            inicio = novo;
            fim = novo;
            novo.setProximo(novo);
        } else {
            fim.setProximo(novo);
            novo.setProximo(inicio);
            fim = novo;
        }

        qtdNos++;
    }

    @Override
    public T removerInicio() {
        if (estaVazia())
            return null;

        T valor = inicio.getConteudo();

        if (inicio == fim) {
            inicio = null;
            fim = null;
        } else {
            inicio = inicio.getProximo();
            fim.setProximo(inicio);
        }

        qtdNos--;
        return valor;
    }

    @Override
    public T removerFim() {
        if (estaVazia())
            return null;

        T valor = fim.getConteudo();

        if (inicio == fim) {
            inicio = null;
            fim = null;
        } else {
            No anterior = inicio;

            while (anterior.getProximo() != fim) {
                anterior = anterior.getProximo();
            }

            anterior.setProximo(inicio);
            fim = anterior;
        }

        qtdNos--;
        return valor;
    }

    /**
     * Representação da lista
     */
    @Override
    public String toString() {
        if (estaVazia())
            return "[]";

        StringBuilder sb = new StringBuilder("[");
        No temp = inicio;

        for (int i = 0; i < qtdNos; i++) {
            sb.append(temp.getConteudo());

            if (i < qtdNos - 1) {
                sb.append(",");
            }

            temp = temp.getProximo();
        }

        sb.append("]");
        return sb.toString();
    }
}