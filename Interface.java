import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicScrollBarUI;


// ====== CLASSE PRINCIPAL ======
public class Interface {
    
    // 🔴 1. TOPO DA CLASSE: Variáveis globais
    private static int pX, pY;
    private static final int LARGURA = 480;
    private static final int ALTURA = 600;

    // variável global para usar a fonte em qualquer botão ou texto
    static Font fonteGraffiti; 
    static Font RockSalt;

    public static void main(String[] args) {
        
        // 🔴 2. INÍCIO DO MAIN: Configurações de renderização do sistema

        System.setProperty("sun.java2d.noddraw", "true");

        // INSTÂNCIA DA LÓGICA
        LogicaJogo jogo = new LogicaJogo();

        
        // =============== CARREGAMENTO DAS FONTES CUSTOMIZADAS: ====================
        try {
            // Busca o arquivo da fonte
            java.io.InputStream isFonteG = new java.io.FileInputStream("C:\\Users\\Rafaela\\Downloads\\p\\src\\fonts\\SpriteGraffiti-Regular.ttf");
            
            if (isFonteG != null) {
                // Cria a fonte física a partir do arquivo e define um tamanho padrão
                Font fonteCrua = Font.createFont(Font.TRUETYPE_FONT, isFonteG);
                fonteGraffiti = fonteCrua.deriveFont(80f); // Define tamanho da letra
                
                // Registra a fonte no sistema do Java para evitar bugs de renderização
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(fonteGraffiti);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fonteGraffiti = new Font("Arial", Font.BOLD, 24);
        }

        try {
            // Busca o arquivo da fonte
            java.io.InputStream isFonteRS = new java.io.FileInputStream("C:\\Users\\Rafaela\\Downloads\\p\\src\\fonts\\RockSalt-Regular.ttf");
            
            if (isFonteRS != null) {
                // Cria a fonte física a partir do arquivo e define um tamanho padrão
                Font fonteCrua2 = Font.createFont(Font.TRUETYPE_FONT, isFonteRS);
                RockSalt = fonteCrua2.deriveFont(80f); // Define tamanho da letra
                
                // Registra a fonte no sistema do Java para evitar bugs de renderização
                GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge2.registerFont(RockSalt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            RockSalt = new Font("Arial", Font.BOLD, 24);
        }
        // =============================================================================


        // ====================== CRIANDO A JANELA PRINCIPAL ===========================
        JFrame janela = new JFrame();

        // 🔴 3. CONFIGURAÇÕES ESTRUTURAIS (Não podem mudar de ordem!)
        janela.setUndecorated(true); 
        janela.setSize(LARGURA, ALTURA);
        janela.setLocationRelativeTo(null); 
        janela.setShape(new RoundRectangle2D.Float(0, 0, LARGURA, ALTURA, 30, 30));

        // 🔴 4. CAMADA DE FUNDO (O Painel Gradiente)
        // Instancia o fundo e diz ao Java que ele vai gerenciar o layout dos botões
        PainelGradienteArredondado fundo = new PainelGradienteArredondado(30);

        // BorderLayout fixa o botão embaixo e o texto em cima de forma limpa
        fundo.setLayout(new BorderLayout());
        janela.setContentPane(fundo);

        // 🔴 5. EVENTOS DO MOUSE (Capturar os cliques no fundo)
        fundo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                pX = e.getX(); pY = e.getY();
            }
        });
        fundo.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                janela.setLocation(janela.getLocation().x + e.getX() - pX, 
                                   janela.getLocation().y + e.getY() - pY);
            }
        });


        // 🔴 6. ELEMENTOS VISUAIS (Botões, Textos, Inputs)

        // ===================== PAINEL SUPERIOR DE TÍTULO E INSTRUÇÃO ========================
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new BoxLayout(painelSuperior, BoxLayout.Y_AXIS));
        painelSuperior.setOpaque(false);

        // subtitulo
        JLabel labelSubtitulo = new JLabel("<html><div align='center'>adivinhe o número</html>");
        labelSubtitulo.setFont(fonteGraffiti.deriveFont(Font.BOLD, 18f));
        labelSubtitulo.setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0)); // espaçamento do topo
        labelSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelSubtitulo.setForeground(Color.decode("#0f2a1d"));

        // Rótulo de instrução
        JLabel labelInstrucao = new JLabel("<html><div align='center'>qual seu palpite? (1 - 100)</html>");
        labelInstrucao.setFont(RockSalt.deriveFont(Font.BOLD, 18f));
        labelInstrucao.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        labelInstrucao.setForeground(Color.decode("#0f2a1d"));
        labelInstrucao.setHorizontalAlignment(SwingConstants.CENTER);

        painelSuperior.add(labelSubtitulo);
        painelSuperior.add(Box.createVerticalStrut(10)); // espaçamento entre os rótulos
        painelSuperior.add(labelInstrucao);

        fundo.add(painelSuperior, BorderLayout.NORTH);

        // ===================== PAINEL CENTRAL: HISTÓRICO E CONTROLES ========================

        JPanel painelJogo = new JPanel();
        painelJogo.setLayout(new BoxLayout(painelJogo, BoxLayout.Y_AXIS));
        painelJogo.setOpaque(false);

        // --- Painel de Controles (Campo + Botão) ---
        JPanel painelControles = new JPanel();
        painelControles.setLayout(new BoxLayout(painelControles, BoxLayout.Y_AXIS));
        painelControles.setOpaque(false);

        // 1. Campo para o usuário digitar o palpite
        JTextField campoPalpite = new JTextField(5);
        campoPalpite.setMaximumSize(new Dimension(100, 35));
        campoPalpite.setFont(fonteGraffiti.deriveFont(20f));
        campoPalpite.setHorizontalAlignment(JTextField.CENTER);
          // === > Configurações visuais do campo de entrada
        campoPalpite.setForeground(Color.decode("#0f2a1d")); // Cor do texto digitado
        campoPalpite.setCaretColor(Color.decode("#0f2a1d"));   // Cor da barrinha piscando
        campoPalpite.setOpaque(false); // Remove o fundo padrão do componente
        campoPalpite.setBackground(new Color(0, 0, 0, 0)); // Fundo com transparência total (RGBA)
        campoPalpite.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#0f2a1d")));

        // 2. Botão para enviar o palpite
        JButton botaoChutar = new JButton("chutar");
        botaoChutar.setAlignmentX(Component.CENTER_ALIGNMENT);
          // === > Configurações visuais do botão de envio
        botaoChutar.setFocusPainted(false);      // Remove o contorno de foco ao clicar
        botaoChutar.setContentAreaFilled(false); // Remove o preenchimento padrão do botão
        botaoChutar.setBorderPainted(false);     // Remove a borda padrão
        botaoChutar.setOpaque(false);            // Torna o componente transparente
        botaoChutar.setForeground(Color.decode("#0f2a1d"));  // Cor do texto do botão
        botaoChutar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muda o cursor para a mãozinha
        botaoChutar.setFont(Interface.RockSalt.deriveFont(Font.BOLD, 18f));

        painelControles.add(campoPalpite);
        painelControles.add(botaoChutar);

        campoPalpite.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        botaoChutar.doClick(); // Simula o clique mecânico no botão
        }
    });

        // --- Painel do Histórico ---
        JPanel painelHistorico = new JPanel();
        painelHistorico.setLayout(new BoxLayout(painelHistorico, BoxLayout.Y_AXIS));
        painelHistorico.setOpaque(false);

        // Envolve o painel de histórico em um ScrollPane caso a lista fique grande
        JScrollPane scrollHistorico = new JScrollPane(painelHistorico);
        scrollHistorico.setOpaque(false);
        scrollHistorico.getViewport().setOpaque(false);
        scrollHistorico.setBorder(BorderFactory.createEmptyBorder());
        scrollHistorico.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollHistorico.setPreferredSize(new Dimension(400, 280));
        scrollHistorico.setMaximumSize(new Dimension(400, 280));

        // 1. Aplica a nova UI que corrige as cores
        scrollHistorico.getVerticalScrollBar().setUI(new MinhaScrollBarUI());

        // 2. Define a largura da barra vertical em pixels (largura, altura)
        scrollHistorico.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));

        scrollHistorico.getVerticalScrollBar().addAdjustmentListener(e -> {
        // Força a barra a ir para o valor máximo possível
        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });

        // Dinâmica do Jogo
        botaoChutar.addActionListener(e -> {
            try {
                String textoDigitado = campoPalpite.getText();
                int palpite = Integer.parseInt(textoDigitado);
                
                String resultado = jogo.verificarPalpite(palpite);

                // Cria o elemento visual para o palpite digitado
                JLabel itemPalpite = new JLabel(textoDigitado);
                itemPalpite.setFont(Interface.fonteGraffiti != null ? Interface.fonteGraffiti.deriveFont(Font.PLAIN, 28f) : new Font("Arial", Font.BOLD, 22));
                itemPalpite.setForeground(Color.decode("#7a1e1e"));
                itemPalpite.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Cria o elemento visual para a resposta
                JLabel itemResposta = new JLabel(resultado.toUpperCase());
                itemResposta.setFont(Interface.fonteGraffiti != null ? Interface.fonteGraffiti.deriveFont(Font.PLAIN, 24f) : new Font("Arial", Font.BOLD, 18));
                itemResposta.setForeground(Color.decode("#0f2a1d"));
                itemResposta.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Adiciona na ordem visual desejada dentro do histórico
                painelHistorico.add(itemPalpite);
                painelHistorico.add(Box.createVerticalStrut(2));
                painelHistorico.add(itemResposta);
                painelHistorico.add(Box.createVerticalStrut(10));

                // Atualiza a tela para exibir os novos elementos instantaneamente
                painelHistorico.revalidate();
                painelHistorico.repaint();

                // Limpa o campo de entrada para o próximo chute (mantendo o histórico intacto acima)
                campoPalpite.setText(""); 
                campoPalpite.requestFocus();
            } catch (NumberFormatException ex) {
                JLabel erro = new JLabel("número inválido");
                erro.setFont(Interface.RockSalt.deriveFont(Font.BOLD, 12f));
                erro.setForeground(Color.decode("#7a1e1e"));
                erro.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                painelHistorico.add(erro);
                painelHistorico.revalidate();
                painelHistorico.repaint();
                campoPalpite.setText("");
            }
        });

        // Adicionando os elementos no painel central
        painelJogo.add(scrollHistorico);
        painelJogo.add(Box.createVerticalStrut(20)); // espaçamento entre o histórico e os controles
        painelJogo.add(painelControles);
        
        // Insere o painel do jogo no centro da tela
        fundo.add(painelJogo, BorderLayout.CENTER);

        // ======================= PAINEL INFERIOR DE SAÍDA ========================
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelInferior.setOpaque(false); // Mantém transparente para ver o gradiente atrás

        JButton botaoFechar = new JButton("Sair");
        botaoFechar.addActionListener(e -> System.exit(0));
          // === > Configurações visuais do botão de saída
        botaoFechar.setContentAreaFilled(false); // Remove o preenchimento padrão do botão
        botaoFechar.setBorderPainted(false);     // Remove a borda padrão
        botaoFechar.setOpaque(false);            // Torna o componente transparente
        botaoFechar.setForeground(Color.decode("#0f2a1d"));  // Cor do texto do botão
        botaoFechar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muda o cursor para a mãozinha
        botaoFechar.setFont(Interface.fonteGraffiti.deriveFont(Font.PLAIN, 30f));

        // Adiciona os botões no agrupador inferior
        painelInferior.add(botaoFechar);

        // Posiciona o agrupador de botões na parte de baixo da janela
        fundo.add(painelInferior, BorderLayout.SOUTH);
        
        // Adicionar SEMPRE ao 'fundo', nunca direto na 'janela'

        // 🔴 7. Sempre por último!
        janela.setVisible(true);
    }
}

// ====== CLASSE DO PAINEL ======
// ATENÇÃO: Não colocar a palavra "public" aqui se estiver no mesmo arquivo!
class PainelGradienteArredondado extends JPanel {
    private final int raioCanto;

    public PainelGradienteArredondado(int raioCanto) {
        this.raioCanto = raioCanto;
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        // ===== DESENHO DO FUNDO COM GRADIENTE ======
        Color corInicio = Color.decode("#7794b6");
        Color corFim = Color.decode("#dffaff");

        LinearGradientPaint gradiente = new LinearGradientPaint(
            0, getHeight(), getWidth(), 0, 
            new float[]{0.0f, 1.0f},
            new Color[]{corInicio, corFim}
        );

        g2d.setPaint(gradiente);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), raioCanto, raioCanto);


        // ===== DESENHO DO TEXTO COM CONTORNO ======
        // Define a fonte e os tamanhos
        Font fonteRender = Interface.fonteGraffiti.deriveFont(Font.PLAIN, 90f);
        g2d.setFont(fonteRender);

        // Configuração das cores
        Color corBorda = Color.decode("#0f2a1d"); // Verde escuro
        Color corTexto = Color.decode("#d9d9d9"); // Cinza claro

        // Letras a serem desenhadas e suas posições
        String[] letras = {"D", "V", "-", "X"};
        int[] posicoesX = {145, 195, 240, 275}; // Disposição horizontal e espaçamento entre as letras
        int posicaoY = 120; // Altura do texto

        // PASSO 1: Desenha contorno de todas as letras
        g2d.setColor(corBorda);
        g2d.setStroke(new BasicStroke(16, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        for (int i = 0; i < letras.length; i++) {
            // Cria um outline do texto para aplicar a borda externa preenchendo o fundo
            java.awt.font.TextLayout tl = new java.awt.font.TextLayout(letras[i], fonteRender, g2d.getFontRenderContext());
            Shape shape = tl.getOutline(java.awt.geom.AffineTransform.getTranslateInstance(posicoesX[i], posicaoY));
            g2d.draw(shape);
            g2d.fill(shape); // Preenche a borda para não vazar fundo no meio da junção
        }

        // PASSO 2: Desenha o preenchimento por cima
        g2d.setColor(corTexto);
        for (int i = 0; i < letras.length; i++) {
            g2d.drawString(letras[i], posicoesX[i], posicaoY);
        }
    }
}

    // ====== CLASSE DA BARRA DE ROLAGEM ======
    class MinhaScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            super.configureScrollBarColors();
            // Configura a cor do trilho (fundo)
            this.trackColor = Color.decode("#d9d9d9"); 
            
            // Configura a cor do botão (thumb)
            this.thumbColor = Color.decode("#b1b8b2"); 
        }

        
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(this.trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
        

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            g.setColor(this.thumbColor);
            g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
        }

        // Remove os botões de seta completamente, retornando dimensões zeradas
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }   
}

