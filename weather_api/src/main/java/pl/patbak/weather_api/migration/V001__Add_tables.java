package pl.patbak.weather_api.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


public class V001__Add_tables extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception{
        JdbcTemplate jdbcTemplate = new JdbcTemplate( new SingleConnectionDataSource(context.getConnection(), true));

        jdbcTemplate.execute("""
            create table requests(
            request_id bigserial primary KEY ,
            latitude double precision,
            longitude double precision,
            invoked_datetime timestamptz
            )    
    """);

    }
}
