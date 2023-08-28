package com.striker.githubapi.entity;


public class RepositoryData {

    private String name;

    private boolean fork;

    public RepositoryData(){

    }

    public RepositoryData(String name, boolean fork) {
        this.name = name;
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    @Override
    public java.lang.String toString() {
        return "RepositoryData{" +
                "name=" + name +
                ", fork=" + fork +
                '}';
    }
}
