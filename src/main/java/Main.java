public class Main {

    public static void main(String[] args) throws InterruptedException {
        GameFrame game = new GameFrame();
        game.setVisible(true);
        while(true) {
            Thread.sleep(100);
            game.repaint();
        }
    }
}
