/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.Answer;
import dtos.Question;
import dtos.SearchQuestionDTO;
import dtos.Subject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import utils.DBUtils;

/**
 *
 * @author dell
 */
public class AdminDAO {

    private Connection conn;
    private PreparedStatement stment;
    private ResultSet rs;
    private final Logger logger = Logger.getLogger(GeneralDAO.class);

    public AdminDAO() {
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

    public boolean updateQuestion(Question question, List<Answer> listAnswer) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_questions SET question_content = ?,subject_id = ?,update_date = ?,status = 'true' WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, question.getQuestionContent());
                stment.setInt(2, question.getSubjectID());
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(3, now);
                stment.setString(4, question.getId());
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    for (Answer answer : listAnswer) {
                        sql = "UPDATE tbl_answers SET answer_content = ?,is_correct = ? WHERE question_id = ? AND id = ?";
                        stment = conn.prepareStatement(sql);
                        stment.setString(1, answer.getAnswerContent());
                        if (answer.isIsCorrect()) {
                            stment.setString(2, "true");
                        } else {
                            stment.setString(2, "false");
                        }
                        stment.setString(3, question.getId());
                        stment.setInt(4, answer.getId());
                        stment.executeUpdate();
                    }
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at updateQuestion in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean createNewQuestion(Question question, List<Answer> listAnswer) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_questions(question_content,"
                        + "subject_id,"
                        + "status,"
                        + "create_date,"
                        + "id) VALUES(?,?,?,?,?)";
                stment = conn.prepareStatement(sql);
                stment.setString(1, question.getQuestionContent());
                stment.setInt(2, question.getSubjectID());
                stment.setBoolean(3, true);
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(4, now);
                String id = UUID.randomUUID().toString();
                stment.setString(5, id);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    for (Answer answer : listAnswer) {
                        sql = "INSERT INTO tbl_answers(question_id,answer_content,is_correct) VALUES(?,?,?)";
                        stment = conn.prepareStatement(sql);
                        stment.setString(1, id);
                        stment.setString(2, answer.getAnswerContent());
                        stment.setBoolean(3, answer.isIsCorrect());
                        stment.executeUpdate();
                    }
                    result = true;
                }
            }

        } catch (Exception e) {
            logger.error("Error at createNewQuestion in AdminDAO:" + e.getMessage());
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
            logger.error("Error at getAllSubjects in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getAllRecordsOfSearchResult(String content, Boolean status, Integer subjectID) throws SQLException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String flag = "0";
                String sql = "SELECT id,question_content,subject_id,status FROM tbl_questions WHERE question_content LIKE ?";
                if (status != null) {
                    sql = sql.concat(" AND status = ?");
                    flag = "1";
                }
                if (subjectID != null) {
                    sql = sql.concat(" AND subject_id = ?");
                    flag = "2";
                }
                stment = conn.prepareStatement(sql);
                switch (flag) {
                    case "0":
                        stment.setString(1, "%" + content + "%");
                        break;
                    case "1":
                        stment.setString(1, "%" + content + "%");
                        stment.setBoolean(2, status);
                        break;
                    case "2":
                        stment.setString(1, "%" + content + "%");
                        if (status != null) {
                            stment.setBoolean(2, status);
                            stment.setInt(3, subjectID);
                        } else {
                            stment.setInt(2, subjectID);
                        }
                        break;
                }
                rs = stment.executeQuery();
                while (rs.next()) {
                    result++;
                }
            }
        } catch (Exception e) {
            logger.error("Error at searchPart1 in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<SearchQuestionDTO> searchPart1(String content, Boolean status, Integer subjectID, Integer page) throws SQLException {
        List<SearchQuestionDTO> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                result = new ArrayList<>();
                String flag = "0";
                String sql = "SELECT id,question_content,subject_id,status FROM tbl_questions WHERE question_content LIKE ?";
                if (status != null) {
                    sql = sql.concat(" AND status = ?");
                    flag = "1";
                }
                if (subjectID != null) {
                    sql = sql.concat(" AND subject_id = ?");
                    flag = "2";
                }
                sql = sql.concat(" ORDER BY question_content ASC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY");
                stment = conn.prepareStatement(sql);
                switch (flag) {
                    case "0":
                        stment.setString(1, "%" + content + "%");
                        stment.setInt(2, page * 5);
                        break;
                    case "1":
                        stment.setString(1, "%" + content + "%");
                        stment.setBoolean(2, status);
                        stment.setInt(3, page * 5);
                        break;
                    case "2":
                        stment.setString(1, "%" + content + "%");
                        if (status != null) {
                            stment.setBoolean(2, status);
                            stment.setInt(3, subjectID);
                            stment.setInt(4, page * 5);
                        } else {
                            stment.setInt(2, subjectID);
                            stment.setInt(3, page * 5);
                        }
                        break;
                }
                rs = stment.executeQuery();
                if (!rs.isBeforeFirst()) {
                    return null;
                } else {
                    while (rs.next()) {
                        Question question = new Question();
                        question.setId(rs.getString("id"));
                        question.setQuestionContent(rs.getString("question_content"));
                        question.setSubjectID(rs.getInt("subject_id"));
                        question.setStatus(rs.getBoolean("status"));
                        result.add(new SearchQuestionDTO(question, new ArrayList<>()));
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Error at searchPart1 in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Answer> searchPart2(List<String> listQuestionID) throws SQLException {
        List<Answer> result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT question_id,answer_content,is_correct FROM tbl_answers WHERE question_id = ?";
                stment = conn.prepareStatement(sql);
                result = new ArrayList<>();
                for (String questionID : listQuestionID) {
                    stment.setString(1, questionID);
                    rs = stment.executeQuery();
                    while (rs.next()) {
                        Answer answer = new Answer();
                        answer.setQuestionID(rs.getString("question_id"));
                        answer.setIsCorrect(rs.getBoolean("is_correct"));
                        answer.setAnswerContent(rs.getString("answer_content"));
                        result.add(answer);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at searchPart2 in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deleteQuestion(String id) throws SQLException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_questions SET status = 'false',update_date = ? WHERE id = ?";
                stment = conn.prepareStatement(sql);
                Date now = Date.valueOf(LocalDate.now());
                stment.setDate(1, now);
                stment.setString(2, id);
                int flag = stment.executeUpdate();
                if (flag > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error at deleteQuestion in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public SearchQuestionDTO getQuestionAndAnswersByQuestionID(String questionID) throws SQLException {
        SearchQuestionDTO result = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT question_content,subject_id FROM tbl_questions WHERE id = ?";
                stment = conn.prepareStatement(sql);
                stment.setString(1, questionID);
                rs = stment.executeQuery();
                if (rs.next()) {
                    Question question = new Question(rs.getString("question_content"), rs.getInt("subject_id"));
                    result = new SearchQuestionDTO();
                    result.setQuestion(question);
                    sql = "SELECT answer_content,is_correct,id FROM tbl_answers WHERE question_id = ?";
                    stment = conn.prepareStatement(sql);
                    stment.setString(1, questionID);
                    rs = stment.executeQuery();
                    List<Answer> listAnswer = new ArrayList<>(4);
                    while (rs.next()) {
                        Answer answer = new Answer(rs.getString("answer_content"), rs.getBoolean("is_correct"));
                        answer.setId(rs.getInt("id"));
                        listAnswer.add(answer);
                    }
                    if (!listAnswer.isEmpty()) {
                        result.setListAnswers(listAnswer);
                    } else {
                        result = null;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error at getQuestionAndAnswersByQuestionID in AdminDAO:" + e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

}
