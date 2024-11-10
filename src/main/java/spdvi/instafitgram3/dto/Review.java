/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.instafitgram3.dto;

/**
 *
 * @author giari
 */
public class Review {

    private int id;
    private int idAttempt;
    private int idReviewer;
    private int review;
    private String comment;
    private String status;

    public Review() {
        status = "created";
    }

    public Review(int idAttempt, int idReviewer, int review, String comment) {
        this.idAttempt = idAttempt;
        this.idReviewer = idReviewer;
        this.review = review;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReviewer() {
        return idReviewer;
    }

    public void setIdReviewer(int idReviewer) {
        this.idReviewer = idReviewer;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "id " + id + "\nidReviewer " + idReviewer + "\nreview " + review + "\ncomment " + comment;
    }

    public int getIdAttempt() {
        return idAttempt;
    }

    public void setIdAttempt(int idAttempt) {
        this.idAttempt = idAttempt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
