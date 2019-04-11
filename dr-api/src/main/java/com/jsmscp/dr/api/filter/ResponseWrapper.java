package com.jsmscp.dr.api.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ResponseWrapper extends HttpServletResponseWrapper {
 
 
    private ByteArrayOutputStream buffer = null;
 
    private ServletOutputStream out = null;
 
    private PrintWriter writer = null;
    
    
	public ResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
        
        buffer = new ByteArrayOutputStream();
        out = new WapperedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, "UTF-8"));
	}
 
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }
    
	@Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }
	
    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }
 
    @Override
    public void reset() {
        buffer.reset();
    }
	
    public String getResponseData(String charset) throws IOException {
        flushBuffer(); //将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
        byte[] bytes = buffer.toByteArray();   
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        } 
 
    }
	
	
    private class WapperedOutputStream extends ServletOutputStream {
 
        private ByteArrayOutputStream bos = null;
 
        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }
 
        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }
 
		@Override
		public boolean isReady() {
			return false;
		}
 
		@Override
		public void setWriteListener(WriteListener listener) {
		}
    }
 
	
}
