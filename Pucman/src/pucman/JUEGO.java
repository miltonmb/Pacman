package pucman;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//La clase se extiende para el uso del JPANEL y se le implementa el ActionListener para los movimientos.
public class JUEGO extends JPanel implements ActionListener {

    //Esto obtiene las dimensiones del JFRAME.
    private Dimension dim;
    //Esto es para poner una fuente.
    private final Font fuente = new Font("Comic Sans MS", Font.BOLD, 14);

    //Esto crea el color de las galletas.
    private final Color galletas = Color.RED;
    //Esto crea el color del mapa.
    private Color color_mapa;

    //ESto es para el inicio del juego.
    private boolean inicio = false;
    //Esto es para saber si pacman muere o no.
    private boolean morir = false;

    //bloque_t es el tamaño de bloques.
    private final int bloque_t = 24;
    //Bloque nro_bloques es el numero de bloques.
    private final int nro_bloques = 15;
    //scr_t crea el tamaño total.
    private final int scr_t = nro_bloques * bloque_t;
    //La animacion de pacma.
    private final int pacanimd = 2;
    private final int pacmananimc = 4;
    //Esto crea lo maximo de fantasmas.
    private final int maxfantasmas = 20;
    //Esto crea la velocidad de pacman.
    private final int pacman_velocidad = 6;

    private int pacmanimc = pacanimd;
    //Esto crea la direccion de de pacman.
    private int pacanim_dir = 1;
    //Esto crea la posicion de pacman.
    private int pacmananim_pos = 0;
    //Esto es el total de fantasmas.
    private int nro_fantasmas = 20;
    //Esto crea las vidas de pacman y el puntaje del juego.
    private int vidas_pacman, puntaje;
    //Esto crea las direcciones en X y en Y.
    private int[] dir_x, dir_y;
    //Aqui sea crean los enteros que van a controlar el movimiento de los fantasmas.
    private int[] fantasma_x, fastama_y, fantasma_dx, fantasma_dy, velocidad_fantasma;
    //Esto crea la variable tipo Image que es la que va almamcenar las imagenes de los fantasmas, y el pacman.
    private Image fantasma;
    private Image pacman1, pacman2A, pacman2izq, pacman2der, pacman2b;
    private Image pacman3A, pacman3b, pacman3izq, pacman3der;
    private Image pacman4A, pacman4b, pacman4izq, pacman4der;

    //Esto crea las variables de los movimientos de pacman.
    private int pac_x, pac_y, pac_dx, pacmandy;
    private int c_dx, c_dy, v_dx, v_dy;
    //Este es el mapa, que esta puesto en un array de tipo short.
    private final short level_map[] = {
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
        1, 19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22, 4,
        1, 17, 16, 24, 24, 24, 24, 24, 24, 24, 24, 24, 16, 20, 4,
        1, 17, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 20, 4,
        1, 17, 16, 18, 18, 18, 22, 0, 19, 18, 18, 18, 16, 20, 4,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 20, 4,
        1, 17, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 16, 20, 4,
        1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 16, 20, 4,
        1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 16, 20, 4,
        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 20, 4,
        1, 17, 16, 24, 24, 24, 28, 0, 25, 24, 24, 24, 16, 20, 4,
        1, 17, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 20, 4,
        1, 17, 16, 18, 18, 18, 18, 18, 18, 18, 18, 18, 16, 20, 4,
        1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28, 4,
        9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12
    };

    //Estas son las velocidades que pueden alcanzar.
    private final int velocidad_valida[] = {1, 2, 3, 4, 6, 8};
    //Esta es la velocidad maxima.
    private final int velocidad_maxima = 6;
    //Esto crea La velocidad actual.
    private int velocidad_actual = 3;
    private short[] mapa_recorrido;
    private Timer timer;

    //Este es el constructor de la clase del juego.
    public JUEGO() {
        //Esto llama la clase Imagenes y la clase Ini_variables.
        Imagenes();
        ini_Variables();
        //Esto Llama al keyListenerr que es para el uso de Teclas.
        addKeyListener(new TAdapter());

        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    private void ini_Variables() {

        mapa_recorrido = new short[nro_bloques * nro_bloques];
        //Se crea el color de los cubos utilizados..
        color_mapa = Color.BLUE;
        //Aqui se inicializa la dimension.
        dim = new Dimension(200, 200);
        //Aqui se le da valores a los movimientos de los fantasmas.
        fantasma_x = new int[maxfantasmas];
        fantasma_dx = new int[maxfantasmas];
        fastama_y = new int[maxfantasmas];
        fantasma_dy = new int[maxfantasmas];
        velocidad_fantasma = new int[maxfantasmas];
        dir_x = new int[4];
        dir_y = new int[4];
        //Esto creal el timer.
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        ini_juego();
    }

    private void Animacion() {
        //Esto es parte para la creacion del pacman y de su animacion en si.
        pacmanimc--;

        if (pacmanimc <= 0) {
            pacmanimc = pacanimd;
            pacmananim_pos = pacmananim_pos + pacanim_dir;

            if (pacmananim_pos == (pacmananimc - 1) || pacmananim_pos == 0) {
                pacanim_dir = -pacanim_dir;
            }
        }
    }

    private void Jugar(Graphics2D g2d) {
        //Estaa decision sirve para iniciar el juego, si el estado de pacman es MORIR entonces que haga la muerte, y sino
        //que ejecute lo demas, como el movimiento del pacman, el dibujo del pacman y el mapa.
        if (morir) {

            muerte();

        } else {

            moverPacman();
            drawPacman(g2d);
            moverfantasmas(g2d);
            ver_mapa();
        }
    }

    private void PantallaInicial(Graphics2D g2d) {
        //Esta es la pantalla que se muestra al usuario para que pueda iniciar el juego.
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(50, scr_t / 2 - 30, scr_t - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, scr_t / 2 - 30, scr_t - 100, 50);

        String s = "INSERTA UNA MONEDA: 1";
        Font small = new Font("Comic Sans MS", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (scr_t - metr.stringWidth(s)) / 2, scr_t / 2);
    }

    private void Puntaje(Graphics2D g) {
        //Esto mueesta el puntaje que se va adquiriendo a medida se van agarrando las galletas.
        int i;
        String s;

        g.setFont(fuente);
        g.setColor(Color.WHITE);
        s = "PUNTAJE ZUCULEMTHO: " + puntaje;
        g.drawString(s, scr_t / 44 + 96, (float) (scr_t + 0.5));

        for (i = 0; i < vidas_pacman; i++) {
            g.drawImage(pacman3izq, i * 28 + 280, scr_t + 1, this);
        }
    }

    private void ver_mapa() {

        short i = 0;
        boolean finished = true;
        //Aqui revisa si el puntaje es mayor o igual a 50 para revisar si puede seguir.
        while (i < nro_bloques * nro_bloques && finished) {

            if ((mapa_recorrido[i] & 48) != 0) {
                finished = false;
            }

            i++;
            System.out.println(i);
        }

        if (finished) {
            //Aqui si el pacman logro terminar el nivel , entonces el puntaje va ser mayor o igual a 50
            //y se va iniciar el otro nivel con mayor dificultad, con mas fantasmas y mayor velocidad.
            puntaje += 50;

            if (nro_fantasmas < maxfantasmas) {
                nro_fantasmas++;
            }

            if (velocidad_actual < velocidad_maxima) {
                velocidad_actual++;
            }

            ini_nivel();
        }
    }

    private void muerte() {
        //Esto resta las vidas de pacman.
        vidas_pacman--;
        //Este AudioClip muestra el sonido de pacman_death.wav, solamente cuando pacman muera.
        AudioClip sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/pacman_death.wav"));
        sonido.play();
        //Si el total de las vidad de pacman es igual, entonces entonces el boolean de la clase inicio se torna false.
        if (vidas_pacman == 0) {
            inicio = false;
            JOptionPane.showMessageDialog(null, "Puntaje es: " + puntaje);

        }
        System.out.println(vidas_pacman);
        //sino puede continuar a otro nivel.
        continuar_nivel();
    }

    private void moverfantasmas(Graphics2D g2d) {
        //En esta clase se va revisando cada posicion de los fantasmas y las condiciones de Colision para que 
        //ningun fantasma pueda atravesar paredes.

        short i;
        int pos;
        int c;

        for (i = 0; i < nro_fantasmas; i++) {
            if (fantasma_x[i] % bloque_t == 0 && fastama_y[i] % bloque_t == 0) {
                pos = fantasma_x[i] / bloque_t + nro_bloques * (int) (fastama_y[i] / bloque_t);

                c = 0;

                if ((mapa_recorrido[pos] & 1) == 0 && fantasma_dx[i] != 1) {
                    dir_x[c] = -1;
                    dir_y[c] = 0;
                    c++;
                }

                if ((mapa_recorrido[pos] & 2) == 0 && fantasma_dy[i] != 1) {
                    dir_x[c] = 0;
                    dir_y[c] = -1;
                    c++;
                }

                if ((mapa_recorrido[pos] & 4) == 0 && fantasma_dx[i] != -1) {
                    dir_x[c] = 1;
                    dir_y[c] = 0;
                    c++;
                }

                if ((mapa_recorrido[pos] & 8) == 0 && fantasma_dy[i] != -1) {
                    dir_x[c] = 0;
                    dir_y[c] = 1;
                    c++;
                }

                if (c == 0) {

                    if ((mapa_recorrido[pos] & 15) == 15) {
                        fantasma_dx[i] = 0;
                        fantasma_dy[i] = 0;
                    } else {
                        fantasma_dx[i] = -fantasma_dx[i];
                        fantasma_dy[i] = -fantasma_dy[i];
                    }

                } else {

                    c = (int) (Math.random() * c);

                    if (c > 3) {
                        c = 3;
                    }

                    fantasma_dx[i] = dir_x[c];
                    fantasma_dy[i] = dir_y[c];
                }

            }

            fantasma_x[i] = fantasma_x[i] + (fantasma_dx[i] * velocidad_fantasma[i]);
            fastama_y[i] = fastama_y[i] + (fantasma_dy[i] * velocidad_fantasma[i]);
            drawfantasmas(g2d, fantasma_x[i] + 1, fastama_y[i] + 1);

            if (pac_x > (fantasma_x[i] - 12) && pac_x < (fantasma_x[i] + 12)
                    && pac_y > (fastama_y[i] - 12) && pac_y < (fastama_y[i] + 12)
                    && inicio) {

                morir = true;
            }
        }
    }

    private void drawfantasmas(Graphics2D g2d, int x, int y) {
        //Esto muestra la imagen de los fantasmas.
        g2d.drawImage(fantasma, x, y, this);
    }

    private void moverPacman() {
        //Esto crea la clase de Movmiento de pacman, y revisa las colisiones, y tambien hace la condicion de 
        //incremento del puntaje cuando pacman coma galletas.
        int pos;
        short ch;

        if (c_dx == -pac_dx && c_dy == -pacmandy) {
            pac_dx = c_dx;
            pacmandy = c_dy;
            v_dx = pac_dx;
            v_dy = pacmandy;
        }

        if (pac_x % bloque_t == 0 && pac_y % bloque_t == 0) {
            pos = pac_x / bloque_t + nro_bloques * (int) (pac_y / bloque_t);
            ch = mapa_recorrido[pos];

            if ((ch & 16) != 0) {
                mapa_recorrido[pos] = (short) (ch & 15);
                puntaje += 1;
                AudioClip sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/chomp.wav"));
                //sonido.stop();
                //sonido.play();

            }

            if (c_dx != 0 || c_dy != 0) {
                if (!((c_dx == -1 && c_dy == 0 && (ch & 1) != 0)
                        || (c_dx == 1 && c_dy == 0 && (ch & 4) != 0)
                        || (c_dx == 0 && c_dy == -1 && (ch & 2) != 0)
                        || (c_dx == 0 && c_dy == 1 && (ch & 8) != 0))) {
                    pac_dx = c_dx;
                    pacmandy = c_dy;
                    v_dx = pac_dx;
                    v_dy = pacmandy;
                }
            }

            if ((pac_dx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pac_dx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pac_dx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pac_dx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pac_dx = 0;
                pacmandy = 0;
            }
        }
        pac_x = pac_x + pacman_velocidad * pac_dx;
        pac_y = pac_y + pacman_velocidad * pacmandy;
    }

    private void drawPacman(Graphics2D g2d) {
//Aqui se dibuja el pacman dependiendo de la direccion de los EJES.
        if (v_dx == -1) {
            drawPacmanIzq(g2d);
        } else if (v_dx == 1) {
            drawPacmanDer(g2d);
        } else if (v_dy == -1) {
            drawPacmanArriba(g2d);
        } else {
            drawPacmanAbajo(g2d);
        }
    }

    private void drawPacmanArriba(Graphics2D g2d) {
        //Esta clase junta todas las las imagenes relacionados con el movimiento de pacman hacia Arriba.
        switch (pacmananim_pos) {
            case 1:
                g2d.drawImage(pacman2A, pac_x + 1, pac_y + 1, this);

                break;
            case 2:
                g2d.drawImage(pacman3A, pac_x + 1, pac_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4A, pac_x + 1, pac_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pac_x + 2, pac_y + 2, this);
                break;
        }
    }

    private void drawPacmanAbajo(Graphics2D g2d) {
//Esta clase junta todas las imagenes relacionadas con el movimiento de pacman hacia Abajo.
        switch (pacmananim_pos) {
            case 1:
                g2d.drawImage(pacman2b, pac_x + 1, pac_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3b, pac_x + 1, pac_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4b, pac_x + 1, pac_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pac_x + 2, pac_y + 2, this);
                break;
        }
    }

    private void drawPacmanIzq(Graphics2D g2d) {
//Esta clase junta todas las imagenes relacionadas con el movimiento de pacman hacia la izquierda.
        switch (pacmananim_pos) {
            case 1:
                g2d.drawImage(pacman2izq, pac_x + 1, pac_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3izq, pac_x + 1, pac_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4izq, pac_x + 1, pac_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pac_x + 2, pac_y + 2, this);
                break;
        }
    }

    private void drawPacmanDer(Graphics2D g2d) {
//Esta clase junta todas las imagenes relacionadas con el movimiento de pacman la derecha.
        switch (pacmananim_pos) {
            case 1:
                g2d.drawImage(pacman2der, pac_x + 1, pac_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3der, pac_x + 1, pac_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4der, pac_x + 1, pac_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pac_x + 2, pac_y + 2, this);
                break;
        }
    }

    private void drawMapa(Graphics2D g2d) {
        //Esto clase dibuja el mapa, y los bloques dependiendo de la matriz creada al principio, utilizando recursividad.

        short i = 0;
        int x, y;

        for (y = 0; y < scr_t; y += bloque_t) {
            for (x = 0; x < scr_t; x += bloque_t) {

                g2d.setColor(color_mapa);
                g2d.setStroke(new BasicStroke(2));

                if ((mapa_recorrido[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + bloque_t - 1);
                }

                if ((mapa_recorrido[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + bloque_t - 1, y);

                }

                if ((mapa_recorrido[i] & 4) != 0) {
                    g2d.drawLine(x + bloque_t - 1, y, x + bloque_t - 1,
                            y + bloque_t - 1);
                }

                if ((mapa_recorrido[i] & 8) != 0) {
                    g2d.drawLine(x, y + bloque_t - 1, x + bloque_t - 1,
                            y + bloque_t - 1);
                }

                if ((mapa_recorrido[i] & 16) != 0) {
                    g2d.setColor(galletas);
                    g2d.fillOval(x + 11, y + 11, 8, 8);
                }

                i++;
            }
        }
    }

    private void ini_juego() {
//Esto hace que el juego inicie con los variables de vida, puntaje, nro_fantasmas, velocidad_actual, luego esto pasa
        //a ini_nivel.
        vidas_pacman = 3;
        puntaje = 0;
        ini_nivel();
        nro_fantasmas = 4;
        velocidad_actual = 3;
    }

    private void ini_nivel() {
        //Esto lee el mapa y obtiene los datos para poder dibujar el mapa.
        int i;
        for (i = 0; i < nro_bloques * nro_bloques; i++) {
            mapa_recorrido[i] = level_map[i];
        }
        //Luego esto hace el llamado a la clase continuar_nivel();
        continuar_nivel();
    }

    private void continuar_nivel() {
//En esta clase es donde pasa todo lo relacionado del juego, y aqui hace el llamado de metodos.
        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nro_fantasmas; i++) {

            fastama_y[i] = 4 * bloque_t;
            fantasma_x[i] = 4 * bloque_t;
            fantasma_dy[i] = 0;
            fantasma_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (velocidad_actual + 1));

            if (random > velocidad_actual) {
                random = velocidad_actual;
            }

            velocidad_fantasma[i] = velocidad_valida[random];
        }

        pac_x = 7 * bloque_t;
        pac_y = 11 * bloque_t;
        pac_dx = 0;
        pacmandy = 0;
        c_dx = 0;
        c_dy = 0;
        v_dx = -1;
        v_dy = 0;
        morir = false;
    }

    private void Imagenes() {
//Esta clase hace el llamado de las imagenes.
        fantasma = new ImageIcon(getClass().getResource("../images/ghost.png")).getImage();
        pacman1 = new ImageIcon(getClass().getResource("../images/pacman.gif")).getImage();
        pacman2A = new ImageIcon(getClass().getResource("../images/up1.gif")).getImage();
        pacman3A = new ImageIcon(getClass().getResource("../images/up2.gif")).getImage();
        pacman4A = new ImageIcon(getClass().getResource("../images/up3.gif")).getImage();
        pacman2b = new ImageIcon(getClass().getResource("../images/down1.gif")).getImage();
        pacman3b = new ImageIcon(getClass().getResource("../images/down2.gif")).getImage();
        pacman4b = new ImageIcon(getClass().getResource("../images/down3.gif")).getImage();
        pacman2izq = new ImageIcon(getClass().getResource("../images/left1.gif")).getImage();
        pacman3izq = new ImageIcon(getClass().getResource("../images/left2.gif")).getImage();
        pacman4izq = new ImageIcon(getClass().getResource("../images/left3.gif")).getImage();
        pacman2der = new ImageIcon(getClass().getResource("../images/right1.gif")).getImage();
        pacman3der = new ImageIcon(getClass().getResource("../images/right2.gif")).getImage();
        pacman4der = new ImageIcon(getClass().getResource("../images/right3.gif")).getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dibujos_pro(g);
    }

    private void Dibujos_pro(Graphics g) {
        //En esta clase es donde se hace todo lo grafico, y los llamados 
        //de las clases que utilizan Graphics.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(10, 0, dim.width, dim.height);

        drawMapa(g2d);
        Puntaje(g2d);
        Animacion();

        if (inicio) {
            Jugar(g2d);
        } else {
            PantallaInicial(g2d);
        }

        //g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            //En esta clase esta lo relacionado con las teclas que se van utlizar para el movimiento
            //de pacman en el juego.
            int key = e.getKeyCode();

            if (inicio) {
                if (key == KeyEvent.VK_LEFT) {
                    c_dx = -1;
                    c_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    c_dx = 1;
                    c_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    c_dx = 0;
                    c_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    c_dx = 0;
                    c_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inicio = false;
                } else if (key == KeyEvent.VK_SPACE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else if (key == '1' || key == '1') {
                //Esto es para el inicio del juego, si el usuario apreta el 1 entonces se inicia el juego
                // y empieza a sonar el sonido pacman_beginning.wav.
                inicio = true;
                ini_juego();
                AudioClip sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/pacman_beginning.wav"));
                sonido.play();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //Esta clase es para cuando el usuario deje de presionar la tecla.
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                c_dx = 0;
                c_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
