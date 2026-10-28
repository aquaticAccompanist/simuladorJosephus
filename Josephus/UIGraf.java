import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Implementa o ambiente gráfico para o Josephus
 */
public class UIGraf extends JFrame {

    private JLabel[] cells;
    
    /**
     * pergunta os dados que o usuario quer usar para simulacao
     * @return   os valores de quantidade, intervalo e espera em um array
     */
    public int[] dadosGrid() {
        JTextField quantidade = new JTextField();
        JTextField intervalo = new JTextField();
        JTextField tempoEspera = new JTextField();
        
        Object[] formulario = {
            "Quantidade de indivíduos:", quantidade,
            "Intervalo:", intervalo,
            "Tempo de espera (ms):", tempoEspera,
        };
        
        int result = JOptionPane.showConfirmDialog(null, formulario, "Configure os dados", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int qtd = Integer.parseInt(quantidade.getText());
                int inter = Integer.parseInt(intervalo.getText());
                int tempo = Integer.parseInt(tempoEspera.getText());

                return new int[]{qtd, inter, tempo};
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Insira somente números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(null, "Revertendo para os valores padrões");
        return new int[]{7, 3, 100}; 
    }
    
    /**
     * constroi o grid do jogo
     * @param  jogo - informacoes centrais do jogojoseph
     */
    public void GRID(jogoJoseph jogo) {
        int qtd = jogo.getQtdIndividuos();
        setTitle("Grid Josephus");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        getContentPane().removeAll();
        
        int side = (int) Math.ceil(Math.sqrt(qtd));
        setLayout(new GridLayout(side, side, 5, 5));
        
        cells = new JLabel[qtd];

        for (int i = 0; i < qtd; i++) {
            cells[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            cells[i].setOpaque(true);
            cells[i].setBackground(Color.GREEN); 
            cells[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cells[i].setFont(new Font("Arial", Font.BOLD, 14));
            add(cells[i]);
        }

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        revalidate();
        repaint();

        executarSimulacao(jogo);
    }
    
    /**
     * executa a simulação no grid construido
     * @param  jogo - informacoes centrais do jogojoseph
     */
    private void executarSimulacao(jogoJoseph jogo) {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int k = jogo.getIntervalo();
                int delay = jogo.getTempoEspera();
                ListaLigadaSimplesCircular<Integer> lista = jogo.l;

                while (lista.getQtdNos() > 1) {
                    for (int i = 0; i < k - 1; i++) {
                        Integer sobreviventeTemporario = lista.removerInicio();
                        lista.inserirFim(sobreviventeTemporario);
                    }
                    
                    Integer eliminadoId = lista.removerInicio();
                    publish(eliminadoId); 
                    Thread.sleep(delay);  
                }

                int sobreviventeFinal = lista.removerInicio();
                
                SwingUtilities.invokeLater(() -> {
                    cells[sobreviventeFinal].setBackground(Color.BLUE);
                    cells[sobreviventeFinal].setForeground(Color.WHITE);
                    
                    JOptionPane.showMessageDialog(null, "Indivíduo " + (sobreviventeFinal + 1) + " sobreviveu");
                    dispose(); 
                    
                });
                
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int id : chunks) {
                    cells[id].setBackground(Color.RED);
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Mostra o menu principal com opcoes usando joptionpane
     * @return State.values()[option] - opcao escolhida no menu
     */
    public State Pergunta() {
        int option = JOptionPane.showOptionDialog(null, "Escolha a opção desejada", "Josephus",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                State.values(), State.values()[0]);
                
        if (option == JOptionPane.CLOSED_OPTION) {
            return State.CLOSE;
        }
        return State.values()[option];
    }
    
    public enum State {
        EXECUTAR("Executar"), CONFIGURAR("Configurar"), CLOSE("Fechar");
        
        private final String text;

        private State(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    
    public void primeiraMensagem(){
        JOptionPane.showMessageDialog(null, "Valores padrões iniciados (quantidade = 7, intervalo = 3, tempo = 100ms)");
    }
}
