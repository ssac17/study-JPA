package com;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "account", schema = "public", catalog = "testdb")
public class AccountEntity {
    @Basic
    @jakarta.persistence.Column(name = "email_verified")
    private boolean emailVerified;

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_created_by_email")
    private boolean studyCreatedByEmail;

    public boolean isStudyCreatedByEmail() {
        return studyCreatedByEmail;
    }

    public void setStudyCreatedByEmail(boolean studyCreatedByEmail) {
        this.studyCreatedByEmail = studyCreatedByEmail;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_created_by_web")
    private boolean studyCreatedByWeb;

    public boolean isStudyCreatedByWeb() {
        return studyCreatedByWeb;
    }

    public void setStudyCreatedByWeb(boolean studyCreatedByWeb) {
        this.studyCreatedByWeb = studyCreatedByWeb;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_enrollment_result_by_email")
    private boolean studyEnrollmentResultByEmail;

    public boolean isStudyEnrollmentResultByEmail() {
        return studyEnrollmentResultByEmail;
    }

    public void setStudyEnrollmentResultByEmail(boolean studyEnrollmentResultByEmail) {
        this.studyEnrollmentResultByEmail = studyEnrollmentResultByEmail;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_enrollment_result_by_web")
    private boolean studyEnrollmentResultByWeb;

    public boolean isStudyEnrollmentResultByWeb() {
        return studyEnrollmentResultByWeb;
    }

    public void setStudyEnrollmentResultByWeb(boolean studyEnrollmentResultByWeb) {
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_updated_by_email")
    private boolean studyUpdatedByEmail;

    public boolean isStudyUpdatedByEmail() {
        return studyUpdatedByEmail;
    }

    public void setStudyUpdatedByEmail(boolean studyUpdatedByEmail) {
        this.studyUpdatedByEmail = studyUpdatedByEmail;
    }

    @Basic
    @jakarta.persistence.Column(name = "study_updated_by_web")
    private boolean studyUpdatedByWeb;

    public boolean isStudyUpdatedByWeb() {
        return studyUpdatedByWeb;
    }

    public void setStudyUpdatedByWeb(boolean studyUpdatedByWeb) {
        this.studyUpdatedByWeb = studyUpdatedByWeb;
    }

    @Basic
    @jakarta.persistence.Column(name = "email_check_token_generated_at")
    private Timestamp emailCheckTokenGeneratedAt;

    public Timestamp getEmailCheckTokenGeneratedAt() {
        return emailCheckTokenGeneratedAt;
    }

    public void setEmailCheckTokenGeneratedAt(Timestamp emailCheckTokenGeneratedAt) {
        this.emailCheckTokenGeneratedAt = emailCheckTokenGeneratedAt;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "joined_at")
    private Timestamp joinedAt;

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Basic
    @Column(name = "bio")
    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Basic
    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "email_check_token")
    private String emailCheckToken;

    public String getEmailCheckToken() {
        return emailCheckToken;
    }

    public void setEmailCheckToken(String emailCheckToken) {
        this.emailCheckToken = emailCheckToken;
    }

    @Basic
    @Column(name = "location")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "nickname")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "occupation")
    private String occupation;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Basic
    @Column(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "profile_image")
    private byte[] profileImage;

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return emailVerified == that.emailVerified && studyCreatedByEmail == that.studyCreatedByEmail && studyCreatedByWeb == that.studyCreatedByWeb && studyEnrollmentResultByEmail == that.studyEnrollmentResultByEmail && studyEnrollmentResultByWeb == that.studyEnrollmentResultByWeb && studyUpdatedByEmail == that.studyUpdatedByEmail && studyUpdatedByWeb == that.studyUpdatedByWeb && id == that.id && Objects.equals(emailCheckTokenGeneratedAt, that.emailCheckTokenGeneratedAt) && Objects.equals(joinedAt, that.joinedAt) && Objects.equals(bio, that.bio) && Objects.equals(email, that.email) && Objects.equals(emailCheckToken, that.emailCheckToken) && Objects.equals(location, that.location) && Objects.equals(nickname, that.nickname) && Objects.equals(occupation, that.occupation) && Objects.equals(password, that.password) && Objects.equals(url, that.url) && Arrays.equals(profileImage, that.profileImage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(emailVerified, studyCreatedByEmail, studyCreatedByWeb, studyEnrollmentResultByEmail, studyEnrollmentResultByWeb, studyUpdatedByEmail, studyUpdatedByWeb, emailCheckTokenGeneratedAt, id, joinedAt, bio, email, emailCheckToken, location, nickname, occupation, password, url);
        result = 31 * result + Arrays.hashCode(profileImage);
        return result;
    }
}
