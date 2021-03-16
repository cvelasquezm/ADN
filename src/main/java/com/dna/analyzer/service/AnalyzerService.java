package com.dna.analyzer.service;

import com.dna.analyzer.common.PersonType;
import com.dna.analyzer.database.VerificationRepository;
import com.dna.analyzer.model.Statistic;
import com.dna.analyzer.model.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AnalyzerService {
        private int sizeMatrix = 0;
        private int indexLimitMatrix = 0;
        private char[][] dna;
        private final int maxNumCharacterOfSeq = 4;
        private final int missingCharsToCompleteChain = maxNumCharacterOfSeq - 1;

        @Autowired
        VerificationRepository repository;

        public Statistic getStats() {

                final List<Verification> allVerifications = repository.findAll();

                if (allVerifications.isEmpty()) {
                        return new Statistic(0, 0);
                }

                final long humans = allVerifications.stream().filter(verification -> verification.isHuman() == true).count();
                final long mutants = allVerifications.size() - humans;

                return new Statistic(mutants, humans);
        }

        public PersonType analyzeDNA(String[] sequences){
                sizeMatrix = sequences.length;
                indexLimitMatrix = sizeMatrix - 1;
                dna = convertToMatrix(sequences);
                final boolean isMutant = isMutant(dna);

                //Save the verification
                repository.save(new Verification(!isMutant));

                return isMutant ? PersonType.MUTANT : PersonType.HUMAN;
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

        //for each position search mutant sequences
        private boolean isMutant(char[][] dna) {
                int numOfSequencesFound = 0;
                for (int i = 0; i < dna.length; i++) {
                        for (int j = 0; j < dna[0].length; j++) {
                                char character = dna[i][j];
                                numOfSequencesFound += findMutantSequence(i, j, character) ? 1 : 0;
                        }
                }
                return numOfSequencesFound > 1;
        }

        private boolean findMutantSequence(int x, int y, char character){
                //Get all checkable positions
                final List<Point> checkableHorizontalPositions = getCheckableHorizontalPositions(x, y);
                final List<Point> checkableVerticalPositions = getCheckableVerticalPositions(x, y);
                final List<Point> checkableObliquePositions = getCheckableObliquePositions(x, y);

                //Check the positions and return the result
                return positionMadeAChain(checkableHorizontalPositions, character) ||
                        positionMadeAChain(checkableVerticalPositions, character) ||
                        positionMadeAChain(checkableObliquePositions, character);
        }

        private boolean positionMadeAChain(List<Point> checkablePositions, char character) {
                final Predicate<Point> predicate = point -> dna[point.getX()][point.getY()] == character;
                return checkablePositions.stream().filter(predicate).count() == (maxNumCharacterOfSeq - 1);
        }

        private List<Point> getCheckableHorizontalPositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (column + missingCharsToCompleteChain <= indexLimitMatrix) {
                        for (int i = 1; i <= missingCharsToCompleteChain; i++) {
                                points.add(new Point(row, column + i));
                        }
                }

                return points;
        }

        private List<Point> getCheckableVerticalPositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (row + missingCharsToCompleteChain <= indexLimitMatrix) {
                        for (int i = 1; i <= missingCharsToCompleteChain; i++) {
                                points.add(new Point(row + i, column));
                        }
                }

                return points;
        }

        private List<Point> getCheckableObliquePositions(int row, int column) {
                List<Point> points = new ArrayList<>();

                if (column + missingCharsToCompleteChain <= indexLimitMatrix && row + missingCharsToCompleteChain <= indexLimitMatrix) {
                        for (int i = 1; i <= missingCharsToCompleteChain; i++) {
                                points.add(new Point(row + i, column + i));
                        }
                }

                if (column - missingCharsToCompleteChain >= 0 && row + missingCharsToCompleteChain <= indexLimitMatrix) {
                        for (int i = 1; i <= missingCharsToCompleteChain; i++) {
                                points.add(new Point(row + i, column - i));
                        }
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
