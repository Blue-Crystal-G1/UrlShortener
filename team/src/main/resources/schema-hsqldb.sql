-- Clean database
DROP TABLE ROLES_PRIVILEGES IF EXISTS;
DROP TABLE USERS_ROLES IF EXISTS;
DROP TABLE ROLES IF EXISTS;
DROP TABLE PRIVILEGE IF EXISTS;
DROP TABLE CLICK IF EXISTS;
DROP TABLE SHORTURL IF EXISTS;
DROP TABLE USER IF EXISTS;

-- User
CREATE TABLE USER(
  ID		        BIGINT IDENTITY,                -- Key
  FIRSTNAME		  VARCHAR(1024) NOT NULL,				  -- First name
  LASTNAME      VARCHAR(1024) NOT NULL,         -- Surname
  EMAIL 	      VARCHAR(1024) NOT NULL UNIQUE,  -- Email
  PASSWORD		  VARCHAR(60)   NOT NULL				  -- Password
);

-- Roles
CREATE TABLE ROLES(
  ID		BIGINT IDENTITY,        -- Key
  NAME	VARCHAR(1024) NOT NULL	-- Rol name
);

-- Privileges
CREATE TABLE PRIVILEGE(
  ID		BIGINT IDENTITY,        -- Key
  NAME	VARCHAR(1024) NOT NULL	-- Privilege name
);

-- User roles
CREATE TABLE USERS_ROLES(
  USER_ID 	BIGINT NOT NULL FOREIGN KEY REFERENCES USER(ID),	-- Foreing key
  ROLE_ID 	BIGINT NOT NULL FOREIGN KEY REFERENCES ROLES(ID)	-- Foreing key
);

-- Role privileges
CREATE TABLE ROLES_PRIVILEGES(
  ROLE_ID 	    BIGINT NOT NULL FOREIGN KEY REFERENCES ROLES(ID),	    -- Foreing key
  PRIVILEGE_ID 	BIGINT NOT NULL FOREIGN KEY REFERENCES PRIVILEGE(ID)	-- Foreing key
);

-- ShortURL
CREATE TABLE SHORTURL(
	HASH		                VARCHAR(30) PRIMARY KEY,	-- Key
	TARGET		              VARCHAR(1024),				    -- Original URL
  URI                     VARCHAR(1024),            -- URI
	CREATED 	              TIMESTAMP,					      -- Creation date
	OWNER		                VARCHAR(255),				      -- User id
	IP			                VARCHAR(20),				      -- IP
	COUNTRY		              VARCHAR(50),				      -- Country
  SAFE		                BOOLEAN,					-- Safe target
  LASTCHECKSAFEDATE       TIMESTAMP,       -- Last check if url is safe
  AVAILABLE		            BOOLEAN,					-- Available target
  LASTCHECKAVAILABLEDATE  TIMESTAMP        -- Last check if url is available
);

-- Access to bypass advertising
CREATE TABLE ADVERTISING_ACCESS(
  ID      VARCHAR(36) PRIMARY KEY,
  HASH    VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES SHORTURL(HASH),	      -- Foreing key
  ACCESS  BOOLEAN     NOT NULL   -- Access to uri (0 if advertising already seen, 1 if has access)
);

-- Click
CREATE TABLE CLICK(
  ID 			  BIGINT IDENTITY,		-- KEY
	HASH 		  VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES SHORTURL(HASH),	-- Foreing key
	CREATED 	TIMESTAMP,					-- Creation date
	REFERRER	VARCHAR(1024),			-- Traffic origin
	BROWSER		VARCHAR(50),				-- Browser
	PLATFORM	VARCHAR(50),				-- Platform
	IP			  VARCHAR(20),				-- IP
	COUNTRY		VARCHAR(50)					-- Country
);
