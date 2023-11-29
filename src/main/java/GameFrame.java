import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class GameFrame extends Frame {

    static int WIDTH = 800;
    static int HEIGHT = 600;
    boolean[][] gameBoard;
    RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_COLOR_RENDERING,
            RenderingHints.VALUE_RENDER_SPEED
    );

    public GameFrame(){
        super("Conway's Game of Life");
        gameBoard = new boolean[HEIGHT][WIDTH];
        Random r = new Random();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                gameBoard[i][j] = r.nextBoolean();
            }
        }
        prepareGUI();
    }

    private void prepareGUI(){
        setSize(WIDTH, HEIGHT);
        super.setBackground(Color.BLACK);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    private void calculateNewBoardState() {
        boolean[][] newGameBoard = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                var currentCellIsAlive = gameBoard[i][j];
                int aliveNeighbors = 0;
                for (int y = i-1; y <= i+1; y++) {
                    for (int x = j-1 ; x <= j+1; x++) {
                        if (!isCurrentCell(i, j, x, y) && isWithinBoardLimits(x, y)) {
                            var surroundingCellIsAlive = gameBoard[y][x];
                            if (surroundingCellIsAlive) {
                                aliveNeighbors++;
                            }
                        }
                    }
                }
                if (currentCellIsAlive) {
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        currentCellIsAlive = false;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        currentCellIsAlive = true;
                    }
                }
                newGameBoard[i][j] = currentCellIsAlive;
            }
        }
        this.gameBoard = newGameBoard;
    }

    private boolean isCurrentCell(int i, int j, int x, int y) {
        return (i == y && j == x);
    }

    private boolean isWithinBoardLimits(int x, int y) {
        return (x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        var g2d = (Graphics2D)g;
        g2d.setRenderingHints(rh);
        calculateNewBoardState();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                g2d.setColor(gameBoard[i][j] ? Color.BLACK : Color.WHITE);
                g2d.drawRect(j, i, 0, 0);
            }
        }
    }
}
