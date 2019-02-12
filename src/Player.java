class Player {
    private final int playerChar;
    private final int enemyChar;

    Player(int playerChar, int enemyChar) {
        this.playerChar = playerChar;
        this.enemyChar = enemyChar;
    }

    int getPlayerChar() {
        return playerChar;
    }

    int getEnemyChar() {
        return enemyChar;
    }

    public int[] chooseMove(int[][] game) {
        return new int[]{0};
    }
}