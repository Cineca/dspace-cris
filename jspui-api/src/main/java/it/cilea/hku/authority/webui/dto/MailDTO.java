/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
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
