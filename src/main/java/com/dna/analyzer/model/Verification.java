package com.dna.analyzer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Table
@Entity
public class Verification implements Serializable {

        @Column
        @Id
        private String id = UUID.randomUUID().toString();
        @Column
        private boolean isHuman;

        public Verification() {

        }

        public Verification(boolean isHuman) {
                this.isHuman = isHuman;
        }

        public String getId() {
                return id;
        }

        public boolean isHuman() {
                return isHuman;
        }
}
