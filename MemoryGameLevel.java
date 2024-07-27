public class MemoryGameLevel {
    int rowCount;
    int colCount;
    int maxFlips;

    int currentLevel;

    public MemoryGameLevel(int level) {
        this.currentLevel = level;
        initializeLevel();
    }

    private void initializeLevel() {
        switch (currentLevel) {
            case 1:
                rowCount = 2;
                colCount = 2;
                maxFlips = Integer.MAX_VALUE; // Unlimited flips
                break;
            case 2:
                rowCount = 3;
                colCount = 4;
                maxFlips = 20;
                break;
            case 3:
                rowCount = 4;
                colCount = 4;
                maxFlips = 20;
                break;
            case 4:
                rowCount = 4;
                colCount = 5;
                maxFlips = 30;
                break;
            case 5:
                rowCount = 5;
                colCount = 6;
                maxFlips = 35;
                break;
            case 6:
                rowCount = 6;
                colCount = 6;
                maxFlips = 50;
                break;
            default:
                rowCount = 3;
                colCount = 2;
                maxFlips = Integer.MAX_VALUE;
                break;
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getMaxFlip() {
        return maxFlips;
    }
}
