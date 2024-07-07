package org.prayertime.repository;

import org.junit.jupiter.api.Test;
import org.prayertime.config.AppConfig;
import org.prayertime.config.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseInitializerTest {
    private final AppConfig appConfig = AppConfig.appConfigFactoryMethod("jdbc:sqlite::memory:");
    DataSource dataSource = DataSourceConfig.createDataSource(30000, 10000, 250, 1, 1,
            "org.sqlite.JDBC", "", "", "jdbc:sqlite::memory:");
    DatabaseInitializer databaseInitializer = new DatabaseInitializer(appConfig, dataSource);

    @Test
    void cityTableIsInitializedCorrectly() throws ClassNotFoundException, SQLException {

        databaseInitializer.initializeDatabase();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, "CITY", null)) {
                isColumnInTable(metaData, "CITY", "cityId", "INTEGER");
                isColumnInTable(metaData, "CITY", "cityName", "VARCHAR");
                isColumnInTable(metaData, "CITY", "cityId", "INTEGER");
            }
        }
    }

    @Test
    void countryTableIsInitializedCorrectly() throws ClassNotFoundException, SQLException {

        databaseInitializer.initializeDatabase();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, "DailyContent", null)) {
                isColumnInTable(metaData, "COUNTRY", "countryId", "INTEGER");
                isColumnInTable(metaData, "COUNTRY", "countryName", "VARCHAR");
            }
        }
    }

    @Test
    void DailyContentTableIsInitializedCorrectly() throws ClassNotFoundException, SQLException {

        databaseInitializer.initializeDatabase();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet tables = metaData.getTables(null, null, "DAILYCONTENT", null)) {
                isColumnInTable(metaData, "DAILYCONTENT", "id", "INTEGER");
                isColumnInTable(metaData, "DAILYCONTENT", "dateId", "INTEGER");
                isColumnInTable(metaData, "DAILYCONTENT", "verse", "VARCHAR");
                isColumnInTable(metaData, "DAILYCONTENT", "verseSource", "VARCHAR");
                isColumnInTable(metaData, "DAILYCONTENT", "hadith", "VARCHAR");
                isColumnInTable(metaData, "DAILYCONTENT", "hadithSource", "VARCHAR");
                isColumnInTable(metaData, "DAILYCONTENT", "pray", "VARCHAR");
                isColumnInTable(metaData, "DAILYCONTENT", "prayerSource", "VARCHAR");
            }
        }
    }

    @Test
    public void DayTableIsInitializedCorrectly() throws ClassNotFoundException, SQLException {
        databaseInitializer.initializeDatabase();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, "DAY", null)) {
                isColumnInTable(metaData, "DAY", "dateId", "INTEGER");
                isColumnInTable(metaData, "DAY", "cityId", "INTEGER");
                isColumnInTable(metaData, "DAY", "fajr", "INTEGER");
                isColumnInTable(metaData, "DAY", "asr", "INTEGER");
                isColumnInTable(metaData, "DAY", "dhuhr", "INTEGER");
                isColumnInTable(metaData, "DAY", "maghrib", "INTEGER");
                isColumnInTable(metaData, "DAY", "isha", "INTEGER");
                isColumnInTable(metaData, "DAY", "sunrise", "INTEGER");
            }
        }
    }

    public void isColumnInTable(DatabaseMetaData metaData, String expectedTableName, String expectedColumnName, String expectedType) throws SQLException {
        try (ResultSet dateIdColumn = metaData.getColumns(null, null, expectedTableName, expectedColumnName)) {
            assertTrue(dateIdColumn.next(), "Day table should have " + expectedColumnName + " column");
            assertEquals(expectedType, dateIdColumn.getString("TYPE_NAME"), expectedColumnName + " should be of type " + expectedType);
        }
    }
}