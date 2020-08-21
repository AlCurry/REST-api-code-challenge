package com.example.demo;


import com.fasterxml.jackson.annotation.JsonTypeId;

public class Field {

        private String id;
        private String name;

        public Field() {
        }

        public Field(String id, String name) {
            this.id = id;
            this.name = name;          
        }

        public Field(String id) {
            this.id = id;
        }
        public String getId() {
           return id;
        }
        public String getName() {
            return name;
        }
}
