package com.address.book.addressbookapi.bulkdatasave;

import com.address.book.addressbookapi.entity.ContactEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


@Component
public class JdbcTemplateBulkOperations implements BulkOperations {

    private final JdbcTemplate jdbcTemplate;
//    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateBulkOperations.class);


    public JdbcTemplateBulkOperations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void bulkPersist(ArrayList<ContactEntity> entityList) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
        String query = "insert into Contact (contact_id, first_name, last_name, email_address, is_active, created_by, created_date, updated_by, updated_date) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ContactEntity contactEntity = entityList.get(i);
                ps.setLong(1, contactEntity.getContactId());
                ps.setString(2, contactEntity.getFirstName());
                ps.setString(3, contactEntity.getLastName());
                ps.setString(4, contactEntity.getEmailAddress());
                ps.setString(5, contactEntity.getIsActive());
                ps.setString(6, contactEntity.getCreatedBy());
                ps.setDate(7, (Date) contactEntity.getCreatedDate());
                ps.setString(8, contactEntity.getUpdatedBy());
                ps.setDate(9, (Date) contactEntity.getUpdatedDate());

            }

            @Override
            public int getBatchSize() {
                return entityList.size();
            }

        });
//
//        stopWatch.stop();
//        logger.info("batchInsert -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());

    }
}
