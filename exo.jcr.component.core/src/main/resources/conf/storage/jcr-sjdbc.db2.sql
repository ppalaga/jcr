CREATE TABLE JCR_SITEM(
	ID VARCHAR(96) NOT NULL,
	PARENT_ID VARCHAR(96) NOT NULL,
	NAME VARCHAR(512) NOT NULL,
	VERSION INTEGER NOT NULL,
	CONTAINER_NAME VARCHAR(96) NOT NULL,
	I_CLASS INTEGER NOT NULL,
	I_INDEX INTEGER NOT NULL,
	N_ORDER_NUM INTEGER,
	P_TYPE INTEGER, 
	P_MULTIVALUED INTEGER,	
	CONSTRAINT JCR_PK_SITEM PRIMARY KEY(ID),
	CONSTRAINT JCR_FK_SITEM_PAREN FOREIGN KEY(PARENT_ID) REFERENCES JCR_SITEM(ID)
);
CREATE UNIQUE INDEX JCR_IDX_SITEM_PARENT ON JCR_SITEM(CONTAINER_NAME, PARENT_ID, NAME, I_INDEX, I_CLASS, VERSION DESC);
CREATE UNIQUE INDEX JCR_IDX_SITEM_PARENT_ID ON JCR_SITEM(I_CLASS, CONTAINER_NAME, PARENT_ID, ID, VERSION DESC);
CREATE INDEX JCR_IDX_SITEM_PARENT_FK ON JCR_SITEM(PARENT_ID);
CREATE INDEX JCR_IDX_SITEM_N_ORDER_NUM ON JCR_SITEM(I_CLASS, CONTAINER_NAME, PARENT_ID, N_ORDER_NUM);
CREATE TABLE JCR_SVALUE(
	ID BIGINT generated by default as identity (START WITH 2, INCREMENT BY 1) NOT NULL, 
	DATA BLOB(2G) NOT LOGGED COMPACT,
	ORDER_NUM INTEGER NOT NULL,
	PROPERTY_ID VARCHAR(96) NOT NULL,
	STORAGE_DESC VARCHAR(512),
	CONSTRAINT JCR_PK_SVALUE PRIMARY KEY(ID),
	CONSTRAINT JCR_FK_SVALUE_PROP FOREIGN KEY(PROPERTY_ID) REFERENCES JCR_SITEM(ID)
);
CREATE UNIQUE INDEX JCR_IDX_SVALUE_PROPERTY ON JCR_SVALUE(PROPERTY_ID, ORDER_NUM);
CREATE TABLE JCR_SREF(
  NODE_ID VARCHAR(96) NOT NULL,
  PROPERTY_ID VARCHAR(96) NOT NULL,
  ORDER_NUM INTEGER NOT NULL,
  CONSTRAINT JCR_PK_SREF PRIMARY KEY(NODE_ID, PROPERTY_ID, ORDER_NUM)
);
CREATE UNIQUE INDEX JCR_IDX_SREF_PROPERTY ON JCR_SREF(PROPERTY_ID, ORDER_NUM);