import javax.swing.*;

/**
 * Controla as regras e a inicialização do problema de Josephus.
 */
public class jogoJoseph {
    private int qtdIndividuos = 7;  
    private int intervalo = 3;
    private int tempoEspera = 100;
    
    public int getQtdIndividuos() { return qtdIndividuos; }
    public int getIntervalo() { return intervalo; }
    public int getTempoEspera() { return tempoEspera; }

    ListaLigadaSimplesCircular<Integer> l;
    UIGraf ui = new UIGraf();
    /**
     * Inicia a simulação em grid
     */
    public void iniciarJogo() {
        l = new ListaLigadaSimplesCircular<>();
        for (int i = 0; i < qtdIndividuos; i++) {
            l.inserirFim(i);
        }
        ui.GRID(this);
    }
    /**
     * Mostra a primeira mensagem avisando que os valores padroes foram carregados
     */
    public void primeiraVez(){
        ui.primeiraMensagem();
    }

    /**
     * Menu principal que deixa o usuario escolher configurar, executar o simulador ou sair do programa
     */
    public void menuPrincipal() {
        UIGraf.State selecao; 
        do{
        selecao = ui.Pergunta();
        if (selecao == null) return;
        
        switch(selecao) {
            case EXECUTAR:
                iniciarJogo(); 
                break;
            case CONFIGURAR:
                int[] dados = ui.dadosGrid();
                this.qtdIndividuos = dados[0];
                this.intervalo = dados[1];
                this.tempoEspera = dados[2];
                break;
                
            case CLOSE:
                selecao = null;
                break;
        }
        }while(selecao != null);
    }
}
