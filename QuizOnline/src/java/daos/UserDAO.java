/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Answer;
import dtos.AnswerOfStudentDTO;
import dtos.HistoryQuizDTO;
import dtos.Question;
import dtos.QuestionHistoryQuizDTO;
import dtos.SearchQuestionDTO;
import dtos.Subject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class UserDAO {
    
    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(GeneralDAO.class);
    
    public UserDAO() {
        logger.addAppender(new ConsoleAppender(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p %c{1}: %C %M - %m%n")));
    }
    
    private void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (stment != null) {
            stment.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
    
    public List<SearchQuestionDTO> getQuestionsForQuiz(int quantity, int subjectID) throws SQLException {
        List<SearchQuestionDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT id,question_content FROM tbl_questions WHERE subject_id = ? AND status = 'true'";
                stment = conn.prepareStatement(sql);
                stment.setInt(1, subjectID);
                rs = stment.executeQuery();
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getString("id"));
                    question.setQuestionContent(rs.getString("question_content"));
                    SearchQuestionDTO dto = new SearchQuestionDTO(question, null);
                    result.add(dto);
                }
                sql = "SELECT id,answer_content FROM tbl_answers WHERE question_id = ?";
                stment = conn.prepareStatement(sql);
                for (SearchQuestionDTO dto : result) {
                    stment.setString(1, dto.getQuestion().getId());
                    rs = stment.executeQuery();
                    List<Answer> listAnswer = new ArrayList<>();
                    while (rs.next()) {
                        Answer answer = new Answer();
                        answer.setId(rs.getInt("id"));
                        answer.setAnswerContent(rs.getString("answer_content"));
                        answer.setQuestionID(dto.getQuestion().getId());
                        listAnswer.add(answer);
                    }
                    dto.setListAnswers(listAnswer);
                }
            }
            Collections.shuffle(result);
            
        } catch (Exception e) {
            logger.error("Error at getQuestionsForQuiz in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public Map<String, Integer> getCorrectAnswersByQuestionIDs(List<String> listQuestionID) throws SQLException {
        Map<String, Integer> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT id FROM tbl_answers WHERE question_id = ? AND is_correct = 'true'";
                result = new HashMap<>();
                stment = conn.prepareStatement(sql);
                for (String questionID : listQuestionID) {
                    stment.setString(1, questionID);
                    rs = stment.executeQuery();
                    if (rs.next()) {
                        result.put(questionID, rs.getInt("id"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at getCorrectAnswersByQuestionIDs in UserDAO:" + e.getMessage());
            
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean saveHistory(HistoryQuizDTO history) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_history_quiz(create_date,score,user_email,subject_id,id) VALUES(?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(1, now);
                stment.setInt(2, history.getScore());
                stment.setString(3, history.getUserEmail());
                stment.setInt(4, history.getSubjectID());
                String id = UUID.randomUUID().toString();
                stment.setString(5, id);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    sql = "INSERT INTO tbl_answer_of_user(history_id,question_id,answer_id_of_user) VALUES(?,?,?)";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, id);
                    for (Map.Entry<String, Integer> entry : history.getMapStudentAnswers().entrySet()) {
                        stment.setString(2, entry.getKey());
                        if (entry.getValue() == null) {
                            stment.setInt(3, -1);
                        } else {
                            stment.setInt(3, entry.getValue());
                        }
                        stment.executeUpdate();
                    }
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at saveHistory in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<QuestionHistoryQuizDTO> processForHistoryViewing(String email, Integer subjectID, Integer page) throws SQLException {
        List<QuestionHistoryQuizDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT id,create_date,score,subject_id FROM tbl_history_quiz WHERE user_email = ?";
                boolean subjectFlag = false;
                if (subjectID != null) {
                    sql = sql.concat(" AND subject_id = ?");
                    subjectFlag = true;
                }
                sql = sql.concat(" ORDER BY create_date ASC OFFSET ? ROWS FETCH NEXT 1 ROWS ONLY");
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                if (subjectFlag) {
                    stment.setInt(2, subjectID);
                    stment.setInt(3, page);
                } else {
                    stment.setInt(2, page);
                }
                rs = stment.executeQuery();
                while (rs.next()) {
                    HistoryQuizDTO history = new HistoryQuizDTO();
                    history.setId(rs.getString("id"));
                    history.setCreateDate(rs.getDate("create_date"));
                    history.setScore(rs.getInt("score"));
                    history.setSubjectID(rs.getInt("subject_id"));
                    QuestionHistoryQuizDTO dto = new QuestionHistoryQuizDTO();
                    dto.setHistory(history);
                    result.add(dto);
                }
                for (QuestionHistoryQuizDTO dto : result) {
                    List<AnswerOfStudentDTO> answersOfStudent = new ArrayList<>();
                    sql = "SELECT question_id,answer_id_of_user,q.question_content FROM tbl_answer_of_user aou JOIN tbl_questions q\n"
                            + "  ON aou.question_id = q.id"
                            + " WHERE history_id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, dto.getHistory().getId());
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        AnswerOfStudentDTO answerDTO = new AnswerOfStudentDTO();
                        answerDTO.setQuestionID(rs.getString("question_id"));
                        answerDTO.setAnswerID(rs.getInt("answer_id_of_user"));
                        answerDTO.setQuestionContent(rs.getString("question_content"));
                        answersOfStudent.add(answerDTO);
                    }
                    dto.setAnswersOfStudent(answersOfStudent);
                }
            }
        } catch (Exception e) {
            logger.error("Error at processForHistoryViewing in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<Answer> getAllAnswersByQuestionID(List<String> listID) throws SQLException {
        List<Answer> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT answer_content,id,question_id FROM tbl_answers WHERE question_id = ?";
                stment = conn.prepareStatement(sql);
                for (String id : listID) {
                    stment.setString(1, id);
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        Answer answer = new Answer();
                        answer.setAnswerContent(rs.getString("answer_content"));
                        answer.setId(rs.getInt("id"));
                        answer.setQuestionID(rs.getString("question_id"));
                        result.add(answer);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at getAllAnswersByQuestionID in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int getAllRecordsOfAUser(String email, Integer subjectID) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT id FROM tbl_history_quiz WHERE user_email = ?";
                boolean subjectFlag = false;
                if (subjectID != null) {
                    sql = sql.concat(" AND subject_id = ?");
                    subjectFlag = true;
                }
                stment = conn.prepareStatement(sql);
                stment.setString(1, email);
                if (subjectFlag) {
                    stment.setInt(2, subjectID);
                }
                rs = stment.executeQuery();
                while (rs.next()) {
                    result++;
                }
            }
        } catch (Exception e) {
            logger.error("Error at getAllRecordsOfAUser in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String sql = "SELECT id,subject FROM tbl_subjects";
                stment = conn.prepareStatement(sql);
                rs = stment.executeQuery();
                while (rs.next()) {
                    result.add(new Subject(rs.getInt("id"), rs.getString("subject")));
                }
            }
            
        } catch (Exception e) {
            logger.error("Error at getAllSubjects in UserDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }
}
