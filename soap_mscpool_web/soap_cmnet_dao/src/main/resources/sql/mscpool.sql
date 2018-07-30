/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2018/3/27 18:32:07                           */
/*==============================================================*/


DROP TABLE T_BUSI_DICT CASCADE CONSTRAINTS;

DROP TABLE T_BUSI_DICT_ITEM CASCADE CONSTRAINTS;

DROP TABLE T_MENU CASCADE CONSTRAINTS;

DROP TABLE T_ORDER CASCADE CONSTRAINTS;

DROP TABLE T_ORDER_RESULT CASCADE CONSTRAINTS;

DROP TABLE T_ORDER_REUSLT_DETAIL CASCADE CONSTRAINTS;

DROP TABLE T_BUSI  CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: T_BUSI_DICT                                           */
/*==============================================================*/
CREATE TABLE T_BUSI_DICT 
(
   ID                   INTEGER                 NOT NULL,
   CREATE_TIME          DATE,
   CREATE_USER_ID       INTEGER,
   BUSI_ID              INTEGER,
   IS_DELETE            SMALLINT,
   CONSTRAINT PK_T_BUSI_DICT PRIMARY KEY (ID)
);

COMMENT ON TABLE T_BUSI_DICT IS
'指令字典表';

COMMENT ON COLUMN T_BUSI_DICT.ID IS
'id';

COMMENT ON COLUMN T_BUSI_DICT.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_BUSI_DICT.CREATE_USER_ID IS
'创建人';

COMMENT ON COLUMN T_BUSI_DICT.BUSI_ID IS
'业务id';

COMMENT ON COLUMN T_BUSI_DICT.IS_DELETE IS
'是否删除（0：否，1：是）';

/*==============================================================*/
/* Table: T_BUSI_DICT_ITEM                                      */
/*==============================================================*/
CREATE TABLE T_BUSI_DICT_ITEM 
(
   ID                   INTEGER                 NOT NULL,
   CREATE_TIME          INTEGER,
   CREATE_USER_ID       INTEGER,
   DICT_ID              INTEGER,
   PARAM_NAME           VARCHAR(20),
   SEQ_NO               SMALLINT,
   STD_NAME             VARCHAR(20),
   CUR_NAME             VARCHAR(20),
   CONSTRAINT PK_T_BUSI_DICT_ITEM PRIMARY KEY (ID)
);

COMMENT ON TABLE T_BUSI_DICT_ITEM IS
'指令字典明细';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.ID IS
'id';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.CREATE_USER_ID IS
'创建人';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.DICT_ID IS
'指令字典id';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.PARAM_NAME IS
'参数名称';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.SEQ_NO IS
'顺序';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.STD_NAME IS
'标准列名';

COMMENT ON COLUMN T_BUSI_DICT_ITEM.CUR_NAME IS
'现网列名';

/*==============================================================*/
/* Table: T_MENU                                                */
/*==============================================================*/
CREATE TABLE T_MENU 
(
   ID                   INTEGER                 NOT NULL,
   MENU_NAME            VARCHAR2(50),
   CREATE_TIME          DATE,
   CREATE_USER_ID       INTEGER,
   MODIFY_TIME          DATE,
   MODIFY_USER_ID       INTEGER,
   PARENT_ID            INTEGER,
   IS_DELETE            SMALLINT,
   CONSTRAINT PK_T_MENU PRIMARY KEY (ID)
);

COMMENT ON TABLE T_MENU IS
'菜单表';

COMMENT ON COLUMN T_MENU.ID IS
'id';

COMMENT ON COLUMN T_MENU.MENU_NAME IS
'菜单名称';

COMMENT ON COLUMN T_MENU.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_MENU.CREATE_USER_ID IS
'创建人id';

COMMENT ON COLUMN T_MENU.MODIFY_TIME IS
'修改时间';

COMMENT ON COLUMN T_MENU.MODIFY_USER_ID IS
'修改人id';

COMMENT ON COLUMN T_MENU.PARENT_ID IS
'父节点id';

COMMENT ON COLUMN T_MENU.IS_DELETE IS
'是否删除（0：否，1：是）';

/*==============================================================*/
/* Table: T_ORDER                                               */
/*==============================================================*/
CREATE TABLE T_ORDER 
(
   ID                   INTEGER                 NOT NULL,
   ORDER_NAME           VARCHAR(200),
   CREATE_TIME          DATE,
   CREATE_USER_ID       INTEGER,
   STATUS               SMALLINT,
   BUSI_ID              INTEGER,
   CONSTRAINT PK_T_ORDER PRIMARY KEY (ID)
);

COMMENT ON TABLE T_ORDER IS
'工单表';

COMMENT ON COLUMN T_ORDER.ID IS
'id';

COMMENT ON COLUMN T_ORDER.ORDER_NAME IS
'工单名称';

COMMENT ON COLUMN T_ORDER.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_ORDER.CREATE_USER_ID IS
'创建人';

COMMENT ON COLUMN T_ORDER.STATUS IS
'工单状态';

COMMENT ON COLUMN T_ORDER.BUSI_ID IS
'业务id';

/*==============================================================*/
/* Table: T_ORDER_RESULT                                        */
/*==============================================================*/
CREATE TABLE T_ORDER_RESULT 
(
   ID                   INTEGER                 NOT NULL,
   CREATE_TIME          DATE,
   CREATE_USER_ID       VARCHAR(50),
   RIGHT_NO             INTEGER,
   WRONG_NO             INTEGER,
   LOST_NO              INTEGER,
   MORE_NO              INTEGER,
   ORDER_ID             INTEGER,
   BUSI_ID              INTEGER,
   DICT_ID              INTEGER,
   CUR_TABLE            VARCHAR(200),
   CONSTRAINT PK_T_ORDER_RESULT PRIMARY KEY (ID)
);

COMMENT ON TABLE T_ORDER_RESULT IS
'核查结果表';

COMMENT ON COLUMN T_ORDER_RESULT.ID IS
'id';

COMMENT ON COLUMN T_ORDER_RESULT.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_ORDER_RESULT.CREATE_USER_ID IS
'创建人id';

COMMENT ON COLUMN T_ORDER_RESULT.RIGHT_NO IS
'正确数';

COMMENT ON COLUMN T_ORDER_RESULT.WRONG_NO IS
'错误数';

COMMENT ON COLUMN T_ORDER_RESULT.LOST_NO IS
'漏做数';

COMMENT ON COLUMN T_ORDER_RESULT.MORE_NO IS
'多做数';

COMMENT ON COLUMN T_ORDER_RESULT.ORDER_ID IS
'工单id';

COMMENT ON COLUMN T_ORDER_RESULT.BUSI_ID IS
'业务id';

COMMENT ON COLUMN T_ORDER_RESULT.DICT_ID IS
'指令字典id';

COMMENT ON COLUMN T_ORDER_RESULT.CUR_TABLE IS
'现网表';

/*==============================================================*/
/* Table: T_ORDER_REUSLT_DETAIL                                 */
/*==============================================================*/
CREATE TABLE T_ORDER_REUSLT_DETAIL 
(
   ID                   INTEGER                 NOT NULL,
   ORDER_ID             INTEGER,
   CREATE_TIME          INTEGER,
   CREATE_USER_ID       INTEGER,
   BUSI_ID              INTEGER,
   DICT_ID              INTEGER,
   DICT_PARAM_ID        INTEGER,
   STD_VAL              VARCHAR(200),
   CUR_VAL              VARCHAR(200),
   CONSTRAINT PK_T_ORDER_REUSLT_DETAIL PRIMARY KEY (ID)
);

COMMENT ON TABLE T_ORDER_REUSLT_DETAIL IS
'核查结果明细表';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.ID IS
'id';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.ORDER_ID IS
'工单id';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.CREATE_USER_ID IS
'创建人';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.BUSI_ID IS
'业务id';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.DICT_ID IS
'指令字典id';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.DICT_PARAM_ID IS
'指令参数ID';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.STD_VAL IS
'标准值';

COMMENT ON COLUMN T_ORDER_REUSLT_DETAIL.CUR_VAL IS
'现网值';

/*==============================================================*/
/* Table: T_BUSI                                                */
/*==============================================================*/
CREATE TABLE T_BUSI
(
   ID                   INTEGER              NOT NULL,
   CREATE_TIME          DATE,
   CREATE_USER_ID       INTEGER,
   BUSI_NAME            VARCHAR(50),
   MENU_URL             VARCHAR(500),
   IMG_URL             VARCHAR(500),
   SEQ_NO               SMALLINT,
   IS_DELETE            SMALLINT,
   PROCESS_ID            VARCHAR(50),
   CONSTRAINT PK_T_BUSI PRIMARY KEY (ID)
);

COMMENT ON TABLE T_BUSI IS
'业务表';

COMMENT ON COLUMN T_BUSI.ID IS
'id';

COMMENT ON COLUMN T_BUSI.CREATE_TIME IS
'创建时间';

COMMENT ON COLUMN T_BUSI.CREATE_USER_ID IS
'创建人id';

COMMENT ON COLUMN T_BUSI.BUSI_NAME IS
'业务名称';

COMMENT ON COLUMN T_BUSI.MENU_URL IS
'菜单地址';

COMMENT ON COLUMN T_BUSI.SEQ_NO IS
'排序';

COMMENT ON COLUMN T_BUSI.IS_DELETE IS
'是否删除（0：否，1：是）';




DROP TABLE "T_PROCESS" CASCADE CONSTRAINTS;

DROP TABLE "T_PROCESS_DETAIL" CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: "T_PROCESS"                                           */
/*==============================================================*/
CREATE TABLE "T_PROCESS"
(
   "ID"                 VARCHAR(50),
   "CREATE_TIME"        TIMESTAMP,
   "CREATE_USER_ID"     VARCHAR(50),
   "MODIFY_TIME"        TIMESTAMP,
   "MODIFY_USER_ID"     VARCHAR(50),
   "NAME"               VARCHAR(200),
   "DESCR"              VARCHAR(500)
);

COMMENT ON TABLE "T_PROCESS" IS
'流程表';

COMMENT ON COLUMN "T_PROCESS"."ID" IS
'id';

COMMENT ON COLUMN "T_PROCESS"."CREATE_TIME" IS
'创建时间';

COMMENT ON COLUMN "T_PROCESS"."CREATE_USER_ID" IS
'创建人';

COMMENT ON COLUMN "T_PROCESS"."MODIFY_TIME" IS
'修改时间';

COMMENT ON COLUMN "T_PROCESS"."MODIFY_USER_ID" IS
'修改人';

COMMENT ON COLUMN "T_PROCESS"."NAME" IS
'流程名称';

COMMENT ON COLUMN "T_PROCESS"."DESCR" IS
'描述';

/*==============================================================*/
/* Table: "T_PROCESS_DETAIL"                                    */
/*==============================================================*/
CREATE TABLE "T_PROCESS_DETAIL"
(
   "ID"                 VARCHAR(50),
   "NAME"               VARCHAR(50),
   "PROCESS_ID"         VARCHAR(50),
   MENU_URL             VARCHAR(500),
   "SEQ_NO"             INT
);

COMMENT ON TABLE "T_PROCESS_DETAIL" IS
'流程明细';

COMMENT ON COLUMN "T_PROCESS_DETAIL"."ID" IS
'id';

COMMENT ON COLUMN "T_PROCESS_DETAIL"."NAME" IS
'名称';

COMMENT ON COLUMN "T_PROCESS_DETAIL"."PROCESS_ID" IS
'所属流程id';

COMMENT ON COLUMN "T_PROCESS_DETAIL"."SEQ_NO" IS
'顺序';

