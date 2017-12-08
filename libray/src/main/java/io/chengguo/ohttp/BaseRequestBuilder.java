package io.chengguo.ohttp;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static io.chengguo.ohttp.Utils.HOSTNAME_VERIFIER;
import static io.chengguo.ohttp.Utils.getDefaultSSLSocketFactory;

/**
 * @author FingerArt http://fingerart.me
 * @date 2017年05月18日 15:07
 */
public abstract class BaseRequestBuilder<T> {
    protected String url;
    protected Map<String, String> queries;
    protected Map<String, String> headers;
    protected Map<String, String> cookies;
    protected SSLSocketFactory sslSocketFactory;
    protected HostnameVerifier hostnameVerifier;

    /**
     * 设置Url
     *
     * @return
     */
    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    /**
     * 添加Query参数
     *
     * @return
     */
    public T addQuery(String key, String value) {
        prepareQueries();
        queries.put(key, value);
        return (T) this;
    }

    /**
     * 添加Query参数
     *
     * @return
     */
    public T addQuery(Map<String, String> querys) {
        prepareQueries();
        this.queries.putAll(querys);
        return (T) this;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, String value) {
        prepareHeaders();
        headers.put(key, value);
        return (T) this;
    }

    /**
     * 添加请求头
     *
     * @param headers
     * @return
     */
    public T addHeader(Map<String, String> headers) {
        prepareHeaders();
        this.headers.putAll(headers);
        return (T) this;
    }

    /**
     * 添加cookie
     *
     * @param key
     * @param value
     * @return
     */
    public T addCookie(String key, String value) {
        prepareCookies();
        cookies.put(key, value);
        return ((T) this);
    }

    /**
     * 添加cookie
     *
     * @param cookies
     * @return
     */
    public T addCookie(Map<String, String> cookies) {
        prepareCookies();
        this.cookies.putAll(cookies);
        return ((T) this);
    }

    public T ssl(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return ((T) this);
    }

    public T ssl(SSLSocketFactory sslSocketFactory, HostnameVerifier hostnameVerifier) {
        ssl(sslSocketFactory);
        this.hostnameVerifier = hostnameVerifier;
        return ((T) this);
    }

    /**
     * 构建请求
     *
     * @return
     */
    public abstract IRequest build();

    protected synchronized void prepareQueries() {
        if (queries == null) {
            queries = new LinkedHashMap<>();
        }
    }

    private synchronized void prepareHeaders() {
        if (headers == null) {
            headers = new LinkedHashMap<>();
        }
    }

    private void prepareCookies() {
        if (cookies == null) {
            cookies = new LinkedHashMap<>();
        }
    }
}
