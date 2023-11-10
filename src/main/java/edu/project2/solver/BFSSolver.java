package edu.project2.solver;

import edu.project2.maze_sekelet.Cell;
import edu.project2.maze_sekelet.Coordinate;
import edu.project2.maze_sekelet.Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSSolver implements Solver {

    private final int height;
    private final int width;
    private final Cell[][] grid;
    private boolean[][] visited;
    private Queue<Coordinate> queue;
    private int[][] parentRow;
    private int[][] parentCol;
    private final int[] directionRow;
    private final int[] directionCol;

    public BFSSolver(Maze maze) {
        height = maze.getHeight();
        width = maze.getWidth();
        grid = maze.getGrid();

        directionRow = new int[]{-1, 1, 0, 0};
        directionCol = new int[]{0, 0, -1, 1};
    }

    private BFSSolver() {
        grid = null;
        height = Integer.MIN_VALUE;
        width = Integer.MIN_VALUE;
        directionRow = null;
        directionCol = null;
    }

    @Override
    public List<Coordinate> solve(Coordinate start, Coordinate end) {

        initializer();

        int startRow = start.row();
        int startCol = start.col();

        int endRow = end.row();
        int endCol = end.col();

        queue.add(new Coordinate(startRow, startCol));
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int currentRow = current.row();
            int currentCol = current.col();

            if (currentRow == endRow && currentCol == endCol) {

                List<Coordinate> path = new ArrayList<>();
                while (currentRow != startRow || currentCol != startCol) {

                    path.add(0, new Coordinate(currentRow, currentCol));

                    int nextRow = parentRow[currentRow][currentCol];
                    int nextCol = parentCol[currentRow][currentCol];

                    currentRow = nextRow;
                    currentCol = nextCol;
                }

                path.add(0, new Coordinate(startRow, startCol));

                return path;
            }

            checkNeighbors(currentRow, currentCol);

        }


        return List.of();
    }

    private void initializer() {
        visited = new boolean[height][width];
        queue = new LinkedList<>();
        parentRow = new int[height][width];
        parentCol = new int[height][width];
    }

    private void checkNeighbors(int currentRow, int currentCol) {
        for (int i = 0; i < directionCol.length; i++) {
            int newRow = currentRow + directionRow[i];
            int newCol = currentCol + directionCol[i];

            if (checkThatCellInTHeMaze(newRow, newCol)
                    && !visited[newRow][newCol]
                    && checkThatCellIsPassage(newRow, newCol)
            ) {

                queue.add(new Coordinate(newRow, newCol));
                visited[newRow][newCol] = true;

                parentRow[newRow][newCol] = currentRow;
                parentCol[newRow][newCol] = currentCol;
            }
        }
    }

    private boolean checkThatCellInTHeMaze(int newRow, int newCol) {
        return newRow >= 0 && newRow < height && newCol >= 0 && newCol < width;
    }

    private boolean checkThatCellIsPassage(int newRow, int newCol) {
        return grid[newRow][newCol].type() == Cell.Type.PASSAGE;
    }
}