/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author dell
 */
public class QuestionHistoryQuizDTO {

    private HistoryQuizDTO history;
    private List<AnswerOfStudentDTO> answersOfStudent;

    public QuestionHistoryQuizDTO() {
    }

    public QuestionHistoryQuizDTO(HistoryQuizDTO history, List<AnswerOfStudentDTO> answersOfStudent) {
        this.history = history;
        this.answersOfStudent = answersOfStudent;
    }

    public List<AnswerOfStudentDTO> getAnswersOfStudent() {
        return answersOfStudent;
    }

    public void setAnswersOfStudent(List<AnswerOfStudentDTO> answersOfStudent) {
        this.answersOfStudent = answersOfStudent;
    }
    
    

    public HistoryQuizDTO getHistory() {
        return history;
    }

    public void setHistory(HistoryQuizDTO history) {
        this.history = history;
    }


}
