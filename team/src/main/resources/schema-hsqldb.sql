-- Clean database

DROP TABLE CLICK IF EXISTS;
DROP TABLE SHORTURL IF EXISTS;

-- ShortURL

CREATE TABLE SHORTURL(
	HASH		  VARCHAR(30) PRIMARY KEY,	-- Key
	TARGET		VARCHAR(1024),				    -- Original URL
	SPONSOR		VARCHAR(1024),				    -- Sponsor URL
  URI       VARCHAR(1024),            -- URI
	CREATED 	TIMESTAMP,					      -- Creation date
	OWNER		  VARCHAR(255),				      -- User id
	MODE		  INTEGER,					        -- Redirect mode
	IP			  VARCHAR(20),				      -- IP
	COUNTRY		VARCHAR(50),				      -- Country
  SAFE		  BOOLEAN NOT NULL,						      -- Safe target
  LASTCHECKSAFEDATE TIMESTAMP NOT NULL,        -- Last check if url is safe
  AVAILABLE		  BOOLEAN NOT NULL,						  -- Available target
  LASTCHECKAVAILABLEDATE TIMESTAMP NOT NULL    -- Last check if url is available
);

-- Click

CREATE TABLE CLICK(
  ID 			BIGINT IDENTITY,			-- KEY
	HASH 		VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES SHORTURL(HASH),	-- Foreing key
	CREATED 	TIMESTAMP,					-- Creation date
	REFERRER	VARCHAR(1024),				-- Traffic origin
	BROWSER		VARCHAR(50),				-- Browser
	PLATFORM	VARCHAR(50),				-- Platform
	IP			VARCHAR(20),				-- IP
	COUNTRY		VARCHAR(50)					-- Country
)
