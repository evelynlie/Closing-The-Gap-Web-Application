-- Create tables for Semester 2 2022 CTG example ER Model
PRAGMA foreign_keys = OFF;
drop table if exists LGA;
drop table if exists PopulationStatistics;
PRAGMA foreign_keys = ON;

CREATE TABLE LGA (
    lga_code21   TEXT   NOT NULL,
    lga_state21  TEXT,
    lga_name21   TEXT   NOT NULL,
    lga_type21   CHAR (2),
    area_sqkm21  DOUBLE,
    lga_code16   TEXT,
    lga_state16  TEXT,
    lga_name16   TEXT,
    lga_type16   CHAR (2),
    area_sqkm16  DOUBLE,
    PRIMARY KEY (lga_code21)
);

CREATE TABLE PopulationStatistics (
    lga_code21 INTEGER  NOT NULL,
    status     TEXT (1) NOT NULL,
    sex        TEXT     NOT NULL,
    age_range  TEXT     NOT NULL,
    min_age    INTEGER  NOT NULL,
    max_age    INTEGER  NOT NULL,
    count21    INTEGER,
    count16    INTEGER,
    PRIMARY KEY (lga_code21, sex, status, age_range),
    FOREIGN KEY (lga_code21)
    REFERENCES LGA (lga_code21) 
);

CREATE TABLE LongTermHealth (
    lga_code21 INTEGER  NOT NULL,
    status     TEXT (1) NOT NULL,
    sex        TEXT     NOT NULL,
    condition  TEXT     NOT NULL,
    count21    INTEGER,
    PRIMARY KEY (lga_code21, sex, status, condition),
    FOREIGN KEY (lga_code21)
    REFERENCES LGA (lga_code21) 
);

CREATE TABLE HighestSchoolYear (
    lga_code21 INTEGER  NOT NULL,
    status     TEXT (1) NOT NULL,
    sex        TEXT     NOT NULL,
    education  TEXT     NOT NULL,
    count21    INTEGER,
    count16    INTEGER,
    PRIMARY KEY (lga_code21, sex, status, education),
    FOREIGN KEY (lga_code21)
    REFERENCES LGA (lga_code21) 
);

CREATE TABLE WeeklyIncome (
    lga_code21 INTEGER  NOT NULL,
    status     TEXT (1) NOT NULL,
    income     TEXT     NOT NULL,
    min_income INTEGER,
    max_income INTEGER,
    count21    INTEGER,
    PRIMARY KEY (lga_code21, status, income),
    FOREIGN KEY (lga_code21)
    REFERENCES LGA (lga_code21) 
);

CREATE TABLE PERSONA (
    Name                    VARCHAR (255),
    Age                     INTEGER,
    Ethnicity               VARCHAR (255),
    Background              VARCHAR (500),
    Needs_1                 VARCHAR (500),
    Needs_2                 VARCHAR (500),
    Goals_1                 VARCHAR (500),
    Goals_2                 VARCHAR (500),
    Skills_and_Experience_1 VARCHAR (500),
    Skills_and_Experience_2 VARCHAR (500),
    PRIMARY KEY (Name)
);

--VIEW
CREATE VIEW totalpop AS
    SELECT *, SUM(count21) AS total_lgapop21, 
           SUM(count16) AS total_lgapop16
    FROM PopulationStatistics
    WHERE count16 IS NOT NULL
    GROUP BY lga_code21

CREATE VIEW WeeklyIncomeGS AS
    SELECT w1.lga_code21,
           w1.status,
           w1.income,
           w1.count21 AS indiCount21,
           w1.count16 AS indiCount16,
           w2.status AS other_status,
           w2.count21 AS nonCount21,
           w2.count16 AS nonCount16,
           w2.count21 / w1.count21 AS WIgapScore21,
           w2.count16 / w1.count16 AS WIgapScore16,
           w2.count21 / w1.count21 - w2.count16 / w1.count16 AS WIGapChange
    FROM WeeklyIncome w1
    JOIN WeeklyIncome w2 ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21
    WHERE w1.status = 'at least 1 indigenous member' AND 
           w2.status = 'non-indigenous household' AND 
           w1.count21 <> w2.count21 AND 
           w2.count16 IS NOT NULL AND 
           WIgapScore21 IS NOT NULL AND 
           WIgapScore16 IS NOT NULL;

CREATE VIEW EducationGS AS
    SELECT hsy1.lga_code21,
           hsy1.status,
           hsy1.sex,
           hsy1.education,
           hsy1.count21 AS indiCount21,
           hsy1.count16 AS indiCount16,
           hsy2.status AS other_status,
           hsy2.count21 AS nonCount21,
           hsy2.count16 AS nonCount16,
           hsy2.count21 / hsy1.count21 AS EdugapScore21,
           hsy2.count16 / hsy1.count16 AS EdugapScore16,
           hsy2.count21 / hsy1.count21 - hsy2.count16 / hsy1.count16 AS EduGapChange
    FROM HIGHESTSCHOOLYEAR hsy1
    JOIN HIGHESTSCHOOLYEAR hsy2 ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex
    WHERE hsy1.status = 'indigenous' AND 
           hsy2.status = 'non-indigenous' AND 
           hsy1.count21 <> hsy2.count21 AND 
           hsy2.count16 IS NOT NULL AND 
           EdugapScore21 IS NOT NULL AND 
           EdugapScore16 IS NOT NULL;

CREATE VIEW HealthGS AS
    SELECT lth1.lga_code21,
           lth1.status,
           lth1.sex,
           lth1.condition,
           lth1.count21 AS indiCount21,
           lth2.status AS other_status,
           lth2.condition,
           lth2.count21 AS nonCount21,
           lth2.count21 / lth1.count21 AS HealthgapScore21
    FROM LONGTERMHEALTH lth1
    JOIN LONGTERMHEALTH lth2 ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex
    WHERE lth1.status = 'indigenous' AND 
           lth2.status = 'non-indigenous' AND 
           lth1.count21 <> lth2.count21 AND 
           HealthgapScore21 IS NOT NULL;


