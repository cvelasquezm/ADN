package com.dna.analyzer.controller;

import com.dna.analyzer.common.PersonType;
import com.dna.analyzer.model.Entry;
import com.dna.analyzer.service.AnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class Analyzer {

        @Autowired
        AnalyzerService analyzerService;

        @RequestMapping(method = RequestMethod.POST, value = "mutant/")
        public ResponseEntity analyzeDNA(@RequestBody Entry entry) {

                if (analyzerService.analyzeDNA(entry.getDNA()).equals(PersonType.MUTANT)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                return ResponseEntity.ok().build();
        }
}
