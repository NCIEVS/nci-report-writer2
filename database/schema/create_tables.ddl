CREATE TABLE report_template
(
  id                 INT NOT NULL AUTO_INCREMENT,
  association        VARCHAR(255) NOT NULL,
  level              INT,
  name               VARCHAR(255) NOT NULL,
  root_concept_code  VARCHAR(255) NOT NULL,
  sort_column        INT,
  status             VARCHAR(20) NOT NULL,
  type               VARCHAR(20) NOT NULL,
  date_created       DATETIME NOT NULL,
  date_last_updated  DATETIME NOT NULL,
  created_by         VARCHAR(20) NOT NULL,
  last_updated_by    VARCHAR(20) NOT NULL,

  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE report_template_column
(
  id                    INT NOT NULL AUTO_INCREMENT,
  column_number         INT NOT NULL,
  label                 VARCHAR(255) NOT NULL,
  display               VARCHAR(100) NOT NULL,
  property_type         VARCHAR(100) NOT NULL,
  property              VARCHAR(255),
  source                VARCHAR(255),
  source_group          VARCHAR(255),
  subsource             VARCHAR(255),
  attr                  VARCHAR(255),
  report_template_id    INT NOT NULL,
  date_created          DATETIME NOT NULL,
  date_last_updated     DATETIME NOT NULL,
  created_by            VARCHAR(20) NOT NULL,
  last_updated_by       VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE report_template_concept_list
(
    id                   INT NOT NULL AUTO_INCREMENT,
    report_template_id   INT NOT NULL,
    concept_code         VARCHAR(20) NOT NULL,
    date_created          DATETIME NOT NULL,
    date_last_updated     DATETIME NOT NULL,
    created_by            VARCHAR(20) NOT NULL,
    last_updated_by       VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE report_task
(
  id                    INT NOT NULL AUTO_INCREMENT,
  report_template_id    INT NOT NULL,
  status                VARCHAR(20),
  date_started          DATETIME,
  date_completed        DATETIME,
  date_created          DATETIME NOT NULL,
  date_last_updated     DATETIME NOT NULL,
  created_by            VARCHAR(20) NOT NULL,
  last_updated_by       VARCHAR(20) NOT NULL,
  version               VARCHAR(20) NULL,
  graph_name            VARCHAR(2000) NULL,
  database_url          VARCHAR(2000) NULL,
  database_type         VARCHAR(200) NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_report_status
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_report_template_status
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_report_template_type
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_association
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_display
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_property_type
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_source
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_group
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_subsource
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_property
(
  code  VARCHAR(20) NOT NULL,
  label VARCHAR(200),
  PRIMARY KEY (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;

CREATE TABLE lk_attr
(
  name  VARCHAR(40) NOT NULL,
  description VARCHAR(200),
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
;
