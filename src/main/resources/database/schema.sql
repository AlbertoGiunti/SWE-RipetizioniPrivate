-- Schema that represents the database structure
-- Syntax: SQLite

-- Drop tables if they already exist
DROP TABLE IF EXISTS tutors;
DROP TABLE IF EXISTS advertisements;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS ads;

-- Table: tutors
CREATE TABLE IF NOT EXISTS tutors
(
    cf TEXT PRIMARY KEY,
    name        TEXT NOT NULL,
    surname     TEXT NOT NULL,
    iban        TEXT NOT NULL
);

-- Table: students
CREATE TABLE IF NOT EXISTS students
(
    cf TEXT PRIMARY KEY,
    name        TEXT NOT NULL,
    surname     TEXT NOT NULL,
    level       TEXT NOT NULL
);

-- Table: advertisements
CREATE TABLE IF NOT EXISTS advertisements
(
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    tutorCF         TEXT NOT NULL,
    studentCF       TEXT,
    title           TEXT NOT NULL,
    description     TEXT,
    subject         TEXT NOT NULL,
    level           TEXT NOT NULL,
    date            TEXT NOT NULL,
    startTime       TEXT NOT NULL,
    endTime         TEXT NOT NULL,
    zone            TEXT,
    isOnline        INTEGER NOT NULL DEFAULT 0,
    price           FLOAT NOT NULL CHECK(price >= 0),
    FOREIGN KEY (tutorCF) REFERENCES tutors (cf) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: lessons
CREATE TABLE IF NOT EXISTS lessons
(
    adID        INTEGER,
    studentCF   TEXT NOT NULL,
    PRIMARY KEY (adID, studentCF),
    FOREIGN KEY (studentCF) REFERENCES students (cf) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (adID) REFERENCES ads (id) ON UPDATE CASCADE ON DELETE CASCADE
);
