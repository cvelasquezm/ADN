package com.dna.analyzer.service;

import com.dna.analyzer.common.PersonType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnalyzerServiceTest {

        @InjectMocks
        AnalyzerService service;


        @Test
        public void testValidateMutantWithHorizontalAndVerticalAndOblique() {
                String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
                final PersonType personType = service.analyzeDNA(dna);

                Assertions.assertEquals(PersonType.MUTANT, personType);
        }

        @Test
        public void testValidateMutantWithHorizontalAndInverseOblique() {
                String[] dna = {"ATGCGA","CAGGAC","TTGTGT","AGAAGT","CCCCTA","TCACTG"};
                final PersonType personType = service.analyzeDNA(dna);

                Assertions.assertEquals(PersonType.MUTANT, personType);
        }

        @Test
        public void testValidateMutantWithHorizontalAndCrossOblique() {
                String[] dna = {"ATGCGA","CAGGAC","TTGTGT","AGAAGG","CCCCTA","TCACTG"};
                final PersonType personType = service.analyzeDNA(dna);

                Assertions.assertEquals(PersonType.MUTANT, personType);
        }

        @Test
        public void testValidateHuman() {
                String[] dna = {"GTGCGA","CAGTTC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
                final PersonType personType = service.analyzeDNA(dna);

                Assertions.assertEquals(PersonType.HUMAN, personType);
        }
}
