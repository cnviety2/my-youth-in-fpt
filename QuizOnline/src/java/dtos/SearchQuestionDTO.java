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
public class SearchQuestionDTO {
    
    private Question question;
    private List<Answer> listAnswers;

    public SearchQuestionDTO() {
    }

    public SearchQuestionDTO(Question question, List<Answer> listAnswers) {
        this.question = question;
        this.listAnswers = listAnswers;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<Answer> listAnswers) {
        this.listAnswers = listAnswers;
    }
    
    
    
}
