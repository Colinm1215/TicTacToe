import java.util.ArrayList;

public class Bot extends Player {

    @SuppressWarnings("SameParameterValue")
    Bot(int botChar, int enemyChar) {
        super(botChar, enemyChar);
    }

    @Override
    public int[] chooseMove(int[][] game) {
        return miniMax(game, 0, getPlayerChar());
    }

    private int gameWin(int[][] game, int player) {
        // 0 = not end state
        // 1 = win
        // 2 = tie
        if (emptyIndexies(game).size() == 0) {
            return 2;
        }

        for (int[] r : game) {
            if (r[0] == r[1]
                    && r[1] == r[2]
                    && r[0] == player) {
                return 1;
            }
        }
        for (int c = 0; c < game[0].length; c++) {
            if (game[0][c] == game[1][c]
                    && game[1][c] == game[2][c]
                    && game[0][c] == player) {
                return 1;
            }
        }
        if (game[0][0] == game[1][1]
                && game[1][1] == game[2][2]
                && game[1][1] == player) {
            return 1;
        } else if (game[2][0] == game[1][1]
                && game[1][1] == game[0][2]
                && game[1][1] == player) return 1;
        return 0;
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

    private int[] miniMax(int[][] game, int depth, int player) { // return int[3] of score, row, col
        ArrayList<int[]> moves = new ArrayList<int[]>();
        ArrayList<int[]> empties = emptyIndexies(game);
        depth += 1;
        if (gameWin(game, getPlayerChar()) == 1) {
            return new int[]{100};
        } else if (gameWin(game, getEnemyChar()) == 1) {
            return new int[]{-100};
        } else if (gameWin(game, getPlayerChar()) == 2
                || gameWin(game, getEnemyChar()) == 2) return new int[]{50};

        for (int[] spot : empties) {

            int[] move = new int[3];
            move[1] = spot[0];
            move[2] = spot[1];
            game[spot[0]][spot[1]] = player;

            if (player == getPlayerChar()) {
                move[0] = miniMax(game, depth, getEnemyChar())[0];
            } else {
                move[0] = miniMax(game, depth, getPlayerChar())[0];
            }
            game[spot[0]][spot[1]] = 0;
            moves.add(move);
        }


        int bestMove = 0;
        if (player == getPlayerChar()) {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i)[0] > bestScore) {
                    bestScore = moves.get(i)[0];
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i)[0] < bestScore) {
                    bestScore = moves.get(i)[0];
                    bestMove = i;
                }
            }
        }

        return moves.get(bestMove);
    }
}
