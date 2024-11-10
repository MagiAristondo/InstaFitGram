/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.instafitgram3.dto;

import java.sql.Date;

/**
 *
 * @author giari
 */
public class Attempt {

    private int id;
    private int idUser;
    private int idExercise;
    private Date timeStampStart;
    private Date timeStampEnd;
    private String videoFile;
    private String exercise;

    public Attempt(int id, int idUser, int idExercise, Date timeStampStart, Date timeStampEnd, String videoFile, String exercise) {
        this.id = id;
        this.idUser = idUser;
        this.idExercise = idExercise;
        this.timeStampStart = timeStampStart;
        this.timeStampEnd = timeStampEnd;
        this.videoFile = videoFile;
        this.exercise = exercise;
    }

    public Attempt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public Date getTimeStampStart() {
        return timeStampStart;
    }

    public void setTimeStampStart(Date timeStampStart) {
        this.timeStampStart = timeStampStart;
    }

    public Date getTimeStampEnd() {
        return timeStampEnd;
    }

    public void setTimeStampEnd(Date timeStampEnd) {
        this.timeStampEnd = timeStampEnd;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    @Override
    public String toString() {
        return ""+id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
