public class ZarrayCompare {
    private int[] answer;
    private int poss[][];

    public ZarrayCompare(int[] ans, int[][] possibilities) {
        answer = new int[ans.length];
        for (int h = 0; h < ans.length; h++) {
            answer[h] = ans[h];
        }
        poss = new int[possibilities.length][possibilities[0].length];
        for (int i = 0; i < possibilities.length; i++) {
            for (int j = 0; j < possibilities[0].length; j++) {
                poss[i][j] = possibilities[i][j];
            }
        }
    }

    public ZarrayCompare(double[] ans, double[][] possibilities) {
        answer = new int[ans.length];
        for (int h = 0; h < ans.length; h++) {
            answer[h] = (int) ans[h];
        }
        poss = new int[possibilities.length][possibilities[0].length];
        for (int i = 0; i < possibilities.length; i++) {
            for (int j = 0; j < possibilities[0].length; j++) {
                poss[i][j] = (int) possibilities[i][j];
            }
        }
    }

    public int getHorShift() {
        int lowestErr = Integer.MAX_VALUE;
        int rowShift = -100;
        for (int m = 0; m < 10; m++) {
            for (int k = 0; k < poss.length; k++) {
                int currentErr = 0;
                for (int l = 0; l < answer.length; l++) {
                    currentErr += (poss[k][l + m] - answer[l]) * (poss[k][l + m] - answer[l]);
                }
                if (currentErr < lowestErr) {
                    lowestErr = currentErr;
                    rowShift = m - 5;
                }
            }
        }
        return rowShift;
    }

    public int getShift() {
        int lowestErr = Integer.MAX_VALUE;
        int rowShift = -100;
        for (int m = 0; m < 10; m++) {
            for (int k = 0; k < poss.length; k++) {
                int currentErr = 0;
                for (int l = 0; l < answer.length; l++) {
                    currentErr += (poss[k][l + m] - answer[l]) * (poss[k][l + m] - answer[l]);
                }
                if (currentErr < lowestErr) {
                    lowestErr = currentErr;
                    rowShift = m - 5;
                }
            }
        }
        return rowShift;
    }

    public int getHorOverlap() {
        int bestRow = -1;
        int lowestErr = Integer.MAX_VALUE;
        for (int m = 0; m < 10; m++) {
            for (int k = 0; k < poss.length; k++) {
                int currentErr = 0;
                for (int l = 0; l < answer.length; l++) {
                    currentErr += (poss[k][l + m] - answer[l]) * (poss[k][l + m] - answer[l]);
                }
                if (currentErr < lowestErr) {
                    lowestErr = currentErr;
                    bestRow = k;
                }
            }
        }
        return bestRow;
    }

    public int getOverlap() {
        int bestRow = -1;
        int lowestErr = Integer.MAX_VALUE;
        for (int m = 0; m < 10; m++) {
            for (int k = 0; k < poss.length; k++) {
                int currentErr = 0;
                for (int l = 0; l < answer.length; l++) {
                    currentErr += (poss[k][l + m] - answer[l]) * (poss[k][l + m] - answer[l]);
                }
                if (currentErr < lowestErr) {
                    lowestErr = currentErr;
                    bestRow = k;
                }
            }
        }
        return bestRow;
    }
}