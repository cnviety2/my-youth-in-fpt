/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dell
 */
public class HistoryQuizDTO {

    private int score;
    private String id;
    private String userEmail;
    private int subjectID;
    private Map<String, Integer> mapStudentAnswers;
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    

    public HistoryQuizDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HistoryQuizDTO(String id, int score, String userEmail, int subjectID) {
        this.score = score;
        this.userEmail = userEmail;
        this.subjectID = subjectID;
        this.id = id;
        this.mapStudentAnswers = new HashMap<>();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public Map<String, Integer> getMapStudentAnswers() {
        return mapStudentAnswers;
    }

    public void setMapStudentAnswers(Map<String, Integer> mapStudentAnswers) {
        this.mapStudentAnswers = mapStudentAnswers;
    }

}
