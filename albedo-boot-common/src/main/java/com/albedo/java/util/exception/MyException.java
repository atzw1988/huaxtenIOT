package com.albedo.java.util.exception;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/2 11:45
 * 自定义异常类
 */
public class MyException extends Exception {

    private static final long serialVersionUID = 1L;

          /**
       * 错误编码
      */
          private String errorCode;
          /**
        * 消息是否为属性文件中的Key
       */
          private boolean propertiesKey = true;

          /**
        * 构造一个基本异常.
        *
        * @param message
      *            信息描述
       */
          public MyException(String message)
      {
                 super(message);
             }

          /**
        * 构造一个基本异常.
        *
        * @param errorCode
        *            错误编码
        * @param message
        *            信息描述
        */
          public MyException(String errorCode, String message)
      {
                 this(errorCode, message, true);
             }

          /**
        * 构造一个基本异常.
        *
       * @param errorCode
        *            错误编码
        * @param message
        *            信息描述
        */
          public MyException(String errorCode, String message, Throwable cause)
      {
                 this(errorCode, message, cause, true);
             }

          /**
       * 构造一个基本异常.
       *
        * @param errorCode
        *            错误编码
        * @param message
        *            信息描述
       * @param propertiesKey
        *            消息是否为属性文件中的Key
        */
          public MyException(String errorCode, String message, boolean propertiesKey)
      {
                 super(message);
                 this.setErrorCode(errorCode);
                 this.setPropertiesKey(propertiesKey);
             }

          /**
        * 构造一个基本异常.
        *
        * @param errorCode
       *            错误编码
       * @param message
        *            信息描述
        */
          public MyException(String errorCode, String message, Throwable cause, boolean propertiesKey)
      {
                 super(message, cause);
                 this.setErrorCode(errorCode);
                 this.setPropertiesKey(propertiesKey);
             }

          /**
        * 构造一个基本异常.
        *
        * @param message
        *            信息描述
        * @param cause
        *            根异常类（可以存入任何异常）
        */
          public MyException(String message, Throwable cause)
     {
                 super(message, cause);
             }

         public String getErrorCode()
     {
                 return errorCode;
             }

         public void setErrorCode(String errorCode)
     {
                 this.errorCode = errorCode;
             }

         public boolean isPropertiesKey()
     {
                 return propertiesKey;
             }

         public void setPropertiesKey(boolean propertiesKey)
     {
                 this.propertiesKey = propertiesKey;
             }

}
