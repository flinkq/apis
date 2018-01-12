package com.apis.test.twitter.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apis.test.twitter.model.ResponseDTO;

import redis.clients.jedis.Jedis;

@RestController
@CrossOrigin
public class RedisControllerImpl
{
    @GetMapping(value = "/data/latest")
    @ResponseBody
    public ResponseDTO readLatest()
    {
	long startTime = System.currentTimeMillis();
	Jedis jedis = new Jedis();

	Map<Date, String> subset = new TreeMap<Date, String>(Comparator.reverseOrder());
	List<String> results = jedis.lrange("sm:flink:tweets:tmc", 0, 15);
	results.stream().map(ss -> toJson(ss))
	.filter(ss -> ss != null)
	.forEach(ss -> subset.put(setCreatedAt(ss), ss.optJSONObject("user").optString("name") + ": " + ss.optString("text")));
	ResponseDTO dto = new ResponseDTO(200, subset);
	jedis.close();
	long endTime = System.currentTimeMillis();
	System.out.println(Thread.currentThread() + " - Took : [" + (endTime - startTime) + "]");
	dto.setTook(endTime - startTime);
	return dto;
    }

    private Date setCreatedAt(JSONObject jsonResponse)
    {
	try
	{
	    String cleanTimeFormat = jsonResponse.getString("created_at");
	    final String TWITTER = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	    SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
	    sf.setLenient(true);
	    return sf.parse(cleanTimeFormat);
	} catch (Exception e)
	{
	    return (new Timestamp(0));
	}
    }
    
    private JSONObject toJson(String ss)
    {
	try
	{
	    return new JSONObject(ss);
	} catch (JSONException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
