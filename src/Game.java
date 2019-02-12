import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class Game extends JPanel {
    private final int useBot;
    private final Player player1;
    private final Player player2;
    private final int[][] game = new int[3][3]; // 0 = empty, 1 = X, 2 = O
    private final int[][] lineCoords = new int[][]{
            {260, 0, 260, 800},
            {530, 0, 530, 800},
            {0, 260, 800, 260},
            {0, 530, 800, 530}};
    private final Marker[][] markers = new Marker[3][3];
    private boolean gameOn = true;
    private boolean turn = true; // true = player1, false = player2


    Game(int width, int height) {
        setSize(width, height);
        useBot = JOptionPane.showConfirmDialog(null, "AI?", "Play against an AI?",
                JOptionPane.YES_NO_OPTION);
        if (useBot == 0) { // 0 = JOptionPane yes option
            player1 = new Player(1, 2);
            player2 = new Bot(2, 1);
        } else { // no option
            player1 = new Player(1, 2);
            player2 = new Player(2, 1);
        }
        markers[0][0] = new Marker(20, 20);
        markers[0][1] = new Marker(20, 290);
        markers[0][2] = new Marker(20, 560);

        markers[1][0] = new Marker(290, 20);
        markers[1][1] = new Marker(290, 290);
        markers[1][2] = new Marker(290, 560);

        markers[2][0] = new Marker(560, 20);
        markers[2][1] = new Marker(560, 290);
        markers[2][2] = new Marker(560, 560);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (useBot == 0) { // JOptionPane yes option (using a robot)
                    boxClicked(mouseEvent.getX(), mouseEvent.getY());
                    repaint();
                    int[] move = player2.chooseMove(game);
                    game[move[1]][move[2]] = player2.getPlayerChar();
                } else { // no option (not using a robot)
                    boxClicked(mouseEvent.getX(), mouseEvent.getY());
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private void boxClicked(int x, int y) {
        int r;
        int c;
        if (x >= 0 && x <= 260) { // first row [0][c]
            r = 0;
        } else if (x > 260 && x <= 530) { // second row [1][c]
            r = 1;
        } else { // third row [2][c]
            r = 2;
        }
        if (y >= 0 && y <= 260) { // first column [r][0]
            c = 0;
        } else if (y > 260 && y <= 530) { // second column [r][1]
            c = 1;
        } else { // third column [r][2]
            c = 2;
        }
        if (game[r][c] == 0) {
            if (useBot == 0) {
                game[r][c] = player1.getPlayerChar();
            } else {
                if (turn) {
                    game[r][c] = player1.getPlayerChar();
                    turn = false;
                } else {
                    game[r][c] = player2.getPlayerChar();
                    turn = true;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int r = 0; r < game.length; r++) {
            for (int c = 0; c < game[0].length; c++) {
                if (game[r][c] == 1) {
                    markers[r][c].setMarker("X");
                } else if (game[r][c] == 2) {
                    markers[r][c].setMarker("O");
                }
            }
        }
        g2.setStroke(new BasicStroke(10));
        for (int[] line : lineCoords) {
            g2.drawLine(line[0], line[1], line[2], line[3]);
        }
        g2.setStroke(new BasicStroke(5));
        for (Marker[] markerRow : markers) {
            for (Marker marker : markerRow) {
                marker.draw(g2);
            }
        }
        if (gameOn) {
            int winner = gameWin();
            if (winner == 0) {
                repaint();
            } else if (winner == 1) { // player1 wins
                JOptionPane.showMessageDialog(null, "Player 1 Wins!");
                gameOn = false;
                repaint();
            } else if (winner == 2) { // player2 wins
                JOptionPane.showMessageDialog(null, "Player 2 Wins!");
                gameOn = false;
                repaint();
            } else if (winner == 3) { // tie
                JOptionPane.showMessageDialog(null, "You Tied!");
                gameOn = false;
                repaint();
            }
        }
    }

    private ArrayList<int[]> emptyIndexies(int[][] game) {
        ArrayList<int[]> empties = new ArrayList<int[]>();
        for (int r = 0; r < game.length; r++) {
            for (int c = 0; c < game[0].length; c++) {
                if (game[r][c] == 0) empties.add(new int[]{r, c});
            }
        }
        return empties;
    }

    private int gameWin() {
        // 0 = no win
        // 1 = player1 win
        // 2 = player2 win
        // 3 = tie
        if (emptyIndexies(game).size() == 0) {
            return 3;
        }

        for (int[] r : game) {
            if (r[0] == r[1]
                    && r[1] == r[2]) {
                if (r[0] == player1.getPlayerChar()) {
                    return 1;
                } else if (r[0] == player2.getPlayerChar()) {
                    return 2;
                }
            }
        }
        for (int c = 0; c < game[0].length; c++) {
            if (game[0][c] == game[1][c]
                    && game[1][c] == game[2][c]) {
                if (game[0][c] == player1.getPlayerChar()) {
                    return 1;
                } else if (game[0][c] == player2.getPlayerChar()) {
                    return 2;
                }
            }
        }
        if (game[0][0] == game[1][1]
                && game[1][1] == game[2][2]) {
            if (game[0][0] == player1.getPlayerChar()) {
                return 1;
            } else if (game[0][0] == player2.getPlayerChar()) {
                return 2;
            }
        } else if (game[2][0] == game[1][1]
                && game[1][1] == game[0][2]) {
            if (game[2][0] == player1.getPlayerChar()) {
                return 1;
            } else if (game[2][0] == player2.getPlayerChar()) {
                return 2;
            }
        }
        return 0;
    }
}