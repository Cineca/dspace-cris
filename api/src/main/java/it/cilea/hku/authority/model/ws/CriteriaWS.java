/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.ws;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dspace.core.ConfigurationManager;

@Entity
@Table(name = "model_criteriaws")
public class CriteriaWS
{

    @Transient
    private static List<String> CRITERIAS = new LinkedList<String>();

    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRITERIAWS_SEQ")
    @SequenceGenerator(name = "CRITERIAWS_SEQ", sequenceName = "CRITERIAWS_SEQ")
    private Integer id;

    private boolean enabled;

    private String criteria;

    private String filter;

    public void setCriteria(String criteria)
    {
        this.criteria = criteria;
    }

    public String getCriteria()
    {
        return criteria;
    }

    public String getFilter()
    {
        return filter;
    }

    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public static List<String> getCRITERIA()
    {
        if (CRITERIAS.isEmpty())
        {
            String tosplitt[] = ConfigurationManager
            .getProperty("webservices.criteria.type").split(",");
            
            for(int i=0; i<tosplitt.length;i++)
            {
                CRITERIAS.add(ConfigurationManager
                        .getProperty("webservices.criteria.type." + tosplitt[i].trim()));
                
            }
        }
        return CRITERIAS;
    }

}
