package com.address.book.addressbookapi.bulkdatasave;

import com.address.book.addressbookapi.entity.ContactEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcTemplateBulkOperations implements BulkOperations {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateBulkOperations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void bulkPersist(ArrayList<ContactEntity> entityList) {
        jdbcTemplate.batchUpdate("insert into Contact (contactId) values(?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, entityList.get(i).getContactId());
            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }
        });

    }
}
