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
package it.cilea.hku.authority.model;

import it.cilea.hku.authority.model.ws.CriteriaWS;
import it.cilea.hku.authority.model.ws.TokenIP;
import it.cilea.hku.authority.model.ws.UsernamePassword;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.ITimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.common.model.Identifiable;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "model_userws")
@NamedQueries( {
        @NamedQuery(name = "UserWS.findAll", query = "from UserWS order by id"),
        @NamedQuery(name = "UserWS.count", query = "select count(*) from UserWS"),
        @NamedQuery(name = "UserWS.paginate.id.asc", query = "from UserWS order by id asc"),
        @NamedQuery(name = "UserWS.paginate.id.desc", query = "from UserWS order by id desc"),
        @NamedQuery(name = "UserWS.uniqueByUsernameAndPassword", query = "from UserWS where normalAuth.username = ? AND normalAuth.password = ?"),
        @NamedQuery(name = "UserWS.uniqueByToken", query = "from UserWS where specialAuth.token = ?")        
})
public class UserWS implements Identifiable, HasTimeStampInfo
{
    
    @Transient
    public static final String TYPENORMAL = "normal"; 
    @Transient
    public static final String TYPESPECIAL = "token";
    
    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "USERWS_SEQ")
    @SequenceGenerator(name = "USERWS_SEQ", sequenceName = "USERWS_SEQ")
    private Integer id;
    
    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;
    
    private boolean enabled; 
    
    private String type;
    
    @Embedded
    private UsernamePassword normalAuth;
    
    @Embedded
    private TokenIP specialAuth;   
    
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)    
    private List<CriteriaWS> criteria;
        
    /**
     * Show or hide the hidden metadata
     */
    private boolean showHiddenMetadata = false;
    
    public UserWS()
    {       
        if(this.getType()==null) {
            this.setType(TYPENORMAL);
        }
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public UsernamePassword getNormalAuth()    
    {
        if(this.normalAuth==null) {
            this.normalAuth = new UsernamePassword();
        }
        return normalAuth;
    }

    public void setNormalAuth(UsernamePassword normalAuth)
    {
        this.normalAuth = normalAuth;
    }

    public TokenIP getSpecialAuth()
    {
        if(this.specialAuth==null) {
            this.specialAuth = new TokenIP();
        }
        return specialAuth;
    }

    public void setSpecialAuth(TokenIP specialAuth)
    {
        this.specialAuth = specialAuth;
    }

    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public ITimeStampInfo getTimeStampInfo()
    {
        if (timeStampInfo == null)
        {
            timeStampInfo = new TimeStampInfo();
        }
        return timeStampInfo;
    }

    public void setCriteria(List<CriteriaWS> criteria)
    {
        this.criteria = criteria;
    }

    public List<CriteriaWS> getCriteria()    
    {
        if(this.criteria==null) {
            criteria = new LinkedList<CriteriaWS>();
        }
        return criteria;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getUsername() {
        if(getNormalAuth()!=null) {
            return getNormalAuth().getUsername();
        }
        return "";
    }
    
    public String getPassword() {
        if(getNormalAuth()!=null) {
            return getNormalAuth().getPassword();
        }
        return "";        
    }
    
    public String getToken() {
        if(getSpecialAuth()!=null) {
            return getSpecialAuth().getToken();
        }
        return "";
                
    }
    
    public String getFromIP() {
        if(getSpecialAuth()!=null) {
            return getSpecialAuth().getFromIP();
        }
        return "";       
    }
    
    public String getToIP() {
        if(getSpecialAuth()!=null) {
            return getSpecialAuth().getToIP();
        }
        return "";        
    }

    public void setShowHiddenMetadata(boolean skipRespondeValidation)
    {
        this.showHiddenMetadata = skipRespondeValidation;
    }

    public boolean isShowHiddenMetadata()
    {
        return showHiddenMetadata;
    }
}
