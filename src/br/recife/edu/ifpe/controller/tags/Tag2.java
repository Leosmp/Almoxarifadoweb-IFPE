package br.recife.edu.ifpe.controller.tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Tag2  extends SimpleTagSupport{
	
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		super.doTag();
		
		StringWriter tagBody = new StringWriter();
		
		getJspBody().invoke(tagBody);
		getJspContext().getOut().write("<h1>" +tagBody.toString() +"</h1>");
	}

}
