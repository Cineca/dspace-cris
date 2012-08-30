/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.validator;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.service.ApplicationService;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ResearcherPageValidator implements Validator
{

    private Class clazz;

    private ApplicationService applicationService;
    
	private long imageMaxSize;
    
    private long cvMaxSize;
    
    public boolean supports(Class arg0)
    {
        return clazz.isAssignableFrom(arg0);
    }

    public void validate(Object arg0, Errors arg1)
    {
        ResearcherPage researcher = (ResearcherPage) arg0;

        ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "staffNo",
                "error.staffNo.mandatory", "StaffNo is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "fullName",
                "error.fullName.mandatory", "FullName is mandatory");

        if (researcher.getPict().getFile() != null && 
        		researcher.getPict().getFile().getSize() > imageMaxSize)
        {
        	arg1.reject("pict.file", "Picture file too big");
        }
        
        if (researcher.getCv().getFile() != null && 
        		researcher.getCv().getFile().getSize() > cvMaxSize)
        {
        	arg1.reject("cv.file", "CV file too big");
        }
        
        String staffNo = researcher.getStaffNo();
        if (staffNo!=null)
        {
            ResearcherPage temp = applicationService
                    .getResearcherPageByStaffNo(staffNo);
            if (temp != null)
            {
                if (!researcher.getId().equals(temp.getId()))
                {
                    arg1.reject("staffNo",
                            "Staff No is already in use by another researcher");
                }
            }
        }
    }

    public void setClazz(Class clazz)
    {
        this.clazz = clazz;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }
    
    public long getImageMaxSize() {
		return imageMaxSize;
	}

	public void setImageMaxSize(long imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}

	public long getCvMaxSize() {
		return cvMaxSize;
	}

	public void setCvMaxSize(long cvMaxSize) {
		this.cvMaxSize = cvMaxSize;
	}
}
