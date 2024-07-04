package com.example.task5.demo;

public class User {

        private int id;
        private String name;
        private String address;
        private int age;

        public User(int id, String name, String address, int age) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.age = age;
        }

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        @Override
        public String toString() {
            return String.format(
                    "{\"id\": %d, \"name\": \"%s\", \"address\": \"%s\", \"age\": %d}",
                    id, name, address, age
            );
        }
}

