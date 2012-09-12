/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.webui.dto;

public class MailDTO
{
    private String text;
    private String template; 
    private Integer submitter;
    private String rp;
    private String subject;
    
    public String getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public String getTemplate()
    {
        return template;
    }
    public void setTemplate(String template)
    {
        this.template = template;
    }
    public String getRp()
    {
        return rp;
    }
    public void setRp(String rp)
    {
        this.rp = rp;
    }
    public Integer getSubmitter()
    {
        return submitter;
    }
    public void setSubmitter(Integer submitter)
    {
        this.submitter = submitter;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }

}
