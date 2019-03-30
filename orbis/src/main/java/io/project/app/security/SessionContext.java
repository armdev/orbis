package io.project.app.security;



import io.project.app.domain.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@Data
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SessionContext.class);

    private User user = new User();
   

    public SessionContext() {

    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
       
       
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    

}
