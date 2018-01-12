package com.apis.test.twitter.model;

import java.io.Serializable;

public class ResponseDTO extends Response implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 5274083796792973675L;

    private Object data;
    
    public ResponseDTO(Integer statusCode, Object data) {
	super(statusCode);
	this.data = data;
    }

    public Object getData()
    {
	return data;
    }

    public void setData(Object data)
    {
	this.data = data;
    }
}
