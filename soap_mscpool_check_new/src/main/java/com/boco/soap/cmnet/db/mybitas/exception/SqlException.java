package com.boco.soap.cmnet.db.mybitas.exception;

public class SqlException extends Throwable {
    private static final long serialVersionUID = 2067479798662738518L;
    private String dataBase;
    private String errorMessage;

    public SqlException(String errorMessage, Throwable e)
    {
        super(errorMessage, e);
        this.errorMessage = errorMessage;
        this.errorMessage = errorMessage;
    }

    public SqlException(Throwable e) {
        super(e);
    }

    public SqlException(String dataBase, String errorMessage, Throwable e)
    {
        super("数据库[" + dataBase + "]出现异常，异常信息为：" + errorMessage, e);
        this.dataBase = dataBase;
        this.errorMessage = errorMessage;
    }

    public String getDataBase()
    {
        return this.dataBase;
    }
    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
