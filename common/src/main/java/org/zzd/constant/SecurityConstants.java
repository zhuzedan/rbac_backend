package org.zzd.constant;

public class SecurityConstants {
  public static final String SECRET_KEY = "zzd";
  public static final long EXPIRATION_TIME = 24 * 60 * 60 *1000L; //有效期24h
  public static final String TOKEN_PREFIX = "Bearer";
  public static final String HEADER_STRING = "Authorization";
}