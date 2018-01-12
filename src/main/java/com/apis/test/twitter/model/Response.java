package com.apis.test.twitter.model;

import java.io.Serializable;

public abstract class Response implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7749280203775963511L;

    private Long timestamp;

    private Integer statusCode;

    private Long took;

    public Response(Integer statusCode) {
	this.timestamp = System.currentTimeMillis();
	this.statusCode = statusCode;
    }

    public Long getTimestamp()
    {
	return timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
	this.timestamp = timestamp;
    }

    public Integer getStatusCode()
    {
	return statusCode;
    }

    public void setStatusCode(Integer statusCode)
    {
	this.statusCode = statusCode;
    }

    public Long getTook()
    {
	return took;
    }

    public void setTook(Long took)
    {
	this.took = took;
    }
}
