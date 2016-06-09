package com.biousco.xuehu.Cgi;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by Biousco on 6/9.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class CommonResponse {
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return test;
    }
}
