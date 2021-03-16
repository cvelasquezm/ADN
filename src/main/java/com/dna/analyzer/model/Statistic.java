package com.dna.analyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Statistic implements Serializable {

        @JsonProperty("count_mutant_dna")
        private long countMutantDna;
        @JsonProperty("count_human_dna")
        private long countHumanDna;
        private double ratio;

        public Statistic(long countMutantDna, long countHumanDna) {
                this.countMutantDna = countMutantDna;
                this.countHumanDna = countHumanDna;
                this.ratio = countHumanDna == 0 ? 0 : (double) this.countMutantDna / (double) this.countHumanDna;
        }

        public Statistic() {

        }

        public long getCountMutantDna() {
                return countMutantDna;
        }

        public long getCountHumanDna() {
                return countHumanDna;
        }

        public double getRatio() {
                return ratio;
        }
}
