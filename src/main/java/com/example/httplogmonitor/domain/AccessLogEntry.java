package com.example.httplogmonitor.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Access log entry pojo object. Sample format is:
 *
 * 180.193.30.81 - - [11/Nov/2010:03:32:39 +0000] "GET /resources/J703.html
 * HTTP/1.1" 200 21984 "http://www.wellho.net/course/sjfull.html" "Mozilla/5.0
 * (Windows; U; Windows NT 5.2; en-US; rv:1.9.0.19) Gecko/2010031422
 * Firefox/3.0.19"
 *
 * @author arakhimoff@gmail.com
 */
@Entity
@Table(name = "ACCESSLOGENTRY")
public class AccessLogEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String host;
    private String time;
    private String request;
    private String method;

    @Column(name = "URL")
    private String url;

    @Column(name = "STATUS")
    private int status;
    private int size;
    private String referer;
    private String userAgent;

    @Column(name = "CREATETIME")
    private String createTime;

    public AccessLogEntry() {}

    public AccessLogEntry(String line) {
        StringTokenizer splitter = new StringTokenizer(line," \t");
        String skip;

        this.host = splitter.nextToken();
        skip = splitter.nextToken();
        skip = splitter.nextToken("[");
        this.time = splitter.nextToken(" \t");
        if(this.time.charAt(0) == '[') {
            this.time = this.time.substring(1);
        }
        skip = splitter.nextToken("\"");

        this.request = splitter.nextToken();
        skip = splitter.nextToken(" \t");

        status = Integer.parseInt(splitter.nextToken(" \t"));

        try {
            size = Integer.parseInt(splitter.nextToken(" \t"));
        } catch (Exception e) {
            size = 0;
        }

        skip = splitter.nextToken("\"");
        this.referer = splitter.nextToken();
        skip = splitter.nextToken();
        this.userAgent = splitter.nextToken();

        // Use another String Tokenizer on the HTTP Request

        splitter = new StringTokenizer(this.request);
        this.method = splitter.nextToken();
        this.url = splitter.nextToken();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AccessLogEntry{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", time='" + time + '\'' +
                ", request='" + request + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", size=" + size +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
