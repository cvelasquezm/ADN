package com.dna.analyzer.service;

import com.dna.analyzer.common.PersonType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AnalyzerService {
        private int sizeMatrix = 0;
        private int indexLimitMatrix = 0;
        private char[][] dna;
        private int numOfSequencesFound = 0;
        private final int maxNumCharacterOfSeq = 4;


        public PersonType analyzeDNA(String[] sequences){
                sizeMatrix = sequences.length;
                indexLimitMatrix = sizeMatrix - 1;
                dna = convertToMatrix(sequences);

                return isMutant(dna) ? PersonType.MUTANT : PersonType.HUMAN;
        }

        private char[][] convertToMatrix(String[] sequences) {
                char[][] dnaTable = new char[sizeMatrix][sizeMatrix];

                for (int i = 0; i < sizeMatrix; i++) {
                        dnaTable[i] = sequences[i].toCharArray();
                }

                //print(dnaTable);

                return dnaTable;
        }

        private void print(char[][] matrix) {
                for (int row = 0; row < matrix.length ;  row ++) {
                        for (int column = 0; column < matrix[0].length ; column ++) {
                                System.out.print("[" + matrix[row][column] + "]\t");
                        }
                        System.out.println();
                }
        }

        private boolean isMutant(char[][] dna) {

                for (int i = 0; i < dna.length; i++) {
                        for (int j = 0; j < dna[0].length; j++) {
                                char character = dna[i][j];
                                numOfSequencesFound += findMutantSequence(i, j, character) ? 1 : 0;
                        }
                }
                return numOfSequencesFound > 1;
        }

        private boolean findMutantSequence(int x, int y, char character){
                final List<Point> checkableHorizontalPositions = getCheckableHorizontalPositions(x, y);
                final List<Point> checkableVerticalPositions = getCheckableVerticalPositions(x, y);
                final List<Point> checkableObliquePositions = getCheckableObliquePositions(x, y);

                return checkPositions(checkableHorizontalPositions, character) || checkPositions(checkableVerticalPositions, character) || checkPositions(checkableObliquePositions, character);
        }

        private boolean checkPositions(List<Point> checkablePositions, char character) {
                final Predicate<Point> predicate = point -> dna[point.getX()][point.getY()] == character;
                return checkablePositions.stream().filter(predicate).count() == (maxNumCharacterOfSeq - 1);
        }

        private List<Point> getCheckableHorizontalPositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (column + (maxNumCharacterOfSeq - 1) <= indexLimitMatrix) {
                        points.add(new Point(row, column + 1));
                        points.add(new Point(row, column + 2));
                        points.add(new Point(row, column + 3));
                }

                return points;
        }

        private List<Point> getCheckableVerticalPositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (row + (maxNumCharacterOfSeq - 1) <= indexLimitMatrix) {
                        points.add(new Point(row + 1, column));
                        points.add(new Point(row + 2, column));
                        points.add(new Point(row + 3, column));
                }

                return points;
        }

        private List<Point> getCheckableObliquePositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (column + (maxNumCharacterOfSeq - 1) <= indexLimitMatrix && row + (maxNumCharacterOfSeq - 1) <= indexLimitMatrix) {
                        points.add(new Point(row + 1, column + 1));
                        points.add(new Point(row + 2, column + 2));
                        points.add(new Point(row + 3, column + 3));
                }

                if (column - (maxNumCharacterOfSeq - 1) >= 0 && row + (maxNumCharacterOfSeq - 1) <= indexLimitMatrix) {
                        points.add(new Point(row + 1, column - 1));
                        points.add(new Point(row + 2, column - 2));
                        points.add(new Point(row + 3, column - 3));
                }

                return points;
        }

        private class Point {

                private int x;
                private int y;

                public Point(int x, int y) {
                        this.x = x;
                        this.y = y;
                }

                public int getX() {
                        return x;
                }

                public int getY() {
                        return y;
                }

                @Override public String toString() {
                        return "Point{" + "x=" + x + ", y=" + y + '}';
                }
        }
}
