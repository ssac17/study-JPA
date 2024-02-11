package com;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "account_tags", schema = "public", catalog = "testdb")
@jakarta.persistence.IdClass(com.AccountTagsEntityPK.class)
public class AccountTagsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "account_id")
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "tags_id")
    private long tagsId;

    public long getTagsId() {
        return tagsId;
    }

    public void setTagsId(long tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTagsEntity that = (AccountTagsEntity) o;
        return accountId == that.accountId && tagsId == that.tagsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, tagsId);
    }
}
