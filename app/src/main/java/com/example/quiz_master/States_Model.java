package com.example.quiz_master;

public class States_Model {
    private int total_question,Right_answer,Wrong_Answer,Total_points;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public States_Model(){}

    public int getTotal_question() {
        return total_question;
    }

    public void setTotal_question(int total_question) {
        this.total_question = total_question;
    }

    public int getRight_answer() {
        return Right_answer;
    }

    public void setRight_answer(int right_answer) {
        Right_answer = right_answer;
    }

    public int getWrong_Answer() {
        return Wrong_Answer;
    }

    public void setWrong_Answer(int wrong_Answer) {
        Wrong_Answer = wrong_Answer;
    }

    public int getTotal_points() {
        return Total_points;
    }

    public void setTotal_points(int total_points) {
        Total_points = total_points;
    }
}
